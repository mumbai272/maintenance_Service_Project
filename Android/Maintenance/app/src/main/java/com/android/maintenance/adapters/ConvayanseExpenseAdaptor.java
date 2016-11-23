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
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
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
public class ConvayanseExpenseAdaptor extends BaseAdapter {
    Context context;
    ArrayList<ClaimConveyanceExpenseDTO> conv_exp_list=null;
    ClaimConveyanceExpenseDTO dto;
    Long id;
    Gson gson;
    private SessionManager session;
    Intent intent;
    String token,role;

    public ConvayanseExpenseAdaptor(Context cxt,  ArrayList<ClaimConveyanceExpenseDTO> list) {
        this.context = cxt;
        this.conv_exp_list = list;
    }


    @Override
    public int getCount() {
        return conv_exp_list.size();
    }

    @Override
    public Object getItem(int i) {
        return conv_exp_list.get(i);
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

        View v= View.inflate(context, R.layout.conveyanse_tab_list, null);

        TextView  part= (TextView) v.findViewById(R.id.li_part);
        TextView  by= (TextView) v.findViewById(R.id.by);
        TextView amt= (TextView) v.findViewById(R.id.li_amount);
        TextView  date= (TextView) v.findViewById(R.id.date_on);
        Button delete= (Button) v.findViewById(R.id.delete);

        part.setText(conv_exp_list.get(i).getTravelFrom()+ " To "+conv_exp_list.get(i).getTravelTo());
        by.setText(conv_exp_list.get(i).getModeOfTransport());
        amt.setText(String.valueOf(conv_exp_list.get(i).getClaimAmount()));
        date.setText(conv_exp_list.get(i).getExpenseDate());

        if(role.equals(ConfigConstant.employeeRole)&& role.equalsIgnoreCase("ACTIVE")){
            delete.setVisibility(View.VISIBLE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dto=new ClaimConveyanceExpenseDTO();
                dto= conv_exp_list.get(i);
                id=dto.getExpenseId();
                new Delete().execute(ConfigConstant.url+"claim/conveyance/expense/"+id);
            }
        });

        v.setTag(conv_exp_list.get(i).getExpenseId());
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

                intent=new Intent(context,ClaimActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            } else if (machineResponse.getStatusCode() == -1) {
                Toast.makeText(context, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
