package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AddNewSpareActivity;
import com.android.maintenance.activities.MachineMakeActivity;
import com.android.maintenance.activities.MachineModelActivity;
import com.android.maintenance.activities.MachineSpareActivity;
import com.android.maintenance.activities.MachineTypeActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 06-Nov-16.
 */
public class MachineAttribute extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID;
    MachineMakeActivity mActivity;
    MachineModelActivity aActivity;
    MachineTypeActivity tActivity;
    MachineSpareActivity sActivity;
    AddNewSpareActivity nActivity;

    public MachineAttribute(MachineMakeActivity activity) {
        this.mActivity = activity;
    }

    public MachineAttribute(AddNewSpareActivity activity) {
        this.nActivity = activity;
    }

    public MachineAttribute(MachineModelActivity activity) {
        this.aActivity = activity;
    }

    public MachineAttribute(MachineTypeActivity activity) {
        this.tActivity = activity;
    }

    public MachineAttribute(MachineSpareActivity activity) {
        this.sActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mActivity instanceof MachineMakeActivity){
          session = new SessionManager(mActivity);
        }else if(aActivity instanceof MachineModelActivity){
            session = new SessionManager(aActivity);
        }else if(tActivity instanceof MachineTypeActivity){
            session = new SessionManager(tActivity);
        }else if(sActivity instanceof MachineSpareActivity){
            session = new SessionManager(sActivity);
        }else if(nActivity instanceof AddNewSpareActivity){
            session = new SessionManager(nActivity);
        }
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);
    }

    @Override
    protected String doInBackground(String... param) {
        String result = "";
        ServiceHandlerWS serviceGet = new ServiceHandlerWS();
       /* if(mActivity instanceof MachineMakeActivity){
            Log.e("url", "" + param[0]);
            result = serviceGet.makeServiceGet(param[0], token);
        }else if(aActivity instanceof MachineModelActivity){
            Log.e("url", "" + param[0]);
            result = serviceGet.makeServiceGet(param[0], token);
        }else if(tActivity instanceof MachineTypeActivity){
            Log.e("url", "" + param[0]);
            result = serviceGet.makeServiceGet(param[0], token);
        }else if(sActivity instanceof MachineSpareActivity){*/
            Log.e("url", "" + param[0]);
            result = serviceGet.makeServiceGet(param[0], token);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject obj;
        JSONArray machineAttr = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            machineAttr = obj.getJSONArray("data");
        } catch (Exception e) {

        }

        BaseResponseDTO machineAttributeResp = gson.fromJson(result, BaseResponseDTO.class);
        if (machineAttributeResp.getStatusCode() == 1) {
          //  Toast.makeText(mActivity, machineAttributeResp.getMsg(), Toast.LENGTH_LONG).show();
            if(mActivity instanceof MachineMakeActivity){
                mActivity.displayMachinemake(machineAttr);
            }else if(aActivity instanceof MachineModelActivity){
                aActivity.displayMachineModel(machineAttr);
            }else if(tActivity instanceof MachineTypeActivity){
                tActivity.displayMachineType(machineAttr);
            }else if(sActivity instanceof MachineSpareActivity){
                sActivity.displayMachineSpare(machineAttr);
            }else if(nActivity instanceof AddNewSpareActivity){
                nActivity.displayMachineSpare(machineAttr);
            }
        } else if (machineAttributeResp.getStatusCode() == -1) {
            Toast.makeText(mActivity, machineAttributeResp.getMsg(), Toast.LENGTH_LONG).show();
        }
    }
}
