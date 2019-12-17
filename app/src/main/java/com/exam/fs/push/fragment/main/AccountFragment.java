package com.exam.fs.push.fragment.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentAccountBinding;
import com.exam.fs.push.viewmodel.AccountViewModel;

/**
 * 登录
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends BaseFragment<FragmentAccountBinding> {

    private AccountViewModel viewModel;

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
        viewModel = new AccountViewModel();
        getBinding().setViewModel(viewModel);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}