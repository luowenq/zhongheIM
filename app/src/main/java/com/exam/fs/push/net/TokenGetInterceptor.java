package com.exam.fs.push.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class TokenGetInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request newRequest = chain.request().newBuilder().build();
        Response response = chain.proceed(newRequest);
        assert response.body() != null;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        return response.newBuilder().body(ResponseBody.create(mediaType, content)).build();
    }
}