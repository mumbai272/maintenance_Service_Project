package com.android.maintenance.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.MachineDetailDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.adapters.MachineListAdapter;
import com.android.maintenance.asyncTask.GetMachinesForUser;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class UserMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Gson gson;
    private SessionManager session;
    public String token, userID;
    Intent intent;
    ImageButton addButton;
    ListView listView;
    Context context = this;
    MachineListAdapter adapter;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);
        session.checkLogin();

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        addButton = (ImageButton) findViewById(R.id.add_machines);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UserMainActivity.this, MachineRegister.class);
                startActivity(intent);
            }
        });

        GetMachinesForUser machineList=  new GetMachinesForUser(this);
        machineList.execute();
        mProgress.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


        if (!session.isLoggedIn()) {
            logOut();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_profile) {
            showProfileActivity();
        } else if (id == R.id.nav_log_out) {
            logOut();
        } else if (id == R.id.nav_machines) {
            showMachineListActivity();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToUserList() {

    }

    private void showMachineListActivity() {
        intent = new Intent(UserMainActivity.this, UserMainActivity.class);
        startActivity(intent);

    }

    private void showProfileActivity() {
        intent = new Intent(UserMainActivity.this, AdminProfileActivity.class);
        startActivity(intent);
    }

    private void logOut() {
        new LogOut().execute();
        session.logoutUser();
    }

    public class LogOut extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            String result = "";
            ServiceHandlerWS serviceget = new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            Log.e("", "logout url is: " + ConfigConstant.url + "logout/" + userID);
            result = serviceget.makeServiceGet(ConfigConstant.url + "logout/" + userID, token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson = new GsonBuilder().create();
            BaseResponseDTO logoutResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (logoutResponse.getStatusCode() == 1) {
            } else if (logoutResponse.getStatusCode() == -1) {
                Toast.makeText(getApplicationContext(), logoutResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void displayClientListView(JSONArray machineData) {
        mProgress.dismiss();
        //if companyData null
        if(machineData==null){
            Log.e("NO Machimes","dsd");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Machines");
            alertDialogBuilder
                    .setMessage("NO Machines in the List.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }else {
        String machineStr = machineData.toString();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type type = new TypeToken<ArrayList<MachineDetailDTO>>() {
        }.getType();
        ArrayList<MachineDetailDTO> machineList = gson.fromJson(machineStr, type);
        Log.e("", "machine list:" + machineList.size());
        listView=(ListView)findViewById(R.id.list_machines);
        adapter=new MachineListAdapter(getApplicationContext(),machineList,token);
        listView.setAdapter(adapter);
        if(machineList.size() !=0) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //navigate to detail page of approval
                    Long userId = (Long) view.getTag();
                    Log.e("userId:", "" + userId);
                }
            });

        }
        }
    }
}
