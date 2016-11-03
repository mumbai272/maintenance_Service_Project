package com.android.maintenance.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.DTO.MachineDetailDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 25-Oct-16.
 */
public class MachineListAdapter extends BaseAdapter {

    private Context cxt;
    private ArrayList<MachineDetailDTO> machineList;

    public MachineListAdapter(Context cxt, ArrayList<MachineDetailDTO> machineList) {
        this.cxt = cxt;
        this.machineList = machineList;
    }

    @Override
    public int getCount() {
        return machineList.size();
    }

    @Override
    public Object getItem(int i) {
        return machineList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.machine_list_view_detail, null);
        TextView machine_title = (TextView) v.findViewById(R.id.machine_title);
        TextView address = (TextView) v.findViewById(R.id.machine_address);
        Button log = (Button) v.findViewById(R.id.log);
        Button delete =(Button) v.findViewById(R.id.delete);

        machine_title.setText(machineList.get(i).getAssetDescription());
        //location.setText();
        address.setText(machineList.get(i).getLocation());

        v.setTag(machineList.get(i).getClientId());
        return v;
    }
}
