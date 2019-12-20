package com.exam.fs.push.model;

import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.model.bean.UploadFileBean;
import com.exam.fs.push.net.Api;

import java.io.File;

import cn.droidlover.xdroid.net.XApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditUserInfoModel {
    public static Observable<SimpleModel<UploadFileBean>> uploadImage(File file) {
        RequestBody fileRQ = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
//        RequestBody rb =RequestBody.create(MediaType.parse("text/plain"), token);
        return Api.getMankoService().loadImage(part)
                .compose(XApi.getObservableScheduler()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<SimpleModel<String>> updateUserInfo(String userName,String headIcon,String birthday,String sign,
                                                                 String nickname,String sex,String province,String city){
        return Api.getMankoService().updateUserInfo(userName,headIcon,birthday,sign,nickname,sex,province,city)
                .compose(XApi.getObservableScheduler()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
