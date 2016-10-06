package com.android.maintenance.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.android.maintenance.R;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;

/**
 * Created by anand on 02-Oct-16.
 */
public class ClientApprovalActivity extends ListActivity{

    String approvalUrl="user?status=r";
    private static String token;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_approval);
        intent=getIntent();
        token=intent.getStringExtra("token");
        getApprovalClientList();

    }

    public void getApprovalClientList() {

        new GetClientApprovalList().execute();
    }

    private class GetClientApprovalList extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS servicepost = new ServiceHandlerWS();
            Log.e("url",""+ConfigConstant.url+approvalUrl);
            result = servicepost.makeServiceGet(ConfigConstant.url+approvalUrl,token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("approval list",""+result);
        }

    }
}
