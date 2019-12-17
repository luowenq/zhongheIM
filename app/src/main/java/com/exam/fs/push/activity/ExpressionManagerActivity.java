package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityExpressionManagerBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 聊天管理
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_EXPRESSION_MANAGER)
public class ExpressionManagerActivity extends BaseActivity<ActivityExpressionManagerBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"聊天背景");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_expression_manager;
    }
}
