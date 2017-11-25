package com.squareup.lib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.squareup.lib.frament.BaseFrament;

import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabAdapter extends FragmentPagerAdapter {
    List<BaseFrament> fragments;

    public TabAdapter(FragmentManager fm, List<BaseFrament> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments == null) {
            return 0;
        }
        return fragments.size();
    }
}
