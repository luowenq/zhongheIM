package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySignSetBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.viewmodel.EditUserInfoViewModel;

import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 个性签名
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SIGN_SET)
public class SignSetActivity extends BaseActivity<ActivitySignSetBinding> {
    private EditUserInfoViewModel viewModel;
    private String sign;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"个性签名");

        viewModel = new EditUserInfoViewModel();
        getBinding().setViewModel(viewModel);
        if(Config.getUsers() != null) {
            viewModel.sign.set(Config.getUsers().sign);
        }

        getBinding().etSign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sign = s.toString();
                if (s.length() > 0) {
                    getBinding().titleView.getRightButton().setEnabled(true);
                } else {
                    getBinding().titleView.getRightButton().setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getBinding().titleView.getRightButton().setOnClickListener(v -> {
            if(TextUtils.isEmpty(sign)){
                ToastManager.showShort(this,"请输入签名");
                return;
            }
            viewModel.updateUserInfo("",sign,"","","","","","",true);
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_set;
    }
}
