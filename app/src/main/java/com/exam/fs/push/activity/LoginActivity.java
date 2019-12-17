package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.LoginFragmentAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityLoginBinding;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.router.RouterTables;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.shihao.library.XStatusBarHelper;

/**
 * 登录
 */
@Route(path = RouterTables.PAGE_ACTIVITY_LOGIN)
public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(this);
        XStatusBarHelper.immersiveStatusBar(this);
        LoginFragmentAdapter mAdapter = new LoginFragmentAdapter(getSupportFragmentManager(), 0);
        getBinding().viewPager.setAdapter(mAdapter);
        getBinding().viewPager.setOffscreenPageLimit(mAdapter.getCount());
        getBinding().tabLayout.setViewPager(getBinding().viewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean busBean) {
        if (busBean.getTag() == EventBusBean.TAG_LOGIN_SUCCESS) {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_MAIN).navigation(context, new NavigationCallback() {
                @Override
                public void onFound(Postcard postcard) {
                }

                @Override
                public void onLost(Postcard postcard) {
                }

                @Override
                public void onArrival(Postcard postcard) {
                    finish();
                }

                @Override
                public void onInterrupt(Postcard postcard) {
                }
            });
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }
}