package com.exam.fs.push.adapter;

import android.content.Context;
import android.view.View;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterWechatImageBinding;
import com.exam.fs.push.utils.LoadImage;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class WechatImageAdapter extends SimpleRecAdapter<String, SimpleRecBindingViewHolder<AdapterWechatImageBinding>> {
    public WechatImageAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterWechatImageBinding> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_wechat_image;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterWechatImageBinding> holder, int i) {
        LoadImage.loadHeadImage(holder.getBinding().imgPic,data.get(i));
    }
}
