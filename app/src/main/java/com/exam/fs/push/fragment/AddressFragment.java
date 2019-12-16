package com.exam.fs.push.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentAddressBinding;

/**
 * 通讯录
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends BaseFragment<FragmentAddressBinding> {

    public AddressFragment() {
    }

    public static AddressFragment newInstance() {
        AddressFragment af = new AddressFragment();
        Bundle bundle = new Bundle();
        af.setArguments(bundle);
        return af;
    }

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "通讯录");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_address;
    }

    @Override
    protected void stop() {

    }

    @Override
    protected void lazyLoad() {

    }
}