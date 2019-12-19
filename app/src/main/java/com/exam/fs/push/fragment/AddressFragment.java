package com.exam.fs.push.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.activeandroid.ActiveAndroid;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.adapter.StickyListAdapter;
import com.exam.fs.push.base.BaseFragment;
import com.exam.fs.push.databinding.FragmentAddressBinding;
import com.exam.fs.push.db.FriendEntry;
import com.exam.fs.push.db.FriendRecommendEntry;
import com.exam.fs.push.db.UserEntry;
import com.exam.fs.push.dialog.AddDialog;
import com.exam.fs.push.model.FriendInvitation;
import com.exam.fs.push.model.bean.Event;
import com.exam.fs.push.model.bean.EventType;
import com.exam.fs.push.router.RouterTables;
import com.exam.fs.push.utils.HanziToPinyin;
import com.exam.fs.push.utils.PinyinComparator;
import com.exam.fs.push.utils.SharePreferenceManager;
import com.exam.fs.push.widget.SideBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * 通讯录
 * A simple {@link Fragment} subclass.
 */
public class AddressFragment extends BaseFragment<FragmentAddressBinding> implements View.OnClickListener, SideBar.OnTouchingLetterChangedListener {

    private LinearLayout mVerify_ll;
    private LinearLayout mGroup_ll;
    private LinearLayout mTime_ll;
    private TextView mGroup_verification_num;
    private TextView mNewFriendNum;
    private ConstraintLayout layout;
    private StickyListAdapter mAdapter;
    private List<FriendEntry> mList = new ArrayList<>();
    private List<FriendEntry> forDelete = new ArrayList<>();

    public AddressFragment() {
    }

    public static AddressFragment newInstance() {
        AddressFragment af = new AddressFragment();
        Bundle bundle = new Bundle();
        af.setArguments(bundle);
        return af;
    }

    @Override
    public void initData(Bundle bundle) {
        initTitle(getBinding().titleView, "通讯录");
        getBinding().sidebar.setTextView(getBinding().groupDialog);
        getBinding().sidebar.bringToFront();
        View header = LayoutInflater.from(context).inflate(R.layout.view_addressd_layout_header, null);
        mVerify_ll = header.findViewById(R.id.verify_ll);
        mGroup_ll = header.findViewById(R.id.group_ll);
        mTime_ll = header.findViewById(R.id.tim_ll);
        mGroup_verification_num = header.findViewById(R.id.group_verification_num);
        mNewFriendNum = header.findViewById(R.id.friend_verification_num);
        layout = header.findViewById(R.id.layout_serson1);
        mGroup_verification_num.setVisibility(INVISIBLE);
        getBinding().listview.addHeaderView(header, null, false);
        getBinding().listview.setDrawingListUnderStickyHeader(true);
        getBinding().listview.setAreHeadersSticky(true);
        getBinding().listview.setStickyHeaderTopOffset(0);
        if (SharePreferenceManager.getCachedNewFriendNum() > 0) {
            showNewFriends(SharePreferenceManager.getCachedNewFriendNum());
        } else {
            mNewFriendNum.setVisibility(INVISIBLE);
        }
        getBinding().titleView.setRightBtnRes(R.drawable.icon_add_friendy_two, v -> {
            AddDialog dialog = new AddDialog(context);
            dialog.setOnItemsClickListener(text -> {
                switch (text) {
                    case "发起群聊":
                        break;
                    case "添加好友":
                        ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_ADD_FRIENDY).navigation();
                        break;
                    case "扫一扫!":
                        break;
                }
            });
            dialog.show();
        });
        mVerify_ll.setOnClickListener(this);
        mGroup_ll.setOnClickListener(this);
        mTime_ll.setOnClickListener(this);
        layout.setOnClickListener(this);
        getBinding().sidebar.setOnTouchingLetterChangedListener(this);
        initContacts();
    }

    //接收到好友事件
    public void onEvent(ContactNotifyEvent event) {
        final UserEntry user = App.getUserEntry();
        final String reason = event.getReason();
        final String username = event.getFromUsername();
        final String appKey = event.getfromUserAppKey();
        //对方接收了你的好友请求
        if (event.getType() == ContactNotifyEvent.Type.invite_accepted) {
            JMessageClient.getUserInfo(username, appKey, new GetUserInfoCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage, UserInfo info) {
                    if (responseCode == 0) {
                        String name = info.getNickname();
                        if (TextUtils.isEmpty(name)) {
                            name = info.getUserName();
                        }
                        FriendEntry friendEntry = FriendEntry.getFriend(user, username, appKey);
                        if (friendEntry == null) {
                            final FriendEntry newFriend = new FriendEntry(info.getUserID(), username, info.getNotename()
                                    , info.getNickname(), appKey, info.getAvatar(), name, getLetter(name), user);
                            newFriend.save();
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    refresh(newFriend);
                                }
                            });
                        }
                    }
                }
            });
            FriendRecommendEntry entry = FriendRecommendEntry.getEntry(user, username, appKey);
            entry.state = FriendInvitation.ACCEPTED.getValue();
            entry.save();

            Conversation conversation = JMessageClient.getSingleConversation(username);
            if (conversation == null) {
                conversation = Conversation.createSingleConversation(username);
                EventBus.getDefault().post(new Event.Builder()
                        .setType(EventType.createConversation)
                        .setConversation(conversation)
                        .build());
            }

            //拒绝好友请求
        } else if (event.getType() == ContactNotifyEvent.Type.invite_declined) {
            App.forAddFriend.remove(username);
            FriendRecommendEntry entry = FriendRecommendEntry.getEntry(user, username, appKey);
            entry.state = FriendInvitation.BE_REFUSED.getValue();
            entry.reason = reason;
            entry.save();
            //收到好友邀请
        } else if (event.getType() == ContactNotifyEvent.Type.invite_received) {
            //如果同一个人申请多次,则只会出现一次;当点击进验证消息界面后,同一个人再次申请则可以收到
            if (App.forAddFriend.size() > 0) {
                for (String forAdd : App.forAddFriend) {
                    if (forAdd.equals(username)) {
                        return;
                    } else {
                        App.forAddFriend.add(username);
                    }
                }
            } else {
                App.forAddFriend.add(username);
            }
            JMessageClient.getUserInfo(username, appKey, new GetUserInfoCallback() {
                @Override
                public void gotResult(int status, String desc, UserInfo userInfo) {
                    if (status == 0) {
                        String name = userInfo.getNickname();
                        if (TextUtils.isEmpty(name)) {
                            name = userInfo.getUserName();
                        }
                        FriendRecommendEntry entry = FriendRecommendEntry.getEntry(user, username, appKey);
                        if (null == entry) {
                            if (null != userInfo.getAvatar()) {
                                String path = userInfo.getAvatarFile().getPath();
                                entry = new FriendRecommendEntry(userInfo.getUserID(), username, userInfo.getNotename(), userInfo.getNickname(), appKey, path,
                                        name, reason, FriendInvitation.INVITED.getValue(), user, 0);
                            } else {
                                entry = new FriendRecommendEntry(userInfo.getUserID(), username, userInfo.getNotename(), userInfo.getNickname(), appKey, null,
                                        username, reason, FriendInvitation.INVITED.getValue(), user, 0);
                            }
                        } else {
                            entry.state = FriendInvitation.INVITED.getValue();
                            entry.reason = reason;
                        }
                        entry.save();
                        //收到好友请求数字 +1
                        int showNum = SharePreferenceManager.getCachedNewFriendNum() + 1;
                        showNewFriends(showNum);
//                        mAllContactNumber.setVisibility(View.VISIBLE);
//                        mAllContactNumber.setText(String.valueOf(showNum));
                        SharePreferenceManager.setCachedNewFriendNum(showNum);
                    }
                }
            });
        } else if (event.getType() == ContactNotifyEvent.Type.contact_deleted) {
            App.forAddFriend.remove(username);
            FriendEntry friendEntry = FriendEntry.getFriend(user, username, appKey);
            friendEntry.delete();
            refreshContact();
        }
    }

    private void refreshContact() {
        final UserEntry user = UserEntry.getUser(JMessageClient.getMyInfo().getUserName(),
                JMessageClient.getMyInfo().getAppKey());
        mList = user.getFriends();
        Collections.sort(mList, new PinyinComparator());
        mAdapter = new StickyListAdapter(context, mList, false);
        getBinding().listview.setAdapter(mAdapter);
    }

    private void refresh(FriendEntry entry) {
        mList.add(entry);
        if (null == mAdapter) {
            mAdapter = new StickyListAdapter(context, mList, false);
        } else {
            Collections.sort(mList, new PinyinComparator());
        }
        mAdapter.notifyDataSetChanged();
    }

    private String getLetter(String name) {
        String letter;
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance()
                .get(name);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        String sortString = sb.toString().substring(0, 1).toUpperCase();
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase();
        } else {
            letter = "#";
        }
        return letter;
    }

    private void showNewFriends(int num) {
        mNewFriendNum.setVisibility(VISIBLE);
        if (num > 99) {
            mNewFriendNum.setText("99+");
        } else {
            mNewFriendNum.setText(String.valueOf(num));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_address;
    }

    @Override
    protected void stop() {
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_ll:
                break;
            case R.id.group_ll:
                break;
            case R.id.tim_ll:
                break;
            case R.id.layout_serson1:
                break;
        }
    }

    private void initContacts() {
        final UserEntry user = UserEntry.getUser(JMessageClient.getMyInfo().getUserName(),
                JMessageClient.getMyInfo().getAppKey());
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage, List<UserInfo> userInfoList) {
                if (responseCode == 0) {
                    if (userInfoList != null && userInfoList.size() != 0) {
                        ActiveAndroid.beginTransaction();
                        try {
                            for (UserInfo userInfo : userInfoList) {
                                String displayName = userInfo.getDisplayName();
                                String letter;
                                if (!TextUtils.isEmpty(displayName.trim())) {
                                    ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance()
                                            .get(displayName);
                                    StringBuilder sb = new StringBuilder();
                                    if (tokens != null && tokens.size() > 0) {
                                        for (HanziToPinyin.Token token : tokens) {
                                            if (token.type == HanziToPinyin.Token.PINYIN) {
                                                sb.append(token.target);
                                            } else {
                                                sb.append(token.source);
                                            }
                                        }
                                    }
                                    String sortString = sb.toString().substring(0, 1).toUpperCase();
                                    if (sortString.matches("[A-Z]")) {
                                        letter = sortString.toUpperCase();
                                    } else {
                                        letter = "#";
                                    }
                                } else {
                                    letter = "#";
                                }
                                //避免重复请求时导致数据重复A
                                FriendEntry friend = FriendEntry.getFriend(user,
                                        userInfo.getUserName(), userInfo.getAppKey());
                                if (null == friend) {
                                    if (TextUtils.isEmpty(userInfo.getAvatar())) {
                                        friend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(), userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                                null, displayName, letter, user);
                                    } else {
                                        friend = new FriendEntry(userInfo.getUserID(), userInfo.getUserName(), userInfo.getNotename(), userInfo.getNickname(), userInfo.getAppKey(),
                                                userInfo.getAvatarFile().getAbsolutePath(), displayName, letter, user);
                                    }
                                    friend.save();
                                    mList.add(friend);
                                }
                                forDelete.add(friend);
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }
                    }
//                    else {
//                        mContactsView.showLine();
//                    }
                    //其他端删除好友后,登陆时把数据库中的也删掉
                    List<FriendEntry> friends = App.getUserEntry().getFriends();
                    friends.removeAll(forDelete);
                    for (FriendEntry del : friends) {
                        del.delete();
                        mList.remove(del);
                    }
                    Collections.sort(mList, new PinyinComparator());
                    mAdapter = new StickyListAdapter(context, mList, false);
                    getBinding().listview.setAdapter(mAdapter);
                }
            }
        });
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        if (null != mAdapter) {
            int position = mAdapter.getSectionForLetter(s);
            if (position != -1 && position < mAdapter.getCount()) {
                getBinding().listview.setSelection(position);
            }
        }
    }
}