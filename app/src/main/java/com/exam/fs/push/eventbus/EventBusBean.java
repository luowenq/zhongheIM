package com.exam.fs.push.eventbus;

import cn.droidlover.xdroid.event.IBus;

public class EventBusBean implements IBus.IEvent {

    private int tag;
    private Object object;

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
