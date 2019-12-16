package com.exam.fs.push.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class BaseConstraintLayoutView<V extends ViewDataBinding> extends ConstraintLayout {

    private V binding;

    public BaseConstraintLayoutView(Context context) {
        this(context, null);
    }

    public BaseConstraintLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseConstraintLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (getLayoutId() > 0) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutId(), this, true);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            initData();
        } else {
            throw new NullPointerException("getLayoutId 必须返回一个布局");
        }
    }
    public V getBinding() {
        return binding;
    }
    protected abstract int getLayoutId();
    protected abstract void initData();
}