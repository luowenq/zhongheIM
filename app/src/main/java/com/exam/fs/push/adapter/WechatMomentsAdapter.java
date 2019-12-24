package com.exam.fs.push.adapter;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterWechatMomentsBinding;
import com.exam.fs.push.model.bean.WechatMomentsBean;
import com.exam.fs.push.utils.LoadImage;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class WechatMomentsAdapter extends SimpleRecAdapter<WechatMomentsBean, SimpleRecBindingViewHolder<AdapterWechatMomentsBinding>> {
    public WechatMomentsAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterWechatMomentsBinding> newViewHolder(View view) {
        RecyclerView rvImg = view.findViewById(R.id.rv_img);
        RecyclerView rvComment = view.findViewById(R.id.rv_comment);
        rvImg.setLayoutManager(new GridLayoutManager(context,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvComment.setLayoutManager(new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_wechat_moments;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterWechatMomentsBinding> holder, int i) {
        WechatMomentsBean bean = data.get(i);
        LoadImage.loadHeadImage(holder.getBinding().imgHead,bean.header);
        holder.getBinding().tvNickname.setText(bean.nickname);
        holder.getBinding().tvContent.setText(bean.content);
        holder.getBinding().tvTime.setText(bean.time);
        holder.getBinding().imgLike.setImageResource(bean.isLike?R.drawable.icon_like:R.drawable.icon_unlike);

        if(bean.imgs.size()>0) {
            WechatImageAdapter adapter = new WechatImageAdapter(context);
            holder.getBinding().rvImg.setAdapter(adapter);
            adapter.setData(bean.imgs);
        }
        if (bean.commentBean.size()>0) {
            WechatCommentAdapter commentAdapter = new WechatCommentAdapter(context);
            holder.getBinding().rvComment.setAdapter(commentAdapter);
            commentAdapter.setData(bean.commentBean);
        }

    }
}
