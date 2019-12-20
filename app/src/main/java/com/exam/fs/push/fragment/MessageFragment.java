package com.exam.fs.push.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentMessageBinding;
import com.exam.fs.push.dialog.AddDialog;
import com.exam.fs.push.router.RouterTables;

/**
 * 消息
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment<FragmentMessageBinding> {

    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        MessageFragment mf = new MessageFragment();
        Bundle bundle = new Bundle();
        mf.setArguments(bundle);
        return mf;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "消息");
        getBinding().titleView.setRightBtnRes(R.drawable.icon_add_friendy, v -> {
            AddDialog dialog = new AddDialog(context);
            dialog.setOnItemsClickListener(text -> {
                switch (text) {
                    case "发起群聊":
                        ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_CHAT_GROUP_MANAGER).navigation();
                        break;
                    case "添加好友":
                        ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_ADD_FRIENDY).navigation();
                        break;
                    case "扫一扫!":
                        break;
                }
            });
            dialog.show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void lazyLoad() {
    }
}