package com.exam.fs.push.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.viewmodel.base.BaseFragmentViewModel;

public class MineViewModel extends BaseFragmentViewModel implements ViewModelLifecycle {
    private static final String TAG = "MineViewModel";

    public ObservableField<String> nickName = new ObservableField<>("");
    public ObservableField<String> userIDCard = new ObservableField<>("24158154851451");

    public MineViewModel(){
        UserModel userModel = Config.getUsers();
        nickName.set(userModel.nickname);
    }

    public void editUserInfo(View view){
        ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_EDIT_USERINFO).navigation();
    }

    @Override
    public void onDestroy() {

    }
}
