package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityUpdateNicknameBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 修改昵称
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_UPDATE_NICKNAME)
public class UpdateNicknameActivity extends BaseActivity<ActivityUpdateNicknameBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"修改昵称");
        getBinding().titleView.getRightButton().setEnabled(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_nickname;
    }
}
