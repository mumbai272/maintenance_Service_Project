package com.android.maintenance.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.adapters.UserApprovalListAdapter;
import com.android.maintenance.asyncTask.GetUserApprovalList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by anand on 02-Oct-16.
 */
public class UserApprovalActivity extends AppCompatActivity {
    ListView listView;
    Intent intent;
    Gson gson;
    UserApprovalListAdapter adapter;
    ArrayList<UserDTO> userList;
    ArrayList<ClientDTO> clientList;
    Context context = this;
    private ProgressDialog mProgress;

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

        mProgress = new ProgressDialog(context);
        mProgress.setTitle("Processing...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        GetUserApprovalList userList =new GetUserApprovalList(this);
        userList.execute();
        mProgress.show();

        Log.e("","Execute thisssss");
        listView=(ListView)findViewById(R.id.listView_users);
        clientList = new ArrayList<ClientDTO>();
        clientList =(ArrayList<ClientDTO>) getIntent().getSerializableExtra("clientList");

    }


    public void navigateToApprovalList(JSONArray dataUsers) {
        mProgress.dismiss();
        if(dataUsers==null){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);
            alertDialogBuilder.setTitle("Use");
            alertDialogBuilder
                    .setMessage("NO User for Approval.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else{
            String userStr = dataUsers.toString();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Type type = new TypeToken<ArrayList<UserDTO>>() {
            }.getType();
            userList = gson.fromJson(userStr, type);
            Log.e("", "user list:" + userList.size());
            adapter=new UserApprovalListAdapter(getApplicationContext(),userList);
            listView.setAdapter(adapter);
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
        }

    }
}

