package com.android.maintenance.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.maintenance.DTO.AddSpareDTO;
import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ReportChargesDTO;
import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
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
 * Created by anand on 22-Nov-16.
 */
public class AddNewSpareActivity extends Activity {

    String token;
    String radio_val;
    Intent intent;
    Gson gson;
    SimpleDateFormat dateFormatter;
    android.app.DatePickerDialog DatePickerDialog;
    private SessionManager session;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportLogDTO> reportLogList;
    RadioGroup chargebleGrp;
    RadioButton chargeble_btn;
    EditText sp_name,sp_no,rate,qty,other,dc_no,dc_date;
    Button add;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_spare);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> users = session.getUserDetails();
        token=users.get(SessionManager.KEY_TOKEN);

        reportLogList=new ArrayList<ReportLogDTO>();
        reportLogList= (ArrayList<ReportLogDTO>) getIntent().getSerializableExtra("reportLogList");
        assetReportDTO= (AssetReportDTO) getIntent().getSerializableExtra("assetReportDTO");
        reportChargesDTO=(ReportChargesDTO) getIntent().getSerializableExtra("reportChargesDTO");
        reportSpareResponseDTO=(ReportSpareResponseDTO) getIntent().getSerializableExtra("reportSpareResponseDTO");

        sp_name=(EditText)findViewById(R.id.sp_name);
        sp_no=(EditText)findViewById(R.id.sp_no);
        rate=(EditText)findViewById(R.id.sp_rate);
        qty=(EditText)findViewById(R.id.qty);
        other=(EditText)findViewById(R.id.otr_amt);
        dc_no=(EditText)findViewById(R.id.dc_no);
        dc_date=(EditText)findViewById(R.id.dc_date);
        add=(Button)findViewById(R.id.add_spr);
        chargebleGrp=(RadioGroup)findViewById(R.id.radio_charge);

        chargebleGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_yes:
                        chargeble_btn=(RadioButton) findViewById(i);
                        break;
                    case R.id.radio_no:
                        chargeble_btn=(RadioButton) findViewById(i);
                        break;
            }
        }
        });




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddSpareDTO dto= new AddSpareDTO();
                dto.setReportId(assetReportDTO.getReportId());
                dto.setDcdateTime(dc_date.getText().toString());
                dto.setDcNo(dc_no.getText().toString());
                dto.setSpaceName(sp_name.getText().toString());
                dto.setSpareNo(Long.parseLong(sp_no.getText().toString()));
                dto.setOtherAmout(Double.parseDouble(other.getText().toString()));
                dto.setQuantity(Double.parseDouble(qty.getText().toString()));
                dto.setRate(Double.parseDouble(rate.getText().toString()));
                radio_val=chargeble_btn.getText().toString();
                if(radio_val.equals("Yes")){
                    radio_val="T";
                }else{
                    radio_val="F";
                }
                dto.setChargeble(radio_val);
                ObjectMapper mapper = new ObjectMapper();
                try{
                    String json="";
                    json=mapper.writeValueAsString(dto);
                    new AddSpare().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        findViewsById();
        setDateTimeField();

    }

    private void setDateTimeField() {

        dc_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dc_date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void findViewsById() {

        dc_date.setInputType(InputType.TYPE_NULL);
        dc_date.requestFocus();
    }

    private class AddSpare extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicePost= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= servicePost.makeServicePostWithToken(ConfigConstant.url+"assetlog/report/spare",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            BaseResponseDTO loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                //    Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                intent=new Intent(AddNewSpareActivity.this,ServiceEngSpareChargesTabsActivity.class);
                intent.putExtra("assetReportDTO",assetReportDTO);
                intent.putExtra("reportLogList",reportLogList);
                intent.putExtra("reportChargesDTO",reportChargesDTO);
                intent.putExtra("reportSpareResponseDTO",reportSpareResponseDTO);
                startActivity(intent);
            }else if(loginResponse.getStatusCode()==-1){

                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
