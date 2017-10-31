package com.waspy.sayekti.waspy.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.List;

/**
 * Created by sayekti on 10/18/17.
 */

public class ConentFragmentAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    List<ContentLogFragment> listFragment;

    public ConentFragmentAdapter(FragmentManager fm, List<ContentLogFragment> listFragment) {
        super(fm);
        this.listFragment = listFragment;
        this.mNumOfTabs = (listFragment != null && listFragment.size() > 0)? listFragment.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
