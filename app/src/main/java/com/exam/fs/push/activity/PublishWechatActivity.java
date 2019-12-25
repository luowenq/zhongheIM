package com.exam.fs.push.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.PublishWechatAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityPublishWechatBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布朋友圈
 */
@Route(path = RouterTables.PAGE_ACTIVITY_PUBLISH_WECHAT)
public class PublishWechatActivity extends BaseActivity<ActivityPublishWechatBinding> {
    private List<LocalMedia> selectList = new ArrayList<>();
    private PublishWechatAdapter adapter;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"");

        selectList = getIntent().getParcelableArrayListExtra("imgs");

        getBinding().rvImgs.setLayoutManager(new GridLayoutManager(context,3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        if (selectList.size()>0) {
            adapter = new PublishWechatAdapter(context);
            getBinding().rvImgs.setAdapter(adapter);
            adapter.setData(selectList);
            adapter.setOnAddPicClickListener(() -> {
                //打开文件选择
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofAll())
                        .selectionMode(PictureConfig.MULTIPLE)
                        .maxSelectNum(9)
                        .previewImage(true)// 是否可预览图片 true or false
                        .compress(true)
                        .selectionMedia(selectList)// 是否传入已选图片
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            });
            adapter.setOnItemClickListener((position, v) -> {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(context).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(context).externalPictureAudio(media.getPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            PictureSelector.create(context)
                                    .themeStyle(R.style.picture_default_style) // xml设置主题
                                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)// 设置相册Activity方向，不设置默认使用系统
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList);
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            selectList.clear();
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                case PictureConfig.REQUEST_CAMERA:
                    selectList.addAll(PictureSelector.obtainMultipleResult(data));
                    adapter.setData(selectList);
                    break;
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_wechat;
    }
}
