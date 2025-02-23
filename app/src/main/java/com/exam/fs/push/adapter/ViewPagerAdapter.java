package com.exam.fs.push.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmList;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragmList = fragments;
    }

    @Override
    public Fragment getItem(int index) {
        // TODO Auto-generated method stub
        return mFragmList.get(index);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mFragmList.size();
    }
}