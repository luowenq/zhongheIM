package com.exam.fs.push.model;

import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.model.bean.UploadFileBean;
import com.exam.fs.push.net.Api;

import java.io.File;

import cn.droidlover.xdroid.net.XApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditUserInfoModel {
    public static Observable<SimpleModel<UploadFileBean>> uploadImage(String token, File file) {
        return Api.getMankoService().loadImage(token, file)
                .compose(XApi.getObservableScheduler()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
