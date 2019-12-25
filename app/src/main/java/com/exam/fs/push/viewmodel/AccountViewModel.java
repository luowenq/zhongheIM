package com.exam.fs.push.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.databinding.ObservableField;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.db.UserEntry;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.viewmodel.base.BaseFragmentViewModel;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.google.gson.Gson;

import cn.droidlover.xdroid.event.BusFactory;
import cn.droidlover.xdroid.net.XApi;
import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.droidlover.xdroidbase.log.XLog;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class AccountViewModel extends BaseFragmentViewModel implements ViewModelLifecycle {

    private String isAccount, isPassword;
    private SimpleObserver<SimpleModel<UserModel>> simpleObserver;
    public ObservableField<String> accountText = new ObservableField<>("");
    public ObservableField<String> passwordText = new ObservableField<>("");
    public ObservableField<String> forgetPwd = new ObservableField<>("忘记密码?");
    public ObservableField<String> loginText = new ObservableField<>("登  录");

    public AccountViewModel() {

    }

    public void onCountTextChanged(CharSequence s, int start, int before, int count) {
        isAccount = s.toString();
    }

    public void onPwdTextChanged(CharSequence s, int start, int before, int count) {
        isPassword = s.toString();
    }

    public void onForgetPwdClick(View view) {
        ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_FORGET_PWD).navigation();
    }

    public void onLoginClick(View view) {
        if (TextUtils.isEmpty(isAccount) || TextUtils.isEmpty(isPassword)) {
            ToastManager.showShort(view.getContext(), R.string.app_isLoginEmpty);
        } else {
            simpleObserver = new SimpleObserver<SimpleModel<UserModel>>() {
                @Override
                public void onNext(SimpleModel<UserModel> model) {
                    Context context = view.getContext();
                    Config.setUser(model.content);
//                    Log.e("EditUserInfoViewModel",new Gson().toJson(model));
                    ToastManager.showShort(context, R.string.app_success);
//                    BusFactory.getBus().post(new EventBusBean(EventBusBean.TAG_LOGIN_SUCCESS));
                    JMessageClient.login(model.content.username, model.content.password, new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {
                            XLog.d("回调结果是: >>>>>>>", s);
                            UserInfo myInfo = JMessageClient.getMyInfo();
                            String username = myInfo.getUserName();
                            String appKey = myInfo.getAppKey();
                            UserEntry user = UserEntry.getUser(username, appKey);
                            if (null == user) {
                                user = new UserEntry(username, appKey);
                                user.save();
                            }
                            ToastManager.showShort(context, R.string.app_success);
                            BusFactory.getBus().post(new EventBusBean(EventBusBean.TAG_LOGIN_SUCCESS));
                            if (context instanceof Activity) {
                                ((Activity) context).finish();
                            }
                        }
                    });
                }
            };
            UserModel.login(isAccount, isPassword).flatMap((Function<SimpleModel<String>, ObservableSource<SimpleModel<UserModel>>>) model -> {
                if (model != null) {
                    Config.setToken(model.content);
                }
                assert model != null;
                return UserModel.getUserInfo(model.content);
            }).compose(XApi.getObservableScheduler()).subscribe(simpleObserver);
        }
    }

    @Override
    public void onDestroy() {
        if (simpleObserver != null && simpleObserver.isDisposed())
            simpleObserver.dispose();
    }
}