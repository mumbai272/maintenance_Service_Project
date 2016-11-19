package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AddEmployeeActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 05-Nov-16.
 */
public class GetDesignationList extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID,cmpID;
    private AddEmployeeActivity mActivity;

    public GetDesignationList(AddEmployeeActivity activity) {
        this.mActivity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        session = new SessionManager(mActivity);
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        cmpID=user.get("KEY_COMPANY_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);
    }

    @Override
    protected String doInBackground(String... param) {
        String result = "";

        ServiceHandlerWS serviceGet = new ServiceHandlerWS();
        Log.e("url", "" + ConfigConstant.url + "company/attributes/designation/"+cmpID);
        result = serviceGet.makeServiceGet(ConfigConstant.url + "company/attributes/designation/"+cmpID, token);
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
            mActivity.getDesignList(attrData);
        } else if (clientResponse.getStatusCode() == -1) {
            Toast.makeText(mActivity, clientResponse.getMsg(), Toast.LENGTH_LONG).show();

        }

    }
}
