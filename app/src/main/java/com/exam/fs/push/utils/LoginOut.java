package com.exam.fs.push.utils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.exam.fs.push.App;
import com.exam.fs.push.router.RouterTables;

public class LoginOut {
    public static void loginOut(){
        Config.setUser(null);
        App.finishAll();
        ARouter.getInstance().build(RouterTables.PAGE_ACTIVITY_LOGIN).navigation();
    }
}
