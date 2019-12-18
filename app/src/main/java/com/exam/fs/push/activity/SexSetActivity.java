package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySexSetBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 性别设置
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SEX_SET)
public class SexSetActivity extends BaseActivity<ActivitySexSetBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"性别");
        getBinding().titleView.getRightButton().setEnabled(true);
        getBinding().btnMan.setSelected(true);
        getBinding().btnMan.setOnClickListener(view -> {
            getBinding().btnMan.getRightImageView().setSelected(true);
            getBinding().btnWoman.getRightImageView().setSelected(false);
        });
        getBinding().btnWoman.setOnClickListener(view -> {
            getBinding().btnWoman.getRightImageView().setSelected(true);
            getBinding().btnMan.getRightImageView().setSelected(false);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sex_set;
    }
}
