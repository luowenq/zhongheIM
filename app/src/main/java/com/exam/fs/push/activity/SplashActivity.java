package com.exam.fs.push.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.SplashAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySplashBinding;
import com.exam.fs.push.router.RouterTables;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.DrawableBar;
import com.shizhefei.view.indicator.slidebar.ScrollBar;

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

    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(this);
        XStatusBarHelper.immersiveStatusBar(this);
        getImages(imges);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void jump() {
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