package com.android.maintenance.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 20-Nov-16.
 */
public class ClaimAccountantAdapter extends BaseAdapter {
    Context cxt;
    Intent intent;
    ArrayList<GetClaimListDTO> claimList;
    public ClaimAccountantAdapter(Context cxt, ArrayList<GetClaimListDTO> claimList) {
        this.cxt = cxt;
        this.claimList = claimList;
    }


    @Override
    public int getCount() {
        return claimList.size();
    }

    @Override
    public Object getItem(int i) {
        return claimList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.claim_list_view, null);
        TextView part= (TextView) v.findViewById(R.id.li_part);
        TextView  end= (TextView) v.findViewById(R.id.li_end);
        TextView  start= (TextView) v.findViewById(R.id.li_start);
        TextView  amt= (TextView) v.findViewById(R.id.li_amount);

        part.setText(claimList.get(i).getParticulars());
        end.setText(claimList.get(i).getClaimEndDate());
        start.setText(claimList.get(i).getClaimStartDate());
        amt.setText(String.valueOf(claimList.get(i).getClaimAmount()));

        v.setTag(claimList.get(i).getClaimId());
        return v;
    }
}
