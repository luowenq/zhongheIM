package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityUpdatePhoneBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 修改手机号
 */
@Route(path = RouterTables.PAGE_ACTIVITY_UPDATE_PHONE)
public class UpdatePhoneActivity extends BaseActivity<ActivityUpdatePhoneBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"更换手机号");
        getBinding().titleView.getRightButton().setEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_phone;
    }
}
