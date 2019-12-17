package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityForgetPwdBinding;
import com.exam.fs.push.router.RouterTables;

import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 忘记密码
 */
@Route(path = RouterTables.PAGE_ACTIVITY_FORGET_PWD)
public class ForgetPwdActivity extends BaseActivity<ActivityForgetPwdBinding> {

    public int codeMaxLength = 6;
    public int phoneMaxLength = 11;
    private String isPhone, isVarly;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "忘记密码");
        getBinding().edPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPhone = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getBinding().edVarly.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isVarly = s.toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getBinding().btnVarly.setOnClickListener(v -> {
            if (getBinding().edPhone.length() < phoneMaxLength) {
                ToastManager.showShort(context, R.string.app_phoneNumber);
            } else {

            }
        });
        getBinding().btnNext.setOnClickListener(v -> {
            if (TextUtils.isEmpty(isPhone) || TextUtils.isEmpty(isVarly)) {
                ToastManager.showShort(context, "请将手机号和验证码填写完整!");
            } else if (getBinding().edVarly.length() < codeMaxLength) {
                ToastManager.showShort(context, R.string.app_codeNumber);
            } else if (getBinding().edPhone.length() < phoneMaxLength) {
                ToastManager.showShort(context, R.string.app_phoneNumber);
            } else {
                ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_RETREN_PWD).navigation();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }
}