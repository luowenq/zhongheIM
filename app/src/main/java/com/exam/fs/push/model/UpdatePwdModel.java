package com.exam.fs.push.model;

import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.net.Api;

import cn.droidlover.xdroid.net.XApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdatePwdModel {
    public static Observable<SimpleModel<String>> updatePassword(String userName, String oldPassword,String newPassword,String checkNewPassword){
        return Api.getMankoService().updatePassword(userName,oldPassword,newPassword,checkNewPassword)
                .compose(XApi.getObservableScheduler()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
