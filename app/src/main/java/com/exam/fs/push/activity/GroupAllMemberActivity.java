package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.ChatGroupManagerAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityGroupAllMemberBinding;
import com.exam.fs.push.model.bean.User;
import com.exam.fs.push.router.RouterTables;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部群成员
 */
@Route(path = RouterTables.PAGE_ACTIVITY_GROUP_ALL_MEMBER)
public class GroupAllMemberActivity extends BaseActivity<ActivityGroupAllMemberBinding> {
    private List<User> list = new ArrayList<>();;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"聊天成员");

        getBinding().rvGroupMember.setLayoutManager(new GridLayoutManager(context,5));
        ChatGroupManagerAdapter adapter = new ChatGroupManagerAdapter(context);
        getBinding().rvGroupMember.setAdapter(adapter);


        for (int i = 0;i<50;i++){
            User user = new User("appkey","昵称"+i,"asdf","https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1806047429,2669047046&fm=26&gp=0.jpg");
            list.add(user);
        }
        adapter.refresh(list,true);

        getBinding().etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s.toString())) {
                    List<User> searchList = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).username.contains(s.toString())) {
                            searchList.add(list.get(i));
                        }
                    }
                    adapter.refresh(searchList,false);
                }else {
                    adapter.refresh(list,true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_all_member;
    }
}
