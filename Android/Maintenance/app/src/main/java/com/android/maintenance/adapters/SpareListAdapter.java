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
import com.android.maintenance.DTO.ReportSpareDTO;
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
public class SpareListAdapter extends BaseAdapter{
    Gson gson;
    private SessionManager session;
    Intent intent;
    String token,role;
    Context context;
    ReportSpareDTO dto;
    Long spare_id;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportLogDTO> reportLogList;


    public SpareListAdapter(Context applicationContext, ArrayList<ReportLogDTO> reportLogList, AssetReportDTO assetReportDTO, ReportChargesDTO reportChargesDTO, ReportSpareResponseDTO reportSpareResponseDTO) {

        this.context=applicationContext;
        this.reportLogList=reportLogList;
        this.assetReportDTO=assetReportDTO;
        this.reportChargesDTO=reportChargesDTO;
        this.reportSpareResponseDTO=reportSpareResponseDTO;
    }


    @Override
    public int getCount() {
        return reportSpareResponseDTO.getSpares().size();
    }

    @Override
    public Object getItem(int i) {
        return reportSpareResponseDTO.getSpares().get(i);
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

        View v= View.inflate(context, R.layout.listview_spare, null);

        TextView part= (TextView) v.findViewById(R.id.spare_name);
        TextView amt= (TextView) v.findViewById(R.id.amt_total);

        ImageButton delete= (ImageButton) v.findViewById(R.id.delete_spare);

        part.setText(reportSpareResponseDTO.getSpares().get(i).getSpaceName());
        amt.setText(reportSpareResponseDTO.getSpareTotal().toString());


        /*if(role.equals(ConfigConstant.employeeRole)&& role.equalsIgnoreCase("ACTIVE")){
            delete.setVisibility(View.VISIBLE);
        }*/

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spare_id= reportSpareResponseDTO.getSpares().get(i).getSpareId();

                new Delete().execute(ConfigConstant.url+"assetlog/report/spare/"+spare_id);
            }
        });

        v.setTag(reportSpareResponseDTO.getSpares().get(i).getSpareId());
        return v;
    }

    private class Delete extends AsyncTask<String, Void, String> {

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
                intent.putExtra("assetReportDTO",assetReportDTO);
                intent.putExtra("reportLogList",reportLogList);
                intent.putExtra("reportChargesDTO",reportChargesDTO);
                intent.putExtra("reportSpareResponseDTO",reportSpareResponseDTO);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else if (machineResponse.getStatusCode() == -1) {
                Toast.makeText(context, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }

}
