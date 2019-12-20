package com.exam.fs.push.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.activity.FriendInfoActivity;
import com.exam.fs.push.activity.GroupNotFriendActivity;
import com.exam.fs.push.activity.SearchFriendDetailActivity;
import com.exam.fs.push.base.SimpleObserver;
import com.exam.fs.push.db.FriendEntry;
import com.exam.fs.push.db.FriendRecommendEntry;
import com.exam.fs.push.db.UserEntry;
import com.exam.fs.push.model.FriendInvitation;
import com.exam.fs.push.model.UserModel;
import com.exam.fs.push.model.base.SimpleModel;
import com.exam.fs.push.model.bean.Event;
import com.exam.fs.push.model.bean.EventType;
import com.exam.fs.push.utils.BitmapLoader;
import com.exam.fs.push.utils.Config;
import com.exam.fs.push.utils.DialogCreator;
import com.exam.fs.push.utils.NativeImageLoader;
import com.exam.fs.push.utils.SharePreferenceManager;
import com.exam.fs.push.utils.ViewHolder;
import com.exam.fs.push.widget.SelectableRoundedImageView;
import com.exam.fs.push.widget.SwipeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.droidlover.xdroid.net.XApi;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class FriendRecommendAdapter extends BaseAdapter {

    private Fragment mFragment;
    private Context mContext;
    private List<FriendRecommendEntry> mList;
    private LayoutInflater mInflater;
    private float mDensity;
    private int mWidth;
    private onAddFriendyListener listener;
    private SimpleObserver<SimpleModel<String>> simpleObserver;

    public FriendRecommendAdapter(Fragment fragment, List<FriendRecommendEntry> list, float density, int width) {
        mFragment = fragment;
        mContext = mFragment.getActivity();
        mList = list;
        mInflater = LayoutInflater.from(mContext);
        mDensity = density;
        mWidth = width;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_friend_recomend, new LinearLayout(mContext));
        }
        SelectableRoundedImageView headIcon = ViewHolder.get(convertView, R.id.item_head_icon);
        TextView name = ViewHolder.get(convertView, R.id.item_name);
        TextView reason = ViewHolder.get(convertView, R.id.item_reason);
        final TextView addBtn = ViewHolder.get(convertView, R.id.item_add_btn);
        final TextView state = ViewHolder.get(convertView, R.id.item_state);
        final LinearLayout itemLl = ViewHolder.get(convertView, R.id.friend_verify_item_ll);


        final SwipeLayout swp_layout = ViewHolder.get(convertView, R.id.swp_layout);
        final TextView txt_del = ViewHolder.get(convertView, R.id.txt_del);

        final FriendRecommendEntry item = mList.get(position);
        SharePreferenceManager.setItem(item.getId());
        Bitmap bitmap = NativeImageLoader.getInstance().getBitmapFromMemCache(item.username);
        if (bitmap == null) {
            String path = item.avatar;
            if (path == null || TextUtils.isEmpty(path)) {
                headIcon.setImageResource(R.drawable.icon_img_default_portrait);
            } else {
                bitmap = BitmapLoader.getBitmapFromFile(path, (int) (50 * mDensity), (int) (50 * mDensity));
                NativeImageLoader.getInstance().updateBitmapFromCache(item.username, bitmap);
                headIcon.setImageBitmap(bitmap);
            }
        } else {
            headIcon.setImageBitmap(bitmap);
        }

        name.setText(item.displayName);
        reason.setText(item.reason);
        JMessageClient.getUserInfo(item.username, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if (i == 0) {
                    if (userInfo.isFriend()) {
                        item.state = FriendInvitation.ACCEPTED.getValue();
                        item.save();
                        FriendEntry entry = FriendEntry.getFriend(App.getUserEntry(), item.username, item.appKey);
                        if (entry == null) {
                            EventBus.getDefault().post(new Event.Builder().setType(EventType.addFriend)
                                    .setFriendId(item.getId()).build());
                        }
                    }
                }
            }
        });

        if (item.state.equals(FriendInvitation.INVITED.getValue())) {
            addBtn.setVisibility(View.VISIBLE);
            state.setVisibility(View.GONE);
            addBtn.setOnClickListener(v -> {
//                final Dialog dialog = DialogCreator.createLoadingDialog(mContext, "正在加载");
                /*ContactManager.acceptInvitation(item.username, item.appKey, new BasicCallback() {//TODO 同意点击事件
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        dialog.dismiss();
                        if (responseCode == 0) {
                            item.state = FriendInvitation.ACCEPTED.getValue();
                            item.save();
                            addBtn.setVisibility(View.GONE);
                            state.setVisibility(View.VISIBLE);
                            state.setTextColor(mContext.getResources().getColor(R.color.grey_500));
                            state.setText("已添加");
                            EventBus.getDefault().post(new Event.Builder().setType(EventType.addFriend)
                                    .setFriendId(item.getId()).build());

                            //添加好友成功创建个会话
                            Conversation conversation = JMessageClient.getSingleConversation(item.username, item.appKey);
                            if (conversation == null) {
                                conversation = Conversation.createSingleConversation(item.username, item.appKey);
                                EventBus.getDefault().post(new Event.Builder()
                                        .setType(EventType.createConversation)
                                        .setConversation(conversation)
                                        .build());
                            }
                        }
                    }
                });*/
                simpleObserver = new SimpleObserver<SimpleModel<String>>(false) {
                    @Override
                    public void onNext(SimpleModel<String> model) {
                    }
                };
                assert Config.getUsers() != null;
                UserModel.gretea(Config.getUsers().username, item.username, reason.getText().toString())
                        .flatMap((Function<SimpleModel<String>, ObservableSource<SimpleModel<String>>>) model -> {
                            if (model.msg.equals("0")) {
//                                dialog.dismiss();
                                item.state = FriendInvitation.ACCEPTED.getValue();
                                item.save();
                                addBtn.setVisibility(View.GONE);
                                state.setVisibility(View.VISIBLE);
                                state.setTextColor(mContext.getResources().getColor(R.color.grey_500));
                                state.setText("已添加");
                                EventBus.getDefault().post(new Event.Builder().setType(EventType.addFriend)
                                        .setFriendId(item.getId()).build());

                                //添加好友成功创建个会话
                                Conversation conversation = JMessageClient.getSingleConversation(item.username, item.appKey);
                                if (conversation == null) {
                                    conversation = Conversation.createSingleConversation(item.username, item.appKey);
                                    EventBus.getDefault().post(new Event.Builder().setType(EventType.createConversation).setConversation(conversation).build());
                                }
                            }
                            return Observer::onComplete;
                        }).compose(XApi.getObservableScheduler()).subscribe(simpleObserver);
            });
        } else if (item.state.equals(FriendInvitation.ACCEPTED.getValue())) {
            addBtn.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.grey_500));
            state.setText(mContext.getString(R.string.added));
        } else if (item.state.equals(FriendInvitation.INVITING.getValue())) {
            addBtn.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.blue_500));
            state.setText(mContext.getString(R.string.friend_inviting));
            state.setTextColor(mContext.getResources().getColor(R.color.green_100));
        } else if (item.state.equals(FriendInvitation.BE_REFUSED.getValue())) {
            addBtn.setVisibility(View.GONE);
            reason.setTextColor(mContext.getResources().getColor(R.color.grey_500));
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.grey_500));
            state.setText(mContext.getString(R.string.decline_friend_invitation));
        } else {
            addBtn.setVisibility(View.GONE);
            state.setVisibility(View.VISIBLE);
            state.setTextColor(mContext.getResources().getColor(R.color.grey_500));
            state.setText(mContext.getString(R.string.refused));
        }


        itemLl.setOnClickListener(view -> {
            final FriendRecommendEntry entry = mList.get(position);
            final Intent intent;
            if (entry.state.equals(FriendInvitation.INVITED.getValue())) {
                //1.没同意也没拒绝时--> 是否同意界面
                intent = new Intent(mContext, SearchFriendDetailActivity.class);
                intent.putExtra("reason", item.reason);
                intent.putExtra("position", position);
                intent.putExtra(App.TARGET_ID, entry.username);
                intent.putExtra(App.TARGET_APP_KEY, entry.appKey);
                mFragment.startActivityForResult(intent, 0);
                //2.已经添加的 --> 好友详情
            } else if (entry.state.equals(FriendInvitation.ACCEPTED.getValue())) {
                JMessageClient.getUserInfo(item.username, new GetUserInfoCallback() {
                    @Override
                    public void gotResult(int i, String s, UserInfo userInfo) {
                        if (i == 0) {
                            Intent intent1 = new Intent();
                            if (userInfo.isFriend()) {
                                intent1.setClass(mContext, FriendInfoActivity.class);
                                intent1.putExtra("fromContact", true);
                            } else {
                                intent1.setClass(mContext, GroupNotFriendActivity.class);
                            }
                            intent1.putExtra(App.TARGET_ID, entry.username);
                            intent1.putExtra(App.TARGET_APP_KEY, entry.appKey);
                            mFragment.startActivityForResult(intent1, 0);
                        }
                    }
                });
                //3.自己拒绝、被对方拒绝、等待对方验证 --> 用户资料界面
            } else {
                intent = new Intent(mContext, GroupNotFriendActivity.class);
                intent.putExtra("reason", item.reason);
                intent.putExtra(App.TARGET_ID, entry.username);
                intent.putExtra(App.TARGET_APP_KEY, entry.appKey);
                mFragment.startActivityForResult(intent, 0);
            }
        });

        swp_layout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
            }

            @Override
            public void onOpen(SwipeLayout layout) {
                //侧滑删除拉出来后,点击删除,删除此条目
                txt_del.setOnClickListener(v -> {
                    final FriendRecommendEntry entry = mList.get(position);
                    FriendRecommendEntry.deleteEntry(entry);
                    mList.remove(position);
                    notifyDataSetChanged();
                });
                //侧滑删除拉出来后,点击整个条目的话,删除回退回去
                itemLl.setOnClickListener(v -> swp_layout.cancelPull());
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
            }

            @Override
            public void onClose(SwipeLayout layout) {
                /*
                 * 这里分三种情况
                 * 1.没同意也没拒绝时--> 是否同意界面
                 * 2.已经添加的 --> 好友详情
                 * 3.自己拒绝、被对方拒绝、等待对方验证 --> 用户资料界面
                 */
                itemLl.setOnClickListener(view -> {
                    FriendRecommendEntry entry = mList.get(position);
                    Intent intent;
                    if (entry.state.equals(FriendInvitation.INVITED.getValue())) {
                        //1.没同意也没拒绝时--> 是否同意界面
                        intent = new Intent(mContext, SearchFriendDetailActivity.class);
                        intent.putExtra("reason", item.reason);
                        intent.putExtra("position", position);
                        //2.已经添加的 --> 好友详情
                    } else if (entry.state.equals(FriendInvitation.ACCEPTED.getValue())) {
                        intent = new Intent(mContext, FriendInfoActivity.class);
                        intent.putExtra("fromContact", true);
                        //3.自己拒绝、被对方拒绝、等待对方验证 --> 用户资料界面
                    } else {
                        intent = new Intent(mContext, GroupNotFriendActivity.class);
                        intent.putExtra("reason", item.reason);
                    }
                    intent.putExtra(App.TARGET_ID, entry.username);
                    intent.putExtra(App.TARGET_APP_KEY, entry.appKey);
                    mFragment.startActivityForResult(intent, 0);
                });
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
            }
        });
        return convertView;
    }

    public void setListener(onAddFriendyListener listener) {
        this.listener = listener;
    }

    public interface onAddFriendyListener {
        void onAddClick(String usearname, String friendyName, String mark);
    }
}