package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.adapters.UserApprovalListAdapter;
import com.android.maintenance.configuration.ConfigConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anand on 02-Oct-16.
 */
public class UserApprovalActivity extends AppCompatActivity {
    ListView listView;
    Intent intent;
    UserApprovalListAdapter adapter;
    ArrayList<UserDTO> userList;
    ArrayList<ClientDTO> clientList;
    Context context = this;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_approval);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("User Approval List");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(UserApprovalActivity.this, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Log.e("","Execute thisssss");
        listView=(ListView)findViewById(R.id.listView_users);
        userList = new ArrayList<UserDTO>();
        clientList = new ArrayList<ClientDTO>();
        userList = (ArrayList<UserDTO>) getIntent().getSerializableExtra("userList");
        clientList =(ArrayList<ClientDTO>) getIntent().getSerializableExtra("clientList");
        displayUserApprovalListView();
    }


    private void displayUserApprovalListView() {
        Log.e("","Execute this");
        adapter=new UserApprovalListAdapter(getApplicationContext(),userList);
        listView.setAdapter(adapter);
        if(userList.size() !=0) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), "User ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    Long userId = (Long) view.getTag();
                    Log.e("userId:", "" + userId);
                    intent = new Intent(UserApprovalActivity.this, UserApprovalDetailsActivity.class);
                    intent.putExtra("arrayList", userList);
                    intent.putExtra("clientList",clientList);
                    intent.putExtra("user_id", userId);
                    startActivity(intent);
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
                            intent = new Intent(UserApprovalActivity.this, AdminMainActivity.class);
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

