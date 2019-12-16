package com.exam.fs.push.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseDialog;
import com.exam.fs.push.databinding.ViewDialogLoadingBinding;

public class LoadingDialog extends BaseDialog<ViewDialogLoadingBinding> {

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_dialog_loading;
    }

    @Override
    public void initData() {
    }

    public void show(String text) {
        if (TextUtils.isEmpty(text)) {
            getBinding().tvTips.setVisibility(View.GONE);
        } else {
            getBinding().tvTips.setText(text);
            getBinding().tvTips.setVisibility(View.VISIBLE);
        }
        super.show();
    }

    public void show() {
        this.show("");
    }
}