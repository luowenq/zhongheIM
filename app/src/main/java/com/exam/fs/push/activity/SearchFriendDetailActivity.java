package com.exam.fs.push.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivitySearchFriendDetailBinding;
import com.exam.fs.push.model.bean.Event;
import com.exam.fs.push.model.bean.EventType;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.NativeImageLoader;
import com.exam.fs.push.utils.SharePreferenceManager;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

@Route(path = RouterTables.PAGE_ACTIVITY_FRIEND_DETAIL)
public class SearchFriendDetailActivity extends BaseActivity<ActivitySearchFriendDetailBinding> {
    private String mUsername;
    private String mAppKey;
    private UserInfo mToUserInfo;
    private String mDisplayName;
    private String mAvatarPath;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "");
        initModel();
    }

    private void initModel() {
        Intent intent = getIntent();
        mUsername = intent.getStringExtra(App.TARGET_ID);
        mAppKey = intent.getStringExtra(App.TARGET_APP_KEY);

        JMessageClient.getUserInfo(mUsername, mAppKey, new GetUserInfoCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, UserInfo info) {
                if (responseCode == 0) {
                    mToUserInfo = info;
                    Bitmap bitmap = NativeImageLoader.getInstance().getBitmapFromMemCache(mUsername);
                    if (bitmap != null) {
                        getBinding().ivUserHeader.setImageBitmap(bitmap);
                    } else if (!TextUtils.isEmpty(info.getAvatar())) {
                        mAvatarPath = info.getAvatarFile().getPath();
                        info.getAvatarBitmap(new GetAvatarBitmapCallback() {
                            @Override
                            public void gotResult(int responseCode, String responseMessage, Bitmap avatarBitmap) {
                                if (responseCode == 0) {
                                    getBinding().ivUserHeader.setImageBitmap(avatarBitmap);
                                    NativeImageLoader.getInstance().updateBitmapFromCache(mUsername, avatarBitmap);
                                } else {
                                    getBinding().ivUserHeader.setImageResource(R.drawable.icon_img_default_portrait);
                                }
                            }
                        });
                    } else {
                        getBinding().ivUserHeader.setImageResource(R.drawable.icon_img_default_portrait);
                    }
                    mDisplayName = info.getNickname();
                    if (TextUtils.isEmpty(mDisplayName)) {
                        mDisplayName = info.getUserName();
                    }
                    getBinding().tvAccount.setText(mDisplayName);
                    /*if (info.getGender() == UserInfo.Gender.male) {
                        mTv_gender.setText("男");
                    } else if (info.getGender() == UserInfo.Gender.female) {
                        mTv_gender.setText("女");
                    }*/
                    getBinding().tvCome.setText("来源: " + intent.getStringExtra("reason"));
                    getBinding().tvSign.setText(info.getSignature());
//                    mUserName.setText(mUsername);
                    /*long birthday = info.getBirthday();
                    if (birthday == 0) {
                        mTv_birthday.setText("");
                    } else {
                        Date date = new Date(birthday);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        mTv_birthday.setText(dateFormat.format(date));
                    }*/
                    getBinding().tvArea.setText(info.getRegion());
                }
            }
        });
        final int position = intent.getIntExtra("position", -1);
        View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.tv_pass:
                    ContactManager.declineInvitation(mToUserInfo.getUserName(), mToUserInfo.getAppKey(), "拒绝了您的好友请求", new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                //拒绝时候要修改button数据库状态,并更新内存
                                Intent btnIntent = new Intent();
                                btnIntent.putExtra("position", position);
                                btnIntent.putExtra("btn_state", 1);
                                setResult(App.RESULT_BUTTON, btnIntent);
                                finish();
                            }
                        }
                    });
                    break;
                case R.id.tv_jujue:
                    //同意添加
                    ContactManager.acceptInvitation(mToUserInfo.getUserName(), mToUserInfo.getAppKey(), new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                Intent btnIntent2 = new Intent();
                                btnIntent2.putExtra("position", position);
                                btnIntent2.putExtra("btn_state", 2);
                                setResult(App.RESULT_BUTTON, btnIntent2);
                                EventBus.getDefault().post(new Event.Builder().setType(EventType.addFriend)
                                        .setFriendId(SharePreferenceManager.getItem()).build());
                                finish();
                            }
                        }
                    });
                    break;
            }
        };
        getBinding().tvPass.setOnClickListener(listener);
        getBinding().tvJujue.setOnClickListener(listener);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_friend_detail;
    }
}