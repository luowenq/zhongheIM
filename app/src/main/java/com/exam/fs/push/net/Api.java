package com.exam.fs.push.net;

import com.exam.fs.push.utils.Config;

import cn.droidlover.xdroid.net.NetProvider;
import cn.droidlover.xdroid.net.XApi;
import okhttp3.Headers;
import okhttp3.Interceptor;

public class Api {
    private static MankoService mankoService;
//    private static final String url = "http://192.168.0.130:8080/api/";
//    public static final String imageUrl = "http://192.168.0.130:8080";

    private static final String url = "http://45.13.161.218:8082/api/";
    public static final String imageUrl = "http://45.13.161.218:8082";

//    private static final String url = "http://45.13.161.218:8082/api/";
//    public static final String imageUrl = "http://45.13.161.218:8082";

    public static String getBaseUrlMankoService() {
        return url;
    }

    public static MankoService getMankoService() {
        if (mankoService == null) {
            mankoService = XApi.getInstance().getRetrofit(getBaseUrlMankoService(), new NetProvider() {

                @Override
                public Headers configCommonHeaders() {
                    String token = Config.getToken();
                    return new Headers.Builder().add("token", token).build();
                }

                @Override
                public Interceptor[] configInterceptors() {
                    return new Interceptor[]{new TokenGetInterceptor()};
                }
            }).create(MankoService.class);
        }
        return mankoService;
    }
}