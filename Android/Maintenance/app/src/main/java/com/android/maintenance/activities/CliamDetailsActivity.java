package com.android.maintenance.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.AdminApproveDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.FinacialApproveDTO;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.LoginResponseDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.android.maintenance.fragments.ClaimDetailspager;
import com.google.gson.Gson;

import org.codehaus.jackson.map.ObjectMapper;

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
    TextView part,s_date,e_date,amt,approved;
    GetClaimListDTO claim;
    ArrayList<ClaimConveyanceExpenseDTO> conven_exp_list;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ArrayList<BusinessDevExpenseDTO> business_exp_list;
    Button approve;

    AlertDialog.Builder alertDialogBuilder;

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
                  finish();
              }
          });

          part = (TextView) findViewById(R.id.text_part);
          e_date = (TextView) findViewById(R.id.text_end);
          s_date = (TextView) findViewById(R.id.text_start);
          amt = (TextView) findViewById(R.id.text_amount);
          approved=(TextView) findViewById(R.id.finance_approve);

          approve = (Button) findViewById(R.id.sub_approval);

        if(role.equals(ConfigConstant.employeeRole)){
            if(claim.getStatus().equalsIgnoreCase("ACTIVE")){
                approve.setVisibility(View.VISIBLE);
            }else {
                if(claim.getStatus().equalsIgnoreCase("SUBMITTED")){
                    approved.setVisibility(View.VISIBLE);
                    approved.setText("Submitted");
                }
                else if(claim.getStatus().equalsIgnoreCase("FINANCE_APPROVED")){
                    approved.setVisibility(View.VISIBLE);
                    approved.setText("Financially Approved");
                }else if(claim.getStatus().equalsIgnoreCase("APPROVED")){
                    approved.setVisibility(View.VISIBLE);
                    approved.setText("Approved");
                }
            }
        }

        else if(role.equals(ConfigConstant.adminRole)&& claim.getStatus().equalsIgnoreCase("SUBMITTED")) {
            approve.setVisibility(View.VISIBLE);
            approve.setText("Approve");
        }
        else if(role.equals(ConfigConstant.accountantRole)&& claim.getStatus().equalsIgnoreCase("APPROVED")) {
            approve.setVisibility(View.VISIBLE);
            approve.setText("Financial Approve");
        }



          approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(role.equals(ConfigConstant.employeeRole)){
                    if(claim.getClaimAmount()==0.0){
                        Toast.makeText(getApplicationContext(), "Can't submit with 0.0 Expense",Toast.LENGTH_LONG).show();
                    }else {
                        new SubmitForApproval().execute(ConfigConstant.url + "claim/submit/" + claim.getClaimId());
                    }
                       } else if(role.equals(ConfigConstant.adminRole)){

                           LayoutInflater li = LayoutInflater.from(getApplicationContext());
                           View promptsView = li.inflate(R.layout.approve_amount, null);

                           alertDialogBuilder = new AlertDialog.Builder(CliamDetailsActivity.this);
                           alertDialogBuilder.setView(promptsView);
                           final EditText userInput = (EditText) promptsView
                                   .findViewById(R.id.amount_approved);

                           alertDialogBuilder
                                   .setCancelable(false)
                                   .setPositiveButton("Submit",
                                           new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog,int id) {
                                                   //WS to Reject
                                                   String amount = userInput.getText().toString();
                                                   if(Utility.isNotNull(amount)) {

                                                       ObjectMapper mapper = new ObjectMapper();
                                                       try{
                                                           String json="";
                                                           AdminApproveDTO adminApproveDTO=new AdminApproveDTO();
                                                           adminApproveDTO.setClaimId(claim.getClaimId());
                                                           adminApproveDTO.setApprovedAmount(Double.parseDouble(amount));
                                                           json=mapper.writeValueAsString(adminApproveDTO);
                                                           new ApproveAdmin().execute(json);
                                                       }catch (Exception e){
                                                           e.printStackTrace();
                                                       }

                                                   }else{
                                                       Toast.makeText(getApplicationContext(), "Please Enter the Amount.", Toast.LENGTH_LONG).show();
                                                   }
                                               }
                                           })
                                   .setNegativeButton("Cancel",
                                           new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog,int id) {
                                                   dialog.cancel();
                                               }
                                           });

                           AlertDialog alertDialog = alertDialogBuilder.create();
                           alertDialog.show();
                       }else if(role.equals(ConfigConstant.accountantRole)){//acountant
                           LayoutInflater li = LayoutInflater.from(getApplicationContext());
                           View promptsView = li.inflate(R.layout.financial_approve, null);

                           alertDialogBuilder = new AlertDialog.Builder(CliamDetailsActivity.this);
                           alertDialogBuilder.setView(promptsView);
                           final EditText adv_paid = (EditText) promptsView
                                   .findViewById(R.id.adv_paid);
                           final EditText pay_details = (EditText) promptsView
                                   .findViewById(R.id.pay_details);
                           final EditText pay_amt = (EditText) promptsView
                                   .findViewById(R.id.pay_amt);
                           final TextView bal_amt = (TextView) promptsView
                                   .findViewById(R.id.bal_amt);

                           alertDialogBuilder
                                   .setCancelable(false)
                                   .setPositiveButton("Submit",
                                           new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog,int id) {
                                                   String pay_det = pay_details.getText().toString();
                                                   String amount =  adv_paid.getText().toString();
                                                   String pay_amtt = pay_amt.getText().toString();
                                                   Double balance= claim.getClaimAmount()-claim.getApprovedAmount();
                                                   bal_amt.setText(balance.toString());

                                                   if(Utility.isNotNull(amount)) {

                                                       ObjectMapper mapper = new ObjectMapper();
                                                       try{
                                                           String json="";
                                                           FinacialApproveDTO finacialApproveDTO=new FinacialApproveDTO();
                                                           finacialApproveDTO.setClaimId(claim.getClaimId());
                                                           finacialApproveDTO.setPayDetails(pay_det);
                                                           finacialApproveDTO.setAdvancePaid(Double.valueOf(amount));
                                                           finacialApproveDTO.setBalanceAmount(balance);
                                                           finacialApproveDTO.setPayAmount(Double.valueOf(pay_amtt));
                                                           json=mapper.writeValueAsString(finacialApproveDTO);
                                                           new FinacialApprove().execute(json);
                                                       }catch (Exception e){
                                                           e.printStackTrace();
                                                       }

                                                   }else{
                                                       Toast.makeText(getApplicationContext(), "Please Enter the Pay Amount.", Toast.LENGTH_LONG).show();
                                                   }
                                               }
                                           })
                                   .setNegativeButton("Cancel",
                                           new DialogInterface.OnClickListener() {
                                               public void onClick(DialogInterface dialog,int id) {
                                                   dialog.cancel();
                                               }
                                           });

                           AlertDialog alertDialog = alertDialogBuilder.create();

                           alertDialog.show();

                }
            }
          });

          Bundle bundle = new Bundle();
          bundle.putSerializable("ClimDTO",claim);
          bundle.putSerializable("business_exp_list", business_exp_list);
          bundle.putSerializable("conven_exp_list", conven_exp_list);
          bundle.putSerializable("misc_exp_list", misc_exp_list);

          part.setText(claim.getParticulars());

          amount=claim.getClaimAmount();
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


    private class SubmitForApproval extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= servicepost.makeServicePutWithOutData(param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson=new Gson();
            BaseResponseDTO loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                finish();
            }else if(loginResponse.getStatusCode()==-1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ApproveAdmin extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS serviceput= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= serviceput.makeServicePut(ConfigConstant.url+"claim",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson=new Gson();
            BaseResponseDTO<LoginResponseDTO> loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                finish();
            }else if(loginResponse.getStatusCode()==-1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class FinacialApprove extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS serviceput= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= serviceput.makeServicePut(ConfigConstant.url+"claim",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson=new Gson();
            BaseResponseDTO<LoginResponseDTO> loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                finish();
            }else if(loginResponse.getStatusCode()==-1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}