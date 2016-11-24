package com.android.maintenance.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.CreateAssetLogDTO;
import com.android.maintenance.DTO.MaintenanceTypeDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.asyncTask.GetMaintainanceType;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;

import org.codehaus.jackson.map.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by anand on 08-Nov-16.
 */
public class AssetLogActivity extends Activity {

    EditText date,comment,problem;
    Button create_log_btn;
    Spinner maintainance_Type;
    Intent intent;
    String dateStr,commentStr,problemStr,criticalityStr,l_throughStr;
    RadioGroup criticalityGrp,logThroughGrp;
    RadioButton cricalitybtn,logThrough_btn;
    SimpleDateFormat dateFormatter;
    private SessionManager session;
    String m_type,token;;
    Long assetId,type_id;
    Gson gson;
    String cmpID,clientId,role;
    private ProgressDialog mProgress;
    ArrayAdapter<String> adapter;
    DatePickerDialog createDatePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset_log);
        assetId= getIntent().getLongExtra("assetID",0);
        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        cmpID = user.get("KEY_COMPANY_ID");
        role=user.get(SessionManager.KEY_ROLE);
        token=user.get(SessionManager.KEY_TOKEN);
        clientId = user.get("KEY_CLIENT_ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_logs);
        toolbar.setTitle("Create Log");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals(ConfigConstant.adminRole)) {

                    finish();
                }else if(role.equals(ConfigConstant.employeeRole)){

                    finish();
                }else if(role.equals(ConfigConstant.userRole)){
                  /*  intent = new Intent(AssetLogActivity.this, UserMainActivity.class);
                    startActivity(intent);*/
                    finish();
                }
            }
        });

        date=(EditText)findViewById(R.id.edit_create_date);
        comment=(EditText)findViewById(R.id.edit_comments);
        problem=(EditText)findViewById(R.id.edit_a_pbm);
        maintainance_Type=(Spinner)findViewById(R.id.m_type_spinner);
        create_log_btn=(Button)findViewById(R.id.log_btn);
        criticalityGrp=(RadioGroup)findViewById(R.id.radio_criticality);
        logThroughGrp=(RadioGroup)findViewById(R.id.radio_log_through);
        setMaintainceTypeSpinner();
        criticalityGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_low:
                        cricalitybtn=(RadioButton) findViewById(i);
                        break;
                    case R.id.radio_medium:
                        cricalitybtn=(RadioButton) findViewById(i);
                        break;
                    case R.id.radio_high:
                        cricalitybtn=(RadioButton) findViewById(i);
                        break;
                }
            }
        });

        logThroughGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.l_app:
                        logThrough_btn=(RadioButton) findViewById(i);
                        break;
                    case R.id.l_email:
                        logThrough_btn=(RadioButton) findViewById(i);
                        break;
                    case R.id.l_phone:
                        logThrough_btn=(RadioButton) findViewById(i);
                        break;
                }
            }
        });


        create_log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //maintainance type

                dateStr=date.getText().toString();
                commentStr=comment.getText().toString();
                problemStr=problem.getText().toString();
                criticalityStr=cricalitybtn.getText().toString();
                l_throughStr=logThrough_btn.getText().toString();
                try {
                    String json = "";
                    CreateAssetLogDTO log = new CreateAssetLogDTO(Long.parseLong(cmpID), Long.parseLong(clientId), assetId, dateStr, type_id, problemStr, criticalityStr, l_throughStr, commentStr);
                    ObjectMapper mapper = new ObjectMapper();
                    json = mapper.writeValueAsString(log);
                   new CreateAssetLog().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        dateFormatter = new SimpleDateFormat("yyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();
    }

    public void setMaintainceTypeSpinner() {
        GetMaintainanceType type= new GetMaintainanceType(this);
        type.execute(ConfigConstant.url + "maintenaceType/"+cmpID);
    }

    private void findViewsById() {
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();
    }

    private void setDateTimeField() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==date){
                createDatePickerDialog.show();
                }
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        createDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    public void getTypeList(final ArrayList<MaintenanceTypeDTO> typeList) {

        final List<String> lables = new ArrayList<String>();
        for (int i=0; i< typeList.size();i++) {
            lables.add(typeList.get(i).getTypeCode());
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        maintainance_Type.setAdapter(adapter);
        maintainance_Type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                m_type = parentView.getItemAtPosition(position).toString();

                for(int i=0;i<typeList.size();i++){
                    if(typeList.get(i).getTypeCode()==m_type){
                        type_id=typeList.get(i).getTypeId();
                        Log.e("slected:"+m_type," ID:"+type_id);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class CreateAssetLog extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            Log.e("param",""+param[0]);
            result= servicepost.makeServicePostWithToken(ConfigConstant.url+"asset/logs",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
           gson=new Gson();
            BaseResponseDTO loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                if(role.equals(ConfigConstant.adminRole)){
                    intent=new Intent(AssetLogActivity.this,MachineListActivity.class);
                    startActivity(intent);
                }else if(role.equals(ConfigConstant.userRole)){
                    intent=new Intent(AssetLogActivity.this,UserMainActivity.class);
                    startActivity(intent);
                }else if(role.equals(ConfigConstant.employeeRole)){
                    intent=new Intent(AssetLogActivity.this,EmployeeMainActivity.class);
                    startActivity(intent);
                }
            }else if(loginResponse.getStatusCode()==-1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
