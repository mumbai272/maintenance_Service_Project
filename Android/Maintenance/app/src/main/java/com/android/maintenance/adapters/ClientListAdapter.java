package com.android.maintenance.adapters;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.ClientDTO;
import com.android.maintenance.R;
import com.android.maintenance.activities.AdminMainActivity;

import java.util.ArrayList;

/**
 * Created by anand on 23-Oct-16.
 */
public class ClientListAdapter extends BaseAdapter {

    private Context cxt;
    private ArrayList<ClientDTO> clientList;

    public ClientListAdapter(Context cxt, ArrayList<ClientDTO> clientList) {
        this.cxt = cxt;
        this.clientList = clientList;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int i) {
        return clientList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(cxt, R.layout.client_list_view, null);
        TextView clientName= (TextView) v.findViewById(R.id.client_name);
        TextView location= (TextView) v.findViewById(R.id.location);
      /*  Button editBtn = (Button) v.findViewById(R.id.edit_button);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Edit Button Clicked", "**********");
                int position= i;
               clientList.get(position);
                Log.e("clientId:", "" + clientList.get(position).getClientId());
                AdminMainActivity activity= new AdminMainActivity();
                activity.showClientDetail(clientList, clientList.get(position).getClientId(),2);
            }
        });*/

        clientName.setText(clientList.get(i).getClientName());
        location.setText(clientList.get(i).getAddress().getLocation()+","+clientList.get(i).getAddress().getCity());
        v.setTag(clientList.get(i).getClientId());
        return v;
    }
}
