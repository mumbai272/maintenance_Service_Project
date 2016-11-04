package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.adapters.EmployeeListAdapter;
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
    Context context = this;
    private SessionManager session;
    public String token, userID;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_list);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);

        displayEmployeeListView();
        addEmployee= (ImageButton) findViewById(R.id.add_employee);
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AdminEmployeeListActivity.this,AddEmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Employee List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(AdminEmployeeListActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayEmployeeListView() {

        new GetEmployeeList().execute();
    }

    private class GetEmployeeList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS serviceGet = new ServiceHandlerWS();
            Log.e("url", "" + ConfigConstant.url + "user");
            result = serviceGet.makeServiceGet(ConfigConstant.url + "user", token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj;
            JSONArray dataUsers = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                dataUsers = obj.getJSONArray("users");
                //  Log.e("users list",""+dataUsers);
            } catch (Exception e) {

            }

            BaseResponseDTO employeeResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (employeeResponse.getStatusCode() == 1) {
                Toast.makeText(getApplicationContext(), employeeResponse.getMsg(), Toast.LENGTH_LONG).show();
                displayEmpList(dataUsers);
            } else if (employeeResponse.getStatusCode() == -1) {
                Toast.makeText(getApplicationContext(), employeeResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayEmpList(JSONArray dataUsers) {
        String userStr = dataUsers.toString();
        gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<UserDTO>>() {
        }.getType();
        ArrayList<UserDTO> tempEmpList = gson.fromJson(userStr, type);
        ArrayList<UserDTO> empList= new ArrayList<UserDTO>();
        for(int i=0;i<tempEmpList.size();i++){

            if(!tempEmpList.get(i).getRole().equals(ConfigConstant.userRole)){
                UserDTO user = new UserDTO(tempEmpList.get(i).getUserId(),tempEmpList.get(i).getUserName(),tempEmpList.get(i).getFirstName(),tempEmpList.get(i).getMiddleName(),tempEmpList.get(i).getPhoneno(),tempEmpList.get(i).getEmailId(),tempEmpList.get(i).getLastName(),tempEmpList.get(i).getRole(),tempEmpList.get(i).getGender(),tempEmpList.get(i).getClientName());
                empList.add(user);
            }
        }
        Log.e("", "user dto" + empList.size());
        adapter=new EmployeeListAdapter(getApplicationContext(),empList);
        listView = (ListView) findViewById(R.id.listView_employee);
        listView.setAdapter(adapter);
        if(empList.size() !=0) {
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
