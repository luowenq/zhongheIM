package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySignSetBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 个性签名
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SIGN_SET)
public class SignSetActivity extends BaseActivity<ActivitySignSetBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"个性签名");
        getBinding().titleView.getRightButton().setEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_set;
    }
}
