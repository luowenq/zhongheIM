package com.exam.fs.push.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentMessageBinding;
import com.exam.fs.push.dialog.AddDialog;

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

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "消息");
        getBinding().titleView.setRightBtnRes(R.drawable.icon_add_friendy, v -> {
            AddDialog dialog = new AddDialog(context);
            dialog.setOnItemsClickListener(text -> {

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