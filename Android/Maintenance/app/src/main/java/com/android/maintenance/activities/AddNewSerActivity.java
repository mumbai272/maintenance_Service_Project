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
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.AddEngineerDTO;
import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ReportChargesDTO;
import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
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
import java.util.Locale;

/**
 * Created by anand on 16-Nov-16.
 */
public class AddNewSerActivity extends Activity {

    DatePickerDialog DatePickerDialog,time_inPickerDialog,time_outPickerDialog;
    Spinner ser_eng;
    Gson gson;
    String token;
    Long id_user;
    Intent intent;
    private SessionManager session;
    ArrayAdapter adapter;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportLogDTO> reportLogList;
    ArrayList<UserDTO> empList;
    SimpleDateFormat dateFormatter;
    Button add;
    TextView date,time_in,time_out,tr_time,reason,action;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_engineer);
        session=new SessionManager(getApplicationContext());
        HashMap<String, String> users = session.getUserDetails();
        token=users.get(SessionManager.KEY_TOKEN);


        date=(EditText)findViewById(R.id.re_date);
        time_in=(EditText)findViewById(R.id.re_time_in);
        time_out=(EditText)findViewById(R.id.re_time_out);
        tr_time=(EditText)findViewById(R.id.re_tr_time);
        reason=(EditText)findViewById(R.id.re_reason);
        action=(EditText)findViewById(R.id.re_action);
        ser_eng=(Spinner)findViewById(R.id.ser_spinner);
        add=(Button)findViewById(R.id.add_ser);

        reportLogList=new ArrayList<ReportLogDTO>();
        reportLogList= (ArrayList<ReportLogDTO>) getIntent().getSerializableExtra("reportLogList");
        assetReportDTO= (AssetReportDTO) getIntent().getSerializableExtra("assetReportDTO");
        reportChargesDTO=(ReportChargesDTO) getIntent().getSerializableExtra("reportChargesDTO");
        reportSpareResponseDTO=(ReportSpareResponseDTO) getIntent().getSerializableExtra("reportSpareResponseDTO");


        setSpinnerData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AddEngineerDTO dto= new AddEngineerDTO();
                dto.setReportId(assetReportDTO.getReportId());
                dto.setActionToTake(action.getText().toString());
                dto.setDateTime(date.getText().toString());
                dto.setTimeIn(time_in.getText().toString());
                dto.setTimeOut(time_out.getText().toString());
                dto.setTravelTime(tr_time.getText().toString());
                dto.setServiceEngineer(id_user);
                dto.setReason(reason.getText().toString());
                ObjectMapper mapper = new ObjectMapper();
                try{
                    String json="";
                    json=mapper.writeValueAsString(dto);
                    new AddEngineer().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
        findViewsById();
        setDateTimeField();

    }

    private void setSpinnerData() {
        GetEmployeeList ser=new GetEmployeeList(this);
        ser.execute();
    }

    private void findViewsById() {
        date.setInputType(InputType.TYPE_NULL);
        date.requestFocus();


        time_in.setInputType(InputType.TYPE_NULL);
        time_in.requestFocus();


        time_out.setInputType(InputType.TYPE_NULL);
        time_out.requestFocus();
    }

    private void setDateTimeField() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.show();
            }
        });


        time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_inPickerDialog.show();
            }
        });


        time_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_outPickerDialog.show();
            }
        });


        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        time_inPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                time_in.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        time_outPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                time_out.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


    public void displayEmpList(JSONArray dataUsers) {
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
        ser_eng.setAdapter(adapter);
        ser_eng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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

    private class AddEngineer extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicePost= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= servicePost.makeServicePostWithToken(ConfigConstant.url+"assetlog/report/log",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            BaseResponseDTO loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                //    Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                intent=new Intent(AddNewSerActivity.this,ServiceEngSpareChargesTabsActivity.class);
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
