package com.exam.fs.push.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseDialog;
import com.exam.fs.push.databinding.DialogWechatPublishBinding;

import cn.droidlover.xdroidbase.kit.Kits;

public class WechatPublishDialog extends BaseDialog<DialogWechatPublishBinding> {
    private View.OnClickListener onClickListener;

    public WechatPublishDialog(Context context) {
        super(context, R.style.LoadingDialog);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_wechat_publish;
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public void initData() {
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        Point outSize = new Point();
        window.getWindowManager().getDefaultDisplay().getSize(outSize);
        params.width = outSize.x - (int) (Kits.Dimens.dpToPx(getContext(),getContext().getResources().getDimension(R.dimen.len_8)));
        window.setWindowAnimations(R.style.AnimFade);
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(params);

        View.OnClickListener listener = v->{
            onClickListener.onClick(v);
            dismiss();
        };
        getBinding().btnCamera.setOnClickListener(listener);
        getBinding().btnFromFile.setOnClickListener(listener);
        getBinding().btnWordsContent.setOnClickListener(listener);
        getBinding().btnCancel.setOnClickListener(v -> {
            dismiss();
        });
    }
}
