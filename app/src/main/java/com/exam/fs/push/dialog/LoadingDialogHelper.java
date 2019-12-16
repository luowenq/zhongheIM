package com.exam.fs.push.dialog;

import android.text.TextUtils;

public class LoadingDialogHelper {
    private static int count = 0;
    private OnShowDialogListener listener;
    private static LoadingDialogHelper loadingDialogHelper;

    public static LoadingDialogHelper getInstance() {
        if (loadingDialogHelper == null) {
            loadingDialogHelper = new LoadingDialogHelper();
        }
        return loadingDialogHelper;
    }

    public void up() {
        up("");
    }

    public void down() {
        count--;
        if (count < 0)
            count = 0;
        if (listener != null) {
            listener.onShow(isShow());
        }
    }

    public void up(String text) {
        count++;
        if (listener != null) {
            if (TextUtils.isEmpty(text))
                listener.onShow(isShow());
            else
                listener.onShow(isShow(), text);
        }
    }

    public void showDialog() {
        up();
    }

    public void showDialog(String text) {
        up(text);
    }

    public void cancelDialog() {
        down();
    }

    public void dismissDialog() {
        LoadingDialogManager.getInstance().dismiss();
    }

    public void rest() {
        count = 0;
        if (listener != null) {
            listener.onShow(isShow());
        }
    }

    private boolean isShow() {
        return count != 0;
    }

    void setOnShowDialogListener(OnShowDialogListener listener) {
        this.listener = listener;
    }

    public interface OnShowDialogListener {
        void onShow(boolean isShow);

        void onShow(boolean isShow, String text);
    }
}