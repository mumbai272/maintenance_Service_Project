package com.android.maintenance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.maintenance.DTO.AssetLogDTO;
import com.android.maintenance.R;
import com.android.maintenance.Utilities.SessionManager;
import com.android.maintenance.configuration.ConfigConstant;
import com.android.maintenance.fragments.TaskReportpager;

import java.util.HashMap;

/**
 * Created by anand on 14-Nov-16.
 */
public class TaskAndReportTabActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Intent intent;
    AssetLogDTO log;
    String role;
    int pos;
    private SessionManager session;
    TextView title,date, m_id;
    public static TaskAndReportTabActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_report);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        role = user.get(SessionManager.KEY_ROLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_task);
        toolbar.setTitle("Tasks and Reports");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role.equals(ConfigConstant.employeeRole)){
                    intent = new Intent(TaskAndReportTabActivity.this, EmployeeWorkAssignListActivity.class);
                    startActivity(intent);
                    finish();
                }else if(role.equals(ConfigConstant.adminRole)) {
                    intent = new Intent(TaskAndReportTabActivity.this, LogListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        title=(TextView)findViewById(R.id.text_part);
        date=(TextView)findViewById(R.id.text_amount);
        m_id=(TextView)findViewById(R.id.log_m_id);
        log= new AssetLogDTO();
       // pos=getIntent().getIntExtra("pos",0);
        log= (AssetLogDTO) getIntent().getSerializableExtra("Log");

        Bundle bundle = new Bundle();
        bundle.putSerializable("Log",log);
        //bundle.putInt("pos",pos);
        title.setText(log.getMaintainanceType());
        m_id.setText(log.getAssetId().toString());
        date.setText(log.getLogCreatedDate());
        tabLayout = (TabLayout) findViewById(R.id.task_report_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.task_report__pager);
        TaskReportpager pager = new TaskReportpager(getSupportFragmentManager(),bundle);

        viewPager.setAdapter(pager);

        tabLayout.setupWithViewPager(viewPager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pager);

    }

    public static TaskAndReportTabActivity getInstance() {
        return instance;
    }

}
