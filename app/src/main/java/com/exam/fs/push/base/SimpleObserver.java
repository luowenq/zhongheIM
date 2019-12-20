package com.exam.fs.push.base;

import com.exam.fs.push.dialog.LoadingDialogHelper;
import com.exam.fs.push.utils.LoginOut;

import cn.droidlover.xdroid.net.exception.ResultErrorException;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.ToastManager;
import io.reactivex.observers.ResourceObserver;

public abstract class SimpleObserver<T> extends ResourceObserver<T> {

    private boolean isShowDialog = true;
    private String dialogText = "请稍后...";

    public SimpleObserver() {
    }

    protected SimpleObserver(boolean isShowDialog) {
        this.isShowDialog = isShowDialog;
    }

    public SimpleObserver(String dialogText) {
        this.dialogText = dialogText;
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ResultErrorException) {
            if(((ResultErrorException) e).msg.equals("token无效")){
                LoginOut.loginOut();
            }
            ToastManager.showShort(AppUtils.getAppContext(), ((ResultErrorException) e).msg);
        } else {
            ToastManager.showShort(AppUtils.getAppContext(), "未知错误");
        }
        onComplete();
    }

    protected boolean isNetError(Throwable throwable) {
        return throwable instanceof ResultErrorException && (((ResultErrorException) throwable)
                .msg.equals("连接失败") || ((ResultErrorException) throwable).msg.equals("网络异常"));
    }

    @Override
    public void onComplete() {
        if (isShowDialog)
            LoadingDialogHelper.getInstance().cancelDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isShowDialog)
            LoadingDialogHelper.getInstance().showDialog(dialogText);
    }
}