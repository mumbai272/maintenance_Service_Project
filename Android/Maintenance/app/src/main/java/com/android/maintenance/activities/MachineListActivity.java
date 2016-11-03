package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.MachineDetailDTO;
import com.android.maintenance.R;
import com.android.maintenance.adapters.MachineListAdapter;


import java.util.ArrayList;

/**
 * Created by anand on 25-Oct-16.
 */
public class MachineListActivity extends Activity {

    ListView listView;
    Intent intent;
    ImageButton addMachine;
    MachineListAdapter adapter;
    ArrayList<MachineDetailDTO> machineList;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.machine_list_view);
        listView=(ListView) findViewById(R.id.listView_machines);
        machineList=new ArrayList<MachineDetailDTO>();
        machineList =(ArrayList<MachineDetailDTO>) getIntent().getSerializableExtra("machineList");
        displayMachineListView();
        addMachine= (ImageButton) findViewById(R.id.add_machine);
        addMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MachineListActivity.this,MachineRegister.class);
                startActivity(intent);
                finish();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Machine List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MachineListActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void displayMachineListView() {
        Log.e("","Execute this");
        adapter=new MachineListAdapter(getApplicationContext(),machineList);
        listView.setAdapter(adapter);
        if(machineList.size() !=0) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), "User ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    //navigate to detail page of approval
                    Long userId = (Long) view.getTag();
                    Log.e("userId:", "" + userId);
                }
            });

        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Approval");
            alertDialogBuilder
                    .setMessage("NO User for Approval.")
                    .setCancelable(false)
                    .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            intent = new Intent(MachineListActivity.this, AdminMainActivity.class);
                           /* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);*/
                            startActivity(intent);
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }
}
