package com.exam.fs.push.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentMinBinding;

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MinFragment extends BaseFragment<FragmentMinBinding> {


    public MinFragment() {
    }

    public static MinFragment newInstance() {
        MinFragment mf = new MinFragment();
        Bundle bundle = new Bundle();
        mf.setArguments(bundle);
        return mf;
    }

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "我的");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_min;
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void lazyLoad() {
    }
}