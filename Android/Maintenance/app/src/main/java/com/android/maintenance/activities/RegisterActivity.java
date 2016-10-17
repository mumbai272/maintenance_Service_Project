package com.android.maintenance.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.RegisterDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;

public class RegisterActivity extends AppCompatActivity {

    EditText userName,email,password,clientName,phoneno;
    String userNameStr,emailIdStr,passwordStr,phonenoStr,clientNameStr,nameStr;
    Button registerBtn;
    private String registerUrl="user/register";
    Context context = this;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        userName=(EditText)findViewById(R.id.user_name);
        email=(EditText) findViewById(R.id.client_email);
        clientName=(EditText)findViewById(R.id.clientname);
        password=(EditText)findViewById(R.id.password);
        phoneno=(EditText)findViewById(R.id.client_phone);
        registerBtn=(Button)findViewById(R.id.btnSignup);

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userNameStr=userName.getText().toString();
                emailIdStr=email.getText().toString();
                passwordStr=password.getText().toString();
                phonenoStr=phoneno.getText().toString();
                clientNameStr=clientName.getText().toString();
                nameStr=userName.getText().toString();
                    if(Utility.isNotNull(userNameStr)&&Utility.isNotNull(emailIdStr)&&Utility.isNotNull(passwordStr)&&Utility.isNotNull(nameStr)&&Utility.isNotNull(clientNameStr)&&Utility.isNotNull(phonenoStr)){
                    mProgress.show();
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        String json = "";
                        RegisterDTO registerDTO= new RegisterDTO( userNameStr,  passwordStr, nameStr ,  phonenoStr,  emailIdStr,  clientNameStr);
                        json=mapper.writeValueAsString(registerDTO);
                        new SignUp().execute(json);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please don't leave any field blank", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private class SignUp extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            result= servicepost.makeServicePost(ConfigConstant.url+registerUrl,param[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO responseDTO=gson.fromJson(result,BaseResponseDTO.class);
            if(responseDTO.getStatusCode()==1){
                mProgress.dismiss();
             //   Toast.makeText(getApplicationContext(),responseDTO.getMsg(), Toast.LENGTH_LONG).show();
                showAlertOfApproval();
            }else if(responseDTO.getStatusCode()==-1){
                mProgress.dismiss();
                Toast.makeText(getApplicationContext(),responseDTO.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showAlertOfApproval() {
        Log.e("inside Alert","");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Approval");
        alertDialogBuilder
                .setMessage("Approval for register is sent to admin, After the approval you will get Email or SMS intimation.")
                .setCancelable(false)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
