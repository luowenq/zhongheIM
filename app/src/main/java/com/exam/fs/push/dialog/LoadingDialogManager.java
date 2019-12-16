package com.exam.fs.push.dialog;

import android.app.Dialog;

public class LoadingDialogManager {

    private static LoadingDialogManager loadingDialogManager;
    private Dialog dialog;

    public static LoadingDialogManager getInstance() {
        if (loadingDialogManager == null) {
            loadingDialogManager = new LoadingDialogManager();
        }
        return loadingDialogManager;
    }

    private LoadingDialogManager() {
        LoadingDialogHelper.getInstance().setOnShowDialogListener(new LoadingDialogHelper.OnShowDialogListener() {
            @Override
            public void onShow(boolean isShow, String text) {
                if (dialog != null) {
                    if (isShow) {
                        if (dialog instanceof LoadingDialog)
                            ((LoadingDialog) dialog).show(text);
                        else
                            dialog.show();
                    } else {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onShow(boolean isShow) {
                if (dialog != null) {
                    if (isShow) {
                        dialog.show();
                    } else {
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void show() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void DialogOnDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}