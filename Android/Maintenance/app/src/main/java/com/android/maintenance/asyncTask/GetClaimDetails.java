package com.android.maintenance.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.activities.ApplyCliamActivity;
import com.android.maintenance.activities.ClaimActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 19-Nov-16.
 */
public class GetClaimDetails  extends AsyncTask<String, Void, String> {

    Gson gson;
    private SessionManager session;
    public String token, clientID;
    ClaimActivity mActivity;
    ApplyCliamActivity bActivity;

    public GetClaimDetails(ClaimActivity activity) {
        this.mActivity = activity;
    }

    public GetClaimDetails(ApplyCliamActivity activity) {
        this.bActivity = activity;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(mActivity instanceof ClaimActivity){
            session = new SessionManager(mActivity);
        }else if(bActivity instanceof ApplyCliamActivity){
            session = new SessionManager(bActivity);
        }

        HashMap<String, String> user = session.getUserDetails();
        clientID = user.get("KEY_CLIENT_ID");
        token = user.get(SessionManager.KEY_TOKEN);
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
        JSONObject obj, claimData = null;
        JSONArray businessExpensesData = null, conveyanceExpensesData = null, miscExpenseDate = null;
        gson = new GsonBuilder().create();
        try {
            obj = new JSONObject(result);
            claimData = obj.getJSONObject("claim");
        //    businessExpensesData = obj.getJSONArray("businessExpenses");
            conveyanceExpensesData = obj.getJSONArray("conveyanceExpenses");
        //    miscExpenseDate = obj.getJSONArray("miscExpenses");
        } catch (Exception e) {
            e.printStackTrace();
        }

        BaseResponseDTO clientResponse = gson.fromJson(result, BaseResponseDTO.class);
        if (clientResponse.getStatusCode() == 1) {
            if(mActivity instanceof ClaimActivity){
                Toast.makeText(mActivity, clientResponse.getMsg(), Toast.LENGTH_LONG).show();
                mActivity.getclaimData(claimData, businessExpensesData, conveyanceExpensesData, miscExpenseDate);
            }else if(bActivity instanceof ApplyCliamActivity){
                bActivity.getclaimData(claimData, businessExpensesData, conveyanceExpensesData, miscExpenseDate);
            }

        } else if (clientResponse.getStatusCode() == -1) {

            if(mActivity instanceof ClaimActivity){
                Toast.makeText(mActivity, clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }else if(bActivity instanceof ApplyCliamActivity){
                Toast.makeText(bActivity, clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
