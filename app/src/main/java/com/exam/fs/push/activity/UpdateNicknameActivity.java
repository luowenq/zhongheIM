package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityUpdateNicknameBinding;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.viewmodel.EditUserInfoViewModel;

import cn.droidlover.xdroidbase.kit.ToastManager;

/**
 * 修改昵称
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_UPDATE_NICKNAME)
public class UpdateNicknameActivity extends BaseActivity<ActivityUpdateNicknameBinding> {
    private EditUserInfoViewModel viewModel;
    private String nickName;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"修改昵称");
        viewModel = new EditUserInfoViewModel();
        getBinding().setViewModel(viewModel);
        UserModel model = Config.getUsers();
        if(model != null) {
            viewModel.nickName.set(model.nickname);
        }
        getBinding().etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nickName = s.toString();
                if (s.length() > 0) {
                    getBinding().titleView.getRightButton().setEnabled(true);
                } else {
                    getBinding().titleView.getRightButton().setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        getBinding().titleView.setRightBtnRes(v -> {
            if(TextUtils.isEmpty(nickName)){
                ToastManager.showShort(this,"请输入昵称");
                return;
            }
            viewModel.updateUserInfo("","","",nickName,"","","",true);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_nickname;
    }
}
