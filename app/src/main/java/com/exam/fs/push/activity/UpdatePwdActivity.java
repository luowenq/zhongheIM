package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityUpdatePwdBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 修改密码
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_UPDATE_PWD)
public class UpdatePwdActivity extends BaseActivity<ActivityUpdatePwdBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"修改密码");
        getBinding().titleView.getRightButton().setEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_pwd;
    }
}
