package com.exam.fs.push.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.View;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterWechatImageBinding;
import com.exam.fs.push.utils.GlideEngine;
import com.exam.fs.push.utils.LoadImage;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class WechatImageAdapter extends SimpleRecAdapter<String, SimpleRecBindingViewHolder<AdapterWechatImageBinding>> {
    private List<LocalMedia> list = new ArrayList<>();

    public WechatImageAdapter(Context context) {
        super(context);
    }

    @Override
    public void setData(List<String> data) {
        super.setData(data);
        for(int i = 0 ;i<data.size();i++){
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(data.get(i));
            list.add(localMedia);
        }
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
        LoadImage.loadImage(holder.getBinding().imgPic,data.get(i));
        holder.getBinding().imgPic.setOnClickListener(v -> {
            // 预览图片 可自定长按保存路径
            PictureSelector.create((Activity) context)
                    .themeStyle(R.style.picture_default_style) // xml设置主题
                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                    .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                    .openExternalPreview(i, list);
        });
    }
}
