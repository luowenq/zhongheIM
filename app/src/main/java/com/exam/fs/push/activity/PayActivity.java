package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityPayBinding;
import com.exam.fs.push.router.RouterTables;

@Route(path = RouterTables.PAGE_ACTIVITY_PAY)
public class PayActivity extends BaseActivity<ActivityPayBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"支付");
        getBinding().titleView.setRightBtnRes(R.drawable.icon_top_right_more,v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_PAY_MANAGER).navigation();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }
}
