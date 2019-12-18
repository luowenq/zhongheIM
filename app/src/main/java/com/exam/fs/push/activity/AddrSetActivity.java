package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityAddrSetBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 设置地区
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SET_ADDR)
public class AddrSetActivity extends BaseActivity<ActivityAddrSetBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"设置地区");

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_addr_set;
    }
}
