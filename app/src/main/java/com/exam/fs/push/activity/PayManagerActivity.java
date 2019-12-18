package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityPayManagerBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 支付管理
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_PAY_MANAGER)
public class PayManagerActivity extends BaseActivity<ActivityPayManagerBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"支付管理");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_manager;
    }
}
