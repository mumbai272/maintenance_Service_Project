package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.ClaimResposnse;
import com.android.maintenance.DTO.CreateAssetReportDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;

/**
 * Created by anand on 15-Nov-16.
 */
public class CreateAssetReportActivity extends Activity {

    EditText person,prb_attended,ser_det,ser_remark,clie_remark,followup_action,clie_followup_action,reportname;
    Button create_btn;
    RadioGroup warrenty,chargable;
    String token;
    Gson gson;
    Intent intent;
    Long Clieid;
    CreateAssetReportDTO dto;
    Long logID;
    AssetLogDTO logDTO;
    AssetReportDTO reportDTO;
    String client_idStr,company_idStr;
    private SessionManager session;
    RadioButton radiowarrentyButton,radiochargableButton;
    String person_Str,prb_attended_Str,ser_det_Str,ser_remark_Str,clie_remark_Str,followup_action_Str,clie_followup_action_Str,radiowarrentyButton_Str,radiochargableButton__Str,reportname_Str;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_asset_report);

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        company_idStr = user.get("KEY_COMPANY_ID");
        client_idStr = user.get("KEY_CLIENT_ID");
        token=user.get(SessionManager.KEY_TOKEN);

        logDTO= (AssetLogDTO) getIntent().getSerializableExtra("Log");

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cre_rep);
        toolbar.setTitle("Create Asset Report");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });*/


        reportname=(EditText)findViewById(R.id.reportname);
        person= (EditText)findViewById(R.id.edit_c_person);
        prb_attended= (EditText)findViewById(R.id.edit_p_attanded);
        ser_det=(EditText)findViewById(R.id.edit_ser_det);
        ser_remark=(EditText)findViewById(R.id.edit_eng_remark);
        clie_remark=(EditText)findViewById(R.id.edit_clie_remark);
        followup_action=(EditText)findViewById(R.id.edit_followup_Action);
        clie_followup_action=(EditText)findViewById(R.id.edit_clie_followup_action);
        create_btn=(Button)findViewById(R.id.crt_report_btn);

        warrenty= (RadioGroup)findViewById(R.id.radio_wrty);
        chargable =(RadioGroup) findViewById(R.id.radio_chargable);

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
                reportname_Str=reportname.getText().toString();
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
                    dto= new CreateAssetReportDTO();
                    dto.setLogId(logDTO.getLogId());
                    dto.setCompanyId(Long.parseLong(company_idStr));
                    dto.setWarranty(radiochargableButton__Str);
                    dto.setContactPerson(person_Str);
                    dto.setProblemAttened(prb_attended_Str);
                    dto.setClientId(logDTO.getClientId());
                    dto.setReportNo(reportname_Str);
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
            ClaimResposnse clientResponse=gson.fromJson(result, ClaimResposnse.class);
            if(clientResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
                /*intent =new Intent(CreateAssetReportActivity.this,AddServiceEngineerActivity.class);
                intent.putExtra("LogID",logID);
                startActivity(intent);*/
               // intent=new Intent();
                //ReportTab tab=new ReportTab();
                intent=new Intent(CreateAssetReportActivity.this,TaskAndReportTabActivity.class);
              /*  intent.putExtra("reportDTO",dto);
                intent.putExtra("reportID",clientResponse.getId());*/
                intent.putExtra("Log",logDTO);
                startActivity(intent);
                finish();

            }else{

                Toast.makeText(getApplicationContext(),clientResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}
