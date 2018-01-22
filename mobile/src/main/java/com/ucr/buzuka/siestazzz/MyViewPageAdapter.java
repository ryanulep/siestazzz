package com.ucr.buzuka.siestazzz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by rwbos on 1/22/2018.
 */

public class MyViewPageAdapter extends FragmentStatePagerAdapter {

    public MyViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) { // The page that we awant to display in a ViewPager
        return new MyViewPagerFragment();
    }

    @Override
    public int getCount() { // getCount is the number of pages that we want to display in the ViewPager.
        return 4;
    }
}
