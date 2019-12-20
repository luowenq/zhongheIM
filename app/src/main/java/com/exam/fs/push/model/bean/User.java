package com.exam.fs.push.model.bean;

/**
 * 极光用户实体类
 * Created by ${chenyn} on 2017/8/14.
 */

public class User {
    public String appkey;
    public String username;
    public String platform;
    public String headIcon;

    public User(String appkey, String username, String platform,String headIcon) {
        this.appkey = appkey;
        this.username = username;
        this.platform = platform;
        this.headIcon = headIcon;
    }
}
