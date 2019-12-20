package com.exam.fs.push.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityEditUserinfoBinding;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.net.Api;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.utils.GlideEngine;
import com.exam.fs.push.utils.LoadImage;
import com.exam.fs.push.viewmodel.EditUserInfoViewModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import java.util.List;


/**
 * 编辑或修改个人信息
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_EDIT_USERINFO)
public class EditUserInfoActivity extends BaseActivity<ActivityEditUserinfoBinding> {
    private EditUserInfoViewModel viewModel;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"修改个人信息");
        if(Config.getUsers() != null) {
            LoadImage.loadHeadImage(getBinding().imgPhoto, Api.imageUrl + Config.getUsers().headIcon);
        }

        getBinding().btnNickname.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_UPDATE_NICKNAME).navigation();
        });
        getBinding().btnSex.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SEX_SET).navigation();
        });
        getBinding().btnAddr.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SET_ADDR).navigation();
        });
        getBinding().btnSign.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_SIGN_SET).navigation();
        });
        getBinding().imgPhoto.setOnClickListener(v -> {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .selectionMode(PictureConfig.SINGLE)
                    .previewImage(true)// 是否可预览图片 true or false
                    .compress(true)
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });

        viewModel = new EditUserInfoViewModel();
        getBinding().setViewModel(viewModel);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserModel userModel = Config.getUsers();
        if(userModel != null) {
            getBinding().btnNickname.setRightText(userModel.nickname);
            getBinding().btnSex.setRightText(TextUtils.isEmpty(userModel.sex) ? "未设置" : (userModel.sex.equals("1") ? "男" : "女"));
            String addr = (TextUtils.isEmpty(userModel.province) ? "" : userModel.province) + (TextUtils.isEmpty(userModel.city) ? "" : userModel.city);
            getBinding().btnAddr.setRightText(TextUtils.isEmpty(addr) ? "未设置" : userModel.province + userModel.city);
            getBinding().btnSign.setRightText(TextUtils.isEmpty(userModel.sign) ? "未设置" : userModel.sign);
            getBinding().btnAccountNumber.setRightText(userModel.username);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    String loadPic;
                    if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        loadPic = selectList.get(0).getAndroidQToPath();
                    }else {
                        loadPic = TextUtils.isEmpty(selectList.get(0).getCompressPath()) ? selectList.get(0).getPath():selectList.get(0).getCompressPath();
                    }
                    LoadImage.loadHeadImage(getBinding().imgPhoto, loadPic);
                    //上传图片
                    viewModel.uploadImage(loadPic);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_userinfo;
    }
}
