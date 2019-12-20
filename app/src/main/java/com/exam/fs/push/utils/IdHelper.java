package com.exam.fs.push.utils;

import android.content.Context;

public class IdHelper {

    public static int getLayout(Context context, String layoutName) {
        return context.getResources().getIdentifier(layoutName, "layout",
                context.getApplicationContext().getPackageName());
    }

    public static int getViewID(Context context, String IDName) {
        return context.getResources().getIdentifier(IDName, "id",
                context.getApplicationContext().getPackageName());
    }
}
