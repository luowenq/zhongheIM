package com.exam.fs.push.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.ChatGroupManagerAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityChatGroupManagerBinding;
import com.exam.fs.push.model.bean.User;
import com.exam.fs.push.router.RouterTables;

import java.util.ArrayList;
import java.util.List;

/**
 * 群聊管理页面（群聊天详情）
 */
@Route(path = RouterTables.PAGE_ACTIVITY_CHAT_GROUP_MANAGER)
public class ChatGroupManagerActivity extends BaseActivity<ActivityChatGroupManagerBinding> {

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"聊天详情");

        getBinding().sivNotice.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_GROUP_NOTICE).navigation();
        });

        getBinding().btnAll.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_GROUP_ALL_MEMBER).navigation();
        });

        getBinding().rvGroupMember.setLayoutManager(new GridLayoutManager(context,5){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        ChatGroupManagerAdapter adapter = new ChatGroupManagerAdapter(context);
        getBinding().rvGroupMember.setAdapter(adapter);

        List<User> list = new ArrayList<>();
        for (int i = 0;i<50;i++){
            User user = new User("appkey","昵称"+i,"asdf","https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1806047429,2669047046&fm=26&gp=0.jpg");
            list.add(user);
        }
        adapter.refresh(list,true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat_group_manager;
    }
}
