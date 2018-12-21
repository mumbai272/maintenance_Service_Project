package com.android.maintenance.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by anand on 18-Nov-16.
 */
public class ClaimDetailspager extends FragmentStatePagerAdapter {

    private final Bundle fragmentBundle;

    public ClaimDetailspager(FragmentManager fm, Bundle bundle) {
        super(fm);
        fragmentBundle=bundle;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new ConvenceExpense();
                frag.setArguments(this.fragmentBundle);
                break;
            case 1:
                frag = new BusinessExpense();
                frag.setArguments(this.fragmentBundle);
                break;
            case 2:
                frag = new MiscExpense();
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
                title = " Conveyance Expense";
                break;
            case 1:
                title = "Business Expense";
                break;
            case 2:
                title = "Misc Expense";
                break;
        }

        return title;
    }
}
