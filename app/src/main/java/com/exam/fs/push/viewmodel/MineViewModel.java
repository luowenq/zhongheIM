package com.exam.fs.push.viewmodel;

import android.view.View;

import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.viewmodel.base.BaseFragmentViewModel;

public class MineViewModel extends BaseFragmentViewModel implements ViewModelLifecycle {
    private static final String TAG = "MineViewModel";

    public ObservableField<String> nickName = new ObservableField<>("");
    public ObservableField<String> username = new ObservableField<>("");

    public MineViewModel(){
    }

    public void editUserInfo(View view){
        ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_EDIT_USERINFO).navigation();
    }

    @Override
    public void onDestroy() {

    }
}
