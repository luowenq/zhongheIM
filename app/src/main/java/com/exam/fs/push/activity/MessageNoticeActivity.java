package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityMessageNoticeBinding;
import com.exam.fs.push.router.RouterTables;

@Route(path = RouterTables.PAGE_ACTIVITY_MESSAGE_NOTICE)
public class MessageNoticeActivity extends BaseActivity<ActivityMessageNoticeBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"新消息通知");
        getBinding().sivMessageNotice.setOnClickListener(view -> {
            getBinding().sivMessageNotice.getRightImageView().setSelected(
                    !getBinding().sivMessageNotice.getRightImageView().isSelected()
            );
        });
        getBinding().sivInvitation.setOnClickListener(view -> {
            getBinding().sivInvitation.getRightImageView().setSelected(
                    !getBinding().sivInvitation.getRightImageView().isSelected()
            );
        });
        getBinding().sivDetails.setOnClickListener(view -> {
            getBinding().sivDetails.getRightImageView().setSelected(
                    !getBinding().sivDetails.getRightImageView().isSelected()
            );
        });
        getBinding().sivVoice.setOnClickListener(view -> {
            getBinding().sivVoice.getRightImageView().setSelected(
                    !getBinding().sivVoice.getRightImageView().isSelected()
            );
        });
        getBinding().sivShock.setOnClickListener(view -> {
            getBinding().sivShock.getRightImageView().setSelected(
                    !getBinding().sivShock.getRightImageView().isSelected()
            );
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message_notice;
    }
}
