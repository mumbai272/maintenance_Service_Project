package com.android.maintenance.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maintenance.DTO.ApplyClaimDTO;
import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.ClaimResposnse;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.asyncTask.GetClaimDetails;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by anand on 15-Nov-16.
 */
public class ApplyCliamActivity extends Activity {

    EditText start,end,part;
    String token;
    Gson gson;
    String client_idStr,company_idStr;
    private SessionManager session;
    String start_Str,end__Str,part_Str;
    Button apply_Claim;
    DatePickerDialog endPickerDialog,startPickerDialog;
    SimpleDateFormat dateFormatter;
    Intent intent;
    Type type;
    ApplyClaimDTO dto;
    GetClaimListDTO claim;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_cliam);

        conven_exp_list=new ArrayList<ClaimConveyanceExpenseDTO>();
        misc_exp_list=new ArrayList<MiscExpenseDTO>();
        business_exp_list=new ArrayList<BusinessDevExpenseDTO>();

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        company_idStr = user.get("KEY_COMPANY_ID");
        client_idStr = user.get("KEY_CLIENT_ID");
        token=user.get(SessionManager.KEY_TOKEN);


        start=(EditText)findViewById(R.id.start_date);
        end=(EditText)findViewById(R.id.end_date);
        part=(EditText)findViewById(R.id.claim_part);

        dateFormatter = new SimpleDateFormat("yyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        apply_Claim=(Button)findViewById(R.id.claim_btn);

        apply_Claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_Str=start.getText().toString();
                end__Str=end.getText().toString();
                part_Str=part.getText().toString();
                if(Utility.isNotNull(part_Str)&& Utility.isNotNull(start_Str)&&  Utility.isNotNull(end__Str)){
                try {
                    String json="";
                    dto= new ApplyClaimDTO();
                    dto.setClaimStartDate(start_Str);
                    dto.setClaimEndDate(end__Str);
                    dto.setParticulars(part_Str);
                    dto.setClaimDate("2016-12-02");
                    ObjectMapper mapper = new ObjectMapper();
                    json = mapper.writeValueAsString(dto);
                    new ApplyCliam().execute(json);

                }catch (Exception e){
                    e.printStackTrace();
                }
                }else{
                    Toast.makeText(getApplicationContext(),"Dont leave any fields blank",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void findViewsById() {

        start.setInputType(InputType.TYPE_NULL);
        start.requestFocus();

        end.setInputType(InputType.TYPE_NULL);
        end.requestFocus();
    }

    private void setDateTimeField() {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPickerDialog.show();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endPickerDialog.show();
            }
        });


        Calendar newCalendar = Calendar.getInstance();
        startPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                start.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        endPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                end.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }



    public class ApplyCliam extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            Log.e("","this input post"+param[0]);
            result= servicepost.makeServicePostWithToken(ConfigConstant.url+"claim/form",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            ClaimResposnse clientResponse=gson.fromJson(result, ClaimResposnse.class);
            if(clientResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
                getData(clientResponse);
            }else{
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void getData(ClaimResposnse clientResponse) {

        GetClaimDetails details= new GetClaimDetails(this);
        details.execute(ConfigConstant.url+"claim/"+clientResponse.getId());
       /* intent=new Intent(ApplyCliamActivity.this,CliamDetailsActivity.class);
        intent.putExtra("CLAIM_ID",clientResponse.getId());
        startActivity(intent);*/
    }

    public void getclaimData(JSONObject claimData, JSONArray businessExpensesData, JSONArray conveyanceExpensesData, JSONArray miscExpenseData) {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String claimStr = claimData.toString();

        claim=new GetClaimListDTO();
        claim=gson.fromJson(claimStr, GetClaimListDTO.class);

        if(businessExpensesData!=null) {
            String businessExpensesStr=businessExpensesData.toString();
            type = new TypeToken<ArrayList<BusinessDevExpenseDTO>>() {
            }.getType();
            business_exp_list = gson.fromJson(businessExpensesStr, type);
        }

        if( conveyanceExpensesData!=null) {
            String conveyanceExpensesStr=conveyanceExpensesData.toString();
            type = new TypeToken<ArrayList<ClaimConveyanceExpenseDTO>>() {
            }.getType();
            conven_exp_list = gson.fromJson(conveyanceExpensesStr, type);
        }
        if( miscExpenseData!=null){
            String miscExpenseStr=miscExpenseData.toString();
            type = new TypeToken<ArrayList<MiscExpenseDTO>>() {
            }.getType();
            misc_exp_list = gson.fromJson(miscExpenseStr, type);
        }

        intent=new Intent(ApplyCliamActivity.this,CliamDetailsActivity.class);
        intent.putExtra("DTO",claim);
        intent.putExtra("business_exp_list",business_exp_list);
        intent.putExtra("conven_exp_list",conven_exp_list);
        intent.putExtra("misc_exp_list",misc_exp_list);
        startActivity(intent);

    }

}
