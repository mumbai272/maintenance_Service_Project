package com.android.maintenance.activities;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.GetMachineAttributeDTO;
import com.android.maintenance.DTO.LoginInputDTO;
import com.android.maintenance.DTO.MachineAttributeDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.Utilities.Utility;
import com.android.maintenance.adapters.EmployeeListAdapter;
import com.android.maintenance.adapters.MachineAttributeAdapter;
import com.android.maintenance.asyncTask.MachineAttribute;
import com.android.maintenance.configuration.ConfigConstant;
import com.android.maintenance.fragments.MakePager;
import com.android.maintenance.fragments.ModelPager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class MachineMakeActivity extends AppCompatActivity {

    ImageButton add;
    ListView listView;
    Intent intent;
    Context context=this;
    ArrayList<GetMachineAttributeDTO> machineAttrList;
    Gson gson;
    MachineAttributeAdapter adapter;
    public String token, userID,companyId;
    private SessionManager session;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_make_activity);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        companyId=user.get("KEY_COMPANY_ID");

        add=(ImageButton)findViewById(R.id.add_machine_make);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent= new Intent(MachineMakeActivity.this,AddMachineMakeActivity.class);
                startActivity(intent);
            }
        });

        MachineAttribute attribute= new MachineAttribute(this);
        attribute.execute(ConfigConstant.url+"machine/machinemake?companyId="+ Long.parseLong(companyId));
    }


    public void displayMachinemake(JSONArray machineAttr) {
        if(machineAttr==null){

        }else{
            String machineData = machineAttr.toString();
            gson = new Gson();
            Type type = new TypeToken<ArrayList<GetMachineAttributeDTO>>() {
            }.getType();
            machineAttrList= new ArrayList<GetMachineAttributeDTO>();
            machineAttrList = gson.fromJson(machineData, type);
            adapter = new MachineAttributeAdapter(getApplicationContext(), machineAttrList);
            listView=(ListView)findViewById(R.id.list_machine_make);
            listView.setAdapter(adapter);
        }
    }
}
