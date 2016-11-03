package com.android.maintenance.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.maintenance.R;
import com.android.maintenance.fragments.SparePager;

/**
 * Created by anand on 28-Oct-16.
 */
public class MachineSpareActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static MachineSpareActivity instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_spare_activity);

        tabLayout = (TabLayout) findViewById(R.id.spare_tabLayout);

        viewPager = (ViewPager) findViewById(R.id.spare_pager);
        SparePager adapter = new SparePager(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

    }


    public static MachineSpareActivity getInstance() {
        return instance;
    }

}
