package com.exam.fs.push.activity;

import android.graphics.BitmapFactory;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySersonBinding;
import com.exam.fs.push.model.InfoModel;
import com.exam.fs.push.router.RouterTables;

import java.io.File;

import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidbase.router.Router;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import me.shihao.library.XStatusBarHelper;

/**
 * 搜索
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SERSON)
public class SersonActivity extends BaseActivity<ActivitySersonBinding> {
    private String userName;

    @Override
    public void initData(Bundle bundle) {
        XStatusBarHelper.forceFitsSystemWindows(this);
        XStatusBarHelper.immersiveStatusBar(this);
        XStatusBarHelper.setHeightAndPadding(this, getBinding().layourRoot);
        getBinding().ivBack.setOnClickListener(v -> Router.pop(context));
        getBinding().layoutSerson.edSerson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = s.toString();
                if (s.length() > 0) {
                    getBinding().btnButton.setEnabled(true);
                } else {
                    getBinding().btnButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getBinding().btnButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(userName)) {
                ToastManager.showShort(context, R.string.app_friendy_userName_empty);
            } else {
                JMessageClient.getUserInfo(userName, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int code, String responseMessage, UserInfo info) {
                        if (code == 0) {
                            InfoModel.getInstance().friendInfo = info;
                            getBinding().searchResult.setVisibility(View.VISIBLE);
                            if (info.isFriend()) {
                                ToastManager.showShort(context, R.string.app_friendy_already);
                            }
                            File avatarFile = info.getAvatarFile();
                            if (avatarFile != null) {
                                getBinding().ivHeader.setImageBitmap(BitmapFactory.decodeFile(avatarFile.getAbsolutePath()));
                                InfoModel.getInstance().setBitmap(BitmapFactory.decodeFile(avatarFile.getAbsolutePath()));
                            } else {
                                getBinding().ivHeader.setImageResource(R.drawable.icon_img_default_portrait);
                                InfoModel.getInstance().setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_img_default_portrait));
                            }
                            getBinding().tvAccount.setText(TextUtils.isEmpty(info.getNickname()) ? info.getUserName() : info.getNickname());
                        } else {
                            ToastManager.showShort(context, R.string.app_user_no_way);
                            getBinding().searchResult.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        getBinding().searchResult.setOnClickListener(v -> {
            if (InfoModel.getInstance().isFriend()) {//如果已经是好友了
                ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_FRIEND_INFO).withBoolean("addFriend", true)
                        .withString("targetId", InfoModel.getInstance().friendInfo.getUserName()).navigation();
            } else {//添加好友
                ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SEARCH_FRIEND_INFO).navigation();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_serson;
    }
}