package com.android.maintenance.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.GetMachineAttributeDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;

/**
 * Created by anand on 06-Nov-16.
 */
public class AddMachineModelActivity extends Activity {

    EditText machine,description;
    Button add;
    Gson gson;
    String name,desc;
    Context context=this;
    Intent intent;
    private ProgressDialog mProgress;
    public String token, userID,companyId;
    private SessionManager session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_addtab);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        companyId=user.get("KEY_COMPANY_ID");

        Toolbar toolbar = (Toolbar) findViewById(R.id.model_toolbar);
        toolbar.setTitle("Create Machine Model");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  intent = new Intent(AdminEmployeeListActivity.this, AdminMainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });


        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        machine = (EditText) findViewById(R.id.machine_model_name);
        description = (EditText) findViewById(R.id.machine_model_desc);

        add = (Button) findViewById(R.id.add_model);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=machine.getText().toString();
                desc=description.getText().toString();
                if(Utility.isNotNull(name) && Utility.isNotNull(desc)){
                    ObjectMapper mapper = new ObjectMapper();
                    try{
                        String json="";
                        GetMachineAttributeDTO machine=new GetMachineAttributeDTO();
                        machine.setMachineName(name);
                        machine.setCompanyId(Long.parseLong(companyId));
                        machine.setDescription(desc);
                        machine.setType("MACHINEMODEL");
                        json=mapper.writeValueAsString(machine);
                        new AddMachineModelAttribute().execute(json);
                        mProgress.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please don't leave any field blank", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private class AddMachineModelAttribute extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... param) {
            String result = "";
            ServiceHandlerWS servicePost = new ServiceHandlerWS();
            Log.e("url", "" + ConfigConstant.url + "machine/machinemodel");
            result = servicePost.makeServicePostWithToken(ConfigConstant.url + "machine/machinemodel",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson = new GsonBuilder().create();
            BaseResponseDTO machineAttrResponse = gson.fromJson(result, BaseResponseDTO.class);
            if (machineAttrResponse.getStatusCode() == 1) {
                mProgress.dismiss();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Success");
                alertDialogBuilder
                        .setMessage(machineAttrResponse.getMsg())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                intent= new Intent(AddMachineModelActivity.this,MachineModelActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else if (machineAttrResponse.getStatusCode() == -1) {
                mProgress.dismiss();
                Toast.makeText(getApplicationContext(), machineAttrResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}