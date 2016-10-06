package com.android.maintenance.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by anand on 27-Sep-16.
 */
public class MakePager extends FragmentStatePagerAdapter {
    public MakePager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new MakeAddTab();
                break;
            case 1:
                frag=new MakeViewTab();
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
                title="Add Machine Make";
                break;
            case 1:
                title="View Machine Make";
                break;
        }

        return title;
    }
}
