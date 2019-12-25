package com.exam.fs.push.activity;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityRetrenPwdBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.viewmodel.RetrenPwdViewModel;

/**
 * 重置密码
 */
@Route(path = RouterTables.PAGE_ACTIVITY_RETREN_PWD)
public class RetrenPwdActivity extends BaseActivity<ActivityRetrenPwdBinding> {
    private String phone,code;
    private RetrenPwdViewModel viewModel;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "重置密码");

        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");

        viewModel = new RetrenPwdViewModel();
        getBinding().setViewModel(viewModel);
        viewModel.setDate(phone,code);

        getBinding().btnFinish.setOnClickListener(v -> {

        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_retren_pwd;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}