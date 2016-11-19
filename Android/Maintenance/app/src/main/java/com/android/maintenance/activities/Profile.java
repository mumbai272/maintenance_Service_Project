package com.android.maintenance.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.android.maintenance.R;
import com.android.maintenance.fragments.ProfilePager;

/**
 * Created by anand on 05-Nov-16.
 */
public class Profile extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static Profile instance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile);

      //  tabLayout = (TabLayout) findViewById(R.id.profile_tabLayout);

       // viewPager = (ViewPager) findViewById(R.id.profile_pager);
        ProfilePager adapter = new ProfilePager(getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);

    }

    public static Profile getInstance() {
        return instance;
    }

}
