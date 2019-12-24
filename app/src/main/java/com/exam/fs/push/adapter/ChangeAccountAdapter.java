package com.exam.fs.push.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterChangeAccountBinding;
import com.exam.fs.push.model.bean.AccountBean;
import com.exam.fs.push.net.Api;
import com.exam.fs.push.utils.LoadImage;

import java.util.List;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class ChangeAccountAdapter extends SimpleRecAdapter<AccountBean, SimpleRecBindingViewHolder<AdapterChangeAccountBinding>> {
    private Drawable login,loginOut;

    public ChangeAccountAdapter(Context context) {
        super(context);
    }

    @Override
    public void setData(List<AccountBean> data) {
        data.add(new AccountBean("","",3));
        super.setData(data);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterChangeAccountBinding> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_change_account;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterChangeAccountBinding> holder, int i) {
        AccountBean bean = data.get(i);
        if(i<=data.size()-2) {
            holder.getBinding().tvNickname.setText(bean.name);
            LoadImage.loadHeadImage(holder.getBinding().imgHead,Api.imageUrl+bean.img);
            holder.getBinding().tvState.setVisibility(bean.state == 3?View.INVISIBLE:View.VISIBLE);
            holder.getBinding().tvState.setText(bean.state == 1?"当前使用":"正在退出");
            if(login == null) {
                login = context.getResources().getDrawable(R.drawable.shape_oval_64d237_10dp);
            }
            if(loginOut == null){
                loginOut = context.getResources().getDrawable(R.drawable.shape_oval_cccccc_10dp);
            }
            holder.getBinding().tvState.setCompoundDrawables(bean.state == 1?login:loginOut,null,null,null);
        }else {
            holder.getBinding().tvNickname.setText("添加账号");
            holder.getBinding().imgHead.setImageResource(R.drawable.icon_add_account);
            holder.getBinding().tvState.setVisibility(View.INVISIBLE);
        }
    }
}
