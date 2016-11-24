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

import com.android.maintenance.DTO.AddressDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.adapters.ClientListAdapter;
import com.android.maintenance.asyncTask.GetClientList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


public class AdminMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;
    Intent intent;
    public String token, userID;
    Gson gson;
    ArrayList<ClientDTO> clientList;
    ListView listView;
    ImageButton addButton;
    ClientListAdapter adapter;
    Context context = this;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);
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

        clientList = new ArrayList<ClientDTO>();
        addButton = (ImageButton) findViewById(R.id.add_client);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AdminMainActivity.this, ClientRegistration.class);
                startActivity(intent);
            }
        });

        GetClientList clients =  new GetClientList(this);
        clients.execute();
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

        if (!session.isLoggedIn()) {
            logOut();
        }

    }

    private void logOut() {

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
        } else if (id == R.id.nav_log_out) {
            logOut();
        } else if (id == R.id.nav_client) {
            showClientListActivity();
        } else if (id == R.id.nav_machines) {
            showMachineListActivity();
        } else if (id == R.id.employees) {
            showEmployeeList();
        } else if (id == R.id.nav_machinetype) {
            showMachineType();
        }else if (id == R.id.nav_machine_spare) {
            showMachineSpare();
        } else if (id == R.id.nav_machinemake) {
            showMachineMake();
        } else if (id == R.id.nav_machine_model) {
            showMachineModel();
        } else if (id == R.id.nav_users) {
            navigateToUserList();
        }else if (id == R.id.nav_logs) {
            navigateToLogList();
        }else if (id == R.id.nav_claim) {
            navigateToClaimList();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToClaimList() {
        intent =new Intent(AdminMainActivity.this,ClaimActivity.class);
        startActivity(intent);
    }

    private void navigateToLogList() {
        intent= new Intent(AdminMainActivity.this,LogListActivity.class);
        startActivity(intent);
    }


    private void showMachineMake() {

        intent = new Intent(AdminMainActivity.this,MachineMakeActivity.class);
        startActivity(intent);
    }

    private void showMachineSpare() {
        intent = new Intent(AdminMainActivity.this,MachineSpareActivity.class);
        startActivity(intent);
    }

    private void showMachineModel() {
        intent = new Intent(AdminMainActivity.this,MachineModelActivity.class);
        startActivity(intent);
    }

    private void showMachineType(){
        intent = new Intent(AdminMainActivity.this,MachineTypeActivity.class);
        startActivity(intent);
    }

    private void showMachineListActivity(){
        intent =new Intent(AdminMainActivity.this,MachineListActivity.class);
        startActivity(intent);

    }

    private void showEmployeeList() {
        intent =new Intent(AdminMainActivity.this,AdminEmployeeListActivity.class);
        intent.putExtra("clientList",clientList);
        startActivity(intent);

    }

    private void navigateToUserList() {
        intent =new Intent(AdminMainActivity.this,UserApprovalActivity.class);
        intent.putExtra("clientList",clientList);
        startActivity(intent);

    }

    private void showProfileActivity() {
        intent = new Intent(AdminMainActivity.this, AdminProfileActivity.class);
        startActivity(intent);
    }

    private void showClientListActivity() {
        startActivity(new Intent(AdminMainActivity.this, AdminMainActivity.class));
    }

    public class LogOut extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            String result = "";
            ServiceHandlerWS serviceGet = new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            Log.e("", "logout url is: " + ConfigConstant.url + "logout/" + userID);
            result = serviceGet.makeServiceGet(ConfigConstant.url + "logout/" + userID, token);
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

    public void displayClientListView(JSONArray companyData) {
        mProgress.dismiss();
        //if companyData null
        if(companyData==null){
            Log.e("NO Clients","dsd");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Clients");
            alertDialogBuilder
                    .setMessage("NO Clients for in the List.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }else {
        String companyDataStr = companyData.toString();
        Log.e("sds","companyDataStr:"+companyDataStr);
        gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<ClientDTO>>() {
        }.getType();
        clientList = gson.fromJson(companyDataStr, type);
        Log.e("ds","userdto:"+clientList.size());

        adapter=new ClientListAdapter(getApplicationContext(),clientList);
        listView = (ListView) findViewById(R.id.listView_clients);
        listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //navigate to detail page of approval
                    Long clientId = (Long) view.getTag();
                    Log.e("clientId:", "" + clientId);
                    showClientDetail(clientList, clientId,1);
                }
            });

        }

    }

    public void showClientDetail(ArrayList<ClientDTO> clientList, Long clientId, int format) {
        ClientDTO client=null;
        AddressDTO address=null;
        ArrayList<ClientDTO> clients= new ArrayList<ClientDTO>();
        for(int i=0; i<clientList.size();i++){
            if(clientList.get(i).getClientId()== clientId){
                Log.e("Clientid:"+clientList.get(i).getClientId(),"name"+clientList.get(i).getClientName());
                address= new AddressDTO(clientList.get(i).getAddress().getAddressDesc(),clientList.get(i).getAddress().getStreet1(),clientList.get(i).getAddress().getStreet2(),clientList.get(i).getAddress().getStreet3(),clientList.get(i).getAddress().getCountry(),clientList.get(i).getAddress().getState(),clientList.get(i).getAddress().getCity(),clientList.get(i).getAddress().getLocation(),clientList.get(i).getAddress().getZipCode(),clientList.get(i).getAddress().getPhoneNo(),clientList.get(i).getAddress().getMobileNo(),clientList.get(i).getAddress().getFaxNo(),clientList.get(i).getAddress().getWebsite(),clientList.get(i).getAddress().getMailId());
                client= new ClientDTO(clientList.get(i).getCompanyId(),clientList.get(i).getClientId() ,clientList.get(i).getClientName(), clientList.get(i).getDescription(), address);
                if(format==1){
                    intent =new Intent(AdminMainActivity.this, ClientDetailsActivity.class);
                    clients.add(client);
                    intent.putExtra("client_list", clients);
                    startActivity(intent);
                    finish();
                }else{
                    intent =new Intent(AdminMainActivity.this, ClientEditDetailsActivity.class);
                    clients.add(client);
                    intent.putExtra("client_list", clients);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

}