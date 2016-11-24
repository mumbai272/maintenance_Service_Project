package com.android.maintenance.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.CreateAssetReportDTO;
import com.android.maintenance.DTO.GetAssetReportDTO;
import com.android.maintenance.DTO.ReportChargesDTO;
import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.CreateAssetReportActivity;
import com.android.maintenance.activities.ServiceEngSpareChargesTabsActivity;
import com.android.maintenance.adapters.ReportAdapter;
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
import java.util.List;

/**
 * Created by anand on 14-Nov-16.
 */
public class ReportTab extends Fragment {

    AssetLogDTO log;
    Gson gson;
    Long rptID;
    Intent intent;
    CreateAssetReportDTO crtDTO;
    ReportAdapter adapter;
    ListView listView;
    ImageButton addreportbtn;
    AssetReportDTO assetReportDTO;
    ArrayList<AssetReportDTO> list;
    private SessionManager session;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO  reportSpareResponseDTO;
    private ArrayList<ReportLogDTO> reportLogList;
    public String token, clientID;
    private ProgressDialog mProgress;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        clientID = user.get("KEY_CLIENT_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        reportLogList=new ArrayList<ReportLogDTO>();
        final Bundle args = getArguments();
        rptID=args.getLong("reportID");
        log= (AssetLogDTO) args.getSerializable("Log");
        crtDTO= (CreateAssetReportDTO) args.getSerializable("reportDTO");

        //  get report
        Log.e("Before Report","Test");
        new GetReport().execute(ConfigConstant.url+"assetlog/report/"+log.getLogId());


    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_tab,container, false);
        listView=(ListView)view.findViewById(R.id.report_list_view);
        addreportbtn = (ImageButton) view.findViewById(R.id.add_report);
        addreportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               /* setClientSpinner();
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog));
                LayoutInflater factory = LayoutInflater.from(getActivity());
                final View f = factory.inflate(R.layout.create_asset_report, null);

                builder.setTitle("Create asset report");
                builder.setView(f);

                Button submit = (Button) f.findViewById(R.id.submit);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });
                builder.show();*/
                intent= new Intent(getActivity(), CreateAssetReportActivity.class);
                intent.putExtra("Log",log);
                startActivity(intent);
            }
        });

        return view;
    }

    private void makeitvisuble() {
    }


    private class GetReport extends AsyncTask<String, Void, String> {

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
            JSONObject obj,assetReport = null,spares = null,reportCharge = null;
            JSONArray  reportLog = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                assetReport = obj.getJSONObject("assetReport");
                reportLog=obj.getJSONArray("reportLog");
                spares=obj.getJSONObject("spares");
                reportCharge=obj.getJSONObject("reportCharge");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            GetAssetReportDTO reportResponse = gson.fromJson(result, GetAssetReportDTO.class);
            if (reportResponse.getStatusCode() == 1) {
                displayReport(assetReport,reportLog,spares,reportCharge);
            } else if (reportResponse.getStatusCode() == -1) {

                Toast.makeText(getActivity(), reportResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayReport(JSONObject assetReport, JSONArray reportLog, JSONObject spares, JSONObject reportCharge) {
        gson = new Gson();
        Type type;
        if (assetReport != null) {
            type = new TypeToken<AssetReportDTO>() {
            }.getType();
            String assetReportStr = assetReport.toString();
            assetReportDTO = gson.fromJson(assetReportStr, type);
        }

        if (spares != null) {
            type = new TypeToken<ReportSpareResponseDTO>() {
            }.getType();
            String sparesStr = spares.toString();
            reportSpareResponseDTO= gson.fromJson(sparesStr, type);
        }


        if(reportLog!=null){
            type = new TypeToken<List<ReportLogDTO>>() {
            }.getType();
            String reportLogStr = reportLog.toString();
            reportLogList = gson.fromJson(reportLogStr, type);
        }

        if(reportCharge!=null){
            type = new TypeToken<ReportChargesDTO>() {
            }.getType();
            String reportChargeStr = reportCharge.toString();
            reportChargesDTO= gson.fromJson(reportChargeStr, type);
        }
        list=new ArrayList<AssetReportDTO>();
        list.add(assetReportDTO);
        if(list.size()>0){
            addreportbtn.setVisibility(View.GONE);
        }
        adapter = new ReportAdapter(getActivity().getApplicationContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Long assignId= (Long)view.getTag();

                intent=new Intent(getActivity(),ServiceEngSpareChargesTabsActivity.class);
                intent.putExtra("assetReportDTO",assetReportDTO);
                intent.putExtra("reportChargesDTO",reportChargesDTO);
                intent.putExtra("reportLogList", reportLogList);
                intent.putExtra("reportSpareResponseDTO",reportSpareResponseDTO);
                startActivity(intent);

            }
        });
    }
}
