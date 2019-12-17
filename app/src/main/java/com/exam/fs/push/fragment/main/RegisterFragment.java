package com.exam.fs.push.fragment.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentRegisterBinding;
import com.exam.fs.push.viewmodel.RegisterViewModel;

/**
 * 注册
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {

    private RegisterViewModel viewModel;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment pf = new RegisterFragment();
        Bundle bundle = new Bundle();
        pf.setArguments(bundle);
        return pf;
    }


    @Override
    public void initData(Bundle bundle) {
        viewModel = new RegisterViewModel();
        getBinding().setViewModel(viewModel);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
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