package com.android.maintenance.activities;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.DTO.UserProfileUpdateDTO;
import com.android.maintenance.DTO.UserUpdateDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.android.maintenance.fragments.ProfilePager;
import com.google.gson.Gson;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;


/**
 * Created by anand on 28-Sep-16.
 */
public class AdminProfileActivity extends AppCompatActivity {

    Button updatebtn;
    Intent intent;
    String token, userID,pho,fname,lname,uname,uemail,role;
    private SessionManager session;
    UserUpdateDTO user;
    UserDTO userDTO;
    UserProfileUpdateDTO update;
    Gson gson;

    EditText username,email,f_name,m_name,l_name,phone;
    String usernameStr,emailStr,f_nameStr,m_nameStr,l_nameStr,phoneStr;
    public static AdminProfileActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        pho= user.get(SessionManager.KEY_PHONE_NO);
        fname=user.get(SessionManager.KEY_FIRST_NAME);
        lname=user.get(SessionManager.KEY_LAST_NAME);
        uemail=user.get(SessionManager.KEY_EMAIL);
        role=user.get(SessionManager.KEY_ROLE);
        uname=user.get(SessionManager.KEY_USER_NAME);
        Log.e("user_ id22:" + userID, "Token112:" + token);

      //  userDTO=(UserDTO) getIntent().getSerializableExtra("userdata");
        updatebtn =(Button) findViewById(R.id.button_update);
       // tabLayout = (TabLayout) findViewById(R.id.profile_tabLayout);



       // viewPager = (ViewPager) findViewById(R.id.profile_pager);

      /*  ProfilePager adapter = new ProfilePager(getSupportFragmentManager());
        View frag=viewPager.getRootView();
        viewPager.setAdapter(adapter);*/

        username=(EditText)findViewById(R.id.puser_name);
        email = (EditText)findViewById(R.id.pemail);
        f_name =(EditText)findViewById(R.id.f_name);
        l_name =(EditText)findViewById(R.id.l_name);
        m_name =(EditText)findViewById(R.id.m_name);
        phone =(EditText)findViewById(R.id.pphone);


        username.setText(uname);
        email.setText(uemail);
        f_name.setText(fname);
        l_name.setText(lname);
        phone.setText(pho);
       // tabLayout.setupWithViewPager(viewPager);

       // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
       // tabLayout.setTabsFromPagerAdapter(adapter);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameStr= username.getText().toString();
                f_nameStr= f_name.getText().toString();
                l_nameStr=l_name.getText().toString();
                m_nameStr=m_name.getText().toString();
                emailStr=email.getText().toString();
                phoneStr=phone.getText().toString();
                ObjectMapper mapper = new ObjectMapper();
                try{
                    String json="";
                    update= new UserProfileUpdateDTO();
                    UserUpdateDTO user= new UserUpdateDTO();
                    user.setEmailId(emailStr);
                    user.setFirstName(f_nameStr);
                    user.setMiddleName(m_nameStr);
                    long l = Long.parseLong(userID);
                    user.setUserId(l);
                    user.setLastName(l_nameStr);
                    user.setUserName(usernameStr);
                    update.setUser(user);
                    json=mapper.writeValueAsString(update);
                    new ProfileUpdate().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public static AdminProfileActivity getInstance() {
        return instance;
    }

    private class ProfileUpdate extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= servicepost.makeServicePut(ConfigConstant.url+"user",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson=new Gson();
            BaseResponseDTO updateResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(updateResponse.getStatusCode()==1){
                if(role.equals(ConfigConstant.userRole)) {
                    intent = new Intent(AdminProfileActivity.this, UserMainActivity.class);
                    startActivity(intent);
                }else if(role.equals(ConfigConstant.employeeRole)){
                    intent = new Intent(AdminProfileActivity.this, EmployeeMainActivity.class);
                    startActivity(intent);
                }
                //new session

               // session.createLoginSessionAdmin(user.getUserId(),user.getUserName(),user.getFirstName(),user.getLastName(),user.getPhoneno(),user.getGender(),role,token);
            }else if(updateResponse.getStatusCode()==-1){

                Toast.makeText(getApplicationContext(),updateResponse.getMsg(), Toast.LENGTH_LONG).show();
            }


        }
    }
}


