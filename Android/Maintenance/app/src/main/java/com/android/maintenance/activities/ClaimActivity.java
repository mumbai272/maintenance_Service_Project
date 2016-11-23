package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.adapters.ClaimAdapter;
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

/**
 * Created by anand on 18-Nov-16.
 */
public class ClaimActivity extends Activity{

    Intent intent;
    ListView listView;
    ImageButton button;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;
    Gson gson;
    Type type;
    private SessionManager session;
    public String token, role;
    GetClaimListDTO claim;
    ArrayList<GetClaimListDTO> claimList;
    ClaimAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim);
        conven_exp_list=new ArrayList<ClaimConveyanceExpenseDTO>();
        misc_exp_list=new ArrayList<MiscExpenseDTO>();
        business_exp_list=new ArrayList<BusinessDevExpenseDTO>();

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);
        token = user.get(SessionManager.KEY_TOKEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_claim);
        toolbar.setTitle("Claim List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        button=(ImageButton)findViewById(R.id.add_claim);
        if(role.equals(ConfigConstant.employeeRole)){
            button.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(ClaimActivity.this,ApplyCliamActivity.class);
                startActivity(intent);
            }
        });

        getCliamList();
    }

    private void getCliamList() {
        GetClaimList list=new GetClaimList(this);
        list.execute(ConfigConstant.url + "claim");
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
            listView=(ListView)findViewById(R.id.listView_claims);
            adapter = new ClaimAdapter(getApplicationContext(), claimList);
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

    public void getDetails(Long c_id) {

        GetClaimDetails details= new GetClaimDetails(this);
        details.execute(ConfigConstant.url+"claim/"+c_id);
      /* */
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

        intent=new Intent(ClaimActivity.this,CliamDetailsActivity.class);
        intent.putExtra("DTO",claim);
        intent.putExtra("business_exp_list",business_exp_list);
        intent.putExtra("conven_exp_list",conven_exp_list);
        intent.putExtra("misc_exp_list",misc_exp_list);
        startActivity(intent);

    }
}
