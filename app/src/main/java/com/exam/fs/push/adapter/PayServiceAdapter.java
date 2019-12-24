package com.exam.fs.push.adapter;

import android.content.Context;
import android.view.View;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterPayServiceBinding;
import com.exam.fs.push.model.bean.PayServiceBean;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class PayServiceAdapter extends SimpleRecAdapter<PayServiceBean, SimpleRecBindingViewHolder<AdapterPayServiceBinding>> {
    public PayServiceAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterPayServiceBinding> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_pay_service;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterPayServiceBinding> holder, int i) {
        PayServiceBean bean = data.get(i);
        holder.getBinding().tvName.setText(bean.name);
        holder.getBinding().imgIcon.setImageResource(bean.img);
    }
}
