package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.adapters.ServiceEngineerListAdaptor;
import com.android.maintenance.asyncTask.GetAssetReportlog;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by anand on 16-Nov-16.
 */
public class AddServiceEngineerActivity  extends Activity{
    Long logID;
    ServiceEngineerListAdaptor adapter;
    Gson gson;
    Intent intent;
    ListView listView;
    ImageButton button;
    ArrayList<ReportLogDTO> reportLogList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service_engineer);

        logID=getIntent().getLongExtra("LogID",0);
        listView=(ListView) findViewById(R.id.listView_serv_eng);
        button=(ImageButton)findViewById(R.id.add_serv_eng);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent =new Intent(AddServiceEngineerActivity.this,AddNewSerActivity.class);
                startActivity(intent);
            }
        });
        getAssetReportlog();

    }

    private void getAssetReportlog() {
        GetAssetReportlog log= new GetAssetReportlog(this);
        log.execute(ConfigConstant.url+"assetlog/report/"+logID);
    }

    public void displayLogList(JSONArray reportLog) {
        if(reportLog==null) {


        }else {
            String logStr = reportLog.toString();
            gson = new Gson();
            reportLogList=new ArrayList<ReportLogDTO>();
            Type type = new TypeToken<ArrayList<ReportLogDTO>>() {
            }.getType();
            reportLogList = gson.fromJson(logStr, type);
            Log.e("", "Log list:" + reportLogList.size());
            adapter = new ServiceEngineerListAdaptor(getApplicationContext(), reportLogList);
            listView.setAdapter(adapter);
        }
    }
}
