package com.android.maintenance.asyncTask;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.AddressDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AdminMainActivity;
import com.android.maintenance.activities.ClientDetailsActivity;
import com.android.maintenance.activities.ClientEditDetailsActivity;
import com.android.maintenance.activities.CreateAssetReportActivity;
import com.android.maintenance.adapters.ClientListAdapter;
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
public class GetClientList extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID,cmpID;
    private AdminMainActivity mActivity;
    private CreateAssetReportActivity cActivity;

    public GetClientList(CreateAssetReportActivity activity) {
        this.cActivity = activity;
    }


    public GetClientList(AdminMainActivity activity) {
        this.mActivity = activity;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mActivity instanceof AdminMainActivity){
            session = new SessionManager(mActivity);
        }else  if(cActivity instanceof CreateAssetReportActivity){
            session = new SessionManager(cActivity);
        }

        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        cmpID= user.get("KEY_COMPANY_ID");
        Log.e("user_ id:" + userID, "Token:" + token);
    }

    @Override
    protected String doInBackground(String... param) {
        String result = "";

        ServiceHandlerWS serviceGet = new ServiceHandlerWS();
        Log.e("url", "" + ConfigConstant.url + "client/"+cmpID+"?fetchAddress=true");
        result = serviceGet.makeServiceGet(ConfigConstant.url + "client/"+cmpID+"?fetchAddress=true", token);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        JSONObject obj;
        JSONArray companyData = null, addressData = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            companyData = obj.getJSONArray("companys");
        } catch (Exception e) {

        }

        BaseResponseDTO clientResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (clientResponse.getStatusCode() == 1) {
            if(mActivity instanceof AdminMainActivity){
                mActivity.displayClientListView(companyData);
            }else  if(cActivity instanceof CreateAssetReportActivity){
                cActivity.displayClientListView(companyData);
            }

        } else if (clientResponse.getStatusCode() == -1) {
            Toast.makeText(mActivity, clientResponse.getMsg(), Toast.LENGTH_LONG).show();

        }
    }

}

    
