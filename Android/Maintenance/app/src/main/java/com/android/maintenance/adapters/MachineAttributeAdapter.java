package com.android.maintenance.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.maintenance.DTO.GetMachineAttributeDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 06-Nov-16.
 */
public class MachineAttributeAdapter extends BaseAdapter {

    private Context cxt;
    private ArrayList<GetMachineAttributeDTO> machineAttrList;

    public MachineAttributeAdapter(Context cxt, ArrayList<GetMachineAttributeDTO> empList) {
        this.cxt = cxt;
        this.machineAttrList = empList;
    }

    @Override
    public int getCount() {
        return machineAttrList.size();
    }

    @Override
    public Object getItem(int i) {
        return machineAttrList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.machine_attr_list, null);
        TextView machineName= (TextView) v.findViewById(R.id.machine_attr_name);
        TextView machineDesc= (TextView) v.findViewById(R.id.machine_attr_desc);

        machineName.setText(machineAttrList.get(i).getMachineName());
        machineDesc.setText(machineAttrList.get(i).getDescription());
        v.setTag(machineAttrList.get(i).getMachineId());
        return v;
    }
}

