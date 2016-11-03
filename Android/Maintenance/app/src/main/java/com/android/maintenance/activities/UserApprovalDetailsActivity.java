package com.android.maintenance.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.ApproveUserDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anand on 08-Oct-16.
 */
public class UserApprovalDetailsActivity extends Activity implements View.OnClickListener{

    private ProgressDialog mProgress;
    Context context = this;
    Intent intent;
    Long userId,clientid,role_long;
    Button approve,reject;
    Spinner client_name,role_spinner;
    TextView user_name,user_email,user_mobile,user_client;
    ArrayList<UserDTO> userList;
    ArrayList<ClientDTO> clienList;
    private SessionManager session;
    String token;
    ArrayAdapter<String> adapter;
    EditText hidden_client;
    EditText clie_str1,clie_str2,clie_str3,clie_location,clie_city,clie_state,clie_country,clie_zip,clie_phone,clie_mobile,clie_fax,clie_email,clie_website;

    String client,role;
    String[] role_array={"Admin","User","Service Engineer"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_approval_detail);
        session=new SessionManager(getApplicationContext());
        HashMap<String, String> users = session.getUserDetails();
        token=users.get(SessionManager.KEY_TOKEN);

        Log.e("","Token:"+token);

        role_spinner= (Spinner)findViewById(R.id.spinner_role);
        user_name = (TextView) findViewById(R.id.approval_user_data);
        user_email = (TextView) findViewById(R.id.approval_email_data);
        user_mobile = (TextView) findViewById(R.id.approval_mobileno_data);
        user_client = (TextView) findViewById(R.id.approval_client_data);
        approve = (Button) findViewById(R.id.approve_button);
        reject = (Button) findViewById(R.id.reject_button);

        clie_str1 = (EditText) findViewById(R.id.stree1);
        clie_str2 = (EditText) findViewById(R.id.steet2);
        clie_str3 = (EditText) findViewById(R.id.street3);
        clie_location = (EditText) findViewById(R.id.edit_location);
        clie_city = (EditText) findViewById(R.id.edit_city);
        clie_state = (EditText) findViewById(R.id.edit_state);
        clie_country = (EditText) findViewById(R.id.edit_country);
        clie_zip = (EditText) findViewById(R.id.edit_zip);
        clie_phone = (EditText) findViewById(R.id.edit_phone);
        clie_mobile = (EditText) findViewById(R.id.edit_mobile);
        clie_fax = (EditText) findViewById(R.id.edit_fax);
        clie_email = (EditText) findViewById(R.id.edit_email);
        clie_website = (EditText) findViewById(R.id.edit_website);

        client_name=(Spinner) findViewById(R.id.spinner_client_name);
        hidden_client= (EditText) findViewById(R.id.hidden_client);

        approve.setOnClickListener(this);
        reject.setOnClickListener(this);

        userList = new ArrayList<UserDTO>();
        clienList =new ArrayList<ClientDTO>();
        intent = getIntent();
        userList = (ArrayList<UserDTO>) intent.getSerializableExtra("arrayList");
        clienList = (ArrayList<ClientDTO>)intent.getSerializableExtra("clientList");
        userId =  intent.getLongExtra("user_id",1);
        Log.e("user id:"+userId,"size:"+userList.size());

        for(UserDTO user : userList) {
            if(user.getUserId().equals(userId)){
                String fullname=user.getUserName();
                String email=user.getEmailId();
                String mobile=user.getPhoneno();
                String client=user.getClientName();
                user_name.setText(fullname);
                user_email.setText(email);
                user_mobile.setText(mobile);
                user_client.setText(client);
            }
        }

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        setRoleSpinner();
        setClientSpinner();

    }

    private void setRoleSpinner() {
        List<String> roleStr= new ArrayList<String>();
        roleStr= Arrays.asList(role_array);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, roleStr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        role_spinner.setAdapter(adapter);
        role_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                role =parentView.getItemAtPosition(position).toString();
                Toast.makeText(parentView.getContext(), "You selected: " +role ,Toast.LENGTH_LONG).show();
                if(role =="Admin"){
                    role_long=1L;
                }else if(role=="User"){
                    role_long=2L;
                }else if(role=="Service Engineer"){
                    role_long=3L;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //    Toast.makeText(parentView.getContext(), "You selected nothing " ,Toast.LENGTH_LONG).show();
            }

        });
        Log.e("","item set");
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.approve_button:
                try {
                String json="";
                ObjectMapper mapper = new ObjectMapper();
                ApproveUserDTO approveUserDTO= new ApproveUserDTO(userId,role_long,clientid);
                json=mapper.writeValueAsString(approveUserDTO);
                mProgress.show();
                new ApproveUser().execute(json);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.reject_button:
                break;
        }

    }


    private class ApproveUser  extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS serviceput= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= serviceput.makeServicePut(ConfigConstant.url+"user/approve",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO approvalResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(approvalResponse.getStatusCode()==1){
                mProgress.dismiss();
                UpdateTheUI();

            }else if(approvalResponse.getStatusCode()==-1){
                mProgress.dismiss();
                Toast.makeText(getApplicationContext(),approvalResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void UpdateTheUI() {
            Log.e("inside Alert","");
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Approval");
            alertDialogBuilder
                    .setMessage("Client approval is successful.")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            Intent intent= new Intent(UserApprovalDetailsActivity.this,UserApprovalActivity.class);
                           intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
    }



    private void setClientSpinner() {
        final List<String> lables = new ArrayList<String>();
        for (int i=0; i< clienList.size();i++) {
           lables.add(clienList.get(i).getClientName());
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        client_name.setAdapter(adapter);
        client_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                client =parentView.getItemAtPosition(position).toString();
                Log.e(" ","hidden"+client);
                for( int i=0;i<clienList.size();i++){
                    if(clienList.get(i).getClientName()== client){
                        //set fields
                        int a = clienList.get(i).getClientId();
                        clientid = Long.parseLong(String.valueOf(a));
                        clie_str1.setText(clienList.get(i).getAddress().getStreet1());
                        clie_str2.setText(clienList.get(i).getAddress().getStreet2());
                        clie_str3.setText(clienList.get(i).getAddress().getStreet3());
                        clie_city.setText(clienList.get(i).getAddress().getCity());
                        clie_email.setText(clienList.get(i).getAddress().getMailId());
                        clie_country.setText(clienList.get(i).getAddress().getCountry());
                        clie_state.setText(clienList.get(i).getAddress().getState());
                        clie_zip.setText(clienList.get(i).getAddress().getZipCode());
                        clie_location.setText(clienList.get(i).getAddress().getLocation());
                        clie_fax.setText(clienList.get(i).getAddress().getFaxNo());
                        clie_phone.setText(clienList.get(i).getAddress().getPhoneNo());
                        clie_mobile.setText(clienList.get(i).getAddress().getMobileNo());
                        clie_website.setText(clienList.get(i).getAddress().getWebsite());
                     //   hidden_client.setText(clienList.get(i).getClientId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //    Toast.makeText(parentView.getContext(), "You selected nothing " ,Toast.LENGTH_LONG).show();
            }

        });
        Log.e("","item set");
    }


}
