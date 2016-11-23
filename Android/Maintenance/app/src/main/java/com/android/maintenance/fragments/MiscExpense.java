package com.android.maintenance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.DTO.MiscExpenseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.activities.MiscExpensesActivity;
import com.android.maintenance.adapters.MiscExpenseAdaptor;
import com.android.maintenance.configuration.ConfigConstant;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 18-Nov-16.
 */
public class MiscExpense extends android.support.v4.app.Fragment {

    private SessionManager session;
    String role;
    Intent intent;
    GetClaimListDTO claim;
    ArrayList<MiscExpenseDTO> misc_exp_list;
    ListView listView;
    MiscExpenseAdaptor adapter;
    ImageButton button;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);

        final Bundle args = getArguments();
        claim= (GetClaimListDTO) args.getSerializable("ClimDTO");
        misc_exp_list = ( ArrayList<MiscExpenseDTO>) args.getSerializable("misc_exp_list");
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.misc_expense_tab,container, false);

        listView= (ListView)view.findViewById(R.id.misc_listView);
        button=(ImageButton)view.findViewById(R.id.add_misc_expense);
        if(role.equals(ConfigConstant.employeeRole)&& claim.getStatus().equalsIgnoreCase("ACTIVE")){
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(), MiscExpensesActivity.class);
                intent.putExtra("ID",claim.getClaimId());
                startActivity(intent);
            }
        });


        adapter= new MiscExpenseAdaptor(getActivity().getApplicationContext(), misc_exp_list);
        listView.setAdapter(adapter);
        return view;
    }
}
