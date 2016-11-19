package com.android.maintenance.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;

import java.util.ArrayList;

/**
 * Created by anand on 19-Nov-16.
 */
public class MiscExpenseAdaptor extends BaseAdapter {
    Context cxt;
    ArrayList<MiscExpenseDTO> misc_exp_list;

    public MiscExpenseAdaptor(Context cxt, ArrayList<MiscExpenseDTO> list) {
        this.cxt = cxt;
        this.misc_exp_list = list;
    }


    @Override
    public int getCount() {
        return misc_exp_list.size();
    }

    @Override
    public Object getItem(int i) {
        return misc_exp_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(cxt, R.layout.misc_tab_list, null);

        TextView part = (TextView) v.findViewById(R.id.part);
        TextView on = (TextView) v.findViewById(R.id.billed_on);
        TextView amt = (TextView) v.findViewById(R.id.amount);
        TextView bill_no = (TextView) v.findViewById(R.id.bill_no);
        TextView date = (TextView) v.findViewById(R.id.exp_on);
        Button delete = (Button) v.findViewById(R.id.delete);

        part.setText(misc_exp_list.get(i).getParticulars());
        bill_no.setText(misc_exp_list.get(i).getBillNumber());
        on.setText(misc_exp_list.get(i).getBillDate());
        amt.setText(String.valueOf(misc_exp_list.get(i).getClaimAmount()));
        date.setText(misc_exp_list.get(i).getExpenseDate());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        v.setTag(misc_exp_list.get(i).getExpenseId());


        return v;
    }
}