package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.EmployeeMainActivity;
import com.android.maintenance.activities.UserMainActivity;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 05-Nov-16.
 */
public class GetMachinesForUser extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID,role;
    private UserMainActivity uActivity;
    private EmployeeMainActivity eActivity;

    public GetMachinesForUser(UserMainActivity activity) {
        this.uActivity = activity;
    }

    public GetMachinesForUser(EmployeeMainActivity activity) {
        this.eActivity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(uActivity instanceof UserMainActivity){
            session = new SessionManager(uActivity);
        }else if(eActivity instanceof EmployeeMainActivity){
            session = new SessionManager(eActivity);
        }

        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        role =user.get(SessionManager.KEY_ROLE);
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
        } catch (Exception e) {

        }

        BaseResponseDTO machineResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (machineResponse.getStatusCode() == 1) {
           if(role.equals(ConfigConstant.userRole)){
                uActivity.displayClientListView(machineData);
            }else if(role.equals(ConfigConstant.employeeRole)){
                eActivity.displayClientListView(machineData);
            }

        } else if (machineResponse.getStatusCode() == -1) {
            Toast.makeText(uActivity, machineResponse.getMsg(), Toast.LENGTH_LONG).show();
        }

    }

}
