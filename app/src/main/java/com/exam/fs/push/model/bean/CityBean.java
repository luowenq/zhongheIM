package com.exam.fs.push.model.bean;

import java.util.List;

public class CityBean {
    private String name;
    private int code;
    private List<AreaBean> areaDOList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AreaBean> getAreaDOList() {
        return areaDOList;
    }

    public void setAreaDOList(List<AreaBean> areaDOList) {
        this.areaDOList = areaDOList;
    }

    public static class AreaBean{
        private String name;
        private int code;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
