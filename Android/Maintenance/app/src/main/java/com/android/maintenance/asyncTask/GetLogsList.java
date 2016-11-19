package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.LogListActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 09-Nov-16.
 */
public class GetLogsList extends AsyncTask<String, Void, String>{

    Gson gson;
    private SessionManager session;
    public String token, clientID;
    LogListActivity mActivity;

    public GetLogsList(LogListActivity activity){
        this.mActivity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        session = new SessionManager(mActivity);
        HashMap<String, String> user = session.getUserDetails();
        clientID = user.get("KEY_CLIENT_ID");
        token = user.get(SessionManager.KEY_TOKEN);
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
    protected void onPostExecute(String result){
            JSONObject obj;
            JSONArray assignData = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                assignData = obj.getJSONArray("assetLogs");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            BaseResponseDTO assetLogResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (assetLogResponse.getStatusCode() == 1) {
                mActivity.displayLogList(assignData);
            } else if (assetLogResponse.getStatusCode() == -1) {
                Toast.makeText(mActivity, assetLogResponse.getMsg(), Toast.LENGTH_LONG).show();
            }

    }
}
