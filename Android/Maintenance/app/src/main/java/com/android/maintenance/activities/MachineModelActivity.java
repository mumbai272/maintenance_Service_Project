package com.android.maintenance.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.maintenance.R;
import com.android.maintenance.fragments.MakePager;

public class MachineModelActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static MachineModelActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_model_activity);

        tabLayout = (TabLayout) findViewById(R.id.model_tabLayout);

        viewPager = (ViewPager) findViewById(R.id.model_pager);
        MakePager adapter = new MakePager(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

    }


    public static MachineModelActivity getInstance() {
        return instance;
    }


}
