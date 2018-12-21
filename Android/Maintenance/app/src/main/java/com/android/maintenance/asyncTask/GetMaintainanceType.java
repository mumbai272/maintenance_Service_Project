package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.MaintenanceTypeDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.AssetLogActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 08-Nov-16.
 */
public class GetMaintainanceType extends AsyncTask<String, Void, String> {
    Gson gson;
    private SessionManager session;
    public String token, userID,cmpId;
    AssetLogActivity activity;
    ArrayList<MaintenanceTypeDTO> typeList;

    public GetMaintainanceType(AssetLogActivity assetLogActivity) {
        this.activity=assetLogActivity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        session = new SessionManager(activity);
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        cmpId=user.get("KEY_COMPANY_ID");
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
    protected void onPostExecute(String result){
        JSONObject obj;
        JSONArray data = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            data = obj.getJSONArray("maintenanceTypes");
        } catch (Exception e) {
            e.printStackTrace();
        }

        BaseResponseDTO typeResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (typeResponse.getStatusCode() == 1) {
            if(data==null){
                Toast.makeText(activity, "NO Maintenance Type found in DB", Toast.LENGTH_LONG).show();
            }else {
                String TypeDataStr = data.toString();
                Log.e("sds","TypeDataStr:"+TypeDataStr);
                gson = new GsonBuilder().create();
                Type type = new TypeToken<ArrayList<MaintenanceTypeDTO>>() {
                }.getType();
                typeList=new ArrayList<MaintenanceTypeDTO>();
                typeList= gson.fromJson(TypeDataStr, type);
                Log.e("ds","typeList:"+typeList.size());
                activity.getTypeList(typeList);
            }
        } else if (typeResponse.getStatusCode() == -1) {
            Toast.makeText(activity, typeResponse.getMsg(), Toast.LENGTH_LONG).show();

        }
    }

}
