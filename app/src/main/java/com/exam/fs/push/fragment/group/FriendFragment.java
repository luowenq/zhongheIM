package com.exam.fs.push.fragment.group;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;

import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.FriendRecommendAdapter;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.databinding.FragmentFriendBinding;
import com.exam.fs.push.db.FriendRecommendEntry;
import com.exam.fs.push.db.UserEntry;
import com.exam.fs.push.model.FriendInvitation;
import com.exam.fs.push.model.base.SimpleModel;

import java.util.List;

import cn.droidlover.xdroidbase.kit.Kits;

/**
 * 好友验证
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends BaseFragment<FragmentFriendBinding> {

    private FriendRecommendAdapter mAdapter;
    private List<FriendRecommendEntry> mList;
    private SimpleObserver<SimpleModel<String>> simpleObserver;

    public FriendFragment() {
    }

    @Override
    public void initData(Bundle bundle) {
        UserEntry user = App.getUserEntry();
        if (null != user) {
            mList = user.getRecommends();
            mAdapter = new FriendRecommendAdapter(this, mList, 0, Kits.Screen.getScreenWidth(context));
            getBinding().friendRecommendListView.setAdapter(mAdapter);
        } else {
            Log.e("FriendRecommendActivity", "Unexpected error: User table null");
        }
        mAdapter.setListener(new FriendRecommendAdapter.onAddFriendyListener() {
            @Override
            public void onAddClick(String usearname, String friendyName, String mark) {

            }
        });
    }

    @Override
    protected void stop() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friend;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == App.RESULT_BUTTON) {
            assert data != null;
            int position = data.getIntExtra("position", -1);
            int btnState = data.getIntExtra("btn_state", -1);
            FriendRecommendEntry entry = mList.get(position);
            if (btnState == 2) {
                entry.state = FriendInvitation.ACCEPTED.getValue();
                entry.save();
            } else if (btnState == 1) {
                entry.state = FriendInvitation.REFUSED.getValue();
                entry.save();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }
}
