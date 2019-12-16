package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySettingBinding;
import com.exam.fs.push.router.RouterTables;

import me.shihao.library.XStatusBarHelper;

@Route(path = RouterTables.PAGE_ACTIVITY_SETTING)
public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(context);
        XStatusBarHelper.immersiveStatusBar(context);
        XStatusBarHelper.setHeightAndPadding(context, getBinding().titleView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }
}
