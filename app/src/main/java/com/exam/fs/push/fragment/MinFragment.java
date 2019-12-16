package com.exam.fs.push.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentMinBinding;
import com.exam.fs.push.router.RouterTables;

import cn.droidlover.xdroidbase.kit.Kits;
import me.shihao.library.XStatusBarHelper;

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MinFragment extends BaseFragment<FragmentMinBinding> {


    public MinFragment() {
    }

    public static MinFragment newInstance() {
        MinFragment mf = new MinFragment();
        Bundle bundle = new Bundle();
        mf.setArguments(bundle);
        return mf;
    }

    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(context);
        XStatusBarHelper.immersiveStatusBar(context);
        XStatusBarHelper.setHeightAndPadding(context, getBinding().clBg);
        Glide.with(this).load("https://manhua.qpic.cn/vertical/0/07_22_36_afe651da2ab940d0e257a1ec894bd992_1504795010150.jpg/420")
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(Kits.Dimens.dpToPxInt(context, 4))))
                .into(getBinding().ivPhoto);
        getBinding().btnSetting.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SETTING).navigation();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_min;
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void lazyLoad() {
    }
}