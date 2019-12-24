package com.exam.fs.push.model.bean;

public class AccountBean {
    public String img;
    public String name;
    public int state;//1 当前使用，2正在退出，3 不显示

    public AccountBean(String img,String name,int state){
        this.img = img;
        this.name = name;
        this.state = state;
    }
}
