package com.exam.fs.push;

import android.content.Context;

import androidx.multidex.MultiDex;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.db.UserEntry;
import com.exam.fs.push.model.bean.NotificationClickEventReceiver;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidbase.XDroidBaseConf;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import me.shihao.library.XStatusBarHelper;

public class App extends MultiDexApplication {

    private static App mInstance;
    public static final String AppKey = "b4cfaca36af4be1298f2334e";
    public static final String TARGET_ID = "targetId";
    public static final String GROUP_ID = "groupId";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String CONV_TITLE = "conv_title";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    public static final int RESULT_BUTTON = 2;
    public static List<String> forAddFriend = new ArrayList<>();
    private static Activity activityTop = null;
    private static List<Activity> activities = new ArrayList<>();
    public static List<UserInfo> mFriendInfoList = new ArrayList<>();
    public static List<Message> forwardMsg = new ArrayList<>();
    public static List<String> forAddIntoGroup = new ArrayList<>();

    public static App getInstance() {
        if (mInstance == null) {
            synchronized (App.class) {
                if (mInstance == null) {
                    mInstance = new App();
                }
            }
        }
        return mInstance;
    }

    public static Activity getTopActivity(){
        return activityTop;
    }

    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        //初始化状态栏的颜色
        XStatusBarHelper.setDefaultAlpha(0);
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();//开启调试模式,上线后切换为调式
        }
        XDroidBaseConf.getInstance().setLog(BuildConfig.DEBUG).build();
        ARouter.init(this);
        JMessageClient.setDebugMode(true);//上线后关闭Debug模式
        JMessageClient.init(this, true);
        ActiveAndroid.initialize(this);
        //设置Notification的模式
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                activities.add(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                activityTop = activity;
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                activities.remove(activity);
            }
        });
    }

    public static UserEntry getUserEntry() {
        return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}