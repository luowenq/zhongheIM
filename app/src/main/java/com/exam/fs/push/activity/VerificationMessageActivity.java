package com.exam.fs.push.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.ViewPagerAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityVerificationMessageBinding;
import com.exam.fs.push.fragment.group.FriendFragment;
import com.exam.fs.push.fragment.group.GroupFragment;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 新朋友
 */
@Route(path = RouterTables.PAGE_ACTIVITY_VERIFICATION_MESSAGE)
public class VerificationMessageActivity extends BaseActivity<ActivityVerificationMessageBinding> {

    private int mCurTabIndex;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "群聊");
        List<Fragment> mFragmentList = new ArrayList<>();
        mFragmentList.add(new FriendFragment());
        mFragmentList.add(new GroupFragment());
        getBinding().rgVerification.check(R.id.rb_friend);
        getBinding().verificationViewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
        getBinding().rgVerification.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_friend:
                    mCurTabIndex = 0;
                    break;
                case R.id.rb_group:
                    mCurTabIndex = 1;
                    break;
                default:
                    break;
            }
            getBinding().verificationViewpager.setCurrentItem(mCurTabIndex, false);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_verification_message;
    }
}