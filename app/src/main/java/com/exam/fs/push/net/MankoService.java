package com.exam.fs.push.net;

import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.model.bean.UploadFileBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @Multipart
    Observable<SimpleModel<UploadFileBean>> loadImage(
            @Part MultipartBody.Part file
    );

    @POST("user/updateUserInfo")
    @FormUrlEncoded
    Observable<SimpleModel<String>> updateUserInfo(
            @Field("userId") String userId,
            @Field("headIcon") String headIcon,
            @Field("sign") String sign,
            @Field("eMail") String eMail,
            @Field("nickname") String nickname,
            @Field("sex") String sex,
            @Field("province") String province,
            @Field("city") String city,
            @Field("backgroundImg") String backgroundImg
    );

    @POST("appUserFriend/deleteFriendByJiGuang")
    @FormUrlEncoded
    Observable<SimpleModel<String>> deleFriendy(@Field("username") String username, @Field("friendUsername") String friendUsername);

    @POST("appFriendApply/addFriendBack")
    @FormUrlEncoded
    Observable<SimpleModel<String>> addGreateFriendy(@Field("username") String usname,
                                                     @Field("friendUsername")String friendUsername, @Field("friendRemark") String friendRemark);//同意添加好友

    @POST("user/updatePassword")
    @FormUrlEncoded
    Observable<SimpleModel<String>> updatePassword(
            @Field("userId") String userId,
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword,
            @Field("checkNewPassword") String checkNewPassword
    );
}