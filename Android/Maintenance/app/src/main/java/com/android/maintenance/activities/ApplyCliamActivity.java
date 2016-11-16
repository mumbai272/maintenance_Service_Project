package com.android.maintenance.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
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
 * Created by anand on 15-Nov-16.
 */
public class ApplyCliamActivity extends Activity {

    EditText start,end,amount,part;
    String token;
    String client_idStr,company_idStr;
    private SessionManager session;
    String start_Str,end__Str,amount_Str,part_Str;
    Button apply_Claim;
    DatePickerDialog endPickerDialog,startPickerDialog;
    SimpleDateFormat dateFormatter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apply_cliam);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        company_idStr = user.get("KEY_COMPANY_ID");
        client_idStr = user.get("KEY_CLIENT_ID");
        token=user.get(SessionManager.KEY_TOKEN);


        start=(EditText)findViewById(R.id.start_date);
        end=(EditText)findViewById(R.id.end_date);
        amount=(EditText)findViewById(R.id.claim_am);
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
                amount_Str=amount.getText().toString();
                part_Str=part.getText().toString();

                try {
                    String json="";
                    ApplyClaimDTO dto= new ApplyClaimDTO();
                    dto.setClaimAmount(Double.parseDouble(amount_Str));
                    dto.setClaimStartDate(start_Str);
                    dto.setClaimEndDate(end__Str);
                    dto.setParticulars(part_Str);
                    ObjectMapper mapper = new ObjectMapper();
                    json = mapper.writeValueAsString(dto);
                    new ApplyCliam().execute(json);

                }catch (Exception e){
                    e.printStackTrace();
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

    private class ApplyCliam extends AsyncTask<String,Void,String> {


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
            BaseResponseDTO clientResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(clientResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
