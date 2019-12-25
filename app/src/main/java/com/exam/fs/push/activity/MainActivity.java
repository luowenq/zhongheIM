package com.exam.fs.push.activity;

import android.Manifest;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.MainIndicatorViewPagerAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityMainBinding;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.shihao.library.XStatusBarHelper;

/**
 * 主界面
 */
@Route(path = RouterTables.PAGE_ACTIVITY_MAIN)
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(this);
        XStatusBarHelper.immersiveStatusBar(this);
        getBinding().viewPager.setCanScroll(false);
        IndicatorViewPager viewPager = new IndicatorViewPager(getBinding().indicatorView, getBinding().viewPager);
        MainIndicatorViewPagerAdapter adapter = new MainIndicatorViewPagerAdapter(context, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageOffscreenLimit(adapter.getCount());

        //做清除缓存功能就需要清空这个
//        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
//        if (PermissionChecker.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            //PictureFileUtils.deleteCacheDirFile(this, PictureMimeType.ofImage());
//            PictureFileUtils.deleteAllCacheDirFile(context);
//        } else {
//            PermissionChecker.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE);
//        }
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
        return R.layout.activity_main;
    }
}