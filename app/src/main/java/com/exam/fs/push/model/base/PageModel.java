package com.exam.fs.push.model.base;

import java.io.Serializable;

public class PageModel<T> implements Serializable {

    public int page;//当前页数
    public int row;//当前显示条数
    public int totalPage;//总页数
    public int tatal;//总条数

    public T rows;//数据

    public boolean hasMore() {
        return page < totalPage;
    }
}