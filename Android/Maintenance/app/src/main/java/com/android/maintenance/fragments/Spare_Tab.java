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
import com.android.maintenance.DTO.ReportSpareDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AddNewSpareActivity;
import com.android.maintenance.adapters.EngListAdapter;
import com.android.maintenance.adapters.SpareListAdapter;
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
public class Spare_Tab extends android.support.v4.app.Fragment {

    private SessionManager session;
    String role,token;
    Double total;
    SpareListAdapter adapter;
    Intent intent;
    Gson gson;
    ListView listView;
    ImageButton add;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportSpareDTO> reportSpareDTOList;
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
        total=reportSpareResponseDTO.getSpareTotal();
        new GetSpareData().execute(ConfigConstant.url+"assetlog/report/spare/"+assetReportDTO.getReportId());
    }

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spare_tab,container, false);
        listView=(ListView)view.findViewById(R.id.spare_list);
        add=(ImageButton)view.findViewById(R.id.add_spare);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(),AddNewSpareActivity.class);
                intent.putExtra("assetReportDTO",assetReportDTO);
                intent.putExtra("reportLogList",reportLogList);
                intent.putExtra("reportChargesDTO",reportChargesDTO);
                intent.putExtra("reportSpareResponseDTO",reportSpareResponseDTO);
                startActivity(intent);
            }
        });


        return view;

    }

    private class GetSpareData extends AsyncTask<String, Void, String> {

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
            Double totalObj = null;
            JSONArray logData = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                logData = obj.getJSONArray("spares");
                totalObj=obj.getDouble("spareTotal");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            BaseResponseDTO assetLogResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (assetLogResponse.getStatusCode() == 1) {
                displaySpareList(logData,totalObj);
            } else if (assetLogResponse.getStatusCode() == -1) {
                Toast.makeText(getActivity(), assetLogResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displaySpareList(JSONArray logData,Double totalObj) {

        gson = new Gson();
        Type type;
        if(logData!=null) {
            String logStr = logData.toString();
            type = new TypeToken<ArrayList<ReportSpareDTO>>() {
            }.getType();
            total=totalObj;
            reportSpareDTOList = gson.fromJson(logStr, type);
            reportSpareResponseDTO=new ReportSpareResponseDTO();
            reportSpareResponseDTO.setSpares(reportSpareDTOList);
            reportSpareResponseDTO.setSpareTotal(total);
            Log.e("", "Log list:" + reportLogList.size());
        }

       // reportSpareDTOList=new  ArrayList<ReportSpareDTO>();

       // reportSpareDTOList= (ArrayList<ReportSpareDTO>) reportSpareResponseDTO.getSpares();
        adapter = new SpareListAdapter(getActivity().getApplicationContext(), reportLogList,assetReportDTO,reportChargesDTO,reportSpareResponseDTO);
        listView.setAdapter(adapter);
    }
}
