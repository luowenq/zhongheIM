package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityQuietHoursBinding;
import com.exam.fs.push.router.RouterTables;

@Route(path = RouterTables.PAGE_ACTIVITY_QUIET_HOURS)
public class QuietHoursActivity extends BaseActivity<ActivityQuietHoursBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"勿扰模式");
        getBinding().sivQuietHours.setOnClickListener(view -> {
            getBinding().sivQuietHours.getRightImageView().setSelected(
                    !getBinding().sivQuietHours.getRightImageView().isSelected()
            );
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_quiet_hours;
    }
}
