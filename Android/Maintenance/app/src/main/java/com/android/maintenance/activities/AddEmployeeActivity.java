package com.android.maintenance.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.android.maintenance.DTO.AttributeDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.DTO.EmployeeDTO;
import com.android.maintenance.DTO.UserEmploymentDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.asyncTask.GetDeparymentList;
import com.android.maintenance.asyncTask.GetDesignationList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import java.lang.reflect.Type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by anand on 03-Nov-16.
 */
public class AddEmployeeActivity extends Activity{
    ArrayAdapter<String> adapter;
    Intent intent;
    EditText emp_name,emp_mail,emp_no,emp_rate,emp_join;
    Spinner emp_depart,emp_design,emp_client,role_Spinner;
    Button add_emp;
    Long id_design,id_depart,id_client,role_long;
    Gson gson;
    String role;
    Context context=this;
    ArrayList<ClientDTO> clientList;
    ArrayList<AttributeDTO> attrDepartList;
    ArrayList<AttributeDTO> attrDesignList;
    private SessionManager session;
    String token,cmp_id;
    SimpleDateFormat dateFormatter;
    private ProgressDialog mProgress;
    DatePickerDialog joinDatePickerDialog;
    String emp_nameStr,emp_mailStr,emp_noStr,emp_rateStr,emp_joinStr;
    String[] role_array={"User","Service Engineer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_employee);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> users = session.getUserDetails();
        token=users.get(SessionManager.KEY_TOKEN);
        cmp_id= users.get("KEY_COMPANY_ID");
        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        Log.e("","Token:"+token);
        clientList = new ArrayList<ClientDTO>();
        clientList =(ArrayList<ClientDTO>) getIntent().getSerializableExtra("clientList");
        setDesignationSpinner();
        setDepartmentSpinner();
        setRoleSpinner();
        setClientSpinner();
        emp_name =(EditText) findViewById(R.id.edit_emp_name);
        emp_mail =(EditText) findViewById(R.id.edit_emp_mail);
        emp_no = (EditText) findViewById(R.id.edit_emp_no);
        emp_rate =(EditText) findViewById(R.id.edit_emp_rate);
        emp_join = (EditText)findViewById(R.id.emp_join);
        emp_design =(Spinner)findViewById(R.id.emp_design);
        emp_depart =(Spinner)findViewById(R.id.emp_depart);

        add_emp =(Button) findViewById(R.id.add_emp_btn);

        dateFormatter = new SimpleDateFormat("yyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

        add_emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emp_mailStr=emp_mail.getText().toString();
                emp_nameStr=emp_name.getText().toString();
                emp_rateStr=emp_rate.getText().toString();
                emp_noStr=emp_no.getText().toString();
                emp_joinStr=emp_join.getText().toString();
                if(Utility.isNotNull(emp_nameStr) && Utility.isNotNull(emp_rateStr) && Utility.isNotNull(emp_noStr) && Utility.isNotNull(emp_mailStr)){
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String json = "";
                        UserEmploymentDTO emp = new UserEmploymentDTO();
                        emp.setDepartment(id_depart);
                        emp.setDesignation(id_design);
                        emp.setHourRate(Double.parseDouble(emp_rateStr));
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date join;
                        join = df.parse(emp_joinStr);
                        emp.setJoiningDay(join);
                        EmployeeDTO empoyee = new EmployeeDTO();
                        empoyee.setEmployment(emp);
                        empoyee.setEmailId(emp_mailStr);
                        empoyee.setName(emp_nameStr);
                        empoyee.setPhoneno(emp_noStr);
                        empoyee.setCompanyId(id_client);
                        empoyee.setRoleTypeId(role_long);
                        json=mapper.writeValueAsString(empoyee);
                        new AddEmployee().execute(json);
                        mProgress.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{

                    Toast.makeText(getApplicationContext(), "Please don't leave any field blank", Toast.LENGTH_LONG).show();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Employee");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AddEmployeeActivity.this, AdminEmployeeListActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void setDateTimeField() {
        emp_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view==emp_join)
                joinDatePickerDialog.show();
            }
        });
        Calendar newCalendar = Calendar.getInstance();
        joinDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                emp_join.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void findViewsById() {
        emp_join.setInputType(InputType.TYPE_NULL);
        emp_join.requestFocus();
    }

    private void setClientSpinner() {
        List<String> lables = new ArrayList<String>();
        for (int i=0; i< clientList.size();i++) {
            lables.add(clientList.get(i).getClientName());
        }

        emp_client =(Spinner)findViewById(R.id.emp_client);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        emp_client.setAdapter(adapter);

        emp_client.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String client=adapterView.getItemAtPosition(i).toString();
                for(int j=0;j<clientList.size();j++){
                    if(client==clientList.get(j).getClientName()){
                        id_client=clientList.get(j).getClientId();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setRoleSpinner() {
        List<String> roleStr= new ArrayList<String>();
        roleStr= Arrays.asList(role_array);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roleStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        role_Spinner=(Spinner)findViewById(R.id.emp_role);
        role_Spinner.setAdapter(adapter);
        role_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                role =parentView.getItemAtPosition(position).toString();
                if(role =="Admin"){
                    role_long=1L;
                }else if(role=="User"){
                    role_long=2L;
                }else if(role=="Service Engineer"){
                    role_long=3L;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //    Toast.makeText(parentView.getContext(), "You selected nothing " ,Toast.LENGTH_LONG).show();
            }

        });
        Log.e("","item set");
    }

    private void setDesignationSpinner() {
        GetDesignationList desgn= new GetDesignationList(this);
        desgn.execute();
    }

    private void setDepartmentSpinner() {
        GetDeparymentList desgn= new GetDeparymentList(this);
        desgn.execute(ConfigConstant.url + "company/attributes/department/"+cmp_id);
    }

    public void getDesignList(JSONArray attrData) {
        String attrDataStr = attrData.toString();
        Log.e("sds","companyDataStr:"+attrDataStr);
        gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<AttributeDTO>>() {
        }.getType();
        attrDesignList = gson.fromJson(attrDataStr, type);
        Log.e("ds","userdto:"+attrDesignList.size());
        List<String> designList= new ArrayList<String>();
        for(int i=0;i<attrDesignList.size();i++){
           designList.add (attrDesignList.get(i).getAttributeValue());
        }

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, designList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        emp_design.setAdapter(adapter);
        emp_design.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String design=adapterView.getItemAtPosition(i).toString();
                for(int j=0;j<attrDesignList.size();j++){
                  if(design==attrDesignList.get(j).getAttributeValue()){
                      id_design=attrDesignList.get(j).getId();
                  }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void getDepartList(JSONArray attrData) {
        String attrDataStr = attrData.toString();
        Log.e("sds","companyDataStr:"+attrDataStr);
        gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<AttributeDTO>>() {
        }.getType();
        attrDepartList = gson.fromJson(attrDataStr, type);
        Log.e("ds","userdto:"+attrDepartList.size());
        List<String> departList= new ArrayList<String>();
        for(int i=0;i<attrDepartList.size();i++){
            departList.add (attrDepartList.get(i).getAttributeValue());
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, departList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        emp_depart.setAdapter(adapter);
        emp_depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String depart=adapterView.getItemAtPosition(i).toString();

                for(int j=0;j<attrDepartList.size();j++){
                    if(depart==attrDepartList.get(j).getAttributeValue()){
                        id_depart=attrDepartList.get(j).getId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public class AddEmployee extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS servicePost = new ServiceHandlerWS();
            Log.e("url", "" + ConfigConstant.url + "user");
            result = servicePost.makeServicePostWithToken(ConfigConstant.url + "user",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            BaseResponseDTO addEmpResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (addEmpResponse.getStatusCode() == 1) {
                mProgress.dismiss();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Success");
                alertDialogBuilder
                        .setMessage("Employee Successfully Added")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                intent= new Intent(AddEmployeeActivity.this,AdminEmployeeListActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else if (addEmpResponse.getStatusCode() == -1) {
                mProgress.dismiss();
                Toast.makeText(getBaseContext(), addEmpResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
