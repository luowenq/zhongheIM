package com.exam.fs.push.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.exam.fs.push.model.UserModel;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import cn.droidlover.xdroidbase.cache.SharedPref;
import cn.droidlover.xdroidbase.kit.AppUtils;

public class Config {

    public static void setUser(UserModel user) {
        if (user == null) {
            SharedPref.getInstance(AppUtils.getAppContext()).putString("user", null);
            setToken(null);
            return;
        }
        String json = new Gson().toJson(user);
        String data;
        try {
//            setToken(user.token);
            data = new String(Base64.encode(json.getBytes("UTF-8"), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            data = "";
        }
        SharedPref.getInstance(AppUtils.getAppContext()).putString("user", data);
    }

    public static UserModel getUsers() {
        String data = SharedPref.getInstance(AppUtils.getAppContext()).getString("user", "");
        if (TextUtils.isEmpty(data))
            return null;
        String json = new String(Base64.decode(data, Base64.DEFAULT));
        return new Gson().fromJson(json, UserModel.class);
    }

    /**
     * 判断用户是否登录
     *
     * @return true 为登录
     */
    public static boolean isLogin() {
        return getUsers() != null;
    }

    public static String getToken() {
        return SharedPref.getInstance(AppUtils.getAppContext()).getString("token", "");
    }

    public static void setToken(String token) {
        SharedPref.getInstance(AppUtils.getAppContext()).putString("token", token);
    }
}