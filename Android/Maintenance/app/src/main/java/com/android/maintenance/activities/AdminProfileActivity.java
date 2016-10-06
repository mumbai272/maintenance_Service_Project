package com.android.maintenance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by anand on 28-Sep-16.
 */
public class AdminProfileActivity extends AppCompatActivity {

    EditText name,company,address;
    String fullname,companyname, addressname,first_name,last_name;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);
        session=new SessionManager(getApplicationContext());
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        first_name=user.get(SessionManager.KEY_FIRST_NAME);
        last_name=user.get(SessionManager.KEY_LAST_NAME);
        fullname=first_name+" "+last_name;
        //Log.e("user_ id:"+userID,"Token:"+token);
        name=(EditText)findViewById(R.id.profile_name);
        company=(EditText)findViewById(R.id.profile_company);
        address=(EditText)findViewById(R.id.profile_address);

        name.setText(fullname);



    }
}
