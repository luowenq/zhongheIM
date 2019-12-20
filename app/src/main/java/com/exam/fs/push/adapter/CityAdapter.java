package com.exam.fs.push.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterCityBinding;
import com.exam.fs.push.model.bean.CityBean;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.ItemCallback;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class CityAdapter extends SimpleRecAdapter<CityBean.AreaBean,SimpleRecBindingViewHolder<AdapterCityBinding>> {

    public CityAdapter(Context context, ItemCallback<CityBean.AreaBean> callback) {
        super(context, callback);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterCityBinding> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_city;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterCityBinding> holder, int position) {
        CityBean.AreaBean cityBean =data.get(position);
        holder.getBinding().btnCity.setLeftText(cityBean.getName());
        holder.getBinding().btnCity.setRightIconIsShow(false);
        holder.getBinding().btnCity.setOnClickListener(v -> {
            getItemClick().onItemClick(position,cityBean);
        });
    }
}
