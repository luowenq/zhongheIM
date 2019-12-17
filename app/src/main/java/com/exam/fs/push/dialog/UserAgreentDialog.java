package com.exam.fs.push.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseDialog;
import com.exam.fs.push.databinding.ViewUserAgreentDialogBinding;

import java.util.Objects;

import cn.droidlover.xdroidbase.kit.AppUtils;

public class UserAgreentDialog extends BaseDialog<ViewUserAgreentDialogBinding> {

    public UserAgreentDialog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_user_agreent_dialog;
    }

    @Override
    public void initData() {
        Objects.requireNonNull(getWindow()).setWindowAnimations(R.style.AnimFade);
        Objects.requireNonNull(getWindow()).setGravity(Gravity.CENTER);
        DisplayMetrics dm = AppUtils.getAppContext().getResources().getDisplayMetrics();
        getWindow().setLayout((int) (dm.widthPixels * 0.9), (int) (dm.heightPixels * 0.65));
        setCancelable(false);
        //设置外部点击取消
        setCanceledOnTouchOutside(true);
        getBinding().ivClose.setOnClickListener(v -> dismiss());
        getBinding().tvBottom.setOnClickListener(v -> dismiss());
    }

    public void show() {
        super.show();
    }
}