package com.android.maintenance.asyncTask;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AdminMainActivity;
import com.android.maintenance.activities.UserApprovalActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 04-Nov-16.
 */
public class GetUserApprovalList extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID;
    private UserApprovalActivity mActivity;

    public GetUserApprovalList(UserApprovalActivity activity) {
      this.mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        session = new SessionManager(mActivity);
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);
    }

    @Override
    protected String doInBackground(String... param) {
        String result = "";
        ServiceHandlerWS servicepost = new ServiceHandlerWS();
        Log.e("url", "" + ConfigConstant.url + "user?status=r");
        result = servicepost.makeServiceGet(ConfigConstant.url + "user?status=r", token);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject obj;
        JSONArray dataUsers = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            dataUsers = obj.getJSONArray("users");
            //  Log.e("users list",""+dataUsers);
        } catch (Exception e) {

        }

        BaseResponseDTO approvalResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (approvalResponse.getStatusCode() == 1) {
            mActivity.navigateToApprovalList(dataUsers);
        } else if (approvalResponse.getStatusCode() == -1) {
            Toast.makeText(mActivity, approvalResponse.getMsg(), Toast.LENGTH_LONG).show();
        }
    }

}
