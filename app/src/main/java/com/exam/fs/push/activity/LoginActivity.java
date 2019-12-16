package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityLoginBinding;
import com.exam.fs.push.router.RouterTables;

import me.shihao.library.XStatusBarHelper;

/**
 * 登录
 */
@Route(path = RouterTables.PAGE_ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(this);
        XStatusBarHelper.immersiveStatusBar(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}