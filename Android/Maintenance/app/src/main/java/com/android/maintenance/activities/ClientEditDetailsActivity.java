package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 26-Oct-16.
 */
public class ClientEditDetailsActivity extends Activity {
    Intent intent;
    ArrayList<ClientDTO> client;
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_edit_details);
        text= (EditText) findViewById(R.id.edit_text_name);

        client=new ArrayList<ClientDTO>();
        client =(ArrayList<ClientDTO>) getIntent().getSerializableExtra("client_list");
        Log.e("","List:"+client.size());

        text.setText(client.get(0).getClientName());
    }
}
