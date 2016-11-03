package com.android.maintenance.activities;

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
import com.android.maintenance.DTO.MachineDetailDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.adapters.ClientListAdapter;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);

        //Toast.makeText(getApplicationContext(), "User LoginActivity Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();
        session.checkLogin();

        clientList = new ArrayList<ClientDTO>();
        addButton = (ImageButton) findViewById(R.id.add_client);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(AdminMainActivity.this, ClientRegistration.class);
                startActivity(intent);
            }
        });
        new GetClientList().execute();

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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        new GetMachineList().execute();
    }

    private void navigateToUserList() {
        new GetUserApprovalList().execute();

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
                Toast.makeText(getApplicationContext(), "Log out" + logoutResponse.getMsg(), Toast.LENGTH_LONG).show();
            } else if (logoutResponse.getStatusCode() == -1) {
                Toast.makeText(getApplicationContext(), logoutResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }

    public class GetClientList extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS serviceGet = new ServiceHandlerWS();
            Log.e("url", "" + ConfigConstant.url + "client/1?fetchAddress=true");
            result = serviceGet.makeServiceGet(ConfigConstant.url + "client/1?fetchAddress=true", token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj;
            JSONArray companyData = null, addressData = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                companyData = obj.getJSONArray("companys");
            } catch (Exception e) {

            }

            BaseResponseDTO clientResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (clientResponse.getStatusCode() == 1) {
                Toast.makeText(getApplicationContext(), clientResponse.getMsg(), Toast.LENGTH_LONG).show();
                displayClientListView(companyData);
            } else if (clientResponse.getStatusCode() == -1) {
                Toast.makeText(getApplicationContext(), clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void displayClientListView(JSONArray companyData) {
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
        if (clientList.size() != 0) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), "Client ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    //navigate to detail page of approval
                    int clientId = (int) view.getTag();
                    Log.e("clientId:", "" + clientId);
                    showClientDetail(clientList, clientId,1);
                }
            });

        } else {
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
        }

    }

    public void showClientDetail(ArrayList<ClientDTO> clientList, int clientId,int format) {
        ClientDTO client=null;
        AddressDTO address=null;
        ArrayList<ClientDTO> clients= new ArrayList<ClientDTO>();
        for(int i=0; i<clientList.size();i++){
            if(clientList.get(i).getClientId()== clientId){
                Log.e("Clientid:"+clientList.get(i).getClientId(),"name"+clientList.get(i).getClientName());
                address= new AddressDTO(clientList.get(i).getAddress().getAddressDesc(),clientList.get(i).getAddress().getStreet1(),clientList.get(i).getAddress().getStreet2(),clientList.get(i).getAddress().getStreet3(),clientList.get(i).getAddress().getCountry(),clientList.get(i).getAddress().getState(),clientList.get(i).getAddress().getCity(),clientList.get(i).getAddress().getLocation(),clientList.get(i).getAddress().getZipCode(),clientList.get(i).getAddress().getMobileNo(),clientList.get(i).getAddress().getPhoneNo(),clientList.get(i).getAddress().getFaxNo(),clientList.get(i).getAddress().getMailId(),clientList.get(i).getAddress().getWebsite());
                client= new ClientDTO(clientList.get(i).getCompanyId(),clientList.get(i).getClientId() ,clientList.get(i).getClientName(), clientList.get(i).getDescription(), address);
                if(format==1){
                intent =new Intent(AdminMainActivity.this, ClientDetailsActivity.class);
                clients.add(client);
                intent.putExtra("client_list", clients);
                startActivity(intent);
                finish();
                }else{
                    intent =new Intent(this, ClientEditDetailsActivity.class);
                    clients.add(client);
                    intent.putExtra("client_list", clients);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }


    public class GetUserApprovalList extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS servicepost = new ServiceHandlerWS();
            Log.e("url", "" + ConfigConstant.url + "user?status=r");
            result = servicepost.makeServiceGet(ConfigConstant.url + "user?status=r", token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj;
            JSONArray dataUsers = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                dataUsers = obj.getJSONArray("users");
                //  Log.e("users list",""+dataUsers);
            } catch (Exception e) {

            }

            BaseResponseDTO approvalResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (approvalResponse.getStatusCode() == 1) {
                Toast.makeText(getApplicationContext(), approvalResponse.getMsg(), Toast.LENGTH_LONG).show();
                navigateToApprovalList(dataUsers);
            } else if (approvalResponse.getStatusCode() == -1) {
                Toast.makeText(getApplicationContext(), approvalResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void navigateToApprovalList(JSONArray users) {
        String userStr = users.toString();
        gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<UserDTO>>() {
        }.getType();
        ArrayList<UserDTO> userList = gson.fromJson(userStr, type);
        Log.e("", "userdto" + userList.size());
        intent = new Intent(AdminMainActivity.this, UserApprovalActivity.class);
        Bundle b=new Bundle();
        b.putSerializable("userList", userList);
        b.putSerializable("clientList",clientList);
        intent.putExtras(b);
        startActivity(intent);
    }


    public class GetMachineList extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS serviceGet = new ServiceHandlerWS();
            Log.e("url", "" + ConfigConstant.url + "asset");
            result = serviceGet.makeServiceGet(ConfigConstant.url + "asset", token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject obj;
            JSONArray machineData = null;
            gson = new GsonBuilder().create();
            try {
                obj = new JSONObject(result);
                machineData = obj.getJSONArray("assets");
            } catch (Exception e) {

            }

            BaseResponseDTO machineResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (machineResponse.getStatusCode() == 1) {
                Toast.makeText(getApplicationContext(), machineResponse.getMsg(), Toast.LENGTH_LONG).show();
                displayMachineListView(machineData);
            } else if (machineResponse.getStatusCode() == -1) {
                Toast.makeText(getApplicationContext(), machineResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void displayMachineListView(JSONArray machineData) {

        String machineStr = machineData.toString();
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type type = new TypeToken<ArrayList<MachineDetailDTO>>() {
        }.getType();
        ArrayList<MachineDetailDTO> machineList = gson.fromJson(machineStr, type);
        Log.e("", "machine list:" + machineList.size());
        intent = new Intent(AdminMainActivity.this, MachineListActivity.class);
        intent.putExtra("machineList", machineList);
        startActivity(intent);
    }
}