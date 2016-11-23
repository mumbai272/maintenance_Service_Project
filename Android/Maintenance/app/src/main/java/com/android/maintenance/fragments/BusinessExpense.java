package com.android.maintenance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.maintenance.DTO.BusinessDevExpenseDTO;
import com.android.maintenance.DTO.GetClaimListDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.activities.BusinessDevelopmentExpenseActivity;
import com.android.maintenance.activities.MiscExpensesActivity;
import com.android.maintenance.adapters.BusinessExpenseAdaptor;
import com.android.maintenance.adapters.ConvayanseExpenseAdaptor;
import com.android.maintenance.configuration.ConfigConstant;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 18-Nov-16.
 */
public class BusinessExpense extends android.support.v4.app.Fragment {

    private SessionManager session;
    String role;
    Intent intent;
    ArrayList<BusinessDevExpenseDTO> bus_exp_list;
    ListView listView;
    BusinessExpenseAdaptor adapter;
    GetClaimListDTO claim;
    ImageButton button;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        session = new SessionManager(getActivity());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);

        final Bundle args = getArguments();
        claim= (GetClaimListDTO) args.getSerializable("ClimDTO");
        bus_exp_list= ( ArrayList<BusinessDevExpenseDTO>) args.getSerializable("business_exp_list");
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.business_expense_tab,container, false);
        listView= (ListView)view.findViewById(R.id.bus_listView);
        button=(ImageButton)view.findViewById(R.id.add_bus_exp);

        if(role.equals(ConfigConstant.employeeRole)&&claim.getStatus().equalsIgnoreCase("ACTIVE")){
            button.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(getActivity(), BusinessDevelopmentExpenseActivity.class);
                intent.putExtra("ID",claim.getClaimId());
                startActivity(intent);
            }
        });

        adapter= new BusinessExpenseAdaptor(getActivity().getApplicationContext(), bus_exp_list);
        listView.setAdapter(adapter);

        return view;
    }
}
