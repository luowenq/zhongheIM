package com.exam.fs.push.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.ViewDataBinding;

import com.exam.fs.push.widget.TitleView;

import cn.droidlover.xdroid.base.XFragment;
import cn.droidlover.xdroidbase.router.Router;
import me.shihao.library.XStatusBarHelper;

public abstract class BaseFragment<V extends ViewDataBinding> extends XFragment<V> {

    //是否可见
    private boolean isVisible = false;
    //是否初始化完成
    private boolean isInit = false;
    //是否已经加载过
    private boolean isLoadOver = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isInit = true;
        setParam();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void initTitle(TitleView titleView, String title) {
        initTitle(titleView, title, "", null);
    }

    protected void initTitle(TitleView titleView, @StringRes int titleRes) {
        initTitle(titleView, "", titleRes, "", 0, null, null);
    }

    protected void initTitle(TitleView titleView, String title, String rightText, View.OnClickListener rightClickListener) {
        initTitle(titleView, title, 0, rightText, 0, rightClickListener, null);
    }

    protected void initTitle(TitleView titleView, String title, View.OnClickListener backClickListener) {
        initTitle(titleView, title, 0, null, 0, null, backClickListener);
    }

    @SuppressLint("ResourceType")
    protected void initTitle(TitleView titleView, String title, @StringRes int titleRes, String rightText
            , @StringRes int rightTextRes, View.OnClickListener rightClickListener, View.OnClickListener backClickListener) {
        if (titleView != null) {
            XStatusBarHelper.forceFitsSystemWindows(context);
            XStatusBarHelper.immersiveStatusBar(context);
            XStatusBarHelper.setHeightAndPadding(context, titleView.getRootLayout());
            titleView.setTitle(title == null ? "" : title);
            if (titleRes > 0)
                titleView.setTitle(titleRes);
            titleView.setRightText(rightText == null ? "" : rightText, rightClickListener);
            if (rightTextRes > 0)
                titleView.setRightText(rightTextRes, rightClickListener);
            if (backClickListener == null)
                titleView.setBackClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Router.pop(context);
                    }
                });
            else
                titleView.setBackClickListener(backClickListener);
        }
    }
    /**
     * 如果fragment对用户可见的情况下
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisible = isVisibleToUser;
        setParam();
    }

    private void setParam() {
        if (isInit && !isLoadOver && isVisible) {
            isLoadOver = true;
            lazyLoad();
        } else {
            stop();
        }
    }
    protected abstract void stop();
    protected abstract void lazyLoad();
}