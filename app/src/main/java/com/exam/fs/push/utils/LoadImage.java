package com.exam.fs.push.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.exam.fs.push.R;

import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.Kits;

public class LoadImage {
    public static void loadHeadImage(ImageView imageView, String url){
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.icon_head)//图片加载出来前，显示的图片
                .fallback( R.drawable.icon_head) //url为空的时候,显示的图片
                .error(R.drawable.icon_head);//图片加载失败后，显示的图片
        Glide.with(AppUtils.getAppContext()).load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadImage(ImageView imageView, String url){
        RequestOptions options = new RequestOptions()
                .placeholder(R.color.color_f4f4f4)//图片加载出来前，显示的图片
                .fallback(R.color.color_f4f4f4) //url为空的时候,显示的图片
                .error(R.color.color_f4f4f4);//图片加载失败后，显示的图片
        Glide.with(AppUtils.getAppContext()).load(url)
                .apply(options)
                .into(imageView);

    }
}
