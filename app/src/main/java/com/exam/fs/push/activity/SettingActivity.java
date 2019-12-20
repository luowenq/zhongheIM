package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySettingBinding;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.router.RouterTables;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 设置
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SETTING)
public class SettingActivity extends BaseActivity<ActivitySettingBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "设置");
        getBinding().btnSafe.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_ACCOUNT_SAFE).navigation();
        });
        getBinding().btnMessageNotice.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_MESSAGE_NOTICE).navigation();
        });
        getBinding().btnStatic.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_QUIET_HOURS).navigation();
        });
        getBinding().btnChat.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_CHAT_SET).navigation();
        });
        getBinding().btnPrivacy.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_PRIVACY).navigation();
        });
        getBinding().btnCurrency.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_CURRENCY).navigation();
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean busBean) {
        if (busBean.getTag() == EventBusBean.TAG_FINISH_ALL_PAGE) {
            finish();
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }
}
