package com.android.maintenance.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.maintenance.DTO.ClaimConveyanceExpenseDTO;
import com.android.maintenance.DTO.GetAssignedAssetLogDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 19-Nov-16.
 */
public class ConvayanseExpenseAdaptor extends BaseAdapter {
    Context cxt;
    ArrayList<ClaimConveyanceExpenseDTO> conv_exp_list;

    public ConvayanseExpenseAdaptor(Context cxt,  ArrayList<ClaimConveyanceExpenseDTO> list) {
        this.cxt = cxt;
        this.conv_exp_list = list;
    }


    @Override
    public int getCount() {
        return conv_exp_list.size();
    }

    @Override
    public Object getItem(int i) {
        return conv_exp_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.conveyanse_tab_list, null);

        TextView  part= (TextView) v.findViewById(R.id.li_part);
        TextView  by= (TextView) v.findViewById(R.id.by);
        TextView amt= (TextView) v.findViewById(R.id.li_amount);
        TextView  date= (TextView) v.findViewById(R.id.date_on);
        Button delete= (Button) v.findViewById(R.id.delete);

        part.setText(conv_exp_list.get(i).getTravelFrom()+ " To "+conv_exp_list.get(i).getTravelTo());
        by.setText(conv_exp_list.get(i).getModeOfTransport());
        amt.setText(String.valueOf(conv_exp_list.get(i).getClaimAmount()));
        date.setText(conv_exp_list.get(i).getExpenseDate());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        v.setTag(conv_exp_list.get(i).getExpenseId());
        return v;
    }
}
