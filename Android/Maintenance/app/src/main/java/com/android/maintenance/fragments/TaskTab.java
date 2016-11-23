package com.android.maintenance.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.DTO.AssignedUserDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.GetAssignedAssetLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AssignLogActivity;
import com.android.maintenance.activities.StartStopActivity;
import com.android.maintenance.activities.TaskAndReportTabActivity;
import com.android.maintenance.adapters.AssignedLogListAdapter;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 14-Nov-16.
 */
public class TaskTab extends android.support.v4.app.Fragment {

    Gson gson;
    public String token, clientID;
    Intent intent;
    AssetLogDTO log;
    AssignedLogListAdapter adapter;
    ListView listView;
    String role;
    private SessionManager session;
    ArrayList<GetAssignedAssetLogDTO> assignlogList;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        clientID = user.get("KEY_CLIENT_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        role = user.get(SessionManager.KEY_ROLE);

        final Bundle args = getArguments();
        log= (AssetLogDTO) args.getSerializable("Log");

        new AssignedLog().execute(ConfigConstant.url+"asset/logs/assign/"+log.getLogId());
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_tab,container, false);
        listView = (ListView)view.findViewById(R.id.listView_tasks);
        ImageButton button = (ImageButton) view.findViewById(R.id.add_task);

        if(role.equals(ConfigConstant.adminRole)){
            button.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent= new Intent(getActivity(),TaskAndReportTabActivity.class);
                intent.putExtra("Log",log);
                startActivity(intent);
            }
        });
        return view;
    }



    private class AssignedLog extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS serviceGet = new ServiceHandlerWS();
            Log.e("url"+param[0], "token" +token);
            result = serviceGet.makeServiceGet(param[0], token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj;
            JSONArray logData = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                logData = obj.getJSONArray("assetLogs");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            BaseResponseDTO assetLogResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (assetLogResponse.getStatusCode() == 1) {
                displayAssignedLogList(logData);
            } else if (assetLogResponse.getStatusCode() == -1) {
                Toast.makeText(getActivity(), assetLogResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
        }

    private void displayAssignedLogList(JSONArray logData) {
        Gson gson = new Gson();
        Type type;
        if(logData==null) {

        }else {
            JSONObject log = null;
            for (int a = 0; a < logData.length(); a++) {
                try {
                    JSONObject obj = logData.getJSONObject(a);
                    log = obj.getJSONObject("assignedTo");
                    type = new TypeToken<AssignedUserDTO>() {
                    }.getType();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            String logStr = logData.toString();
            type = new TypeToken<ArrayList<GetAssignedAssetLogDTO>>() {
            }.getType();
            assignlogList = gson.fromJson(logStr, type);
            Log.e("", "Log list:" + assignlogList.size());

            adapter = new AssignedLogListAdapter(getActivity().getApplicationContext(), assignlogList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Long assignId= (Long)view.getTag();
                    intent=new Intent(getActivity(),StartStopActivity.class);
                    intent.putExtra("assignId",assignId);
                    intent.putExtra("assignList",assignlogList);
                    startActivity(intent);
                }
            });

        }
    }
}
