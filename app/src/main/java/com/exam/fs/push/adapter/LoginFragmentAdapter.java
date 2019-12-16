package com.exam.fs.push.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.exam.fs.push.fragment.main.AccountFragment;
import com.exam.fs.push.fragment.main.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

public class LoginFragmentAdapter extends FragmentPagerAdapter {
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    public LoginFragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments.add(AccountFragment.newInstance());
        fragments.add(RegisterFragment.newInstance());
        titles.add("登录");
        titles.add("注册");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }
}