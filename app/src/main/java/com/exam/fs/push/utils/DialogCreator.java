package com.exam.fs.push.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exam.fs.push.App;
import com.exam.fs.push.R;
import com.exam.fs.push.model.bean.Event;
import com.exam.fs.push.model.bean.EventType;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.FileContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

public class DialogCreator {

    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(IdHelper.getLayout(context, "jmui_loading_view"), null);
        RelativeLayout layout = v.findViewById(IdHelper.getViewID(context, "jmui_dialog_view"));
        ImageView mLoadImg = v.findViewById(IdHelper.getViewID(context, "jmui_loading_img"));
        TextView mLoadText = v.findViewById(IdHelper.getViewID(context, "jmui_loading_txt"));
        AnimationDrawable mDrawable = (AnimationDrawable) mLoadImg.getDrawable();
        mDrawable.start();
        mLoadText.setText(msg);
        final Dialog loadingDialog = new Dialog(context, R.style.LoadingDialog);
        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }

    public static Dialog createBusinessCardDialog(Context context, View.OnClickListener listener,
                                                  String nameTo, String name, String avatarPath) {
        Dialog dialog = new Dialog(context, R.style.jmui_default_dialog_style);
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.jmui_dialog_business_card, null);
        dialog.setContentView(view);
        TextView cardTo = view.findViewById(R.id.tv_businessCardTo);
        TextView cardName = view.findViewById(R.id.tv_businessCard);
        ImageView imageView = view.findViewById(R.id.iv_businessHead);

        cardTo.setText(nameTo);
        cardName.setText(name);
        if (avatarPath != null) {
            imageView.setImageBitmap(BitmapFactory.decodeFile(avatarPath));
        }

        final Button cancel = view.findViewById(R.id.btn_cancel);
        final Button commit = view.findViewById(R.id.btn_sure);

        cancel.setOnClickListener(listener);
        commit.setOnClickListener(listener);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static void createForwardMsg(final Context context, int mWidth, final boolean isSingle, final Conversation conv,
                                        final GroupInfo groupInfo, String groupName, final UserInfo userInfo) {
        final Dialog dialog = new Dialog(context, R.style.jmui_default_dialog_style);
        View forwardView = LayoutInflater.from(context).inflate(R.layout.jmui_dialog_forward_text_button, null);
        dialog.setContentView(forwardView);
        TextView name = forwardView.findViewById(R.id.tv_forward_name);
        TextView content = forwardView.findViewById(R.id.tv_forward_text);
        ImageView imageContent = forwardView.findViewById(R.id.iv_forward_image);
        ImageView videoContent = forwardView.findViewById(R.id.iv_forward_video);
        FrameLayout videoLayout = forwardView.findViewById(R.id.fl_forward_video);
        final Button cancel = forwardView.findViewById(R.id.btn_cancel);
        final Button commit = forwardView.findViewById(R.id.btn_send);

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        if (conv != null) {
            if (conv.getType() == ConversationType.single) {
                name.setText(((UserInfo) conv.getTargetInfo()).getDisplayName());
            } else {
                name.setText(conv.getTitle());
            }
        }
        if (groupName != null) {
            name.setText(groupName);
        }
        final Message message = App.forwardMsg.get(0);

        switch (message.getContentType()) {
            case text:
                content.setVisibility(View.VISIBLE);
                TextContent text = (TextContent) message.getContent();
                if (text.getStringExtra("businessCard") != null) {
                    content.setText("[名片]");
                } else {
                    content.setText(text.getText());
                }
                break;
            case voice:
                content.setVisibility(View.VISIBLE);
                content.setText("[语音消息]");
                break;
            case image:
                imageContent.setVisibility(View.VISIBLE);
                ImageContent image = (ImageContent) message.getContent();
                String imagePath = image.getLocalThumbnailPath();
                imageContent.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                break;
            case file:
                FileContent fileVideo = (FileContent) message.getContent();
                String videoExtra = fileVideo.getStringExtra("video");
                content.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(videoExtra)) {
                    content.setText("[小视频]");
                } else {
                    content.setText("[文件]" + fileVideo.getFileName());
                }
                break;
            case location:
                LocationContent locationContent = (LocationContent) message.getContent();
                content.setVisibility(View.VISIBLE);
                content.setText("[位置]" + locationContent.getAddress());
                break;
            default:
                break;
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mLoadingDialog = DialogCreator.createLoadingDialog(context, "发送中");
//                mLoadingDialog.show();
                String userName = null;
                String appKey = null;

                if (userInfo != null) {
                    userName = userInfo.getUserName();
                    appKey = userInfo.getAppKey();
                }
                Conversation conversation = null;
                if (userInfo == null && groupInfo == null) {
                    conversation = conv;
                } else {
                    if (isSingle) {
                        conversation = JMessageClient.getSingleConversation(userName, appKey);
                        if (conversation == null) {
                            conversation = Conversation.createSingleConversation(userName, appKey);
                            EventBus.getDefault().post(new Event.Builder()
                                    .setType(EventType.createConversation)
                                    .setConversation(conversation)
                                    .build());
                        }
                    } else {
                        conversation = JMessageClient.getGroupConversation(groupInfo.getGroupID());
                        if (conversation == null) {
                            conversation = Conversation.createGroupConversation(groupInfo.getGroupID());
                            EventBus.getDefault().post(new Event.Builder()
                                    .setType(EventType.createConversation)
                                    .setConversation(conversation)
                                    .build());
                        }
                    }
                }
                MessageSendingOptions options = new MessageSendingOptions();
                options.setNeedReadReceipt(true);
                JMessageClient.forwardMessage(message, conversation, options, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {
//                        mLoadingDialog.dismiss();
                        dialog.dismiss();
                        if (i == 0) {
                            Toast.makeText(context, "已发送", Toast.LENGTH_SHORT).show();
                            SharePreferenceManager.setIsOpen(true);
//                            ActivityController.finishAll();结束finish
                        } else {
//                            HandleResponseCode.onHandle(context, i, false);
                        }
                    }
                });
            }
        });
    }
}