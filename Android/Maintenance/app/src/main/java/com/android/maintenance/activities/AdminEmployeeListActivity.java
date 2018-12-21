package com.android.maintenance.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.DTO.MachineDetailDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.adapters.EmployeeListAdapter;
import com.android.maintenance.asyncTask.GetEmployeeList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 03-Nov-16.
 */
public class AdminEmployeeListActivity extends Activity {

    ListView listView;
    Intent intent;
    ImageButton addEmployee;
    EmployeeListAdapter adapter;
    private SessionManager session;
    public String token, userID;
    Gson gson;
    Context context=this;
    ArrayList<ClientDTO> clientList;
    ArrayList<UserDTO> empList;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_list);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        GetEmployeeList empList = new GetEmployeeList(this);
        empList.execute();
        mProgress.show();

        clientList = new ArrayList<ClientDTO>();
        clientList =(ArrayList<ClientDTO>) getIntent().getSerializableExtra("clientList");

        addEmployee= (ImageButton) findViewById(R.id.add_employee);
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AdminEmployeeListActivity.this,AddEmployeeActivity.class);
                intent.putExtra("clientList",clientList);
                startActivity(intent);
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Employee List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  intent = new Intent(AdminEmployeeListActivity.this, AdminMainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
    }


    public void displayEmpList(JSONArray dataUsers) {
        mProgress.dismiss();
        if(dataUsers==null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Employees");
            alertDialogBuilder
                    .setMessage("NO machines  in the List.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }else {
            String userStr = dataUsers.toString();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Type type = new TypeToken<ArrayList<UserDTO>>() {
            }.getType();
            empList = new ArrayList<UserDTO>();
            Log.e("", "Emp list:" + empList.size());
            ArrayList<UserDTO> tempEmpList = gson.fromJson(userStr, type);
            for(int i=0;i<tempEmpList.size();i++){
                if(!tempEmpList.get(i).getRole().equals(ConfigConstant.userRole)){
                    UserDTO user = new UserDTO(tempEmpList.get(i).getUserId(),tempEmpList.get(i).getUserName(),tempEmpList.get(i).getFirstName(),tempEmpList.get(i).getMiddleName(),tempEmpList.get(i).getPhoneno(),tempEmpList.get(i).getEmailId(),tempEmpList.get(i).getLastName(),tempEmpList.get(i).getRole(),tempEmpList.get(i).getGender(),tempEmpList.get(i).getClientName(),tempEmpList.get(i).getStatus());
                    empList.add(user);
                }
            }
            adapter = new EmployeeListAdapter(getApplicationContext(), empList);
            listView = (ListView) findViewById(R.id.listView_employee);
            listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   /* Toast.makeText(getApplicationContext(), "User ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    Long userId = (Long) view.getTag();
                    Log.e("userId:", "" + userId);
                    intent = new Intent(AdminEmployeeListActivity.this,EmployeeDetailsActivity.class);
                    intent.putExtra("arrayList", );
                    intent.putExtra("clientList", );
                    intent.putExtra("user_id", userId);
                    startActivity(intent);*/
                    }
                });
        }
    }
}
