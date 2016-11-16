package com.android.maintenance.adapters;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.MachineDetailDTO;
import com.android.maintenance.R;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AssetLogActivity;
import com.android.maintenance.activities.MachineListActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by anand on 25-Oct-16.
 */
public class MachineListAdapter extends BaseAdapter {

    private Context cxt;
    Intent intent;
    private ArrayList<MachineDetailDTO> machineList;
    String token;
    Long assetId;
    MachineDetailDTO machine;
    Gson gson;

    public MachineListAdapter(Context cxt, ArrayList<MachineDetailDTO> machineList,String token) {
        this.cxt = cxt;
        this.machineList = machineList;
        this.token=token;
    }

    @Override
    public int getCount() {
        return machineList.size();
    }

    @Override
    public Object getItem(int i) {
        return machineList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.machine_list_view_detail, null);
        TextView machine_title = (TextView) v.findViewById(R.id.machine_title);
        TextView address = (TextView) v.findViewById(R.id.machine_address);
        Button log = (Button) v.findViewById(R.id.log);
        Button delete =(Button) v.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                machine=new MachineDetailDTO();
                machine= machineList.get(i);
                assetId=machine.getAssetId();
                new DeleteAsset().execute(ConfigConstant.url+"asset/"+assetId);

            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                machine=new MachineDetailDTO();
                machine= machineList.get(i);
                assetId=machine.getAssetId();
                intent =new Intent(cxt, AssetLogActivity.class);
                intent.putExtra("assetID",assetId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                cxt.startActivity(intent);
            }
        });

        machine_title.setText(machineList.get(i).getAssetDescription());
        //location.setText();
        address.setText(machineList.get(i).getLocation());

        v.setTag(machineList.get(i).getAssetId());
        return v;
    }

    private class DeleteAsset extends AsyncTask<String, Void, String> {

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
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        cxt);
                alertDialogBuilder.setTitle("Delete");
                alertDialogBuilder
                        .setMessage(machineResponse.getMsg())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                intent= new Intent(cxt, MachineListActivity.class);
                                cxt.startActivity(intent);

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
               intent= new Intent(cxt, MachineListActivity.class);
                cxt.startActivity(intent);
            } else if (machineResponse.getStatusCode() == -1) {
                Toast.makeText(cxt, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}

