package com.android.maintenance.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by anand on 04-Nov-16.
 */
public class ProfilePager extends FragmentStatePagerAdapter {
    public ProfilePager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new ProfileUser();
                break;
            case 1:
                frag=new ProfileAddress();
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
                title="User";
                break;
            case 1:
                title="Address";
                break;
        }

        return title;
    }
}

