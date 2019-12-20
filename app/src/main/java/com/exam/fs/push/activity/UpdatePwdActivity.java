package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityUpdatePwdBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.viewmodel.UpdatePwdViewModel;

/**
 * 修改密码
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_UPDATE_PWD)
public class UpdatePwdActivity extends BaseActivity<ActivityUpdatePwdBinding> {
    private UpdatePwdViewModel viewModel;

    private String oldPwd,newPwd,checkPwd;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"修改密码");

        viewModel = new UpdatePwdViewModel();
        getBinding().setViewModel(viewModel);

        addTextWatcher(getBinding().etOldPwd,1);
        addTextWatcher(getBinding().etNewPwd,2);
        addTextWatcher(getBinding().etAgainEditPwd,3);

        getBinding().titleView.getRightButton().setOnClickListener(v -> {
            viewModel.updatePassword(oldPwd,newPwd,checkPwd);
        });
        getBinding().btnForgetPwd.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_FORGET_PWD).navigation();
        });
    }

    private void addTextWatcher(EditText editText,int type){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switch (type){
                    case 1:
                        oldPwd = s.toString();
                        break;
                    case 2:
                        newPwd = s.toString();
                        break;
                    case 3:
                        checkPwd = s.toString();
                        break;
                }
                if(s.toString().length()>0){
                    getBinding().titleView.getRightButton().setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_pwd;
    }
}
