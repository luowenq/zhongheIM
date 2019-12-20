package com.exam.fs.push.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityVarlyFriendyBinding;
import com.exam.fs.push.db.FriendRecommendEntry;
import com.exam.fs.push.db.UserEntry;
import com.exam.fs.push.model.FriendInvitation;
import com.exam.fs.push.model.InfoModel;
import com.exam.fs.push.router.RouterTables;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 好友验证
 */
@Route(path = RouterTables.PAGE_ACTIVITY_VARLY_FRIENDY)
public class VarlyFriendyActivity extends BaseActivity<ActivityVarlyFriendyBinding> {
    private UserInfo mMyInfo;
    private String mTargetAppKey;

    @Override
    public void initData(Bundle bundle) {
        getBinding().edUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    getBinding().titleView.getRightButton().setEnabled(true);
                } else {
                    getBinding().titleView.getRightButton().setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        initView();
        getBinding().titleView.getRightButton().setOnClickListener(v -> {
            sendAddReason();
        });
    }

    @SuppressLint("WrongConstant")
    private void sendAddReason() {
        final String userName;
        String displayName;
        String targetAvatar;
        Long targetUid;
        if (getIntent().getFlags() == 1) {
            //添加好友申请时对方信息
            userName = getIntent().getStringExtra("detail_add_friend");
            displayName = getIntent().getStringExtra("detail_add_nick_name");
            targetAvatar = getIntent().getStringExtra("detail_add_avatar_path");
            targetUid = getIntent().getLongExtra("detail_add_uid", 0);
            if (TextUtils.isEmpty(displayName)) {
                displayName = userName;
            }
            //搜索方式添加好友
        } else {
            targetAvatar = InfoModel.getInstance().getAvatarPath();
            displayName = InfoModel.getInstance().getNickName();
            targetUid = InfoModel.getInstance().getUid();
            if (TextUtils.isEmpty(displayName)) {
                displayName = InfoModel.getInstance().getUserName();
            }
            userName = InfoModel.getInstance().getUserName();
        }
        final String reason = getBinding().edUsername.getText().toString();
        final String finalTargetAvatar = targetAvatar;
        final String finalDisplayName = displayName;
        final Long finalUid = targetUid;
        ContactManager.sendInvitationRequest(userName, null, reason, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    UserEntry userEntry = UserEntry.getUser(mMyInfo.getUserName(), mMyInfo.getAppKey());
                    FriendRecommendEntry entry = FriendRecommendEntry.getEntry(userEntry, userName, mTargetAppKey);
                    if (null == entry) {
                        entry = new FriendRecommendEntry(finalUid, userName, "", finalDisplayName, mTargetAppKey,
                                finalTargetAvatar, finalDisplayName, reason, FriendInvitation.INVITING.getValue(), userEntry, 100);
                    } else {
                        entry.state = FriendInvitation.INVITING.getValue();
                        entry.reason = reason;
                    }
                    entry.save();
                    ToastManager.showShort(context, "申请成功");
                    finish();
                } else if (responseCode == 871317) {
                    ToastManager.showShort(context, "不能添加自己为好友");
                } else {
                    ToastManager.showShort(context, "申请失败");
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void initView() {
        initTitle(getBinding().titleView, "好友验证", "发送",v -> sendAddReason());
        mMyInfo = JMessageClient.getMyInfo();
        mTargetAppKey = mMyInfo.getAppKey();
        String name;
        //群组详细信息点击非好友头像,跳转到此添加界面
        if (getIntent().getFlags() == 1) {
            name = getIntent().getStringExtra("detail_add_friend_my_nickname");
            if (TextUtils.isEmpty(name)) {
                getBinding().edUsername.setText("我是");
            } else {
                getBinding().edUsername.setText("我是" + name);
            }
            //搜索用户发送添加申请
        } else {
            name = mMyInfo.getNickname();
            if (TextUtils.isEmpty(name)) {
                getBinding().edUsername.setText("我是");
            } else {
                getBinding().edUsername.setText("我是" + name);
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_varly_friendy;
    }
}