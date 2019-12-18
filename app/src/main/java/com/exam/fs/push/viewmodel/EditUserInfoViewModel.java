package com.exam.fs.push.viewmodel;

import androidx.databinding.ObservableField;

import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.viewmodel.base.BaseActivityViewModel;

public class EditUserInfoViewModel extends BaseActivityViewModel implements ViewModelLifecycle {
    private static final String TAG = "EditUserInfoViewModel";

    public ObservableField<String> nickName = new ObservableField<>("");
    public ObservableField<String> userIDCard = new ObservableField<>("24158154851451");
    public ObservableField<String> sex = new ObservableField<>("未设置");
    public ObservableField<String> addr = new ObservableField<>("未设置");
    public ObservableField<String> sign = new ObservableField<>("未设置");

    public EditUserInfoViewModel(){
        UserModel userModel = Config.getUsers();
        nickName.set(userModel.nickname);
        sex.set(userModel.sex);
        addr.set(userModel.province);
        sign.set(userModel.sign);
    }



    @Override
    public void onDestroy() {

    }
}
