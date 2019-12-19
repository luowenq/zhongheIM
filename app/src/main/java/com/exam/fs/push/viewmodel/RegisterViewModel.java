package com.exam.fs.push.viewmodel;

import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.exam.fs.push.R;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.dialog.UserAgreentDialog;
import com.exam.fs.push.model.CodeModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.viewmodel.base.BaseFragmentViewModel;

import java.util.concurrent.TimeUnit;

import cn.droidlover.xdroid.net.XApi;
import cn.droidlover.xdroidbase.kit.ToastManager;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class RegisterViewModel extends BaseFragmentViewModel implements ViewModelLifecycle {

    private int phoneLength, codeLength, pwdLength;
    public int codeMaxLength = 6;
    public int phoneMaxLength = 11;
    public int pwdMaxLength = 8;
    private boolean isChecked = true;
    private String isPhone, isVarly, isPassword;
    public ObservableBoolean codeEnable = new ObservableBoolean(false);
    public ObservableField<String> phoneText = new ObservableField<>("");
    public ObservableField<String> varlyText = new ObservableField<>("");
    public ObservableField<String> passwordText = new ObservableField<>("");
    public ObservableField<String> getVarlyText = new ObservableField<>("获取验证码");
    public ObservableField<String> registerText = new ObservableField<>("注  册");
    public ObservableField<String> greateText = new ObservableField<>("已阅读同意");
    public ObservableField<String> xyText = new ObservableField<>("《风声用户协议》");
    private SimpleObserver<Long> observer;
    private SimpleObserver<SimpleModel<String>> simpleObserver;
    private boolean isClick = false;

    public RegisterViewModel() {
    }

    public void onPhoneTextChanged(CharSequence s, int start, int before, int count) {
        isPhone = s.toString().trim();
        phoneLength = s.length();
        if (phoneLength > 0) {
            if (!isClick) {
                codeEnable.set(true);
            }
        } else {
            codeEnable.set(false);
        }
    }

    public void onVarlyTextChanged(CharSequence s, int start, int before, int count) {
        isVarly = s.toString().trim();
        codeLength = s.length();
    }

    public void onPwdTextChanged(CharSequence s, int start, int before, int count) {
        isPassword = s.toString().trim();
        pwdLength = s.length();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void onGetVarlyClick(View view) {
        if (phoneLength != phoneMaxLength) {
            ToastManager.showShort(view.getContext(), R.string.app_phoneNumber);
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
            CodeModel.code(isPhone, "0").flatMap((Function<SimpleModel<String>, ObservableSource<Long>>) model ->
                    Observable.interval(0, 1, TimeUnit.SECONDS).take(120)).compose(XApi.getObservableScheduler()).subscribe(observer);
        }
    }

    public void onReisterClick(View view) {
        if (TextUtils.isEmpty(isPhone) || TextUtils.isEmpty(isVarly) || TextUtils.isEmpty(isPassword)) {
            ToastManager.showShort(view.getContext(), R.string.app_isRegisterEmpty);
        } else if (codeLength != codeMaxLength) {
            ToastManager.showShort(view.getContext(), R.string.app_codeNumber);
        } else if (pwdLength != pwdMaxLength) {
            ToastManager.showShort(view.getContext(), R.string.app_pwdNumber);
        } else if (!isChecked) {
            ToastManager.showShort(view.getContext(), R.string.app_great);
        } else {
            simpleObserver = new SimpleObserver<SimpleModel<String>>() {
                @Override
                public void onNext(SimpleModel<String> stringSimpleModel) {
                }
            };
            CodeModel.register(isPhone, isVarly, isPassword).flatMap((Function<SimpleModel<String>
                    , ObservableSource<SimpleModel<String>>>) model -> {
                if (model.msg.equals("0")) {
                    ToastManager.showShort(view.getContext(), R.string.app_register_success);
                }
                return Observer::onComplete;
            }).compose(XApi.getObservableScheduler()).subscribe(simpleObserver);
        }
    }

    public void onXYClick(View view) {
        UserAgreentDialog dialog = new UserAgreentDialog(view.getContext());
        dialog.show();
    }

    @Override
    public void onDestroy() {
        if (observer != null && observer.isDisposed())
            observer.dispose();
        if (simpleObserver != null && simpleObserver.isDisposed())
            simpleObserver.dispose();
    }
}