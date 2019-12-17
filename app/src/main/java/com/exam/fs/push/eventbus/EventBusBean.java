package com.exam.fs.push.eventbus;

import cn.droidlover.xdroid.event.IBus;

public class EventBusBean implements IBus.IEvent {
    /**
     * 登录成功
     */
    public static final int TAG_LOGIN_SUCCESS = 1;

    private int tag;
    private Object object;

    public EventBusBean(int tag) {
        this.tag = tag;
    }

    public EventBusBean(int tag, Object object) {
        this.tag = tag;
        this.object = object;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public int getTag() {
        return tag;
    }
}
