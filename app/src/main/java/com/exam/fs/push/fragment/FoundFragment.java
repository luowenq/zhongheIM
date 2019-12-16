package com.exam.fs.push.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentFoundBinding;

/**
 * 发现
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends BaseFragment<FragmentFoundBinding> {


    public FoundFragment() {
    }

    public static FoundFragment newInstance() {
        FoundFragment mf = new FoundFragment();
        Bundle bundle = new Bundle();
        mf.setArguments(bundle);
        return mf;
    }

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "发现");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    protected void stop() {

    }

    @Override
    protected void lazyLoad() {

    }
}
