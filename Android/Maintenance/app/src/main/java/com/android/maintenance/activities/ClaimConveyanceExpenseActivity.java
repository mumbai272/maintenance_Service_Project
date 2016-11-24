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

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.ClaimResposnse;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.android.maintenance.fragments.ConvenceExpense;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by anand on 17-Nov-16.
 */
public class ClaimConveyanceExpenseActivity extends Activity {

    EditText date,t_from,t_to,transport_type,exp_amount;
    Button btn_conve_exp;
    String date_Str,t_from__Str,t_to_Str,transport_type_Str,exp_amount_Str;
    private SessionManager session;
    String token;
    DatePickerDialog startPickerDialog;
    SimpleDateFormat dateFormatter;
    Intent intent;
    Type type;
    Gson gson;
    ClaimConveyanceExpenseDTO dto;
    GetClaimListDTO claim;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;
    Long ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conveyance_expense);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token=user.get(SessionManager.KEY_TOKEN);

        claim= (GetClaimListDTO) getIntent().getSerializableExtra("ClimDTO");
        business_exp_list= (ArrayList<BusinessDevExpenseDTO>) getIntent().getSerializableExtra("business_exp_list");
        misc_exp_list= (ArrayList<MiscExpenseDTO>) getIntent().getSerializableExtra("misc_exp_list");
        conven_exp_list= ( ArrayList<ClaimConveyanceExpenseDTO>) getIntent().getSerializableExtra("conven_exp_list");
        date=(EditText)findViewById(R.id.exp_date);
        t_from=(EditText)findViewById(R.id.tr_from);
        t_to=(EditText)findViewById(R.id.tr_to);
        transport_type=(EditText)findViewById(R.id.mof_transport);
        exp_amount=(EditText)findViewById(R.id.exp_amount);
        btn_conve_exp=(Button)findViewById(R.id.con_expense_btn);

        dateFormatter = new SimpleDateFormat("yyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        btn_conve_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_Str=date.getText().toString();
                t_from__Str=t_from.getText().toString();
                t_to_Str=t_to.getText().toString();
                transport_type_Str=transport_type.getText().toString();
                exp_amount_Str=exp_amount.getText().toString();
                if(Utility.isNotNull(date_Str)&&Utility.isNotNull(t_from__Str)&&Utility.isNotNull(t_to_Str)&&Utility.isNotNull(transport_type_Str)&&Utility.isNotNull(exp_amount_Str)) {
                    try {
                        String json = "";
                        dto= new ClaimConveyanceExpenseDTO();
                        dto.setClaimId(ID);
                        dto.setClaimAmount(Double.parseDouble(exp_amount_Str));
                        dto.setExpenseDate(date_Str);
                        dto.setModeOfTransport(transport_type_Str);
                        dto.setTravelFrom(t_from__Str);
                        dto.setTravelTo(t_to_Str);

                        ObjectMapper mapper = new ObjectMapper();
                        json = mapper.writeValueAsString(dto);
                        new ClaimConveyanceExpense().execute(json);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Dont leave any feilds blank",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void findViewsById() {

        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();

    }

    private void setDateTimeField() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPickerDialog.show();
            }
        });



        Calendar newCalendar = Calendar.getInstance();
        startPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    private class ClaimConveyanceExpense extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            Log.e("","this input post"+param[0]);
            result= servicepost.makeServicePostWithToken(ConfigConstant.url+"claim/conveyance/expense",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            ClaimResposnse clientResponse=gson.fromJson(result, ClaimResposnse.class);
            if(clientResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();

                intent=new Intent(ClaimConveyanceExpenseActivity.this,CliamDetailsActivity.class);
                intent.putExtra("ClimDTO",claim);
                intent.putExtra("business_exp_list", business_exp_list);
                intent.putExtra("conven_exp_list", conven_exp_list);
                intent.putExtra("misc_exp_list", misc_exp_list);
                startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
