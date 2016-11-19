package com.android.maintenance.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.activities.TaskAndReportTabActivity;

import java.util.ArrayList;

/**
 * Created by anand on 09-Nov-16.
 */
public class LogListAdapter extends BaseAdapter{
    Context cxt;
    Intent intent;
    ArrayList<AssetLogDTO> logList;
    AssetLogDTO log;
    public LogListAdapter(Context cxt, ArrayList<AssetLogDTO> logList) {
        this.cxt = cxt;
        this.logList = logList;
    }


    @Override
    public int getCount() {
        return logList.size();
    }

    @Override
    public Object getItem(int i) {
        return logList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.asset_log_list, null);
        TextView log_title = (TextView) v.findViewById(R.id.log_title);
        Button task=(Button)v.findViewById(R.id.task);
        Button report=(Button)v.findViewById(R.id.report);
        TextView status = (TextView) v.findViewById(R.id.log_status);
        log_title.setText(logList.get(i).getMaintainanceType());
        //location.setText();
        status.setText(logList.get(i).getStatus());

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log=new AssetLogDTO();
                log=logList.get(i);
                intent=new Intent(cxt,TaskAndReportTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Log",log);
                cxt.startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {intent=new Intent(cxt,TaskAndReportTabActivity.class);
                log=new AssetLogDTO();
                log=logList.get(i);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Log",log);
                cxt.startActivity(intent);
            }
        });

        v.setTag(logList.get(i).getLogId());
        return v;
    }
}
