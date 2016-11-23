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
import com.android.maintenance.DTO.ClaimResposnse;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by anand on 17-Nov-16.
 */
public class MiscExpensesActivity extends Activity {

    private SessionManager session;
    String token;
    EditText date,part,bill_no,bill_date,amount;
    Button btn;
    String date_Str,part_Str,bill_no_Str,bill_date_Str,amount_Str;
    DatePickerDialog expPickerDialog,billPickerDialog;
    SimpleDateFormat dateFormatter;
    Intent intent;
     Long ID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misc_expense);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token=user.get(SessionManager.KEY_TOKEN);

        ID=getIntent().getLongExtra("ID",1);

        date=(EditText)findViewById(R.id.bus_exp_date);
        part=(EditText)findViewById(R.id.particular_misc);
        bill_date=(EditText)findViewById(R.id.misc_bill_date_exp);
        bill_no=(EditText)findViewById(R.id.misc_bill_no_exp);
        amount=(EditText)findViewById(R.id.misc_exp_amt);

        dateFormatter = new SimpleDateFormat("yyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        btn=(Button)findViewById(R.id.mis_dev_exp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_Str=date.getText().toString();
                part_Str=part.getText().toString();
                bill_no_Str=bill_no.getText().toString();
                bill_date_Str=bill_date.getText().toString();
                amount_Str=amount.getText().toString();
                if(Utility.isNotNull(date_Str)&&Utility.isNotNull(part_Str)&&Utility.isNotNull(bill_no_Str)&&Utility.isNotNull(bill_date_Str)&&Utility.isNotNull(amount_Str)) {
                    try {
                    String json="";
                    MiscExpenseDTO dto= new MiscExpenseDTO();
                    dto.setClaimId(ID);
                    dto.setClaimAmount(Double.parseDouble(amount_Str));
                    dto.setBillDate(bill_date_Str);
                    dto.setExpenseDate(date_Str);
                    dto.setBillNumber(bill_no_Str);
                    dto.setParticulars(part_Str);

                    ObjectMapper mapper = new ObjectMapper();
                    json = mapper.writeValueAsString(dto);
                    new MiscExpense().execute(json);

                }catch (Exception e){
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

    private class MiscExpense extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            Log.e("","this input post"+param[0]);
            result= servicepost.makeServicePostWithToken(ConfigConstant.url+"claim/misc/expense",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            ClaimResposnse clientResponse=gson.fromJson(result, ClaimResposnse.class);
            if(clientResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
               finish();

            }else{
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}