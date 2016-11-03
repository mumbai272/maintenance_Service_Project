package com.android.maintenance.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.maintenance.DTO.AddressDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ClientDTO;
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
    String token;

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

        session=new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        token=user.get(SessionManager.KEY_TOKEN);
        Log.e("","Token:"+token);

        client_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validation
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
                    ClientDTO client= new ClientDTO(ConfigConstant.company_id, strname,strdescription,addressDTO);
                    json=mapper.writeValueAsString(client);
                    Log.e("this is","json data"+json);
                    new ClientPost().execute(json);
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
                Log.e("Success :",""+clientOutput.getMsg());
                Toast.makeText(getApplicationContext(),clientOutput.getMsg(), Toast.LENGTH_LONG).show();
            }else{
                Log.e("ERROR :",""+clientOutput.getMsg());
                Toast.makeText(getApplicationContext(),clientOutput.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
