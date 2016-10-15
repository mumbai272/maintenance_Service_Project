package com.android.maintenance.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.ApproveUserDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.LoginResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 08-Oct-16.
 */
public class UserApprovalDetailsActivity extends Activity implements View.OnClickListener{

    private ProgressDialog mProgress;
    Context context = this;
    Intent intent;
    Long userId,role=1L,clientid=1L;
    Button approve,reject;
    TextView user_name,user_email,user_mobile,user_client;
    ArrayList<UserDTO> userList;
    private SessionManager session;
    String token;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_approval_detail);
        user_name = (TextView) findViewById(R.id.approval_user_data);
        user_email = (TextView) findViewById(R.id.approval_email_data);
        user_mobile = (TextView) findViewById(R.id.approval_mobileno_data);
        user_client = (TextView) findViewById(R.id.approval_client_data);
        approve = (Button) findViewById(R.id.approve_button);
        reject = (Button) findViewById(R.id.reject_button);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> users = session.getUserDetails();
        token=users.get(SessionManager.KEY_TOKEN);
        Log.e("","Token:"+token);

        approve.setOnClickListener(this);
        reject.setOnClickListener(this);


        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        userList = new ArrayList<UserDTO>();
        intent = getIntent();
        userList = (ArrayList<UserDTO>) intent.getSerializableExtra("arrayList");
        intent.getExtras();
        userId =  intent.getLongExtra("user_id",1);

        for(UserDTO user : userList) {
            if(user.getUserId().equals(userId)){
                String fullname=user.getUserName();
                String email=user.getEmailId();
                String mobile=user.getPhoneno();
                user_name.setText(fullname);
                user_email.setText(email);
                user_mobile.setText(mobile);
            }
        }


    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.approve_button:
                try {
                String json="";
                ObjectMapper mapper = new ObjectMapper();
                ApproveUserDTO approveUserDTO= new ApproveUserDTO(userId,role,clientid);
                json=mapper.writeValueAsString(approveUserDTO);
                mProgress.show();
                new ApproveUser().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.reject_button:
                break;
        }

    }


    private class ApproveUser  extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS serviceput= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= serviceput.makeServicePut(ConfigConstant.url+"user/approve",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO approvalResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(approvalResponse.getStatusCode()==1){
                mProgress.dismiss();
                UpdateTheUI();

            }else if(approvalResponse.getStatusCode()==-1){
                mProgress.dismiss();
                Toast.makeText(getApplicationContext(),approvalResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void UpdateTheUI() {
            Log.e("inside Alert","");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Approval");
            alertDialogBuilder
                    .setMessage("Client approval is successful.")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            Intent intent= new Intent(UserApprovalDetailsActivity.this,UserApprovalActivity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
    }
}
