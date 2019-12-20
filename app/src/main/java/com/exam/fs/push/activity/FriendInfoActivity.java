package com.exam.fs.push.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.activeandroid.query.Update;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityFriendInfoBinding;
import com.exam.fs.push.db.FriendEntry;
import com.exam.fs.push.model.bean.Event;
import com.exam.fs.push.model.bean.EventType;
import com.exam.fs.push.router.RouterTables;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 已经是好友的情况下
 */
@Route(path = RouterTables.PAGE_ACTIVITY_FRIEND_INFO)
public class FriendInfoActivity extends BaseActivity<ActivityFriendInfoBinding> {

    @Autowired(name = "targetId")
    public String mUserID;
    @Autowired(name = App.TARGET_ID)
    public String mTargetId;
    @Autowired(name = App.TARGET_APP_KEY)
    public String mTargetAppKey;
    @Autowired(name = "addFriend")
    public boolean mIsAddFriend = false;
    private UserInfo mUserInfo;
    private String mTitle;
    public boolean mIsFromContact;

    @Override
    public void initData(Bundle bundle) {
        ARouter.getInstance().inject(this);
        initTitle(getBinding().titleView, "");
        mIsFromContact = getIntent().getBooleanExtra("fromContact", false);
        if (mTargetAppKey == null) {
            mTargetAppKey = JMessageClient.getMyInfo().getAppKey();
        }
        if (mIsFromContact || mIsAddFriend) {
            updateUserInfo();
        } else {
            Conversation conv = JMessageClient.getSingleConversation(mTargetId, mTargetAppKey);
            mUserInfo = (UserInfo) conv.getTargetInfo();
            initInfo(mUserInfo);
            updateUserInfo();
        }
        getBinding().tvSendMsg.setOnClickListener(v -> {//发消息按钮
            startChatActivity();
        });
    }

    private void updateUserInfo() {
        if (TextUtils.isEmpty(mTargetId) && !TextUtils.isEmpty(mUserID)) {
            mTargetId = mUserID;
        }
        JMessageClient.getUserInfo(mTargetId, mTargetAppKey, new GetUserInfoCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, UserInfo info) {
                if (responseCode == 0) {
                    //拉取好友信息时候要更新数据库中的nickName.因为如果对方修改了nickName我们是无法感知的.如果不在拉取信息
                    //时候更新数据库的话会影响到搜索好友的nickName, 注意要在没有备注名并且有昵称时候去更新.因为备注名优先级更高
                    new Update(FriendEntry.class).set("DisplayName=?", info.getDisplayName()).where("Username=?", mTargetId).execute();
                    new Update(FriendEntry.class).set("NickName=?", info.getNickname()).where("Username=?", mTargetId).execute();
                    new Update(FriendEntry.class).set("NoteName=?", info.getNotename()).where("Username=?", mTargetId).execute();

                    if (info.getAvatarFile() != null) {
                        new Update(FriendEntry.class).set("Avatar=?", info.getAvatarFile().getAbsolutePath()).where("Username=?", mTargetId).execute();
                    }
                    mUserInfo = info;
//                    mFriendInfoController.setFriendInfo(info);
                    mTitle = info.getNotename();
                    if (TextUtils.isEmpty(mTitle)) {
                        mTitle = info.getNickname();
                    }
                    initInfo(info);
                } else {
//                    HandleResponseCode.onHandle(FriendInfoActivity.this, responseCode, false);
                }
            }
        });
    }

    private void initInfo(UserInfo userInfo) {
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.getAvatar())) {
                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int status, String desc, Bitmap bitmap) {
                        if (status == 0) {
                            getBinding().ivUserHeader.setImageBitmap(bitmap);
                        } else {
                            getBinding().ivUserHeader.setImageResource(R.drawable.icon_img_default_portrait);
                        }
                    }
                });
            } else {
                getBinding().ivUserHeader.setImageResource(R.drawable.icon_img_default_portrait);
            }
            String noteName = userInfo.getNotename();
            String nickName = userInfo.getNickname();
            String userName = userInfo.getUserName();
            //有备注 有昵称
            getBinding().tvUsername.setText(userName);
            if (!TextUtils.isEmpty(noteName) && !TextUtils.isEmpty(nickName)) {
                getBinding().tvNickname.setVisibility(View.VISIBLE);
                getBinding().tvNickname.setText(nickName);
                getBinding().tvAccount.setText("备注名: " + noteName);
            }
            //没有备注 有昵称
            else if (TextUtils.isEmpty(noteName) && !TextUtils.isEmpty(nickName)) {
                getBinding().tvNickname.setVisibility(View.GONE);
                getBinding().tvAccount.setText("昵称: " + nickName);
            }
            //有备注 没有昵称
            else if (!TextUtils.isEmpty(noteName) && TextUtils.isEmpty(nickName)) {
                getBinding().tvNickname.setVisibility(View.VISIBLE);
                getBinding().tvNickname.setText(userInfo.getNickname());
                getBinding().tvAccount.setText("备注名: " + noteName);
            }
            //没有备注名 没有昵称
            else {
                getBinding().tvNickname.setVisibility(View.GONE);
                getBinding().tvNickname.setText("用户名: " + userName);
            }

            getBinding().tvArea.setText(userInfo.getRegion());
//            mTv_signature.setText(userInfo.getSignature());
//            mTv_birthday.setText(getBirthday(userInfo));
        }
    }

    public void startChatActivity() {
        if (mIsAddFriend) {
            String title = mUserInfo.getNotename();
            if (TextUtils.isEmpty(title)) {
                title = mUserInfo.getNickname();
                if (TextUtils.isEmpty(title)) {
                    title = mUserInfo.getUserName();
                }
            }
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_CHAT)
                    .withString(App.CONV_TITLE, title).withString(App.TARGET_ID, mUserInfo.getUserName())
                    .withString(App.TARGET_APP_KEY, mUserInfo.getAppKey()).navigation();
        } else {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_CHAT).withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .withString(App.TARGET_ID, mTargetId)
                    .withString(App.TARGET_APP_KEY, mTargetAppKey).navigation();
        }
        Conversation conv = JMessageClient.getSingleConversation(mTargetId, mTargetAppKey);
        //如果会话为空，使用EventBus通知会话列表添加新会话
        if (conv == null) {
            conv = Conversation.createSingleConversation(mTargetId, mTargetAppKey);
            EventBus.getDefault().post(new Event.Builder().setType(EventType.createConversation).setConversation(conv).build());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_friend_info;
    }
}