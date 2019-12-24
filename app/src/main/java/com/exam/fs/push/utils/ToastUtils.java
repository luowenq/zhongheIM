package com.exam.fs.push.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidbase.kit.AppUtils;

public class ToastUtils implements View.OnAttachStateChangeListener{
    private Toast mToast;
    private static ToastUtils manager;
    private final List<String> messages;

    private ToastUtils() {
        if (this.mToast == null) {
            this.mToast = Toast.makeText(AppUtils.getAppContext(), "", Toast.LENGTH_SHORT);
        }
        messages = new ArrayList<>();
        mToast.getView().addOnAttachStateChangeListener(this);
    }

    public static ToastUtils getInstance() {
        if (manager == null) {
            synchronized (ToastUtils.class) {
                if (manager == null) {
                    manager = new ToastUtils();
                }
            }
        }

        return manager;
    }

    public void setText(String msg) {
        this.mToast.setText(msg);
        if(TextUtils.isEmpty(msg)) {
            return;
        }

        synchronized (messages) {
            boolean showToast = false;
            if (messages.size() == 0)
                showToast = true;
            if (!messages.contains(msg)) {
                messages.add(0, msg);
            }
            if (showToast) {
                this.mToast.setText(messages.get(messages.size() - 1));
                this.mToast.show();
            }
        }
    }

    public void setText(@StringRes int resId) {
        this.mToast.setText(resId);
        synchronized (messages) {
            boolean showToast = false;
            if (messages.size() == 0)
                showToast = true;
            if (!messages.contains(AppUtils.getAppContext().getResources().getString(resId))) {
                messages.add(0, AppUtils.getAppContext().getResources().getString(resId));
            }
            if (showToast) {
                this.mToast.setText(messages.get(messages.size() - 1));
                this.mToast.show();
            }
        }
    }

    private synchronized void removeMessage(){
        synchronized (messages){
            if (messages.size() > 0) {
                messages.remove(messages.size() - 1);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public ToastUtils durationShort() {
        this.mToast.setDuration(Toast.LENGTH_SHORT);
        return this;
    }

    @SuppressLint("WrongConstant")
    public ToastUtils durationLong() {
        this.mToast.setDuration(Toast.LENGTH_LONG);
        return this;
    }

    public static void showShort(String msg) {
        getInstance().durationShort().setText(msg);
    }

    public static void showLong(String msg) {
        getInstance().durationLong().setText(msg);
    }

    public static void showShort(@StringRes int resId) {
        getInstance().durationShort().setText(resId);
    }

    public static void showLong(@StringRes int resId) {
        getInstance().durationLong().setText(resId);
    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        removeMessage();
        if (messages.size() > 0){
            this.mToast.setText(messages.get(messages.size() - 1));
            this.mToast.show();
        }
    }
}
