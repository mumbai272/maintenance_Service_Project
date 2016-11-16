package com.android.maintenance.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.DTO.CreateAssetReportDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.asyncTask.GetClientList;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anand on 15-Nov-16.
 */
public class CreateAssetReportActivity extends Activity {

    EditText person,prb_attended,ser_det,ser_remark,clie_remark,followup_action,clie_followup_action;
    Button create_btn;
    Spinner clie;
    RadioGroup warrenty,chargable;
    String token;
    Gson gson;
    ArrayList<ClientDTO> clientList;
    Intent intent;
    Long Clieid;
    Long logID;
    ArrayAdapter<String> adapter;
    String client_idStr,company_idStr;
    private SessionManager session;
    RadioButton radiowarrentyButton,radiochargableButton;
    String person_Str,prb_attended_Str,ser_det_Str,ser_remark_Str,clie_remark_Str,followup_action_Str,clie_followup_action_Str,radiowarrentyButton_Str,radiochargableButton__Str;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_asset_report);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        company_idStr = user.get("KEY_COMPANY_ID");
        client_idStr = user.get("KEY_CLIENT_ID");
        token=user.get(SessionManager.KEY_TOKEN);

        logID=getIntent().getLongExtra("logId",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cre_rep);
        toolbar.setTitle("Create Asset Report");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(CreateAssetReportActivity.this, EmployeeWorkAssignListActivity.class);
                startActivity(intent);
                finish();
            }
        });



        person= (EditText)findViewById(R.id.edit_c_person);
        prb_attended= (EditText)findViewById(R.id.edit_p_attanded);
        ser_det=(EditText)findViewById(R.id.edit_ser_det);
        ser_remark=(EditText)findViewById(R.id.edit_eng_remark);
        clie_remark=(EditText)findViewById(R.id.edit_clie_remark);
        followup_action=(EditText)findViewById(R.id.edit_followup_Action);
        clie_followup_action=(EditText)findViewById(R.id.edit_clie_followup_action);
        create_btn=(Button)findViewById(R.id.crt_report_btn);
        clie=(Spinner)findViewById(R.id.cli_spin);

        warrenty= (RadioGroup)findViewById(R.id.radio_wrty);
        chargable =(RadioGroup) findViewById(R.id.radio_chargable);

        setClientSpinner();
        warrenty.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.wrty_yes:
                        radiowarrentyButton = (RadioButton) findViewById(i);
                        break;
                    case R.id.wrty_no:
                        radiowarrentyButton = (RadioButton) findViewById(i);
                        break;
                }
            }
        });

        chargable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.ch_yes:
                        radiochargableButton = (RadioButton) findViewById(i);
                        break;
                    case R.id.ch_no:
                        radiochargableButton = (RadioButton) findViewById(i);
                        break;
                }
            }
        });


        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person_Str=person.getText().toString();
                prb_attended_Str=prb_attended.getText().toString();
                ser_det_Str=ser_det.getText().toString();
                ser_remark_Str=ser_remark.getText().toString();
                clie_remark_Str=clie_remark.getText().toString();
                followup_action_Str=followup_action.getText().toString();
                clie_followup_action_Str=clie_followup_action.getText().toString();
                radiowarrentyButton_Str=radiowarrentyButton.getText().toString();
                radiochargableButton__Str=radiochargableButton.getText().toString();

                if(radiowarrentyButton_Str.equals("Yes")){

                    radiowarrentyButton_Str="T";
                }else{
                    radiowarrentyButton_Str="F";
                }

                if(radiochargableButton__Str.equals("Yes")){

                    radiochargableButton__Str="T";
                }else{
                    radiochargableButton__Str="F";
                }

                try {
                    String json="";
                   CreateAssetReportDTO dto= new CreateAssetReportDTO();
                    dto.setLogId(logID);
                    dto.setCompanyId(Long.parseLong(company_idStr));
                    dto.setWarranty(radiochargableButton__Str);
                    dto.setContactPerson(person_Str);
                    dto.setProblemAttened(prb_attended_Str);
                    dto.setClientId(Long.parseLong(client_idStr));
                 //   dto.setReportNo();
                    dto.setSerEngRemarks(ser_remark_Str);
                    dto.setClientRemarks(clie_remark_Str);
                    dto.setServiceDetails(ser_det_Str);
                    dto.setClientFallowUpAction(clie_followup_action_Str);
                    dto.setFallowUpAction(followup_action_Str);
                    dto.setChargeble(radiochargableButton__Str);

                    ObjectMapper mapper = new ObjectMapper();
                    json = mapper.writeValueAsString(dto);
                    new CreateAssetReport().execute(json);

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });



    }


    private void setClientSpinner() {
        GetClientList list=new GetClientList(this);
        list.execute();
    }

    public void displayClientListView(JSONArray companyData) {
        if(companyData==null){

        }else {
            clientList=new ArrayList<ClientDTO>();
            String companyDataStr = companyData.toString();
            Log.e("sds","companyDataStr:"+companyDataStr);
            gson = new GsonBuilder().create();
            Type type = new TypeToken<ArrayList<ClientDTO>>() {
            }.getType();
            clientList = gson.fromJson(companyDataStr, type);
            Log.e("ds","userdto:"+clientList.size());
            final List<String> lables = new ArrayList<String>();
            for (int i=0; i< clientList.size();i++) {
                lables.add(clientList.get(i).getClientName());
            }
            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lables);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // attaching data adapter to spinner
            clie.setAdapter(adapter);
            clie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                  String  client =adapterView.getItemAtPosition(position).toString();
                    for(int i=0;i<clientList.size();i++){
                        if(clientList.get(i).getClientName().equals(client)){
                            Clieid=clientList.get(i).getClientId();
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

        });

    }
    }

    private class CreateAssetReport extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            Log.e("","this input post"+param[0]);
            result= servicepost.makeServicePostWithToken(ConfigConstant.url+"assetlog/report",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO clientResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(clientResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
                intent =new Intent(CreateAssetReportActivity.this,AddServiceEngineerActivity.class);
                intent.putExtra("LogID",logID);
                startActivity(intent);
            }else{

                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
