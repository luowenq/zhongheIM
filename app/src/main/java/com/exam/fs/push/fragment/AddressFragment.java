package com.exam.fs.push.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentAddressBinding;
import com.exam.fs.push.dialog.AddDialog;

/**
 * 通讯录
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends BaseFragment<FragmentAddressBinding> {

    public AddressFragment() {
    }

    public static AddressFragment newInstance() {
        AddressFragment af = new AddressFragment();
        Bundle bundle = new Bundle();
        af.setArguments(bundle);
        return af;
    }

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "通讯录");
        getBinding().titleView.setRightBtnRes(R.drawable.icon_add_friendy_two, v -> {
            AddDialog dialog = new AddDialog(context);
            dialog.setOnItemsClickListener(text -> {

            });
            dialog.show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_address;
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void lazyLoad() {
    }
}