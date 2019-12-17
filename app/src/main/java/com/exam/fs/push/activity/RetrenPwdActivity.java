package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityRetrenPwdBinding;
import com.exam.fs.push.router.RouterTables;

import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 重置密码
 */
@Route(path = RouterTables.PAGE_ACTIVITY_RETREN_PWD)
public class RetrenPwdActivity extends BaseActivity<ActivityRetrenPwdBinding> {

    public int onepwdMaxLength = 8;
    public int twopwdMaxLength = 8;
    private String onePwd, twoPwd;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "重置密码");
        getBinding().edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onePwd = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getBinding().edAnglePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                twoPwd = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getBinding().btnFinish.setOnClickListener(v -> {
            if (getBinding().edPassword.length() < onepwdMaxLength || getBinding().edAnglePassword.length() < twopwdMaxLength) {
                ToastManager.showShort(context, R.string.app_pwdNumber);
            } else if (TextUtils.isEmpty(onePwd) || TextUtils.isEmpty(twoPwd)) {
                ToastManager.showShort(context, R.string.app_input_new_pwd);
            } else if (!onePwd.equals(twoPwd)) {
                ToastManager.showShort(context, R.string.app_pwd_noway);
            } else {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_retren_pwd;
    }
}