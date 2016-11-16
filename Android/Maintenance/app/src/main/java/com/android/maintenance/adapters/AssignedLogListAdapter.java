
package com.android.maintenance.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.maintenance.DTO.EmployeeAssignedWorkDTO;
import com.android.maintenance.DTO.GetAssignedAssetLogDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 11-Nov-16.
 */
public class AssignedLogListAdapter extends BaseAdapter {
    Context cxt;
    ArrayList<GetAssignedAssetLogDTO> empLogList;

    public AssignedLogListAdapter(Context cxt, ArrayList<GetAssignedAssetLogDTO> empLogList) {
        this.cxt = cxt;
        this.empLogList = empLogList;
    }


    @Override
    public int getCount() {
        return empLogList.size();
    }

    @Override
    public Object getItem(int i) {
        return empLogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.assigned_log_list, null);
        TextView log_title = (TextView) v.findViewById(R.id.assigned_title);
        TextView status = (TextView) v.findViewById(R.id.assigned_sub);

        log_title.setText(empLogList.get(i).getWorkType());
        //location.setText();
        status.setText(empLogList.get(i).getAssignedTo().getUserName());

        v.setTag(empLogList.get(i).getAssignId());
        return v;
    }
}
