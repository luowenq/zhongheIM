package com.exam.fs.push.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterCityBinding;
import com.exam.fs.push.model.bean.CityBean;
import com.exam.fs.push.router.RouterTables;

import java.util.List;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class ProvinceAdapter extends SimpleRecAdapter<CityBean,SimpleRecBindingViewHolder<AdapterCityBinding>> {

    public ProvinceAdapter(Context context, List<CityBean> data) {
        super(context, data);
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
        CityBean cityBean =data.get(position);
        holder.getBinding().btnCity.setLeftText(cityBean.getName());
        holder.getBinding().btnCity.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SET_ADDR)
                    .withString("city",cityBean.getName())
                    .navigation();
        });
    }
}
