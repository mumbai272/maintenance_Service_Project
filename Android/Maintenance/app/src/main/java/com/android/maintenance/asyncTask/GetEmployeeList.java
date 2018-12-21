package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AddNewSerActivity;
import com.android.maintenance.activities.AdminEmployeeListActivity;
import com.android.maintenance.activities.AssignLogActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 04-Nov-16.
 */
public class GetEmployeeList extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID;
    private AdminEmployeeListActivity mActivity;
    private AssignLogActivity bActivity;
    AddNewSerActivity cActivity;

    public GetEmployeeList(AdminEmployeeListActivity activity) {
        this.mActivity = activity;
    }

    public GetEmployeeList(AssignLogActivity activity) {
        this.bActivity = activity;
    }

    public GetEmployeeList(AddNewSerActivity addNewSerActivity) {
        this.cActivity=addNewSerActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mActivity instanceof AdminEmployeeListActivity){
            session = new SessionManager(mActivity);
        }else if(bActivity instanceof AssignLogActivity){
            session = new SessionManager(bActivity);
        }else if(cActivity instanceof AddNewSerActivity){
            session=new SessionManager(cActivity);
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
        Log.e("url", "" + ConfigConstant.url + "user");
        result = serviceGet.makeServiceGet(ConfigConstant.url + "user", token);
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

        BaseResponseDTO employeeResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (employeeResponse.getStatusCode() == 1) {
            if(mActivity instanceof AdminEmployeeListActivity){
                mActivity.displayEmpList(dataUsers);
            }else if(bActivity instanceof AssignLogActivity){
                bActivity.displayEmpList(dataUsers);
            }else if(cActivity instanceof AddNewSerActivity){
                cActivity.displayEmpList(dataUsers);
            }

        } else if (employeeResponse.getStatusCode() == -1) {
            Toast.makeText(mActivity, employeeResponse.getMsg(), Toast.LENGTH_LONG).show();
        }
    }
}
