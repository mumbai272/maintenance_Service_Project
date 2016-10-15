package com.android.maintenance.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;
    Intent intent;
    public String token,userID;
    TextView tokenTxt;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);
        session=new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User LoginActivity Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tokenTxt=(TextView)findViewById(R.id.token);

        HashMap<String, String> user = session.getUserDetails();
        userID=user.get("KEY_USER_ID");
        token=user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:"+userID,"Token:"+token);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(!session.isLoggedIn()){
          logOut();
        }
    }

    private void logOut( ) {

        new LogOut().execute();
        session.logoutUser();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            showProfileActivity();
        }else if(id==R.id.nav_client_approval){
            showClientApprovalActivity();
        }
        else if(id==R.id.nav_machineRegister){
            showMachineRegisterActivity();
        }else if (id == R.id.nav_log_out) {
            logOut();
        } else if(id == R.id.nav_machineMake){
            
            showMachineMakeActivity();
        } else if(id == R.id.nav_machineType){

            showMachineTypeActivity();
        } else if(id == R.id.nav_machineModel){

            showMachineModelActivity();
        } else if(id==R.id.nav_add_client){

            showAddClientActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showClientApprovalActivity() {
        new GetClientApprovalList().execute();

    }

    private void showMachineTypeActivity() {
        startActivity(new Intent(AdminMainActivity.this,MachineTypeActivity.class));
    }

    private void showMachineModelActivity() {
        startActivity(new Intent(AdminMainActivity.this,MachineModelActivity.class));
    }

    private void showMachineMakeActivity() {
        startActivity(new Intent(AdminMainActivity.this,MachineMakeActivity.class));
    }

    private void showMachineRegisterActivity() {
        intent= new Intent(AdminMainActivity.this,ClientAssetRegister.class );
        startActivity(intent);
    }

    private void showProfileActivity() {
        intent= new Intent(AdminMainActivity.this,AdminProfileActivity.class);
        startActivity(intent);
    }

    private void showAddClientActivity() {
        startActivity(new Intent(AdminMainActivity.this,ClientRegistration.class));
    }

    private class LogOut extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... arg0) {
            String result="";
            ServiceHandlerWS serviceget= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            Log.e("","logout url is: "+ConfigConstant.url+"logout/"+userID);
            result= serviceget.makeServiceGet(ConfigConstant.url+"logout/"+userID, token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson = new GsonBuilder().create();
            BaseResponseDTO logoutResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(logoutResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),"Log out"+logoutResponse.getMsg(), Toast.LENGTH_LONG).show();
            }else if(logoutResponse.getStatusCode()==-1){
                Toast.makeText(getApplicationContext(),logoutResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }


    }

    public class GetClientApprovalList extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS servicepost = new ServiceHandlerWS();
            Log.e("url",""+ConfigConstant.url+"user?status=r");
            result = servicepost.makeServiceGet(ConfigConstant.url+"user?status=r",token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj;
            JSONArray dataUsers = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result).getJSONObject("data");
                dataUsers = obj.getJSONArray("users");
              //  Log.e("users list",""+dataUsers);
            }catch (Exception e){

            }

            BaseResponseDTO approvalResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(approvalResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),approvalResponse.getMsg(), Toast.LENGTH_LONG).show();
                navigateToApprovalList(dataUsers);
            }else if(approvalResponse.getStatusCode()==-1){
                Toast.makeText(getApplicationContext(),approvalResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

        private void navigateToApprovalList(JSONArray users) {
            String userStr= users.toString();
            gson=new Gson();
            Type type = new TypeToken<ArrayList<UserDTO>>(){}.getType();
            ArrayList<UserDTO> userList = gson.fromJson(userStr, type);
            Log.e("","userdto"+userList.size());
            intent=new Intent(AdminMainActivity.this,UserApprovalActivity.class);
            intent.putExtra("arrayList",userList);
            startActivity(intent);
        }

    }
}
