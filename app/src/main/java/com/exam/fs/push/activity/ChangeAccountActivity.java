package com.exam.fs.push.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.ChangeAccountAdapter;
import com.exam.fs.push.adapter.ChatGroupManagerAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityChangeAccountBinding;
import com.exam.fs.push.model.bean.AccountBean;
import com.exam.fs.push.router.RouterTables;

import java.util.ArrayList;
import java.util.List;

/**
 * 切换账号
 */
@Route(path = RouterTables.PAGE_ACTIVITY_CHANGE_ACCOUNT)
public class ChangeAccountActivity extends BaseActivity<ActivityChangeAccountBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"切换账号");

        getBinding().rvAccount.setLayoutManager(new GridLayoutManager(context,3));
        ChangeAccountAdapter adapter = new ChangeAccountAdapter(context);
        getBinding().rvAccount.setAdapter(adapter);

        List<AccountBean> list = new ArrayList<>();
        list.add(new AccountBean("","1354784021545645255",1));
        list.add(new AccountBean("","1354784021545645255",2));
        list.add(new AccountBean("","1354784021545645255",3));
        adapter.setData(list);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_account;
    }
}
