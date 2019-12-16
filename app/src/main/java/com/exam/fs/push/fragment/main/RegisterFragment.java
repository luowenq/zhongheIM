package com.exam.fs.push.fragment.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentRegisterBinding;

/**
 * 注册
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding> {

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment pf = new RegisterFragment();
        Bundle bundle = new Bundle();
        pf.setArguments(bundle);
        return pf;
    }


    @Override
    public void initData(Bundle bundle) {
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
}
