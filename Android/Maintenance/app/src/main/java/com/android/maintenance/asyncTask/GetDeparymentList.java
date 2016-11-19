package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AddEmployeeActivity;
import com.android.maintenance.activities.AssetLogActivity;
import com.android.maintenance.activities.AssignLogActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 05-Nov-16.
 */
public class GetDeparymentList extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID;
    private AssignLogActivity bActivity;
    private AddEmployeeActivity mActivity;

    public GetDeparymentList(AddEmployeeActivity activity) {
        this.mActivity = activity;
    }
    public GetDeparymentList(AssignLogActivity activity) {
        this.bActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mActivity instanceof AddEmployeeActivity){
            session = new SessionManager(mActivity);
        }else if(bActivity instanceof AssignLogActivity){
            session = new SessionManager(bActivity);
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
        Log.e("url", "" + param[0]);
        result = serviceGet.makeServiceGet(param[0], token);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject obj;
        JSONArray attrData = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            attrData = obj.getJSONArray("attributes");
        } catch (Exception e) {

        }

        BaseResponseDTO clientResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (clientResponse.getStatusCode() == 1) {
            if(mActivity instanceof AddEmployeeActivity){
                mActivity.getDepartList(attrData);
            }else if(bActivity instanceof AssignLogActivity){
                bActivity.getWorkTypeList(attrData);
            }

        } else if (clientResponse.getStatusCode() == -1) {
            Toast.makeText(mActivity, clientResponse.getMsg(), Toast.LENGTH_LONG).show();

        }

    }
}

