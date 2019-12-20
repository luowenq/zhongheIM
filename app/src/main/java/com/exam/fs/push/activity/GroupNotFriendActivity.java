package com.exam.fs.push.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityGroupNotFriendBinding;
import com.exam.fs.push.model.bean.Event;
import com.exam.fs.push.model.bean.EventType;
import com.exam.fs.push.router.RouterTables;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

@Route(path = RouterTables.PAGE_ACTIVITY_GROUP_NOT_FRIEND)
public class GroupNotFriendActivity extends BaseActivity<ActivityGroupNotFriendBinding> {

    private String mUserName;
    private UserInfo mUserInfo;
    private String mMyName;
    private String mNickName;
    private String mAvatarPath;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "详细资料");
        initData();
    }

    private void initData() {
        mUserName = getIntent().getStringExtra(App.TARGET_ID);
        String reason = getIntent().getStringExtra("reason");
        if (reason == null) {
            return;
        } else {
            getBinding().tvCome.setText("附加消息: " + reason);
        }

        JMessageClient.getUserInfo(mUserName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, UserInfo info) {
                if (responseCode == 0) {
                    mUserInfo = info;
                    File avatar = info.getAvatarFile();
                    if (avatar != null) {
                        mAvatarPath = avatar.getAbsolutePath();
                        getBinding().ivUserHeader.setImageBitmap(BitmapFactory.decodeFile(mAvatarPath));
                    } else {
                        getBinding().ivUserHeader.setImageResource(R.drawable.icon_img_default_portrait);
                    }
                    String noteName = info.getNotename();
                    mNickName = info.getNickname();
                    //有备注 有昵称
                    getBinding().tvUsername.setText(mUserName);
                    if (!TextUtils.isEmpty(noteName) && !TextUtils.isEmpty(mNickName)) {
                        getBinding().tvNickname.setVisibility(View.VISIBLE);
                        getBinding().tvNickname.setText(mNickName);
                        getBinding().tvAccount.setText("备注名: " + noteName);
                    }
                    //没有备注 有昵称
                    else if (TextUtils.isEmpty(noteName) && !TextUtils.isEmpty(mNickName)) {
                        getBinding().tvNickname.setVisibility(View.GONE);
                        getBinding().tvAccount.setText("昵称: " + mNickName);
                    }
                    //有备注 没有昵称
                    else if (!TextUtils.isEmpty(noteName) && TextUtils.isEmpty(mNickName)) {
                        getBinding().tvNickname.setVisibility(View.VISIBLE);
                        getBinding().tvNickname.setText(info.getNickname());
                        getBinding().tvAccount.setText("备注名: " + noteName);
                    }
                    //没有备注名 没有昵称
                    else {
                        getBinding().tvNickname.setVisibility(View.GONE);
                        getBinding().tvNickname.setText("用户名: " + mUserName);
                    }
                    getBinding().tvSign.setText(info.getSignature());
                    getBinding().tvArea.setText(info.getRegion());
                    /*if (info.getGender() == UserInfo.Gender.male) {
                        mTv_gender.setText("男");
                    } else if (info.getGender() == UserInfo.Gender.female) {
                        mTv_gender.setText("女");
                    } else {
                        mTv_gender.setText("未知");
                    }
                    mTv_birthday.setText(getBirthday(info));
                    mTv_address.setText(info.getRegion());*/
                }
            }
        });
//        getBinding().btnAddFriend.setOnClickListener(this);
//        getBinding().btnSendMessage.setOnClickListener(this);
        UserInfo myInfo = JMessageClient.getMyInfo();
        mMyName = myInfo.getNickname();
        if (TextUtils.isEmpty(mMyName)) {
            mMyName = myInfo.getUserName();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_not_friend;
    }

   /* @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_add_friend:
                if (mUserInfo.isFriend()) {
                    ToastManager.showShort(context, "对方已经是你的好友");
                } else {
                    intent.setClass(GroupNotFriendActivity.this, VarlyFriendyActivity.class);
                    //对方信息
                    intent.putExtra("detail_add_friend", mUserName);
                    intent.putExtra("detail_add_nick_name", mNickName);
                    intent.putExtra("detail_add_avatar_path", mAvatarPath);
                    //自己的昵称或者是用户名
                    intent.putExtra("detail_add_friend_my_nickname", mMyName);
                    intent.setFlags(1);
                    startActivity(intent);
                }
                break;
            case R.id.btn_send_message:
                intent.setClass(GroupNotFriendActivity.this, ChatActivity.class);
                //创建会话
                intent.putExtra(App.TARGET_ID, mUserInfo.getUserName());
                intent.putExtra(App.TARGET_APP_KEY, mUserInfo.getAppKey());
                String notename = mUserInfo.getNotename();
                if (TextUtils.isEmpty(notename)) {
                    notename = mUserInfo.getNickname();
                    if (TextUtils.isEmpty(notename)) {
                        notename = mUserInfo.getUserName();
                    }
                }
                intent.putExtra(App.CONV_TITLE, notename);
                Conversation conv = JMessageClient.getSingleConversation(mUserInfo.getUserName(), mUserInfo.getAppKey());
                //如果会话为空，使用EventBus通知会话列表添加新会话
                if (conv == null) {
                    conv = Conversation.createSingleConversation(mUserInfo.getUserName(), mUserInfo.getAppKey());
                    EventBus.getDefault().post(new Event.Builder()
                            .setType(EventType.createConversation)
                            .setConversation(conv)
                            .build());
                }
                break;
        }
    }*/
}