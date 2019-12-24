package com.exam.fs.push.model.bean;

import java.util.List;

public class WechatMomentsBean {
    public String header;
    public String nickname;
    public String time;
    public String content;
    public List<String> imgs;
    public boolean isLike;
    public List<CommentBean> commentBean;

    public static class CommentBean{
        public String name;//回复人
        public String toName;//被回复人
        public String content;
    }
}
