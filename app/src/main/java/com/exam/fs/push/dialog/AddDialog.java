package com.exam.fs.push.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseDialog;
import com.exam.fs.push.databinding.ViewAddDialogBinding;

import cn.droidlover.xdroidbase.kit.Kits;

public class AddDialog extends BaseDialog<ViewAddDialogBinding> {

    private OnItemsClickListener onItemsClickListener;

    public AddDialog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.view_add_dialog;
    }

    @Override
    public void initData() {
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = (int) (Kits.Screen.getScreenWidth(getContext()) * 0.35);
        params.y = (int) (Kits.Screen.getScreenHeight(getContext()) * 0.04);
        params.height = (int) (Kits.Screen.getScreenHeight(getContext()) * 0.16);
        params.width = (int) (Kits.Screen.getScreenWidth(getContext()) * 0.3);
        window.setWindowAnimations(R.style.AnimFade);
        window.setGravity(Gravity.TOP);
        window.setAttributes(params);
        setCancelable(false);
        //设置外部点击取消
        setCanceledOnTouchOutside(true);
        getBinding().tvGroup.setOnClickListener(v -> {
            dismiss();
            if (onItemsClickListener != null) {
                onItemsClickListener.onItemClick(getBinding().tvGroup.getText().toString());
            }
        });
        getBinding().tvAdd.setOnClickListener(v -> {
            dismiss();
            if (onItemsClickListener != null) {
                onItemsClickListener.onItemClick(getBinding().tvAdd.getText().toString());
            }
        });
        getBinding().tvSaoyisao.setOnClickListener(v -> {
            dismiss();
            if (onItemsClickListener != null) {
                onItemsClickListener.onItemClick(getBinding().tvSaoyisao.getText().toString());
            }
        });
    }

    public void setOnItemsClickListener(OnItemsClickListener listener) {
        this.onItemsClickListener = listener;
    }

    public interface OnItemsClickListener{
        void onItemClick(String text);
    }

    public void show() {
        super.show();
    }
}