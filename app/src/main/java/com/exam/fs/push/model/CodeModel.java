package com.exam.fs.push.model;

import com.exam.fs.push.model.base.BaseModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.net.Api;

import cn.droidlover.xdroid.net.XApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CodeModel extends BaseModel {

    public static Observable<SimpleModel<String>> code(String phone, String type) {
        return Api.getMankoService().code(phone, type).compose(XApi.getObservableScheduler())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SimpleModel<String>> register(String username, String verifyCode, String password) {
        return Api.getMankoService().register(username, verifyCode, password)
                .compose(XApi.getObservableScheduler()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}