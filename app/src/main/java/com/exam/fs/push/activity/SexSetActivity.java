package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySexSetBinding;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.viewmodel.EditUserInfoViewModel;

/**
 * 性别设置
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SEX_SET)
public class SexSetActivity extends BaseActivity<ActivitySexSetBinding> {
    private EditUserInfoViewModel viewModel;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"性别");
        getBinding().btnMan.setOnClickListener(view -> {
            if (!getBinding().titleView.getRightButton().isEnabled()) {
                getBinding().titleView.getRightButton().setEnabled(true);
            }
            getBinding().btnMan.getRightImageView().setSelected(true);
            getBinding().btnWoman.getRightImageView().setSelected(false);
        });
        getBinding().btnWoman.setOnClickListener(view -> {
            if (!getBinding().titleView.getRightButton().isEnabled()) {
                getBinding().titleView.getRightButton().setEnabled(true);
            }
            getBinding().btnWoman.getRightImageView().setSelected(true);
            getBinding().btnMan.getRightImageView().setSelected(false);
        });

        viewModel = new EditUserInfoViewModel();
        getBinding().setViewModel(viewModel);

        UserModel userModel = Config.getUsers();
        if(userModel == null){
            return;
        }
        if(!TextUtils.isEmpty(userModel.sex)) {
            if (!getBinding().titleView.getRightButton().isEnabled()) {
                getBinding().titleView.getRightButton().setEnabled(true);
            }
            if (userModel.sex.equals("1")) {
                getBinding().btnMan.setSelected(true);
            } else if (userModel.sex.equals("2")) {
                getBinding().btnWoman.setSelected(true);
            }
        }

        getBinding().titleView.getRightButton().setOnClickListener(v -> {
            viewModel.updateUserInfo("","","","",getBinding().btnMan.getRightImageView().isSelected()?"1":"2"
            ,"","","",true);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sex_set;
    }
}
