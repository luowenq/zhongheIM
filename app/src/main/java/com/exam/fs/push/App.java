package com.exam.fs.push;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.activeandroid.ActiveAndroid;
import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.db.UserEntry;

import cn.droidlover.xdroidbase.XDroidBaseConf;
import cn.droidlover.xdroidbase.kit.AppUtils;
import cn.jpush.im.android.api.JMessageClient;
import me.shihao.library.XStatusBarHelper;

public class App extends MultiDexApplication {

    private static App mInstance;
    public static final String AppKey = "b4cfaca36af4be1298f2334e";
    public static final String TARGET_ID = "targetId";
    public static final String GROUP_ID = "groupId";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String CONV_TITLE = "conv_title";

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
    }

    public static UserEntry getUserEntry() {
        return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();

    }
}