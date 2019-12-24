package com.exam.fs.push.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterWechatCommentBinding;
import com.exam.fs.push.model.bean.WechatMomentsBean;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class WechatCommentAdapter extends SimpleRecAdapter<WechatMomentsBean.CommentBean, SimpleRecBindingViewHolder<AdapterWechatCommentBinding>> {
    public WechatCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterWechatCommentBinding> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_wechat_comment;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterWechatCommentBinding> holder, int i) {
        WechatMomentsBean.CommentBean bean = data.get(i);
        if(!TextUtils.isEmpty(bean.toName)){
            holder.getBinding().tvNickname.setText(bean.name);
            holder.getBinding().tvNicknameTo.setText(bean.toName+"：");
            holder.getBinding().tvReply.setVisibility(View.VISIBLE);
            holder.getBinding().tvNicknameTo.setVisibility(View.VISIBLE);
        }else {
            holder.getBinding().tvNickname.setText(bean.name+"：");
            holder.getBinding().tvReply.setVisibility(View.GONE);
            holder.getBinding().tvNicknameTo.setVisibility(View.GONE);
        }
        holder.getBinding().tvContent.setText(bean.content);
    }
}
