package com.exam.fs.push.viewmodel;

import android.text.TextUtils;

import androidx.databinding.ObservableField;

import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.callback.ViewModelLifecycle;
import com.exam.fs.push.eventbus.EventBusBean;
import com.exam.fs.push.model.EditUserInfoModel;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.model.bean.UploadFileBean;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.viewmodel.base.BaseActivityViewModel;

import java.io.File;

import cn.droidlover.xdroid.event.BusFactory;
import cn.droidlover.xdroid.net.XApi;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.droidlover.xdroidbase.kit.ToastManager;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class EditUserInfoViewModel extends BaseActivityViewModel implements ViewModelLifecycle {
    private static final String TAG = "EditUserInfoViewModel";

    public ObservableField<String> nickName = new ObservableField<>("");
    public ObservableField<String> sign = new ObservableField<>("未设置");
    public ObservableField<String> username = new ObservableField<>("");

    private SimpleObserver<SimpleModel<String>> simpleObserver;
    private SimpleObserver<SimpleModel<UploadFileBean>> uploadFileObserver;
    private String urlImage;

    public EditUserInfoViewModel(){

    }

    public void uploadImage(String loadImage){
        uploadFileObserver = new SimpleObserver<SimpleModel<UploadFileBean>>() {
            @Override
            public void onNext(SimpleModel<UploadFileBean> uploadFileBeanSimpleModel) {

            }
        };
        EditUserInfoModel.uploadImage(new File(loadImage)).flatMap((Function<SimpleModel<UploadFileBean>
                , ObservableSource<SimpleModel<UploadFileBean>>>) model -> {
            if (model.msg.equals("0")) {
                urlImage = model.content.fileName;
            }
            updateUserInfo(urlImage,"","","","","","",false);
            return Observer::onComplete;
        }).compose(XApi.getObservableScheduler()).subscribe(uploadFileObserver);
    }

    public void updateUserInfo(String headIcon,String birthday, String sign, String nickname, String sex, String province,
                               String city, boolean isFinish){
        if (simpleObserver != null && simpleObserver.isDisposed())
            simpleObserver.dispose();
        simpleObserver = new SimpleObserver<SimpleModel<String>>() {
            @Override
            public void onNext(SimpleModel<String> uploadFileBeanSimpleModel) {

            }
        };
        UserModel userModel = Config.getUsers();
        if(userModel == null){
            return;
        }
        EditUserInfoModel.updateUserInfo(userModel.username,TextUtils.isEmpty(headIcon)?userModel.headIcon:headIcon,
                birthday,
                TextUtils.isEmpty(sign)?userModel.sign:sign,
                TextUtils.isEmpty(nickname)?userModel.nickname:nickname,
                TextUtils.isEmpty(sex)?userModel.sex:sex,
                TextUtils.isEmpty(province)?userModel.province:province,
                TextUtils.isEmpty(city)?userModel.city:city).flatMap((Function<SimpleModel<String>
                , ObservableSource<SimpleModel<String>>>) model -> {
            if (model.msg.equals("0")) {
                ToastManager.showShort(AppUtils.getAppContext(), R.string.app_update_userinfo_success);
                if(!TextUtils.isEmpty(headIcon)){
                    userModel.headIcon = headIcon;
                }
                if(!TextUtils.isEmpty(sign)){
                    userModel.sign = sign;
                }
                if(!TextUtils.isEmpty(nickname)){
                    userModel.nickname = nickname;
                }
                if(!TextUtils.isEmpty(sex)){
                    userModel.sex = sex;
                }
                if(!TextUtils.isEmpty(province)){
                    userModel.province = province;
                }
                if(!TextUtils.isEmpty(city)){
                    userModel.city = city;
                }
                Config.setUser(userModel);
                BusFactory.getBus().post(new EventBusBean(EventBusBean.TAG_REFRESH_USERINFO));
                if (isFinish && !App.getTopActivity().getLocalClassName().equals("EditUserInfoActivity")) {
                    App.getTopActivity().finish();
                    BusFactory.getBus().post(new EventBusBean(EventBusBean.TAG_FINISH_ADDR_SELECT_PAGE));//设置地区页面关闭
                }
            }
            return Observer::onComplete;
        }).compose(XApi.getObservableScheduler()).subscribe(simpleObserver);
    }

    @Override
    public void onDestroy() {
        if (simpleObserver != null && simpleObserver.isDisposed())
            simpleObserver.dispose();
        if (uploadFileObserver != null && uploadFileObserver.isDisposed())
            uploadFileObserver.dispose();
    }
}
