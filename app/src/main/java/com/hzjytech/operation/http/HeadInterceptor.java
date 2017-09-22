package com.hzjytech.operation.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hehongcan on 2017/8/23.
 */

class HeadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .header("content-type", "")
                .build();
        return chain.proceed(request);
    }
}
