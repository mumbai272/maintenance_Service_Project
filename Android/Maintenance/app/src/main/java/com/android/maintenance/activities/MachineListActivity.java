package com.android.maintenance.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.MachineDetailDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.adapters.MachineListAdapter;
import com.android.maintenance.asyncTask.GetMachineList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 25-Oct-16.
 */
public class MachineListActivity extends Activity {

    ListView listView;
    private SessionManager session;
    Intent intent;
    public String token, userID;
    Gson gson;
    ImageButton addMachine;
    MachineListAdapter adapter;
    ArrayList<MachineDetailDTO> machineList;
    Context context = this;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_list_view);
        listView=(ListView) findViewById(R.id.listView_machines);

        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        userID = user.get("KEY_USER_ID");
        token = user.get(SessionManager.KEY_TOKEN);
        Log.e("user_ id:" + userID, "Token:" + token);

        addMachine= (ImageButton) findViewById(R.id.add_machine);
        addMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MachineListActivity.this,MachineRegister.class);
                startActivity(intent);
                finish();

            }
        });

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        GetMachineList machineList=  new GetMachineList(this);
        machineList.execute();
        mProgress.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Machine List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  intent = new Intent(MachineListActivity.this, AdminMainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });

    }

    public void displayMachineListView(JSONArray machineData) {
        mProgress.dismiss();
        if(machineData==null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Machines");
            alertDialogBuilder
                    .setMessage("NO machines in the List.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }else {
            String machineStr = machineData.toString();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Type type = new TypeToken<ArrayList<MachineDetailDTO>>() {
            }.getType();
            machineList = gson.fromJson(machineStr, type);
            Log.e("", "machine list:" + machineList.size());
            adapter = new MachineListAdapter(getApplicationContext(), machineList,token);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), "User ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    //navigate to detail page of approval
                    Long userId = (Long) view.getTag();
                    Log.e("userId:", "" + userId);
                }
            });
        }
    }
}
