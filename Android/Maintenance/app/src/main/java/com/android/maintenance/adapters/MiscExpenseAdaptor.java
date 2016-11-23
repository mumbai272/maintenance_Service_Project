package com.android.maintenance.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.ClaimActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 19-Nov-16.
 */
public class MiscExpenseAdaptor extends BaseAdapter {
    Context cxt;
    ArrayList<MiscExpenseDTO> misc_exp_list=null;
    MiscExpenseDTO dto;
    Long id;
    Intent intent;
    String token,role;
    private SessionManager session;
    Gson gson;

    public MiscExpenseAdaptor(Context cxt, ArrayList<MiscExpenseDTO> list) {
        this.cxt = cxt;
        this.misc_exp_list = list;
    }


    @Override
    public int getCount() {
        if(misc_exp_list!=null) {
            return misc_exp_list.size();
        }else{
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return misc_exp_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        session = new SessionManager(cxt);
        HashMap<String, String> user = session.getUserDetails();
        role=user.get(SessionManager.KEY_ROLE);
        token = user.get(SessionManager.KEY_TOKEN);

        View v = View.inflate(cxt, R.layout.misc_tab_list, null);

        TextView part = (TextView) v.findViewById(R.id.part);
        TextView on = (TextView) v.findViewById(R.id.billed_on);
        TextView amt = (TextView) v.findViewById(R.id.amount);
        TextView bill_no = (TextView) v.findViewById(R.id.bill_no);
        TextView date = (TextView) v.findViewById(R.id.exp_on);
        Button delete = (Button) v.findViewById(R.id.delete);

        part.setText(misc_exp_list.get(i).getParticulars());
        bill_no.setText(misc_exp_list.get(i).getBillNumber());
        on.setText(misc_exp_list.get(i).getBillDate());
        amt.setText(String.valueOf(misc_exp_list.get(i).getClaimAmount()));
        date.setText(misc_exp_list.get(i).getExpenseDate());

        if(role.equals(ConfigConstant.employeeRole) && role.equalsIgnoreCase("ACTIVE")){
            delete.setVisibility(View.VISIBLE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dto=new MiscExpenseDTO();
                dto= misc_exp_list.get(i);
                id=dto.getExpenseId();
                new Delete().execute(ConfigConstant.url+"claim/misc/expense/"+id);
            }
        });

        v.setTag(misc_exp_list.get(i).getExpenseId());

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
            gson = new Gson();
            BaseResponseDTO machineResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (machineResponse.getStatusCode() == 1) {
                intent=new Intent(cxt,ClaimActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cxt.startActivity(intent);


            } else if (machineResponse.getStatusCode() == -1) {
                Toast.makeText(cxt, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}