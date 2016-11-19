package com.android.maintenance.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.GetAssignedAssetLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 16-Nov-16.
 */
public class StartStopActivity extends Activity implements LocationListener {
    protected LocationManager locationManager;
    protected Context context;
    ProgressDialog dialog;
    private SessionManager session;
    String role,token;
    Intent intent;
    ArrayList<GetAssignedAssetLogDTO> assignlogList;
    Long assignId;
    TextView hr, exp, work,complete;
    Button Start, Stop;
    private double latitude = 0;
    private double longitude = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_stop_log);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);
        token=user.get(SessionManager.KEY_TOKEN);
        hr = (TextView) findViewById(R.id.hr_e);
        exp = (TextView) findViewById(R.id.exp_e);
        work = (TextView) findViewById(R.id.type_e);
        complete=(TextView)findViewById(R.id.complte);
        Start = (Button) findViewById(R.id.start);
        Stop = (Button) findViewById(R.id.stop);

        dialog = new ProgressDialog(StartStopActivity.this);
        dialog.show();
        dialog.setMessage("Getting Coordinates");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 10000,
                    1, this);
        } else if (locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 10000,
                    1, this);
        }
        else {
            dialog.dismiss();

            Toast.makeText(getApplicationContext(), "Enable Location", Toast.LENGTH_LONG).show();
        }


        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latLong= String.valueOf(latitude)+","+String.valueOf(longitude);
              StartStop start= new StartStop();
                start.execute(ConfigConstant.url+"asset/logs/assign/"+assignId+"/start/"+latLong);
            }

        });


        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latLong= String.valueOf(latitude)+","+String.valueOf(longitude);
                StartStop start= new StartStop();
                start.execute(ConfigConstant.url+"asset/logs/assign/"+assignId+"/end/"+latLong);;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_start_stop);
        toolbar.setTitle("Start Or Stop");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals(ConfigConstant.employeeRole)){
                    intent = new Intent(StartStopActivity.this, EmployeeWorkAssignListActivity.class);
                    startActivity(intent);
                    finish();
                }else if(role.equals(ConfigConstant.adminRole)){
                    intent = new Intent(StartStopActivity.this, LogListActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        assignlogList = new ArrayList<GetAssignedAssetLogDTO>();
        assignlogList = (ArrayList<GetAssignedAssetLogDTO>) getIntent().getSerializableExtra("assignList");
        assignId = getIntent().getLongExtra("assignId", 1);
        for (int i = 0; i < assignlogList.size(); i++) {
            if (assignId.equals(assignlogList.get(i).getAssignId())) {
                hr.setText(assignlogList.get(i).getPlannedHours().toString());
                exp.setText(assignlogList.get(i).getExpServiceDateTime());
                work.setText(assignlogList.get(i).getWorkType());
                String status=assignlogList.get(i).getStatus();

                if(status.equals("NEW")){
                    Start.setVisibility(View.VISIBLE);
                }else if(status.equals("INPROGRESS")){
                    Stop.setVisibility(View.VISIBLE);
                }else if(status.equals("COMPLETE")){
                    complete.setVisibility(View.VISIBLE);
                }
            }
        }


    }

    protected void refresh() {

        super.onResume();
        this.onCreate(null);

    }


    @Override
    public void onLocationChanged(Location location) {
        dialog.show();
        latitude = location.getLatitude();
        longitude =location.getLongitude();
        if (latitude != 0 && longitude != 0){

           Log.e("getLatitude"+location.getLatitude(),"getLongitude"+location.getLongitude());
            dialog.dismiss();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    private class StartStop extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... param) {
            String result="";

            Log.e("LatLong",":"+param[0]);
            ServiceHandlerWS serviceput= new ServiceHandlerWS();
            result= serviceput.makeServicePutWithOutData(param[0],token);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("result data",":"+s);
            //deserialize the data
            Gson gson = new GsonBuilder().create();
            BaseResponseDTO clientOutput=gson.fromJson(s, BaseResponseDTO.class);
            Log.e("result data",":"+clientOutput.toString());
            if(clientOutput.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),clientOutput.getMsg(), Toast.LENGTH_LONG).show();
                intent=new Intent(StartStopActivity.this,EmployeeWorkAssignListActivity.class);
                startActivity(intent);
            }else{
                Log.e("ERROR :",""+clientOutput.getMsg());
                Toast.makeText(getApplicationContext(),clientOutput.getMsg(), Toast.LENGTH_LONG).show();
            }
        }

    }
}


