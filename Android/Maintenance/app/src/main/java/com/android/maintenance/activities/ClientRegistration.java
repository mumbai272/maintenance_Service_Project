package com.android.maintenance.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maintenance.DTO.AddClientDTO;
import com.android.maintenance.DTO.AddressDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;

/**
 * Created by anand on 15-Sep-16.
 */
public class ClientRegistration extends AppCompatActivity {

    EditText name,email,description,address,street1,street2,street3,country,state,city,location,zip,phone,mobile,fax,website;
    Button client_add;
    String strname,stremail,strdescription,straddress,strstreet1,strstreet2,strstreet3,strcountry,strstate,strcity,strlocation,strzip,strphone,strmobile,strfax,strwebsite;
    private SessionManager session;
    String token,companyId;
    Intent intent;
    Context context=this;
    private ProgressDialog mProgress;
    EditText tin,pan,cinNo,eccNo,serviceTaxNO,cstNo;
    String tinStr,panStr,cinNoStr,eccNoStr,serviceTaxNOStr,cstNoStr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_registration);
        name=(EditText) findViewById(R.id.edit_client_name);
        email=(EditText) findViewById(R.id.edit_client_email);
        description=(EditText) findViewById(R.id.edit_client_desc);
        address=(EditText) findViewById(R.id.edit_client_address);
        street1=(EditText) findViewById(R.id.edit_client_street1);
        street2=(EditText) findViewById(R.id.edit_client_street2);
        street3=(EditText) findViewById(R.id.edit_client_street3);
        country=(EditText) findViewById(R.id.edit_client_country);
        state=(EditText) findViewById(R.id.edit_client_state);
        city=(EditText) findViewById(R.id.edit_client_city);
        location=(EditText) findViewById(R.id.edit_client_location);
        zip=(EditText) findViewById(R.id.edit_client_zip);
        phone=(EditText) findViewById(R.id.edit_client_phone);
        mobile=(EditText) findViewById(R.id.edit_client_mobile);
        fax=(EditText) findViewById(R.id.edit_client_fax);
        website=(EditText) findViewById(R.id.edit_client_website);
        client_add=(Button) findViewById(R.id.client_register_btn);
        tin=(EditText) findViewById(R.id.tin_no);
        pan=(EditText) findViewById(R.id.pan_no);
        cinNo=(EditText) findViewById(R.id.cinNo);
        eccNo=(EditText)findViewById(R.id.eccNo);
        serviceTaxNO=(EditText)findViewById(R.id.servicetax);
        cstNo=(EditText)findViewById(R.id.cstNo);
        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token=user.get(SessionManager.KEY_TOKEN);
        companyId=user.get("KEY_COMPANY_ID");
        Log.e("","Token:"+token);

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        client_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validation
                tinStr=tin.getText().toString();
                serviceTaxNOStr=serviceTaxNO.getText().toString();
                panStr=pan.getText().toString();
                cstNoStr=cstNo.getText().toString();
                eccNoStr=eccNo.getText().toString();
                cinNoStr=cinNo.getText().toString();
                strname= name.getText().toString();
                stremail=email.getText().toString();
                strdescription=description.getText().toString();
                straddress=address.getText().toString();
                strstreet1=street1.getText().toString();
                strstreet2=street2.getText().toString();
                strstreet3=street3.getText().toString();
                strcountry=country.getText().toString();
                strstate=state.getText().toString();
                strcity=city.getText().toString();
                strlocation=location.getText().toString();
                strzip=zip.getText().toString();
                strphone=phone.getText().toString();
                strmobile=mobile.getText().toString();
                strfax=fax.getText().toString();
                strwebsite=website.getText().toString();
                ObjectMapper mapper = new ObjectMapper();
                try{
                    String json="";
                    AddressDTO addressDTO= new AddressDTO(straddress,strstreet1,strstreet2,strstreet3,strcountry,strstate,strcity,strlocation,strzip,strphone,strmobile,strfax,strwebsite,stremail);
                    AddClientDTO client= new AddClientDTO(Long.parseLong(companyId), strname,strdescription,addressDTO,tinStr,cstNoStr,serviceTaxNOStr,eccNoStr,cinNoStr,panStr);
                    json=mapper.writeValueAsString(client);
                    Log.e("this is","json data"+json);
                    new ClientPost().execute(json);
                    mProgress.show();
                }catch (Exception e){

                }
            }
        });

    }

    private class ClientPost extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            result= servicepost.makeServicePostWithToken(ConfigConstant.url+"client",param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("result data",":"+s);
            //deserialize the data
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO clientOutput=gson.fromJson(s, BaseResponseDTO.class);
            Log.e("result data",":"+clientOutput.toString());
            if(clientOutput.getStatusCode()==1){
                mProgress.dismiss();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Success");
                    alertDialogBuilder
                            .setMessage(clientOutput.getMsg())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    intent= new Intent(ClientRegistration.this,AdminMainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                  alertDialog.show();
            }else{
                Log.e("ERROR :",""+clientOutput.getMsg());
                mProgress.dismiss();
                Toast.makeText(getApplicationContext(),clientOutput.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
