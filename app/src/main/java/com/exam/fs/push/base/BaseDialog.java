package com.exam.fs.push.base;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseDialog<V extends ViewDataBinding> extends Dialog {
    private V binding;

    public BaseDialog(Context context) {
        this(context, 0);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        if (getLayoutId() > 0) {
            View view = LayoutInflater.from(context).inflate(getLayoutId(), null);
            setContentView(view);
            binding = DataBindingUtil.bind(view);
            initData();
        } else {
            throw new NullPointerException("getLayoutId 必须返回一个布局文件");
        }
    }

    public V getBinding() {
        return binding;
    }

    public abstract int getLayoutId();

    public abstract void initData();
}
