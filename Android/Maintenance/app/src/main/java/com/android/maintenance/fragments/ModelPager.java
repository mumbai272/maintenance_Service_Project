package com.android.maintenance.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by anand on 28-Sep-16.
 */
public class ModelPager extends FragmentStatePagerAdapter {
    public ModelPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new ModelAddTab();
                break;
            case 1:
                frag=new ModelViewTab();
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
                title="Add Machine Model";
                break;
            case 1:
                title="View Machine Model";
                break;
        }

        return title;
    }
}
