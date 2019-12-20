package com.exam.fs.push.model;

import com.exam.fs.push.model.base.BaseModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.net.Api;

import cn.droidlover.xdroid.net.XApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserModel extends BaseModel {

    public String id;
    public String sex;
    public String sign;
    public String city;
    public String created;
    public String modified;
    public String province;
    public String nickname;
    public String username;
    public String password;
    public String headIcon;

    public static Observable<SimpleModel<String>> login(String userName, String password) {
        return Api.getMankoService().login(userName, password)
                .compose(XApi.getObservableScheduler()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SimpleModel<UserModel>> getUserInfo(String token) {
        return Api.getMankoService().getUserInfo(token).compose(XApi.getObservableScheduler())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SimpleModel<String>> gretea(String username, String friendyname, String mark) {
        return Api.getMankoService().addGreateFriendy(username, friendyname, mark).compose(XApi.getObservableScheduler())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SimpleModel<String>> delte(String username, String friendyname) {
        return Api.getMankoService().deleFriendy(username, friendyname).compose(XApi.getObservableScheduler())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}