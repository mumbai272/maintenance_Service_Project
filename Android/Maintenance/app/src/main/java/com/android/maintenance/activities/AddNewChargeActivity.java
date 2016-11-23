package com.android.maintenance.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.maintenance.DTO.AddChargeDTO;
import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.AttributeDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ReportChargesDTO;
import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.asyncTask.GetDeparymentList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by anand on 22-Nov-16.
 */
public class AddNewChargeActivity extends Activity{

    String token,cmp_id,tax_data;
    String spare_data;
    Intent intent;
    Gson gson;
    DatePickerDialog datePickerDialog;
    private SessionManager session;
    ArrayAdapter adapterTaxType,spareTaxtype;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<AttributeDTO> taxtypeList;
    ArrayList<ReportLogDTO> reportLogList;
    SimpleDateFormat dateFormatter;
    EditText name,date,tofro,taxper,sparetxper,holiday,after,service;
    Spinner taxtype,sparetaxtype;
    Button add;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_charge);
        session=new SessionManager(getApplicationContext());
        HashMap<String, String> users = session.getUserDetails();
        token=users.get(SessionManager.KEY_TOKEN);
        cmp_id= users.get("KEY_COMPANY_ID");

        reportLogList=new ArrayList<ReportLogDTO>();
        reportLogList= (ArrayList<ReportLogDTO>) getIntent().getSerializableExtra("reportLogList");
        assetReportDTO= (AssetReportDTO) getIntent().getSerializableExtra("assetReportDTO");
        reportChargesDTO=(ReportChargesDTO) getIntent().getSerializableExtra("reportChargesDTO");
        reportSpareResponseDTO=(ReportSpareResponseDTO) getIntent().getSerializableExtra("reportSpareResponseDTO");

        name=(EditText)findViewById(R.id.invoice_name);
        date=(EditText)findViewById(R.id.invoice_date);
        tofro=(EditText)findViewById(R.id.tonfor_ch);
        taxper=(EditText)findViewById(R.id.tax_per);
        sparetxper=(EditText)findViewById(R.id.spare_tax_per);
        holiday=(EditText)findViewById(R.id.holidat_charge);
        after=(EditText)findViewById(R.id.after_charge);
        service=(EditText)findViewById(R.id.service_ch);
        taxtype=(Spinner) findViewById(R.id.tax_type_spinner);
        sparetaxtype=(Spinner)findViewById(R.id.spare_tax_type_spinner);
        add=(Button)findViewById(R.id.add_charge);

        setSpinnerData();

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.US);
        findViewsById();
        setDateTimeField();



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddChargeDTO dto= new AddChargeDTO();
                dto.setHoidayCharges(Double.parseDouble(holiday.getText().toString()));
                dto.setInvoiceDate(date.getText().toString());
                dto.setInvoiceNo(name.getText().toString());
                dto.setToFroCharges(Double.parseDouble(tofro.getText().toString()));
                dto.setTaxPercentage(Double.parseDouble(taxper.getText().toString()));
                dto.setSpareTaxPercentage(Double.parseDouble(sparetxper.getText().toString()));
                dto.setOffHourCharges(Double.parseDouble(after.getText().toString()));
                dto.setServiceCharges(Double.parseDouble(service.getText().toString()));
                dto.setTaxType(tax_data);
                dto.setReportId(assetReportDTO.getReportId());
                dto.setSpareAmount(reportSpareResponseDTO.getSpareTotal());
                dto.setSpareTaxType(spare_data);
                ObjectMapper mapper = new ObjectMapper();
                try{
                    String json="";
                    json=mapper.writeValueAsString(dto);
                    new AddCharge().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
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
                datePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void setSpinnerData() {
        GetDeparymentList attr=new GetDeparymentList(this);
        attr.execute(ConfigConstant.url+"company/attributes/taxtype/"+cmp_id);
    }


    public void getWorkTypeList(JSONArray attrData) {

        String attrDataStr = attrData.toString();
        Log.e("sds","companyDataStr:"+attrDataStr);
        gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<AttributeDTO>>() {
        }.getType();
        taxtypeList = gson.fromJson(attrDataStr, type);
        Log.e("ds","userdto:"+taxtypeList.size());
        List<String> lable= new ArrayList<String>();
        for(int i=0;i<taxtypeList.size();i++){
            lable.add (taxtypeList.get(i).getAttributeValue());
        }
        adapterTaxType = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lable);
        adapterTaxType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        taxtype.setAdapter(adapterTaxType);
        taxtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 tax_data=adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       spareTaxtype=new ArrayAdapter<String>(this,
               android.R.layout.simple_spinner_item, lable);
        spareTaxtype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        sparetaxtype.setAdapter(adapterTaxType);
        sparetaxtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spare_data=adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private class AddCharge extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicePost= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= servicePost.makeServicePostWithToken(ConfigConstant.url+"assetlog/report/charges",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            BaseResponseDTO loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                //    Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                intent=new Intent(AddNewChargeActivity.this,ServiceEngSpareChargesTabsActivity.class);
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
