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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by anand on 17-Nov-16.
 */
public class BusinessDevelopmentExpenseActivity extends Activity {

    private SessionManager session;
    String token;
    EditText guest,date,part,bill_no,bill_date,amount;
    Button btn;
    String guest_Str,date_Str,part_Str,bill_no_Str,bill_date_Str,amount_Str;
    DatePickerDialog expPickerDialog,billPickerDialog;
    SimpleDateFormat dateFormatter;
    Intent intent;
    Long ID;
    GetClaimListDTO claim;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_development_expense);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManager.KEY_TOKEN);

        claim= (GetClaimListDTO) getIntent().getSerializableExtra("ClimDTO");
        business_exp_list= (ArrayList<BusinessDevExpenseDTO>) getIntent().getSerializableExtra("business_exp_list");
        misc_exp_list= (ArrayList<MiscExpenseDTO>) getIntent().getSerializableExtra("misc_exp_list");
        conven_exp_list= ( ArrayList<ClaimConveyanceExpenseDTO>) getIntent().getSerializableExtra("conven_exp_list");

        guest=(EditText)findViewById(R.id.guest);
        date=(EditText)findViewById(R.id.bus_exp_date);
        part=(EditText)findViewById(R.id.particular_bus);
        bill_date=(EditText)findViewById(R.id.bus_bill_date_exp);
        bill_no=(EditText)findViewById(R.id.bus_bill_no_exp);
        amount=(EditText)findViewById(R.id.bus_exp_amt);

        dateFormatter = new SimpleDateFormat("yyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        btn=(Button)findViewById(R.id.busineess_dev_exp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guest_Str=guest.getText().toString();
                date_Str=date.getText().toString();
                part_Str=part.getText().toString();
                bill_no_Str=bill_no.getText().toString();
                bill_date_Str=bill_date.getText().toString();
                amount_Str=amount.getText().toString();
                if(Utility.isNotNull(guest_Str)&&Utility.isNotNull(date_Str)&&Utility.isNotNull(part_Str)&&Utility.isNotNull(bill_no_Str)&&Utility.isNotNull(bill_date_Str)&&Utility.isNotNull(amount_Str)) {
                    try {
                        String json = "";
                        BusinessDevExpenseDTO dto = new BusinessDevExpenseDTO();
                        dto.setClaimId(ID);
                        dto.setExpenseDate(date_Str);
                        dto.setClaimAmount(Double.parseDouble(amount_Str));
                        dto.setBillDate(bill_date_Str);
                        dto.setBillNumber(bill_no_Str);
                        dto.setParticulars(part_Str);
                        dto.setGuest(guest_Str);


                        ObjectMapper mapper = new ObjectMapper();
                        json = mapper.writeValueAsString(dto);
                        new BusinessDevExpense().execute(json);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Dont leave any fields blank",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findViewsById() {
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();

        bill_date.setInputType(InputType.TYPE_NULL);
        bill_date.requestFocus();
    }

    private void setDateTimeField() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expPickerDialog.show();
            }
        });

        bill_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billPickerDialog.show();
            }
        });


        Calendar newCalendar = Calendar.getInstance();
        expPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        billPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                bill_date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private class BusinessDevExpense extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            Log.e("","this input post"+param[0]);
            result= servicepost.makeServicePostWithToken(ConfigConstant.url+"claim/business/development/expense",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            ClaimResposnse clientResponse=gson.fromJson(result, ClaimResposnse.class);
            if(clientResponse.getStatusCode()==1){
                intent=new Intent(BusinessDevelopmentExpenseActivity.this,CliamDetailsActivity.class);
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
