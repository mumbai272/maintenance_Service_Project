package com.android.maintenance.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by anand on 21-Nov-16.
 */
public class SpareChargepager extends FragmentStatePagerAdapter {
    private final Bundle fragmentBundle;

    public SpareChargepager(FragmentManager fm,Bundle bundle) {
        super(fm);
        fragmentBundle=bundle;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new Service_Eng_Tab();
                frag.setArguments(this.fragmentBundle);
                break;
            case 1:
                frag = new Spare_Tab();
                frag.setArguments(this.fragmentBundle);
                break;
            case 2:
                frag = new Charge_Tab();
                frag.setArguments(this.fragmentBundle);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = "Service Engineer";
                break;
            case 1:
                title = "Spare";
                break;

            case 2:
                title = "Charge";
                break;
        }

        return title;
    }
}
