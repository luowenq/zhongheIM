package com.exam.fs.push.net;

import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.model.bean.UploadFileBean;

import java.io.File;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MankoService {

    @POST("sms/sendSms")
    @FormUrlEncoded
    Observable<SimpleModel<String>> code(@Field("phoneNumber") String number, @Field("sendType") String sendType);//获取短信

    @POST("user/loginByUsername")
    @FormUrlEncoded
    Observable<SimpleModel<String>> login(@Field("username") String username, @Field("password") String password);//登录获取token

    @POST("user/userInfo")
    @FormUrlEncoded
    Observable<SimpleModel<UserModel>> getUserInfo(@Field("token") String token);//获取用户信息

    @POST("user/regist")
    @FormUrlEncoded
    Observable<SimpleModel<String>> register(@Field("username") String username
            , @Field("verifyCode") String verifyCode, @Field("password") String password);//注册

    @POST("file/upload")
    @FormUrlEncoded
    Observable<SimpleModel<UploadFileBean>> loadImage(
            @Field("token") String token,
            @Field("file") File file
    );
}