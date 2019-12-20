package com.exam.fs.push.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.exam.fs.push.R;
import com.exam.fs.push.databinding.AdapterChatGroupManagerBinding;
import com.exam.fs.push.model.bean.User;
import com.exam.fs.push.utils.LoadImage;

import cn.droidlover.xdroid.base.SimpleRecBindingViewHolder;
import cn.droidlover.xdroidbase.base.SimpleRecAdapter;

public class ChatGroupManagerAdapter extends SimpleRecAdapter<User,SimpleRecBindingViewHolder<AdapterChatGroupManagerBinding>> {

    public ChatGroupManagerAdapter(Context context) {
        super(context);
    }

    @Override
    public SimpleRecBindingViewHolder<AdapterChatGroupManagerBinding> newViewHolder(View view) {
        return new SimpleRecBindingViewHolder(view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_chat_group_manager;
    }

    @Override
    public void onBindViewHolder(SimpleRecBindingViewHolder<AdapterChatGroupManagerBinding> holder, int position) {
        if(position <= data.size()-2) {
            User user = data.get(position);
            holder.getBinding().tvNickname.setText(user.username);
            LoadImage.loadHeadImage(holder.getBinding().imgPhoto, user.headIcon);
        }else {
            holder.getBinding().tvNickname.setText("邀请");
            holder.getBinding().imgPhoto.setImageResource(R.drawable.icon_invitation_friends);
        }
    }
}
