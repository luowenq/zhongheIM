package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityChatBgSetBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 聊天背景设置
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_CHAT_BG_SET)
public class ChatBgSetActivity extends BaseActivity<ActivityChatBgSetBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"聊天背景");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_bg_set;
    }
}
