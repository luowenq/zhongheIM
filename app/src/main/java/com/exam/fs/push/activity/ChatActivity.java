package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityChatBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 聊天界面
 */
@Route(path = RouterTables.PAGE_ACTIVITY_CHAT)
public class ChatActivity extends BaseActivity<ActivityChatBinding> {
    @Autowired(name = App.CONV_TITLE)
    public String title;
    @Autowired(name = App.TARGET_ID)
    public String targetId;
    @Autowired(name = App.TARGET_APP_KEY)
    public String targetAppKey;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "");
        getBinding().titleView.getRightImageView().setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_CHAT_GROUP_MANAGER).navigation();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }
}