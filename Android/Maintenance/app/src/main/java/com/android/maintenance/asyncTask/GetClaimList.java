package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AccountantMainActivity;
import com.android.maintenance.activities.ApplyCliamActivity;
import com.android.maintenance.activities.ClaimActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 18-Nov-16.
 */
public class GetClaimList extends AsyncTask<String, Void, String> {

    Gson gson;
    private SessionManager session;
    public String token, clientID;
    ClaimActivity mActivity;
    AccountantMainActivity aActivity;
    public GetClaimList(ClaimActivity activity) {
        this.mActivity = activity;
    }

    public GetClaimList(AccountantMainActivity activity) {
        this.aActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mActivity instanceof ClaimActivity) {
            session = new SessionManager(mActivity);
        }else if(aActivity instanceof  AccountantMainActivity){
            session=new SessionManager(aActivity);
        }
        HashMap<String, String> user = session.getUserDetails();
        clientID = user.get("KEY_CLIENT_ID");
        token = user.get(SessionManager.KEY_TOKEN);
    }

    @Override
    protected String doInBackground(String... param) {
        String result = "";
        ServiceHandlerWS serviceGet = new ServiceHandlerWS();
        Log.e("url" + param[0], "token:" + token);
        result = serviceGet.makeServiceGet(param[0], token);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject obj;
        JSONArray assignData = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            assignData = obj.getJSONArray("claims");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        BaseResponseDTO assetLogResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (assetLogResponse.getStatusCode() == 1) {
            if(mActivity instanceof ClaimActivity){
                mActivity.displayclaimList(assignData);
            }else if(aActivity instanceof AccountantMainActivity){
                aActivity.displayclaimList(assignData);
            }
        } else if (assetLogResponse.getStatusCode() == -1) {
            Toast.makeText(mActivity, assetLogResponse.getMsg(), Toast.LENGTH_LONG).show();
        }

    }
}