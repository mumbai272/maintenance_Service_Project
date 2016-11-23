package com.android.maintenance.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.maintenance.DTO.AssetReportDTO;
import com.android.maintenance.DTO.BaseResponseDTO;
import com.android.maintenance.DTO.ReportChargesDTO;
import com.android.maintenance.DTO.ReportLogDTO;
import com.android.maintenance.DTO.ReportSpareResponseDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.WS.ServiceHandlerWS;
import com.android.maintenance.configuration.ConfigConstant;
import com.android.maintenance.fragments.SpareChargepager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by anand on 21-Nov-16.
 */
public class ServiceEngSpareChargesTabsActivity  extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SessionManager session;
    String role,token;
    ReportChargesDTO reportChargesDTO;
    ReportSpareResponseDTO reportSpareResponseDTO;
    AssetReportDTO assetReportDTO;
    ArrayList<ReportLogDTO> reportLogList;
    TextView rpt_name,status;
    Button generate;
    Gson gson;
    ImageButton download;

    public static ServiceEngSpareChargesTabsActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ser_spare_charge_tab);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);
        token=user.get(SessionManager.KEY_TOKEN);

        rpt_name=(TextView)findViewById(R.id.rpt_name);
        status=(TextView)findViewById(R.id.status_text);
        download=(ImageButton)findViewById(R.id.downld);
        generate=(Button)findViewById(R.id.generate);

        reportLogList=new ArrayList<ReportLogDTO>();
        reportLogList= (ArrayList<ReportLogDTO>) getIntent().getSerializableExtra("reportLogList");
        assetReportDTO= (AssetReportDTO) getIntent().getSerializableExtra("assetReportDTO");
        reportChargesDTO=(ReportChargesDTO) getIntent().getSerializableExtra("reportChargesDTO");
        reportSpareResponseDTO=(ReportSpareResponseDTO) getIntent().getSerializableExtra("reportSpareResponseDTO");
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GeneratePDF().execute(ConfigConstant.url+"assetlog/report/"+assetReportDTO.getReportId());
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ser);
        toolbar.setTitle("Reports");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });


        rpt_name.setText(assetReportDTO.getReportNo());


        Bundle b=new Bundle();
        b.putSerializable("assetReportDTO",assetReportDTO);
        b.putSerializable("reportChargesDTO",reportChargesDTO);
        b.putSerializable("reportSpareResponseDTO",reportSpareResponseDTO);
        b.putSerializable("reportLogList",reportLogList);

        if(assetReportDTO.getStatus().equalsIgnoreCase("DONE")){
            download.setVisibility(View.VISIBLE);
        }
        tabLayout = (TabLayout) findViewById(R.id.charge_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.charge_pager);
        SpareChargepager pager = new SpareChargepager(getSupportFragmentManager(),b);

        viewPager.setAdapter(pager);

        tabLayout.setupWithViewPager(viewPager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pager);

    }

    public static ServiceEngSpareChargesTabsActivity getInstance() {
        return instance;
    }

    private class GeneratePDF extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... param) {
            String result="";
            ServiceHandlerWS servicepost= new ServiceHandlerWS();
            // Log.e(TAG,"this input post"+param[0]);
            result= servicepost.makeServicePostWithToken(param[0],null,token);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            gson=new Gson();
            BaseResponseDTO loginResponse=gson.fromJson(result, BaseResponseDTO.class);
            if(loginResponse.getStatusCode()==1){
                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                finish();
            }else if(loginResponse.getStatusCode()==-1){

                Toast.makeText(getApplicationContext(),loginResponse.getMsg(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
