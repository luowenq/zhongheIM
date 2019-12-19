package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityAddFriendyBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 添加好友
 */
@Route(path = RouterTables.PAGE_ACTIVITY_ADD_FRIENDY)
public class AddFriendyActivity extends BaseActivity<ActivityAddFriendyBinding> {

    @Override
    public void initData(Bundle bundle) {
        ARouter.getInstance().inject(this);
        initTitle(getBinding().titleView, "添加好友");
        getBinding().layoutSerson.tvSerson.setOnClickListener(v -> ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SERSON).navigation());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_friendy;
    }
}