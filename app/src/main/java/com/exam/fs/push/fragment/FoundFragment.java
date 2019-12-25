package com.exam.fs.push.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentFoundBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.ToastUtils;

/**
 * 发现
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends BaseFragment<FragmentFoundBinding> {


    public FoundFragment() {
    }

    public static FoundFragment newInstance() {
        FoundFragment mf = new FoundFragment();
        Bundle bundle = new Bundle();
        mf.setArguments(bundle);
        return mf;
    }

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "发现");

        View.OnClickListener onClickListener = view ->{
            ToastUtils.showShort(R.string.app_no_function);
        };
        getBinding().btnShake.setOnClickListener(onClickListener);
        getBinding().btnShopping.setOnClickListener(onClickListener);
        getBinding().btnGame.setOnClickListener(onClickListener);

        getBinding().btnWechatMoments.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_WECHAT_MOMENTS).navigation();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    protected void stop() {

    }

    @Override
    protected void lazyLoad() {

    }
}
