package com.android.maintenance.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.adapters.LogListAdapter;
import com.android.maintenance.asyncTask.GetAssignedLogsList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 11-Nov-16.
 */
public class EmployeeWorkAssignListActivity extends Activity{
    ListView listView;
    private SessionManager session;
    Intent intent;
    public String token,clientID,userID;
    Gson gson;
    LogListAdapter adapter;
    ArrayList<AssetLogDTO> empLogList;
    Context context = this;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_log_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_emplogList);
        toolbar.setTitle("Log List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(EmployeeWorkAssignListActivity.this, EmployeeMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView=(ListView)findViewById(R.id.list_emplogs);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        clientID = user.get("KEY_CLIENT_ID");
        Log.e("user_ id:" + userID, "Token:" + token);

        getEmpLogList();

    }

    private void getEmpLogList() {

        GetAssignedLogsList list=new GetAssignedLogsList(this);
        list.execute(ConfigConstant.url + "asset/logs?clientId="+clientID);
    }

    public void displayAssignedLogList(JSONArray logData) {
        gson = new Gson();
        Type type;
        if(logData==null) {

        }else{
            String logStr = logData.toString();
            gson = new Gson();
            type = new TypeToken<ArrayList<AssetLogDTO>>() {
            }.getType();
            empLogList = gson.fromJson(logStr, type);
            Log.e("", "Log list:" + empLogList.size());
            adapter = new LogListAdapter(getApplicationContext(), empLogList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(getApplicationContext(), "Log ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    //assign log on
/*
                    Long logId = (Long) view.getTag();
                    Log.e("userId:", "" + logId);
                    for(int j=0;j<empLogList.size();j++){
                        if(empLogList.get(j).getStatus().equals("NEW")){
                            intent= new Intent(EmployeeWorkAssignListActivity.this,EmployeeMainActivity.class);
                            intent.putExtra("list",empLogList);
                            intent.putExtra("logId",logId);
                            startActivity(intent);
                        }
                    }*/


                }
            });

        }
    }
}


