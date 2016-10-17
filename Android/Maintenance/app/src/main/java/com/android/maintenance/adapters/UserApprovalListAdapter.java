package com.android.maintenance.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.maintenance.DTO.UserDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 08-Oct-16.
 */
public class UserApprovalListAdapter extends BaseAdapter {

    private Context cxt;
    private ArrayList<UserDTO> userList;

    public UserApprovalListAdapter(Context cxt, ArrayList<UserDTO> userList) {
        this.cxt = cxt;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.userlist_view, null);
        TextView userName= (TextView) v.findViewById(R.id.user_name);
        TextView userEmail= (TextView) v.findViewById(R.id.user_email);

        userName.setText(userList.get(i).getUserName());
        userEmail.setText(userList.get(i).getEmailId());
        v.setTag(userList.get(i).getUserId());
        return v;
    }
}
