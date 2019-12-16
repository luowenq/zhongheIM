package com.exam.fs.push;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.droidlover.xdroidbase.XDroidBaseConf;
import cn.droidlover.xdroidbase.kit.AppUtils;
import me.shihao.library.XStatusBarHelper;

public class App extends MultiDexApplication {

    private static App mInstance;

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
    }
}