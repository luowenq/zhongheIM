package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityAccountSafeBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 账号与安全
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_ACCOUNT_SAFE)
public class AccountSafeActivity extends BaseActivity<ActivityAccountSafeBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"账号与安全");
        getBinding().btnPhone.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_BIND_PHONE).navigation();
        });
        getBinding().btnUpdatePwd.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_UPDATE_PWD).navigation();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_safe;
    }
}
