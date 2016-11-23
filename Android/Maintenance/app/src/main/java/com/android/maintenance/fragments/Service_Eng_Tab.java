package com.android.maintenance.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ReportChargesDTO;
import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AddNewSerActivity;
import com.android.maintenance.adapters.EngListAdapter;
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
 * Created by anand on 21-Nov-16.
 */
public class Service_Eng_Tab extends android.support.v4.app.Fragment {

    private SessionManager session;
    String role,token;
    EngListAdapter adapter;
    Intent intent;
    ListView listView;
    ImageButton add;
    Gson gson;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportLogDTO> reportLogList;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);
        token=user.get(SessionManager.KEY_TOKEN);
        reportLogList=new ArrayList<ReportLogDTO>();
        final Bundle args = getArguments();
        assetReportDTO= (AssetReportDTO) args.getSerializable("assetReportDTO");
        reportLogList= (ArrayList<ReportLogDTO>) args.getSerializable("reportLogList");
        reportChargesDTO= (ReportChargesDTO) args.getSerializable("reportChargesDTO");
        reportSpareResponseDTO= (ReportSpareResponseDTO) args.getSerializable("reportSpareResponseDTO");


        new GetServiceEng().execute(ConfigConstant.url+"assetlog/report/log/"+assetReportDTO.getReportId());
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.service_eng_tab,container, false);
            listView=(ListView)view.findViewById(R.id.serv_eng_list);
            add=(ImageButton)view.findViewById(R.id.add_eng);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent=new Intent(getActivity(),AddNewSerActivity.class);
                    intent.putExtra("assetReportDTO",assetReportDTO);
                    intent.putExtra("reportLogList",reportLogList);
                    intent.putExtra("reportChargesDTO",reportChargesDTO);
                    intent.putExtra("reportSpareResponseDTO",reportSpareResponseDTO);
                    startActivity(intent);
                }
            });


        return view;

    }

    private class GetServiceEng extends AsyncTask<String, Void, String> {

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
                logData = obj.getJSONArray("reportLog");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            BaseResponseDTO assetLogResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (assetLogResponse.getStatusCode() == 1) {
                displayServiceEngList(logData);
            } else if (assetLogResponse.getStatusCode() == -1) {
                Toast.makeText(getActivity(), assetLogResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayServiceEngList(JSONArray logData) {
        gson = new Gson();
        Type type;
       if(logData!=null) {
            String logStr = logData.toString();
            type = new TypeToken<ArrayList<ReportLogDTO>>() {
            }.getType();
           reportLogList=new ArrayList<ReportLogDTO>();
            reportLogList = gson.fromJson(logStr, type);
            Log.e("", "Log list:" + reportLogList.size());
        }
        adapter = new EngListAdapter(getActivity().getApplicationContext(), reportLogList,assetReportDTO,reportChargesDTO,reportSpareResponseDTO);
        listView.setAdapter(adapter);

    }
}
