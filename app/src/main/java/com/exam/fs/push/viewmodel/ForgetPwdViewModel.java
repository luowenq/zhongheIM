package com.exam.fs.push.viewmodel;

import android.text.TextUtils;
import android.view.View;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.model.CodeModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.viewmodel.base.BaseFragmentViewModel;

import java.util.concurrent.TimeUnit;

import cn.droidlover.xdroid.net.XApi;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.ToastManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ForgetPwdViewModel extends BaseFragmentViewModel implements ViewModelLifecycle {

    private String phone,number;
    public int codeMaxLength = 6;
    public int phoneMaxLength = 11;
    private SimpleObserver<Long> observer;
    private SimpleObserver<SimpleModel<String>> observer1;
    private boolean isClick = false;
    public ObservableBoolean codeEnable = new ObservableBoolean(false);
    public ObservableField<String> getVarlyText = new ObservableField<>("获取验证码");

    public ForgetPwdViewModel(){

    }

    public void onPhoneTextChanged(CharSequence s, int start, int before, int count) {
        phone = s.toString();
        if (phone.length() > 0) {
            if (!isClick) {
                codeEnable.set(true);
            }
        } else {
            codeEnable.set(false);
        }
    }

    public void onNumTextChanged(CharSequence s, int start, int before, int count) {
        number = s.toString();
    }

    public void getCode(View view){
        if (phone.length() < phoneMaxLength) {
            ToastManager.showShort(AppUtils.getAppContext(), R.string.app_phoneNumber);
        } else {
            observer = new SimpleObserver<Long>() {
                @Override
                public void onNext(Long aLong) {
                    if (aLong == 1) {
                        isClick = false;
                        ToastManager.showShort(view.getContext(), R.string.app_getVarly_success);
                    }
                    if (aLong == 120) {
                        isClick = false;
                    }
                    codeEnable.set(false);
                    getVarlyText.set(120 - aLong + "s");
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                    getVarlyText.set("获取验证码");
                    codeEnable.set(true);
                }
            };
            observer1 = new SimpleObserver<SimpleModel<String>>() {
                @Override
                public void onNext(SimpleModel<String> stringSimpleModel) {
                    if(stringSimpleModel.msg.equals("0")){
                        Observable.interval(0, 1, TimeUnit.SECONDS).take(120).compose(XApi.getObservableScheduler()).subscribe(observer);
                    }
                }
            };
            CodeModel.code(phone, "2").compose(XApi.getObservableScheduler()).subscribe(observer1);
        }
    }

    public void onNext(View view){
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(number)) {
            ToastManager.showShort(AppUtils.getAppContext(), "请将手机号和验证码填写完整!");
        } else if (number.length() < codeMaxLength) {
            ToastManager.showShort(AppUtils.getAppContext(), R.string.app_codeNumber);
        } else if (phone.length() < phoneMaxLength) {
            ToastManager.showShort(AppUtils.getAppContext(), R.string.app_phoneNumber);
        } else {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_RETREN_PWD).withString("code",number).withString("phone",phone).navigation();
        }
    }

    @Override
    public void onDestroy() {
        if (observer != null && observer.isDisposed())
            observer.dispose();
    }
}