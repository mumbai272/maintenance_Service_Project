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
 * Created by anand on 03-Nov-16.
 */
public class EmployeeListAdapter  extends BaseAdapter {

    private Context cxt;
    private ArrayList<UserDTO> empList;

    public EmployeeListAdapter(Context cxt, ArrayList<UserDTO> empList) {
        this.cxt = cxt;
        this.empList = empList;
    }

    @Override
    public int getCount() {
        return empList.size();
    }

    @Override
    public Object getItem(int i) {
        return empList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.emp_list_view, null);
        TextView userName= (TextView) v.findViewById(R.id.emp_name);
        TextView userEmail= (TextView) v.findViewById(R.id.emp_designation);

        userName.setText(empList.get(i).getUserName());
        userEmail.setText(empList.get(i).getRole());
        v.setTag(empList.get(i).getUserId());
        return v;
    }
}
