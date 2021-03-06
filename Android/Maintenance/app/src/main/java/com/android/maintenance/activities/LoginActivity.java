package com.android.maintenance.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.CompanyDTO;
import com.android.maintenance.DTO.LoginInputDTO;
import com.android.maintenance.DTO.LoginResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by anand on 16-Sep-16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static String signinURL = ConfigConstant.url + "login" ;
    private SessionManager session;
    private ProgressDialog mProgress;
    private static final String TAG="Mymessage";
    EditText emailET,pwdET;
    Button loginBTN,registerBTN;
    String email,password;
    TextView forgot_password_text,error_str;
    Intent intent;
    UserDTO userDTO;
    Context context = this;
    public String token, userID,roleStr,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        session=new SessionManager(this);

        forgot_password_text=(TextView) findViewById(R.id.forgotpassword);
        emailET=(EditText)findViewById(R.id.email);
        pwdET=(EditText)findViewById(R.id.password);
        error_str=(TextView) findViewById(R.id.login_error);
        loginBTN=(Button)findViewById(R.id.btnLogin);
        registerBTN=(Button)findViewById(R.id.btnRegister);
        registerBTN.setOnClickListener(this);
        loginBTN.setOnClickListener(this);
        forgot_password_text.setOnClickListener(this);

        //Toast.makeText(getApplicationContext(), "User LoginActivity Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        if(session.isLoggedIn()){
            HashMap<String, String> user = session.getUserDetails();
            userID = user.get("KEY_USER_ID");
            token = user.get(SessionManager.KEY_TOKEN);
            roleStr= user.get(SessionManager.KEY_ROLE);
            status=user.get(SessionManager.KEY_STATUS);
           // userDTO= new UserDTO(Long.parseLong(userID),user.get(SessionManager.KEY_USER_NAME),user.get(SessionManager.KEY_FIRST_NAME),"",user.get(SessionManager.KEY_PHONE_NO),user.get(SessionManager.KEY_EMAIL),user.get(SessionManager.KEY_LAST_NAME),user.get(SessionManager.KEY_ROLE),user.get(SessionManager.KEY_GENDER),"" ,status);
            if(roleStr.equals(ConfigConstant.adminRole)){
                if(status.equals("N")) {
                    intent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                    finish();
                }
            }else if(roleStr.equals(ConfigConstant.employeeRole)){
                if(status.equals("N")) {
                    intent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setFlags(Intent.FLAG_FROM_BACKGROUND);
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(LoginActivity.this, EmployeeMainActivity.class));
                    finish();
                }
            }else if(roleStr.equals(ConfigConstant.userRole)){
                if(status.equals("N")) {
                    intent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(LoginActivity.this, UserMainActivity.class));
                    finish();
                }
            }else if(roleStr.equals(ConfigConstant.accountantRole)){
                if(status.equals("N")) {
                    intent = new Intent(LoginActivity.this, AccountantMainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    startActivity(new Intent(LoginActivity.this, AccountantMainActivity.class));
                    finish();
                }
            }
        }
    }

    public void updateUI(JSONObject userjson,JSONObject companyjson,String token) {
        //create session for new logged in user
        Gson gson = new GsonBuilder().create();
        String userStr = userjson.toString();
        UserDTO user = gson.fromJson(userStr, UserDTO.class);
        String companyStr = null;
        CompanyDTO company;
        String role=user.getRole();
        Log.e("Role",":"+user.getRole());
            // intent = new Intent();

             if(role.equals(ConfigConstant.userRole)) {
                 if(user.getStatus().equals("N")){
                     companyStr = companyjson.toString();
                     company = gson.fromJson(companyStr, CompanyDTO.class);
                     session.createLoginSessionAdmin(user.getUserId(),user.getEmailId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getPhoneno(), user.getGender(), user.getRole(), token,company.getClientId(),company.getCompanyId(),user.getStatus());
                     intent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                     startActivity(intent);
                     finish();
                 }else{
                     companyStr = companyjson.toString();
                     company = gson.fromJson(companyStr, CompanyDTO.class);
                     session.createLoginSessionAdmin(user.getUserId(),user.getEmailId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getPhoneno(), user.getGender(), user.getRole(), token,company.getClientId(),company.getCompanyId(),user.getStatus());
                     intent = new Intent(LoginActivity.this, UserMainActivity.class);
                     startActivity(intent);
                     finish();
                 }

             }else if(role.equals(ConfigConstant.accountantRole)){
                 if(user.getStatus().equals("N")){
                     companyStr = companyjson.toString();
                     company = gson.fromJson(companyStr, CompanyDTO.class);
                     session.createLoginSessionAdmin(user.getUserId(),user.getEmailId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getPhoneno(), user.getGender(), user.getRole(), token,company.getClientId(),company.getCompanyId(),user.getStatus());
                     intent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                     startActivity(intent);
                     finish();
                 }else{
                     companyStr = companyjson.toString();
                     company = gson.fromJson(companyStr, CompanyDTO.class);
                     session.createLoginSessionAdmin(user.getUserId(),user.getEmailId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getPhoneno(), user.getGender(), user.getRole(), token,company.getClientId(),company.getCompanyId(),user.getStatus());
                     intent = new Intent(LoginActivity.this, AccountantMainActivity.class);
                     startActivity(intent);
                     finish();
                 }
             } else if(role.equals(ConfigConstant.employeeRole)){
                 if(user.getStatus().equals("N")){
                     companyStr = companyjson.toString();
                     company = gson.fromJson(companyStr, CompanyDTO.class);
                     session.createLoginSessionAdmin(user.getUserId(),user.getEmailId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getPhoneno(), user.getGender(), user.getRole(), token,company.getClientId(),company.getCompanyId(),user.getStatus());
                     intent = new Intent(LoginActivity.this, AdminProfileActivity.class);
                     startActivity(intent);
                     finish();
                 }else{
                     companyStr = companyjson.toString();
                     company = gson.fromJson(companyStr, CompanyDTO.class);
                 session.createLoginSessionAdmin(user.getUserId(),user.getEmailId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getPhoneno(), user.getGender(), user.getRole(), token,company.getClientId(),company.getCompanyId(),user.getStatus());
                 intent = new Intent(LoginActivity.this, EmployeeMainActivity.class);
                 startActivity(intent);
                 finish();
                 }
             } else if(role.equals(ConfigConstant.adminRole)){
            //
            companyStr = companyjson.toString();
            company = gson.fromJson(companyStr, CompanyDTO.class);
            Log.e("userId:" + user.getUserId(), "token:" + token);
            Log.e("", "created session");
            session.createLoginSessionAdmin(user.getUserId(),user.getEmailId(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getPhoneno(), user.getGender(), user.getRole(), token,company.getClientId(),company.getCompanyId(),user.getStatus());
            intent = new Intent(LoginActivity.this, AdminMainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.btnLogin:
                Log.e(TAG,"signIn cliked");

                email=emailET.getText().toString();
                password=pwdET.getText().toString();
                if(Utility.isNotNull(email) && Utility.isNotNull(password)){
                    mProgress.show();
                        Log.e(TAG,"in side excute");
                        ObjectMapper mapper = new ObjectMapper();
                        try{
                            String json="";
                            LoginInputDTO dto= new LoginInputDTO();
                            dto.setUsername(email);
                            dto.setPassword(password);
                            json=mapper.writeValueAsString(dto);
                            new SignIn().execute(json);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    /*}else{
                        Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
                    }*/
                }else{
                    Toast.makeText(getApplicationContext(), "Please don't leave any field blank", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnRegister:
                Log.e(TAG,"signup clicked");
                navigateToRegisterActivity();
                break;

            case R.id.forgotpassword:
                Log.e(TAG,"Forgot password clicked");
                navigateToForgotPasswordActivity();
                break;
        }
    }

    private void navigateToForgotPasswordActivity() {
        Intent forgorpass = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
        startActivity(forgorpass);
    }

    public void navigateToRegisterActivity(){
        Intent register = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(register);
    }

    private class SignIn extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            error_str.setText("");
        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
           // Log.e(TAG,"this input post"+param[0]);
            result= servicepost.makeServicePost(signinURL,param[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
          //  Log.e(TAG,"Response is"+result);
            JSONObject obj,datauser=null,datacompany = null;
            String datatoken = null;
            Gson gson = new GsonBuilder().create();
            try {
                 obj = new JSONObject(result);
                 datauser = obj.getJSONObject("user");
                 datatoken = obj.getString("token");
                 datacompany= obj.getJSONObject("company");


            }catch (Exception e){

            }
            BaseResponseDTO<LoginResponseDTO> loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                mProgress.dismiss();
              //  Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                updateUI(datauser,datacompany,datatoken);
            }else if(loginResponse.getStatusCode()==-1){
                mProgress.dismiss();
                error_str.setText(loginResponse.getMsg());
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
