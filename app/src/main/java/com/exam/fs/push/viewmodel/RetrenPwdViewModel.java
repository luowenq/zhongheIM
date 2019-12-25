package com.exam.fs.push.viewmodel;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.model.RetrenPwdModel;
import com.exam.fs.push.model.UpdatePwdModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.utils.LoginOut;
import com.exam.fs.push.viewmodel.base.BaseFragmentViewModel;

import cn.droidlover.xdroid.event.BusFactory;
import cn.droidlover.xdroid.net.XApi;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.ToastManager;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class RetrenPwdViewModel extends BaseFragmentViewModel implements ViewModelLifecycle {
    private SimpleObserver<SimpleModel<String>> observer;
    private String newPwd,againPwd;
    public int pwdMaxLength = 8;
    private String phone,code;

    public RetrenPwdViewModel(){

    }

    public void setDate(String phone,String code){
        this.phone = phone;
        this.code = code;
    }

    public void onNewPwd(CharSequence s, int start, int before, int count){
        newPwd = s.toString().trim();
    }

    public void onAgainPwd(CharSequence s, int start, int before, int count){
        againPwd = s.toString().trim();
    }

    public void onComplete(View view){
        if (newPwd.length() < pwdMaxLength || againPwd.length() < pwdMaxLength) {
            ToastManager.showShort(AppUtils.getAppContext(), R.string.app_pwdNumber);
        } else if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(againPwd)) {
            ToastManager.showShort(AppUtils.getAppContext(), R.string.app_input_new_pwd);
        } else if (!newPwd.equals(againPwd)) {
            ToastManager.showShort(AppUtils.getAppContext(), R.string.app_pwd_noway);
        } else {
            observer = new SimpleObserver<SimpleModel<String>>() {
                @Override
                public void onNext(SimpleModel<String> s) {
                    Log.e("HJQresult","成功");
                }
            };
            RetrenPwdModel.retrievePassword(phone,newPwd,code).compose(XApi.getObservableScheduler()).subscribe(observer);
        }
    }

    @Override
    public void onDestroy() {
        if (observer != null && observer.isDisposed())
            observer.dispose();
    }
}
