package com.android.maintenance.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;
import com.android.maintenance.adapters.UserApprovalListAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 02-Oct-16.
 */
public class UserApprovalActivity extends Activity{
    ListView listView;
    Intent intent;
    UserApprovalListAdapter adapter;
    ArrayList<UserDTO> userList;
    Context context = this;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_approval);
        Log.e("","Execute thisssss");
        listView=(ListView)findViewById(R.id.listView_users);
        userList = new ArrayList<UserDTO>();
        userList = (ArrayList<UserDTO>) getIntent().getSerializableExtra("arrayList");
        displayUserApprovalListView();
    }

    private void displayUserApprovalListView() {
        Log.e("","Execute this");
        adapter=new UserApprovalListAdapter(getApplicationContext(),userList);
        listView.setAdapter(adapter);
        if(userList.size() !=0) {
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new ModeCallback());
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext(), "User ID:" + view.getTag(), Toast.LENGTH_SHORT).show();
                    //navigate to detail page of approval
                    Long userId = (Long) view.getTag();
                    Log.e("userId:", "" + userId);
                    intent = new Intent(UserApprovalActivity.this, UserApprovalDetailsActivity.class);
                    intent.putExtra("arrayList", userList);
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

    private class ModeCallback implements  ListView.MultiChoiceModeListener {

        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
            final int checkedCount = listView.getCheckedItemCount();
           // actionMode.setTitle(checkedCount + " Selected");
            switch (checkedCount) {
                case 0:
                    actionMode.setSubtitle(null);
                    break;
                case 1:
                    actionMode.setSubtitle("1 item selected");
                    break;
                default:
                    actionMode.setSubtitle("" + checkedCount + " items selected");
                    break;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.user_list_select,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete_id:
                    Toast.makeText(UserApprovalActivity.this, "Delete " + listView.getCheckedItemCount() +
                            " items", Toast.LENGTH_SHORT).show();
                    //Log.e("size",""+note_id_list.size());
                    Intent objIndent = new Intent(getApplicationContext(),UserApprovalActivity.class);
                    startActivity(objIndent);
                    actionMode.finish();
                    break;
                default:
                    Toast.makeText(UserApprovalActivity.this, "Clicked " + menuItem.getTitle(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {

        }

        }


}

