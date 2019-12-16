package com.exam.fs.push.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.exam.fs.push.R;
import com.shizhefei.view.indicator.IndicatorViewPager;

import cn.droidlover.xdroidbase.kit.Kits;

public class SplashAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

    private Context mContext;
    private int[] imges;

    public SplashAdapter(Context mContext, int[] imges) {
        this.mContext = mContext;
        this.imges = imges;
    }

    @Override
    public int getCount() {
        return imges.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null)
            convertView = new View(mContext);
        convertView.setBackgroundResource(R.drawable.shape_guide_tab);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Kits.Dimens.dpToPxInt(mContext, 15), Kits.Dimens.dpToPxInt(mContext, 4));
        if (position == 1)
            params.leftMargin = params.rightMargin = Kits.Dimens.dpToPxInt(mContext, 5);
        convertView.setLayoutParams(params);
        return convertView;
    }

    @Override
    public View getViewForPage(int position, View convertView, ViewGroup container) {
        if (convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_item_guide, new ConstraintLayout(mContext));
        ImageView imageView = convertView.findViewById(R.id.image);
        if (imges.length != 0) {
            Glide.with(mContext).load(imges[position]).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(new DrawableTransitionOptions().crossFade(500)).into(imageView);
        }
        return convertView;
    }
}
