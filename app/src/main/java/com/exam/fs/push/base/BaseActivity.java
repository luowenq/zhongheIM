package com.exam.fs.push.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.ViewDataBinding;

import com.exam.fs.push.dialog.LoadingDialog;
import com.exam.fs.push.dialog.LoadingDialogManager;
import com.exam.fs.push.widget.TitleView;

import cn.droidlover.xdroid.base.XActivity;
import cn.droidlover.xdroidbase.router.Router;
import me.shihao.library.XStatusBarHelper;

public abstract class BaseActivity<V extends ViewDataBinding> extends XActivity<V> {

    private boolean isFirst = true;
    public static final String EXTRA_TITLE = "extra_title";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LoadingDialogManager.getInstance().setDialog(new LoadingDialog(this));//设置加载dialog
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            LoadingDialogManager.getInstance().setDialog(new LoadingDialog(this));
        }
        isFirst = true;
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
            XStatusBarHelper.forceFitsSystemWindows(this);
            XStatusBarHelper.immersiveStatusBar(this);
            XStatusBarHelper.setHeightAndPadding(this, titleView.getRootLayout());
            titleView.setTitle(title == null ? "" : title);
            if (titleRes > 0)
                titleView.setTitle(title);
            titleView.setRightText(rightText == null ? "" : rightText, rightClickListener);
            if (rightTextRes > 0)
                titleView.setRightText(rightTextRes, rightClickListener);
            if (backClickListener == null)
                titleView.setBackClickListener(v -> Router.pop(context));
            else
                titleView.setBackClickListener(backClickListener);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @return true和false
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            EditText editText = (EditText) v;
            editText.requestFocus();
            editText.setCursorVisible(true);
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}