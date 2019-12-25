package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityAccountSafeBinding;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 账号与安全
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_ACCOUNT_SAFE)
public class AccountSafeActivity extends BaseActivity<ActivityAccountSafeBinding> {

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"账号与安全");
        getBinding().btnPhone.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_BIND_PHONE).navigation();
        });
        getBinding().btnUpdatePwd.setOnClickListener(view -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_UPDATE_PWD).navigation();
        });

        View.OnClickListener onClickListener = view ->{
            ToastUtils.showShort(R.string.app_no_function);
        };
        getBinding().btnUrgentContacts.setOnClickListener(onClickListener);
        getBinding().btnLoginManage.setOnClickListener(onClickListener);
        getBinding().btnMoreSetting.setOnClickListener(onClickListener);

        UserModel userModel = Config.getUsers();
        if(userModel != null) {
            getBinding().btnAccountNumber.setRightText(userModel.username);
            getBinding().btnUpdatePwd.setRightText(TextUtils.isEmpty(userModel.password) ? "未设置" : "已设置");
            getBinding().btnPhone.setRightText(userModel.username);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventBusBean busBean) {
        if (busBean.getTag() == EventBusBean.TAG_FINISH_ALL_PAGE) {
            finish();
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_account_safe;
    }
}
