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
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;
    Intent intent;
    public String token,userID;
    TextView tokenTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);
        session=new SessionManager(getApplicationContext());

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
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
      //  session.logoutUser();

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
            String tokenStr=tokenTxt.getText().toString();
            showClientApprovalActivity(tokenStr);
        }
        else if(id==R.id.nav_machineRegister){
            String tokenStr=tokenTxt.getText().toString();
            showMachineRegisterActivity(tokenStr);
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

    private void showClientApprovalActivity(String token) {
        intent=new Intent(AdminMainActivity.this,ClientApprovalActivity.class);
        intent.putExtra("token",token);
        startActivity(intent);
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

    private void showMachineRegisterActivity(String token) {
        Log.e("Token is",""+token);
        intent= new Intent(AdminMainActivity.this,ClientAssetRegister.class );
        intent.putExtra("token",token);
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
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO logoutResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(logoutResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),"Log out"+logoutResponse.getMsg(), Toast.LENGTH_LONG).show();
            }else if(logoutResponse.getStatusCode()==-1){
                Toast.makeText(getApplicationContext(),logoutResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }


    }
}
