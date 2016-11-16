package com.android.maintenance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.activities.AssignLogActivity;
import com.android.maintenance.activities.CreateAssetReportActivity;
import com.android.maintenance.configuration.ConfigConstant;

/**
 * Created by anand on 14-Nov-16.
 */
public class ReportTab extends android.support.v4.app.Fragment {
    int pos;
    Intent intent;
    AssetLogDTO log;
    ListView listView;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        final Bundle args = getArguments();
        log= (AssetLogDTO) args.getSerializable("Log");
      //  get report

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_tab,container, false);
        listView = (ListView)view.findViewById(R.id.listView_reports);
        ImageButton button = (ImageButton) view.findViewById(R.id.add_report);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent= new Intent(getActivity(),CreateAssetReportActivity.class);
                intent.putExtra("Log",log.getLogId());
                startActivity(intent);
            }
        });
        return view;

    }
}
