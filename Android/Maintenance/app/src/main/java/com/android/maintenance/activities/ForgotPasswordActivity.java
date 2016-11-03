package com.android.maintenance.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.LoginInputDTO;
import com.android.maintenance.DTO.LoginResponseDTO;
import com.android.maintenance.DTO.ResetPasswordDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

/**
 * Created by anand on 19-Sep-16.
 */
public class ForgotPasswordActivity extends AppCompatActivity {
    EditText phone_no,email;
    Button resetButton;
    String phoneStr,emailStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        phone_no = (EditText) findViewById(R.id.forgot_number);
        email =(EditText) findViewById(R.id.forgot_email);
        resetButton= (Button) findViewById(R.id.resetpassbtn);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneStr= phone_no.getText().toString();
                emailStr= email.getText().toString();
                if(Utility.isNotNull(phoneStr) && Utility.isNotNull(emailStr)){
                    ObjectMapper mapper = new ObjectMapper();
                    try{
                        String json="";
                        ResetPasswordDTO dto= new ResetPasswordDTO();
                        dto.setEmailId(emailStr);
                        dto.setPhoneno(phoneStr);
                        json=mapper.writeValueAsString(dto);
                        new ResetPassword().execute(json);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private class ResetPassword  extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            result= servicepost.makeServicePost("user/forgotPassword",param[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj;
            String datatoken = null;
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO<LoginResponseDTO> loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){


            }else if(loginResponse.getStatusCode()==-1){

                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
