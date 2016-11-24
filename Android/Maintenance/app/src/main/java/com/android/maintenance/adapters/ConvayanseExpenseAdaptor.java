package com.android.maintenance.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.CliamDetailsActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 19-Nov-16.
 */
public class ConvayanseExpenseAdaptor extends BaseAdapter {
    Context context;
    GetClaimListDTO claim;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;
    Long id;
    Gson gson;
    private SessionManager session;
    Intent intent;
    String token,role;



    public ConvayanseExpenseAdaptor(Context applicationContext, ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list, ArrayList<MiscExpenseDTO> misc_exp_list, ArrayList<BusinessDevExpenseDTO> business_exp_list, GetClaimListDTO claim) {
        this.context=applicationContext;
        this.conven_exp_list=conven_exp_list;
        this.misc_exp_list=misc_exp_list;
        this.business_exp_list=business_exp_list;
        this.claim=claim;
    }


    @Override
    public int getCount() {
        return conven_exp_list.size();
    }

    @Override
    public Object getItem(int i) {
        return conven_exp_list.get(i);
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

        part.setText(conven_exp_list.get(i).getTravelFrom()+ " To "+conven_exp_list.get(i).getTravelTo());
        by.setText(conven_exp_list.get(i).getModeOfTransport());
        amt.setText(String.valueOf(conven_exp_list.get(i).getClaimAmount()));
        date.setText(conven_exp_list.get(i).getExpenseDate());

        if(role.equals(ConfigConstant.employeeRole)&& role.equalsIgnoreCase("ACTIVE")){
            delete.setVisibility(View.VISIBLE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              id=conven_exp_list.get(i).getClaimId();
                new Delete().execute(ConfigConstant.url+"claim/conveyance/expense/"+id);
            }
        });

        v.setTag(conven_exp_list.get(i).getExpenseId());
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

                intent=new Intent(context,CliamDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ClimDTO",claim);
                intent.putExtra("business_exp_list", business_exp_list);
                intent.putExtra("conven_exp_list", conven_exp_list);
                intent.putExtra("misc_exp_list", misc_exp_list);
                context.startActivity(intent);

            } else if (machineResponse.getStatusCode() == -1) {
                Toast.makeText(context, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
