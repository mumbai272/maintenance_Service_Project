package com.android.maintenance.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.maintenance.fragments.SpareAddTab;
import com.android.maintenance.fragments.SpareViewTab;

/**
 * Created by anand on 28-Oct-16.
 */
public class SparePager extends FragmentStatePagerAdapter {
    public SparePager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new SpareAddTab();
                break;
            case 1:
                frag=new SpareViewTab();
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
        String title=" ";
        switch (position){
            case 0:
                title="Add Machine Type";
                break;
            case 1:
                title="View Machine Type";
                break;
        }

        return title;
    }
}
