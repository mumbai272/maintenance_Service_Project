package com.android.maintenance.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 16-Nov-16.
 */
public class ServiceEngineerListAdaptor  extends BaseAdapter {

    private Context cxt;
    ArrayList<ReportLogDTO> AreportLogList;
    public ServiceEngineerListAdaptor(Context applicationContext, ArrayList<ReportLogDTO> reportLogList) {
        this.cxt = cxt;
        this.AreportLogList=reportLogList;
    }

    @Override
    public int getCount() {
        return AreportLogList.size();
    }

    @Override
    public Object getItem(int i) {
        return AreportLogList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.list_report_log, null);
 /*       TextView title= (TextView) v.findViewById(R.id.);
        TextView subtitile= (TextView) v.findViewById(R.id.);
*/
        v.setTag(AreportLogList.get(i).getReportId());
        return v;
    }
}
