package com.exam.fs.push.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.SplashAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySplashBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.utils.JumpPermissionManagement;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.TimeUnit;

import cn.droidlover.xdroidbase.kit.AppUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.shihao.library.XStatusBarHelper;

/**
 * 启动页
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SPLASH)
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    private int[] imges = {R.drawable.icon_spalsh_one, R.drawable.icon_spalsh_two,R.drawable.icon_spalsh_threed};

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(this);
        XStatusBarHelper.immersiveStatusBar(this);
        getImages(imges);
        getBinding().tvJump.setOnClickListener(v -> jump());
        getBinding().btnNext.setOnClickListener(v -> jump());
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.CAMERA).subscribe(aBoolean -> {
            if (aBoolean) {
                start();
            } else {
                new AlertDialog.Builder(context).setTitle("提示").setMessage("应用需要获取相关权限才能正常使用")
                        .setNegativeButton("退出程序", (dialogInterface, i) -> finish())
                        .setPositiveButton("立即设置", (dialogInterface, i) -> {
                            JumpPermissionManagement.GoToSetting(context);
                            finish();
                        }).show();
            }
        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void start() {
        Observable.timer(1000, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<Long, ObservableSource<Long>>) aLong -> {
                    getBinding().tvJump.setVisibility(View.VISIBLE);
                    return Observable.interval(1000, TimeUnit.MILLISECONDS).take(6);
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(this.bindToLifecycle())
                .subscribe(aLong -> {
                    if (getBinding().tvJump.getVisibility() != View.VISIBLE || aLong == 5)
                        jump();
                    else {
                        getBinding().tvJump.setText(String.format(AppUtils.getAppContext().getResources().getString(R.string.app_jump), (5 - aLong)));
                    }
                });
    }

    private void jump() {
        ARouter.getInstance().build(Config.isLogin() ? RouterTables.PAGE_ACTIVITY_MAIN : RouterTables.PAGE_ACTIVITY_LOGIN).navigation(context, new NavigationCallback() {
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

    private void getImages(final int[] imges) {
        SplashAdapter adapter = new SplashAdapter(this, imges);
        IndicatorViewPager viewPager = new IndicatorViewPager(getBinding().indicatorView, getBinding().viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPageOffscreenLimit(adapter.getCount());
        DrawableBar drawableBar = new DrawableBar(context, R.drawable.shape_guide_tab_select, ScrollBar.Gravity.CENTENT);
        viewPager.setIndicatorScrollBar(drawableBar);
        viewPager.setIndicatorOnTransitionListener((view, position, selectPercent) -> {
            if (position + 1 == imges.length) {
                getBinding().btnNext.setAlpha(selectPercent);
            }
        });
        getBinding().viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }
}