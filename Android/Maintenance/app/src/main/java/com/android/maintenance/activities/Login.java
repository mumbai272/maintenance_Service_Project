package com.android.maintenance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.maintenance.R;

/**
 * Created by anand on 16-Sep-16.
 */
public class Login extends AppCompatActivity {

    private static final String TAG="Mymessage";
    EditText emailET,pwdET;
    Button loginBTN,registerBTN;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailET=(EditText)findViewById(R.id.email);
        pwdET=(EditText)findViewById(R.id.password);
        loginBTN=(Button)findViewById(R.id.btnLogin);
        registerBTN=(Button)findViewById(R.id.btnRegister);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //navigate to register page
                Log.e(TAG,"signup cliked");
                navigatetoRegisterActivity();
            }
        });

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //get email and password then call WS

            }
        });
    }

    private void updateUI(){

    }

    public void navigatetoRegisterActivity(){
        Intent register = new Intent(getApplicationContext(),Register.class);
        startActivity(register);
    }
}
