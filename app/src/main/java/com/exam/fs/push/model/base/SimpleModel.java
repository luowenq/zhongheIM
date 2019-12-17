package com.exam.fs.push.model.base;

import android.text.TextUtils;

public class SimpleModel<T> implements cn.droidlover.xdroid.net.model.SimpleModel {

    public String msg;//返回信息
    public String error;
    public T content;

    @Override
    public boolean isValid() {
        return TextUtils.equals(msg, "0");
    }

    @Override
    public int getResultCode() {
        int code = 0;
        try {
            code = Integer.parseInt(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }

    @Override
    public String getResultMsg() {
        return error;
    }
}