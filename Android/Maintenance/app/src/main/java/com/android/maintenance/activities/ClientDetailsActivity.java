package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 26-Oct-16.
 */
public class ClientDetailsActivity extends Activity {
    Intent intent;
    ArrayList<ClientDTO> client;
    TextView clie_name,clie_desc,clie_str1,clie_str2,clie_str3,clie_location,clie_city,clie_state,clie_country,clie_zip,clie_phone,clie_mobile,clie_fax,clie_email,clie_website;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_details);
        clie_name = (TextView) findViewById(R.id.clie_name);
        clie_desc = (TextView) findViewById(R.id.clie_address);
        clie_str1 = (TextView) findViewById(R.id.clie_str1);
        clie_str2 = (TextView) findViewById(R.id.clie_str2);
        clie_str3 = (TextView) findViewById(R.id.clie_str3);
        clie_location = (TextView) findViewById(R.id.clie_location);
        clie_city = (TextView) findViewById(R.id.clie_city);
        clie_state = (TextView) findViewById(R.id.clie_state);
        clie_country = (TextView) findViewById(R.id.clie_country);
        clie_zip = (TextView) findViewById(R.id.clie_zip);
        clie_phone = (TextView) findViewById(R.id.clie_phone);
        clie_mobile = (TextView) findViewById(R.id.clie_mobile);
        clie_fax = (TextView) findViewById(R.id.clie_fax);
        clie_email = (TextView) findViewById(R.id.clie_mail);
        clie_website = (TextView) findViewById(R.id.clie_website);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Client Details");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent = new Intent(ClientDetailsActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        client=new ArrayList<ClientDTO>();
        client =(ArrayList<ClientDTO>) getIntent().getSerializableExtra("client_list");
        Log.e("","List:"+client.size());
        displayDetailView();



    }

    private void displayDetailView() {
        for(int i=0;i<client.size();i++){
            clie_name.setText(client.get(i).getClientName());
            clie_desc.setText(client.get(i).getAddress().getAddressDesc());
            clie_str1.setText(client.get(i).getAddress().getStreet1());
            clie_str2.setText(client.get(i).getAddress().getStreet2());
            clie_str3.setText(client.get(i).getAddress().getStreet3());
            clie_city.setText(client.get(i).getAddress().getCity());
            clie_state.setText(client.get(i).getAddress().getState());
            clie_location.setText(client.get(i).getAddress().getLocation());
            clie_country.setText(client.get(i).getAddress().getCountry());
            clie_zip.setText(client.get(i).getAddress().getZipCode());
            clie_mobile.setText(client.get(i).getAddress().getMobileNo());
            clie_phone.setText(client.get(i).getAddress().getPhoneNo());
            clie_fax.setText(client.get(i).getAddress().getFaxNo());
            clie_email.setText(client.get(i).getAddress().getMailId());
            clie_website.setText(client.get(i).getAddress().getWebsite());

    }
    }
}
