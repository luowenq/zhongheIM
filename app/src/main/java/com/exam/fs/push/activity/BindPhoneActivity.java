package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityBindPhoneBinding;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;

import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 绑定手机号
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_BIND_PHONE)
public class BindPhoneActivity extends BaseActivity<ActivityBindPhoneBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"绑定手机号");
        getBinding().btnUpdatePhone.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_UPDATE_PHONE).navigation();
        });
        getBinding().btnViewAddressBook.setOnClickListener(v -> {
            ToastManager.showShort(context,R.string.app_no_function);
        });

        UserModel userModel = Config.getUsers();
        if(userModel != null) {
            getBinding().tvPhone.setText(AppUtils.getAppContext().getResources().getString(R.string.app_your_phone_number) + userModel.username);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }
}
