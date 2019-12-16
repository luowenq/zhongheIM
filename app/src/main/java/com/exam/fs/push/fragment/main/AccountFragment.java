package com.exam.fs.push.fragment.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentAccountBinding;

/**
 * 登录
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment<FragmentAccountBinding> {

    public AccountFragment() {
    }

    public static AccountFragment newInstance() {
        AccountFragment af = new AccountFragment();
        Bundle bundle = new Bundle();
        af.setArguments(bundle);
        return af;
    }


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void stop() {

    }

    @Override
    protected void lazyLoad() {

    }
}
