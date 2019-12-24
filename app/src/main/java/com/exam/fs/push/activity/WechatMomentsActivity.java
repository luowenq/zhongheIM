package com.exam.fs.push.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.WechatMomentsAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityWechatMomentsBinding;
import com.exam.fs.push.dialog.WechatPublishDialog;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.model.bean.WechatMomentsBean;
import com.exam.fs.push.net.Api;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 朋友圈
 */
@Route(path = RouterTables.PAGE_ACTIVITY_WECHAT_MOMENTS)
public class WechatMomentsActivity extends BaseActivity<ActivityWechatMomentsBinding> {
    private List<WechatMomentsBean> list = new ArrayList<>();
    private WechatPublishDialog dialog;

    @SuppressLint("Range")
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"");

        getBinding().rvContent.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        WechatMomentsAdapter adapter = new WechatMomentsAdapter(this);
        getBinding().rvContent.setAdapter(adapter);

        UserModel model = Config.getUsers();
        if(model != null) {
            LoadImage.loadHeadImage(getBinding().ivHeader, Api.imageUrl+model.headIcon);
            getBinding().tvSign.setText(model.sign);
            getBinding().tvNickname.setText(model.nickname);
        }
        List<String> imgs = new ArrayList<>();
        for (int i = 0;i<6;i++){
            imgs.add("https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1188387633,958216909&fm=26&gp=0.jpg");
        }
        List<WechatMomentsBean.CommentBean> commentBeans = new ArrayList<>();
        for (int i = 0;i<8;i++){
            WechatMomentsBean.CommentBean commentBean = new WechatMomentsBean.CommentBean();
            commentBean.content = "纵算水尽山穷，叶落成空，那老去的年华依旧可以风姿万种。纵算岁月朦胧";
            commentBean.name = "巴啦啦";
            commentBean.toName = "二柱子";
            commentBeans.add(commentBean);
        }
        for(int i = 0;i<3;i++) {
            WechatMomentsBean bean = new WechatMomentsBean();
            bean.header = "https://dss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2462146637,4274174245&fm=26&gp=0.jpg";
            bean.nickname = "二柱子";
            bean.content = "纵算水尽山穷，叶落成空，那老去的年华依旧可以风姿万种。纵算岁月朦胧，天涯西东，依然可以觅寻当年遗落的影踪。忽然间红尘梦醒，又是一场盛宴散去。将万千心事寄放天涯的年龄早已过去，那份年少时的冲动，也被岁月消磨得荡然无存。早已擦肩而过的何必追忆，反反复复，终究还是和昨天告了别。";
            if(i == 1) {
                bean.isLike = true;
            }else {
                bean.isLike = false;
            }
            bean.time = "1小时前";
            bean.imgs = imgs;
            bean.commentBean = commentBeans;
            list.add(bean);
        }

        adapter.setData(list);

        getBinding().titleView.getRootLayout().getBackground().setAlpha(0);
        getBinding().scrollView.setOnScrollListener((scrollY, oldScrollY) -> {
            float alpha = ((float)255/310)*scrollY;
            if(alpha <= 0){
                getBinding().titleView.getRootLayout().getBackground().setAlpha(0);
            }else if(alpha<255) {
                getBinding().titleView.getRootLayout().getBackground().setAlpha((int) alpha);
                if(alpha>180) {
                    getBinding().titleView.setTitle(R.string.app_pyq);
                }else {
                    getBinding().titleView.setTitle("");
                }
            }else if(alpha>=255){
                getBinding().titleView.getRootLayout().getBackground().setAlpha(255);
            }
        });

        getBinding().titleView.getRightImageView().setOnClickListener(v -> {
            if(dialog == null){
                dialog = new WechatPublishDialog(this);
            }
            dialog.show();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wechat_moments;
    }
}
