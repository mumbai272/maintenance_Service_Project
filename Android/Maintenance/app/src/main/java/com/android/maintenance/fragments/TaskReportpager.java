package com.android.maintenance.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by anand on 14-Nov-16.
 */
public class TaskReportpager extends FragmentStatePagerAdapter {
    private final Bundle fragmentBundle;
    public TaskReportpager(FragmentManager fm,Bundle bundle) {
        super(fm);
        fragmentBundle=bundle;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new TaskTab();
                frag.setArguments(this.fragmentBundle);
                break;
            case 1:
                frag = new ReportTab();
                frag.setArguments(this.fragmentBundle);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = "Tasks";
                break;
            case 1:
                title = "Reports";
                break;
        }

        return title;
    }
}
