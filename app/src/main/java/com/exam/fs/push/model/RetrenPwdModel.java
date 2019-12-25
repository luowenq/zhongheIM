package com.exam.fs.push.model;

import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.net.Api;

import cn.droidlover.xdroid.net.XApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RetrenPwdModel {
    public static Observable<SimpleModel<String>> retrievePassword(String phoneNumber, String password, String verifyCode){
        return Api.getMankoService().retrievePassword(phoneNumber,password,verifyCode)
                .compose(XApi.getObservableScheduler()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
