package com.exam.fs.push.activity;

import android.content.Context;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.GroupListAdapter;
import com.exam.fs.push.base.BaseActivity;
import com.exam.fs.push.databinding.ActivityGroupBinding;
import com.exam.fs.push.router.RouterTables;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidbase.kit.Kits;
import cn.droidlover.xdroidbase.kit.ToastManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.GroupInfo;

/**
 * 群聊
 */
@Route(path = RouterTables.PAGE_ACTIVITY_GROUP)
public class GroupActivity extends BaseActivity<ActivityGroupBinding> {

    private Context mContext;
    private boolean isFromForward = false;
    private boolean isBusinessCard = false;
    private String mUserName;
    private String mAppKey;
    private String mAvatarPath;
    private GroupListAdapter mGroupListAdapter;

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "群聊");
        //待发送名片的参数
        mUserName = getIntent().getStringExtra("userName");
        mAppKey = getIntent().getStringExtra("appKey");
        mAvatarPath = getIntent().getStringExtra("avatar");
        initData();
    }

    private void initData() {
        final List<GroupInfo> infoList = new ArrayList<>();
        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, final List<Long> groupIDList) {
                if (responseCode == 0) {
                    final int[] groupSize = {groupIDList.size()};
                    if (groupIDList.size() > 0) {
                        for (Long id : groupIDList) {
                            JMessageClient.getGroupInfo(id, new GetGroupInfoCallback() {
                                @Override
                                public void gotResult(int i, String s, GroupInfo groupInfo) {
                                    if (i == 0) {
                                        groupSize[0] = groupSize[0] - 1;
                                        infoList.add(groupInfo);
                                        if (groupSize[0] == 0) {
                                            setAdapter(infoList);
                                        }

                                    }
                                }
                            });
                        }
                    } else {
                        ToastManager.showShort(context, "您还没有群组");
                    }
                } else {
//                    HandleResponseCode.onHandle(mContext, responseCode, false);
                }
            }
        });
    }

    public void setAdapter(List<GroupInfo> infoList) {
        //来自转发时flag是1
        if (getIntent().getFlags() == 1) {
            isFromForward = true;
        }
        //来自名片的请求设置flag==2
        if (getIntent().getFlags() == 2) {
            isBusinessCard = true;
        }
        mGroupListAdapter = new GroupListAdapter(mContext, infoList, isFromForward, Kits.Screen.getScreenWidth(context),
                isBusinessCard, mUserName, mAppKey, mAvatarPath);
        getBinding().groupList.setAdapter(mGroupListAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_group;
    }
}
