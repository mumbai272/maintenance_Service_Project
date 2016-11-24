package com.android.maintenance.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.DTO.AssignAssetLogDTO;
import com.android.maintenance.DTO.AttributeDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.asyncTask.GetDeparymentList;
import com.android.maintenance.asyncTask.GetEmployeeList;
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
 * Created by anand on 10-Nov-16.
 */
public class AssignLogActivity extends Activity{

    DatePickerDialog expiredDatePickerDialog;
    SimpleDateFormat dateFormatter;
    Spinner worktype,assign;
    ArrayList<UserDTO> empList;
    ArrayList<AttributeDTO> attrWorkTypeList;
    private SessionManager session;
    EditText hour,expire;
    Long id_user;
    String cmp_id,token,id_type;
    Intent intent;
    ArrayList<AssetLogDTO> logList;
    Long logID;
    ArrayAdapter adapter;
    Button assignBtn;
    Gson gson;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assign_log);


        logList=new ArrayList<AssetLogDTO>();
        //logList=(ArrayList<AssetLogDTO>)getIntent().getSerializableExtra("list");
        logID=getIntent().getLongExtra("logId",0);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> users = session.getUserDetails();
        token=users.get(SessionManager.KEY_TOKEN);
        cmp_id= users.get("KEY_COMPANY_ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_assign);
        toolbar.setTitle("Assign Log");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AssignLogActivity.this, LogListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        hour=(EditText)findViewById(R.id.work_hour);
        expire=(EditText)findViewById(R.id.expire_work);
        assign=(Spinner)findViewById(R.id.assign_spinner);
        worktype=(Spinner)findViewById(R.id.work_type_spinner);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss" , Locale.US);
        assignBtn=(Button)findViewById(R.id.assignbtn);
        findViewsById();
        setDateTimeField();
        setAssignSpinner();
        setWorkTypeSinner();

        assignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AssignAssetLogDTO dto= new AssignAssetLogDTO();
                dto.setLogId(logID);
                dto.setAssignedTo(id_user);
                dto.setWorkType(id_type);
                dto.setPlannedHours(Double.parseDouble(hour.getText().toString()));
                dto.setExpServiceDateTime( expire.getText().toString());
                ObjectMapper mapper = new ObjectMapper();
                try{
                    String json="";

                    json=mapper.writeValueAsString(dto);
                    new AssignLog().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void setAssignSpinner() {
        GetEmployeeList emp=new GetEmployeeList(this);
        emp.execute();
    }

    private void setWorkTypeSinner() {
        GetDeparymentList workTypeList=new GetDeparymentList(this);
        workTypeList.execute(ConfigConstant.url + "company/attributes/worktype/"+cmp_id);
    }

    private void findViewsById() {
        expire.setInputType(InputType.TYPE_NULL);
        expire.requestFocus();
    }

    private void setDateTimeField() {
        expire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==expire){
                    expiredDatePickerDialog.show();
                }
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        expiredDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                expire.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    public void displayEmpList(JSONArray dataUsers) {
        if(dataUsers==null) {


        }else {
            String userStr = dataUsers.toString();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Type type = new TypeToken<ArrayList<UserDTO>>() {
            }.getType();
            empList = new ArrayList<UserDTO>();
            Log.e("", "Emp list:" + empList.size());
            ArrayList<UserDTO> tempEmpList = gson.fromJson(userStr, type);
                for(int i=0;i<tempEmpList.size();i++){
                    if(tempEmpList.get(i).getRole().equals(ConfigConstant.employeeRole)){
                        UserDTO user=new UserDTO(tempEmpList.get(i).getUserId(),tempEmpList.get(i).getUserName(),tempEmpList.get(i).getFirstName(),tempEmpList.get(i).getMiddleName(),tempEmpList.get(i).getPhoneno(),tempEmpList.get(i).getEmailId(),tempEmpList.get(i).getLastName(),tempEmpList.get(i).getRole(),tempEmpList.get(i).getGender(),tempEmpList.get(i).getClientName(),tempEmpList.get(i).getStatus());
                        empList.add(user);
                    }
                }
            ArrayList<String> lable= new ArrayList<String>();
            for(int i=0;i<empList.size();i++){
                lable.add(empList.get(i).getFirstName());
            }

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lable);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            assign.setAdapter(adapter);
            assign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String emp=adapterView.getItemAtPosition(i).toString();

                    for(int j=0;j<empList.size();j++){
                        if(emp==empList.get(j).getFirstName()){
                            id_user=empList.get(j).getUserId();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    public void getWorkTypeList(JSONArray attrData) {
        String attrDataStr = attrData.toString();
        Log.e("sds","companyDataStr:"+attrDataStr);
        gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<AttributeDTO>>() {
        }.getType();
        attrWorkTypeList = gson.fromJson(attrDataStr, type);
        Log.e("ds","userdto:"+attrWorkTypeList.size());
        List<String> departList= new ArrayList<String>();
        for(int i=0;i<attrWorkTypeList.size();i++){
            departList.add (attrWorkTypeList.get(i).getAttributeValue());
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        worktype.setAdapter(adapter);
        worktype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String depart=adapterView.getItemAtPosition(i).toString();

                for(int j=0;j<attrWorkTypeList.size();j++){
                    if(depart==attrWorkTypeList.get(j).getAttributeValue()){
                        id_type=attrWorkTypeList.get(j).getAttributeValue();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class AssignLog extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicePost= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= servicePost.makeServicePostWithToken(ConfigConstant.url+"asset/logs/assign",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            BaseResponseDTO loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
            //    Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                intent= new Intent(getApplicationContext(), LogListActivity.class);
                startActivity(intent);
            }else if(loginResponse.getStatusCode()==-1){

                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
