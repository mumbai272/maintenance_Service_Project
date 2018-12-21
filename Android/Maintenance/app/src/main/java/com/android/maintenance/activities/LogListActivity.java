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
import com.android.maintenance.asyncTask.GetLogsList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 09-Nov-16.
 */
public class LogListActivity extends Activity{

    ListView listView;
    private SessionManager session;
    Intent intent;
    public String token,clientID,userID;
    Gson gson;
    LogListAdapter adapter;
    ArrayList<AssetLogDTO> logList;
    Context context = this;
    private ProgressDialog mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_logList);
        toolbar.setTitle("Log List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LogListActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView=(ListView)findViewById(R.id.listView_logs);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        clientID = user.get("KEY_CLIENT_ID");
        Log.e("user_ id:" + userID, "Token:" + token);

        getlogList();
    }

    private void getlogList() {

        GetLogsList list=new GetLogsList(this);
        list.execute(ConfigConstant.url + "asset/logs");
    }

    public void displayLogList(JSONArray logData) {
        if(logData==null) {
          /*  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Log");
            alertDialogBuilder
                    .setMessage("NO Logs found.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();*/

        }else {
            String logStr = logData.toString();
            gson = new Gson();
            Type type = new TypeToken<ArrayList<AssetLogDTO>>() {
            }.getType();
            logList = gson.fromJson(logStr, type);
            Log.e("", "Log list:" + logList.size());
            adapter = new LogListAdapter(getApplicationContext(), logList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(getApplicationContext(), "Log ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    //assign log on

                    Long logId = (Long) view.getTag();
                    Log.e("userId:", "" + logId);
                    for(int j=0;j<logList.size();j++){
                        if(logList.get(j).getStatus().equals("NEW")){
                            intent= new Intent(LogListActivity.this,AssignLogActivity.class);
                            intent.putExtra("list",logList);
                            intent.putExtra("logId",logId);
                            startActivity(intent);
                        }
                    }


                }
            });
        }
    }
}
