package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityGroupNoticeBinding;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.LoadImage;
import com.exam.fs.push.viewmodel.GroupNoticeViewModel;

/**
 * 群公告
 */
@Route(path = RouterTables.PAGE_ACTIVITY_GROUP_NOTICE)
public class GroupNoticieActivity extends BaseActivity<ActivityGroupNoticeBinding> {
    private GroupNoticeViewModel viewModel;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"群公告");

        LoadImage.loadHeadImage(getBinding().imgPhoto,"https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1806047429,2669047046&fm=26&gp=0.jpg");

        viewModel = new GroupNoticeViewModel();
        getBinding().setViewModel(viewModel);

        getBinding().tvContent.setOnClickListener(v -> {
            ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_EDIT_GROUP_NOTICE).withString("content",viewModel.content.get()).navigation();
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group_notice;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }
}
