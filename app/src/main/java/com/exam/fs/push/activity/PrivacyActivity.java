package com.exam.fs.push.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityPrivacyBinding;
import com.exam.fs.push.router.RouterTables;

/**
 * 隐私
 * HJQ
 */
@Route(path = RouterTables.PAGE_ACTIVITY_PRIVACY)
public class PrivacyActivity extends BaseActivity<ActivityPrivacyBinding> {
    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView,"隐私");
        getBinding().sivVerification.setOnClickListener(view -> {
            getBinding().sivVerification.getRightImageView().setSelected(
                    !getBinding().sivVerification.getRightImageView().isSelected()
            );
        });
        getBinding().sivRecommend.setOnClickListener(view -> {
            getBinding().sivRecommend.getRightImageView().setSelected(
                    !getBinding().sivRecommend.getRightImageView().isSelected()
            );
        });
        getBinding().btnProhibitSeeOther.setOnClickListener(view -> {
            getBinding().btnProhibitSeeOther.getRightImageView().setSelected(
                    !getBinding().btnProhibitSeeOther.getRightImageView().isSelected()
            );
        });
        getBinding().btnWechatRenew.setOnClickListener(view -> {
            getBinding().btnWechatRenew.getRightImageView().setSelected(
                    !getBinding().btnWechatRenew.getRightImageView().isSelected()
            );
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_privacy;
    }
}
