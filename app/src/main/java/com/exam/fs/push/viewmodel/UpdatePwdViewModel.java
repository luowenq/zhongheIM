package com.exam.fs.push.viewmodel;

import android.text.TextUtils;
import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.model.UpdatePwdModel;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.utils.LoginOut;
import com.exam.fs.push.viewmodel.base.BaseFragmentViewModel;

import cn.droidlover.xdroid.event.BusFactory;
import cn.droidlover.xdroid.net.XApi;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.ToastManager;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class UpdatePwdViewModel extends BaseFragmentViewModel implements ViewModelLifecycle {
    private static final String TAG = "UpdatePwdViewModel";
    private SimpleObserver<SimpleModel<String>> simpleObserver;

    public UpdatePwdViewModel(){

    }

    public void updatePassword(String oldPassword,String newPassword,String checkNewPassword){
        if(TextUtils.isEmpty(oldPassword)){
            ToastManager.showShort(AppUtils.getAppContext(),R.string.app_edit_old_pwd);
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            ToastManager.showShort(AppUtils.getAppContext(),R.string.app_edit_new_pwd);
            return;
        }
        if(TextUtils.isEmpty(checkNewPassword)){
            ToastManager.showShort(AppUtils.getAppContext(),R.string.app_again_edit_pwd);
            return;
        }

        if(oldPassword.length()<8||newPassword.length()<8||checkNewPassword.length()<8){
            ToastManager.showShort(AppUtils.getAppContext(),R.string.app_pwdNumber);
            return;
        }

        simpleObserver = new SimpleObserver<SimpleModel<String>>() {
            @Override
            public void onNext(SimpleModel<String> uploadFileBeanSimpleModel) {

            }
        };
        UserModel userModel = Config.getUsers();
        if(userModel == null){
            return;
        }
        UpdatePwdModel.updatePassword(userModel.id,oldPassword,newPassword,checkNewPassword).flatMap((Function<SimpleModel<String>
                , ObservableSource<SimpleModel<String>>>) model -> {
            if (model.msg.equals("0")) {
                ToastManager.showShort(AppUtils.getAppContext(), R.string.app_update_pwd_success);
                LoginOut.loginOut();
                if(App.getTopActivity().getLocalClassName().equals("UpdatePwdActivity")) {
                    App.getTopActivity().finish();
                }
                BusFactory.getBus().post(new EventBusBean(EventBusBean.TAG_FINISH_ALL_PAGE));
            }
            return Observer::onComplete;
        }).compose(XApi.getObservableScheduler()).subscribe(simpleObserver);
    }

    @Override
    public void onDestroy() {
        if (simpleObserver != null && simpleObserver.isDisposed())
            simpleObserver.dispose();
    }
}
