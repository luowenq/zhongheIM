package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityChatSetBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 聊天设置
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_CHAT_SET)
public class ChatSetActivity extends BaseActivity<ActivityChatSetBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"聊天");
        getBinding().sivReceiverVoice.setOnClickListener(view -> {
            getBinding().sivReceiverVoice.getRightImageView().setSelected(
                    !getBinding().sivReceiverVoice.getRightImageView().isSelected()
            );
        });
        getBinding().sivEnterSend.setOnClickListener(view -> {
            getBinding().sivEnterSend.getRightImageView().setSelected(
                    !getBinding().sivEnterSend.getRightImageView().isSelected()
            );
        });
        getBinding().btnChatBg.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_CHAT_BG_SET).navigation();
        });
        getBinding().btnExpressionManager.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_EXPRESSION_MANAGER).navigation();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_set;
    }
}
