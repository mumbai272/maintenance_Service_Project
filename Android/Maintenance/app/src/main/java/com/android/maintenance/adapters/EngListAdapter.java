package com.android.maintenance.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ReportChargesDTO;
import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.ServiceEngSpareChargesTabsActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 22-Nov-16.
 */
public class EngListAdapter extends BaseAdapter{
    Gson gson;
    private SessionManager session;
    Intent intent;
    String token,role;
    Context context;
    ReportLogDTO dto;
    Long log_id,ser_id;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportLogDTO> reportLogList;


    public EngListAdapter(Context cxt, ArrayList<ReportLogDTO>  list,AssetReportDTO assetReportDTO, ReportChargesDTO reportChargesDTO, ReportSpareResponseDTO reportSpareResponseDTO) {
        this.context = cxt;
        this.reportLogList = list;
        this.assetReportDTO=assetReportDTO;
        this.reportChargesDTO=reportChargesDTO;
        this.reportSpareResponseDTO=reportSpareResponseDTO;
    }


    @Override
    public int getCount() {
        return reportLogList.size();
    }

    @Override
    public Object getItem(int i) {
        return reportLogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();
        token = user.get(SessionManager.KEY_TOKEN);
        role=user.get(SessionManager.KEY_ROLE);

        View v= View.inflate(context, R.layout.listview_eng, null);

        TextView part= (TextView) v.findViewById(R.id.time_in);
        TextView  by= (TextView) v.findViewById(R.id.time_out);
        TextView amt= (TextView) v.findViewById(R.id.eng_ser);

        ImageButton delete= (ImageButton) v.findViewById(R.id.delete_ser);

        part.setText(reportLogList.get(i).getTimeIn());
        by.setText(reportLogList.get(i).getTimeOut());
        amt.setText(reportLogList.get(i).getServiceEngineer().toString());


        /*if(role.equals(ConfigConstant.employeeRole)&& role.equalsIgnoreCase("ACTIVE")){
            delete.setVisibility(View.VISIBLE);
        }*/

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dto=new ReportLogDTO();
                dto= reportLogList.get(i);
                log_id=assetReportDTO.getLogId();
                ser_id=dto.getServiceEngineer();
                new DeleteSerEng().execute(ConfigConstant.url+"assetlog/report/log/"+log_id+"/"+ser_id);
            }
        });

        v.setTag(reportLogList.get(i).getReportId());
        return v;
    }

    private class DeleteSerEng extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS serviceGet = new ServiceHandlerWS();
            Log.e("url", "" + param[0]);
            result = serviceGet.makeServiceDetele( param[0], token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson= new Gson();
            BaseResponseDTO machineResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (machineResponse.getStatusCode() == 1) {
                intent=new Intent(context,ServiceEngSpareChargesTabsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("assetReportDTO",assetReportDTO);
                intent.putExtra("reportLogList",reportLogList);
                intent.putExtra("reportChargesDTO",reportChargesDTO);
                intent.putExtra("reportSpareResponseDTO",reportSpareResponseDTO);
                context.startActivity(intent);
            } else if (machineResponse.getStatusCode() == -1) {
                Toast.makeText(context, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}

