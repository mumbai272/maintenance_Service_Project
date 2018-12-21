package com.android.maintenance.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.GetAssignedAssetLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.activities.CreateAssetReportActivity;

import java.util.ArrayList;

/**
 * Created by anand on 21-Nov-16.
 */
public class ReportAdapter extends BaseAdapter {
    Context cxt;
    Intent intent;
    ArrayList<AssetReportDTO> assetReportList;

    public ReportAdapter(Context cxt, ArrayList<AssetReportDTO> list) {
        this.cxt = cxt;
        this.assetReportList = list;
    }


    @Override
    public int getCount() {
        return assetReportList.size();
    }

    @Override
    public Object getItem(int i) {
        return assetReportList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(cxt, R.layout.report_tab_view, null);
        TextView report_name=(TextView)v.findViewById(R.id.report_name);
        TextView status=(TextView)v.findViewById(R.id.status);

        ImageButton download = (ImageButton) v.findViewById(R.id.download);
        v.setTag(assetReportList.get(i).getReportId());
       /* if(assetReportList.size()!=0 && assetReportList.get(i).getStatus().equalsIgnoreCase("Done")){
            download.setVisibility(View.VISIBLE);
        }*/



        report_name.setText(assetReportList.get(i).getReportNo());
        status.setText(assetReportList.get(i).getStatus());
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return v;
    }

}