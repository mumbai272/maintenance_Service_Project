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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.adapters.ClaimAccountantAdapter;
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.asyncTask.GetClaimDetails;
import com.android.maintenance.asyncTask.GetClaimList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountantMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;
    public String token, userID;
    ClaimAccountantAdapter adapter;
    Intent intent;
    ListView listView;
    ArrayList<GetClaimListDTO> claimList;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;
    Gson gson;
    Type type;
    GetClaimListDTO claim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountant_main);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);
        session.checkLogin();
        listView=(ListView) findViewById(R.id.finance_approve_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFinancialApprovalClaims();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getFinancialApprovalClaims() {
        GetClaimList list =new GetClaimList(this);
        list.execute(ConfigConstant.url+"claim");
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
        getMenuInflater().inflate(R.menu.accountant_main, menu);
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

        if (id == R.id.account_profile) {
            showProfileActivity();
        } else if (id == R.id.account_claim) {

        } else if (id == R.id.account_log_out) {
            logOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showProfileActivity() {
        intent = new Intent(AccountantMainActivity.this, AdminProfileActivity.class);
        startActivity(intent);
    }

    private void logOut() {
        new LogOut().execute();
        session.logoutUser();
    }

    public void displayclaimList(JSONArray claimData) {
        claimList=new ArrayList<GetClaimListDTO>();
        if(claimData==null) {

        }else {
            String logStr = claimData.toString();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Type type = new TypeToken<ArrayList<GetClaimListDTO>>() {
            }.getType();
            claimList = gson.fromJson(logStr, type);
            Log.e("", "claim list:" + claimList.size());
            adapter = new ClaimAccountantAdapter(getApplicationContext(), claimList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Long C_ID = (Long) view.getTag();
                    Log.e("claim id:", "" + C_ID);
                    getDetails(C_ID);
                    /**/
                }
            });
        }
    }

    private void getDetails(Long c_id) {

        GetClaimDetails details= new GetClaimDetails(this);
        details.execute(ConfigConstant.url+"claim/"+c_id);
    }

    public void getclaimData(JSONObject claimData, JSONArray businessExpensesData, JSONArray conveyanceExpensesData, JSONArray miscExpenseData) {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String claimStr = claimData.toString();

        claim=new GetClaimListDTO();
        claim=gson.fromJson(claimStr, GetClaimListDTO.class);

        if(businessExpensesData==null){

        }else {
            String businessExpensesStr=businessExpensesData.toString();
            type = new TypeToken<ArrayList<BusinessDevExpenseDTO>>() {
            }.getType();
            business_exp_list = gson.fromJson(businessExpensesStr, type);
        }

        if( conveyanceExpensesData==null){

        }else {
            String conveyanceExpensesStr=conveyanceExpensesData.toString();
            type = new TypeToken<ArrayList<ClaimConveyanceExpenseDTO>>() {
            }.getType();
            conven_exp_list = gson.fromJson(conveyanceExpensesStr, type);
        }
        if( miscExpenseData==null){

        }else{
            String miscExpenseStr=miscExpenseData.toString();
            type = new TypeToken<ArrayList<MiscExpenseDTO>>() {
            }.getType();
            misc_exp_list = gson.fromJson(miscExpenseStr, type);
        }
        intent=new Intent(AccountantMainActivity.this,CliamDetailsActivity.class);
        intent.putExtra("DTO",claim);
        intent.putExtra("business_exp_list",business_exp_list);
        intent.putExtra("conven_exp_list",conven_exp_list);
        intent.putExtra("misc_exp_list",misc_exp_list);
        startActivity(intent);

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
}
