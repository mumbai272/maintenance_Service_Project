package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.MachineListActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 04-Nov-16.
 */
public class GetMachineList extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID;
    private MachineListActivity mActivity;

    public GetMachineList(MachineListActivity activity) {
        this.mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        session = new SessionManager(mActivity);
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);
    }

    @Override
    protected String doInBackground(String... param) {
        String result = "";

        ServiceHandlerWS serviceGet = new ServiceHandlerWS();
        Log.e("url", "" + ConfigConstant.url + "asset");
        result = serviceGet.makeServiceGet(ConfigConstant.url + "asset", token);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject obj;
        JSONArray machineData = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            machineData = obj.getJSONArray("assets");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseResponseDTO machineResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (machineResponse.getStatusCode() == 1) {
            mActivity.displayMachineListView(machineData);
        } else if (machineResponse.getStatusCode() == -1) {
            Toast.makeText(mActivity, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
        }
    }


}


