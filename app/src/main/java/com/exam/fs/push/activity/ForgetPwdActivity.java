package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityForgetPwdBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.viewmodel.ForgetPwdViewModel;

/**
 * 忘记密码
 */
@Route(path = RouterTables.PAGE_ACTIVITY_FORGET_PWD)
public class ForgetPwdActivity extends BaseActivity<ActivityForgetPwdBinding> {
    private ForgetPwdViewModel viewModel;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "忘记密码");

        viewModel = new ForgetPwdViewModel();
        getBinding().setViewModel(viewModel);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}