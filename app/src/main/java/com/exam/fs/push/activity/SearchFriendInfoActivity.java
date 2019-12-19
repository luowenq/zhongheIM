package com.exam.fs.push.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySearchFriendInfoBinding;
import com.exam.fs.push.model.InfoModel;
import com.exam.fs.push.router.RouterTables;

/**
 * 添加好友界面
 */
@Route(path = RouterTables.PAGE_ACTIVITY_SEARCH_FRIEND_INFO)
public class SearchFriendInfoActivity extends BaseActivity<ActivitySearchFriendInfoBinding> {

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "");
        initData();
        getBinding().tvAddCon.setOnClickListener(v -> ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_VARLY_FRIENDY).navigation());
    }

    private void initData() {
        InfoModel instance = InfoModel.getInstance();
        if (instance.getAvatar() == null) {
            getBinding().ivUserHeader.setImageResource(R.drawable.icon_img_default_portrait);
        } else {
            getBinding().ivUserHeader.setImageBitmap(instance.getAvatar());
        }
        if (TextUtils.isEmpty(instance.getNickName())) {
            getBinding().tvAccount.setText(instance.getUserName());
        } else {
            getBinding().tvAccount.setText("昵称:" + instance.getNickName());
        }
        getBinding().tvSign.setText("个性签名: " + instance.getSign());
        getBinding().tvArea.setText("地区: " + instance.getCity());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_friend_info;
    }
}