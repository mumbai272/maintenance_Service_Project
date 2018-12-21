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
public class ChargeListAdapter extends BaseAdapter {
    Gson gson;
    private SessionManager session;
    Intent intent;
    String token,role;
    Context context;
    Long log_id,ser_id;
     ArrayList<ReportChargesDTO> list;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportLogDTO> reportLogList;


    public ChargeListAdapter(Context applicationContext,ArrayList<ReportChargesDTO> list, ArrayList<ReportLogDTO> reportLogList, AssetReportDTO assetReportDTO, ReportChargesDTO reportChargesDTO, ReportSpareResponseDTO reportSpareResponseDTO) {
        this.context=applicationContext;
        this.list=list;
        this.reportLogList=reportLogList;
        this.assetReportDTO=assetReportDTO;
        this.reportChargesDTO=reportChargesDTO;
        this.reportSpareResponseDTO=reportSpareResponseDTO;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.size();
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

        View v= View.inflate(context, R.layout.charge_view, null);

        TextView part= (TextView) v.findViewById(R.id.invoiceNo);
        TextView  by= (TextView) v.findViewById(R.id.invoiceDate);
        TextView amt= (TextView) v.findViewById(R.id.grandtotal);
        TextView  taxtype= (TextView) v.findViewById(R.id.tax_type);
        TextView spareTax= (TextView) v.findViewById(R.id.spareTaxType);

        ImageButton delete= (ImageButton) v.findViewById(R.id.delete_charge);
        if(reportChargesDTO.getGrandTotal()!=null) {
            part.setText(reportChargesDTO.getInvoiceNo());
            by.setText(reportChargesDTO.getInvoiceDate());
            amt.setText(reportChargesDTO.getGrandTotal().toString());
            taxtype.setText(reportChargesDTO.getTaxType());
            spareTax.setText(reportChargesDTO.getSpareTaxType());

        }
        /*if(role.equals(ConfigConstant.employeeRole)&& role.equalsIgnoreCase("ACTIVE")){
            delete.setVisibility(View.VISIBLE);
        }*/

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Delete().execute(ConfigConstant.url+"assetlog/report/charges/"+assetReportDTO.getReportId());
            }
        });

        v.setTag(reportChargesDTO.getReportId());
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
