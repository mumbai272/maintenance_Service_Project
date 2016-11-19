package com.android.maintenance.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.android.maintenance.fragments.ClaimDetailspager;
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
public class CliamDetailsActivity extends AppCompatActivity {

    private SessionManager session;
    String token,role;
    Gson gson;
    Double amount;
    Type type;
    Intent intent;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    TextView part,s_date,e_date,amt;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;
    Button approve;
    GetClaimListDTO claim;

    public static CliamDetailsActivity instance;


    public static CliamDetailsActivity getInstance() {
        return instance;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.claim_details);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);
        token = user.get(SessionManager.KEY_TOKEN);
        conven_exp_list=new ArrayList<ClaimConveyanceExpenseDTO>();
        misc_exp_list=new ArrayList<MiscExpenseDTO>();
        business_exp_list=new ArrayList<BusinessDevExpenseDTO>();

        claim= (GetClaimListDTO) getIntent().getSerializableExtra("DTO");
        business_exp_list= (ArrayList<BusinessDevExpenseDTO>) getIntent().getSerializableExtra("business_exp_list");
        misc_exp_list= (ArrayList<MiscExpenseDTO>) getIntent().getSerializableExtra("misc_exp_list");
        conven_exp_list= (ArrayList<ClaimConveyanceExpenseDTO>) getIntent().getSerializableExtra("conven_exp_list");


          Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_claim_details);
          toolbar.setTitle("Claim Expenses");
          toolbar.setNavigationOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  intent = new Intent(CliamDetailsActivity.this, ClaimActivity.class);
                  startActivity(intent);

              }
          });

          part = (TextView) findViewById(R.id.text_part);
          e_date = (TextView) findViewById(R.id.text_end);
          s_date = (TextView) findViewById(R.id.text_start);
          amt = (TextView) findViewById(R.id.text_amount);

          approve = (Button) findViewById(R.id.sub_approval);

          Bundle bundle = new Bundle();
          bundle.putSerializable("Clim_ID",claim.getClaimId());
          bundle.putSerializable("business_exp_list", business_exp_list);
          bundle.putSerializable("conven_exp_list", conven_exp_list);
          bundle.putSerializable("misc_exp_list", misc_exp_list);
       /* if(business_exp_list.size()> 0){

            for(int i=0; i<=business_exp_list.size();i++) {
                amount = amount + business_exp_list.get(i).getClaimAmount();
            }
        }

        if(conven_exp_list.size()> 0){
            for(int i=0; i<=conven_exp_list.size();i++) {
                amount = amount + conven_exp_list.get(i).getClaimAmount();
            }
        }

        if(misc_exp_list.size()> 0){
            for(int i=0; i<=misc_exp_list.size();i++){
                amount=amount+misc_exp_list.get(i).getClaimAmount();
            }
        }*/


          part.setText(claim.getParticulars());
          if (amount == null) {
              amount = 0.0;
          }
          amt.setText(amount.toString());

          e_date.setText(claim.getClaimEndDate());
          s_date.setText(claim.getClaimStartDate());

          tabLayout = (TabLayout) findViewById(R.id.claim_detail_tabLayout);
          viewPager = (ViewPager) findViewById(R.id.claim_details__pager);

          ClaimDetailspager pager = new ClaimDetailspager(getSupportFragmentManager(), bundle);

          viewPager.setAdapter(pager);

          tabLayout.setupWithViewPager(viewPager);

          viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
          tabLayout.setTabsFromPagerAdapter(pager);

    }


}