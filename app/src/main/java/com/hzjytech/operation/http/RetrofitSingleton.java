package com.hzjytech.operation.http;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hzjytech.operation.BuildConfig;
import com.hzjytech.operation.http.api.ApiInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static ApiInterface apiService = null;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;
    private static final String TAG = RetrofitSingleton.class.getSimpleName();


    /**
     * 初始化zzz
     */

    public static void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // SSLSocketFactory sslSocketFactory = getSSLSocketFactory(MyApplication.getInstance(), R.raw.XXX);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 向Request Header添加一些业务相关数据，如APP版本，token
                //.addInterceptor(new HeadInterceptor())
                //日志Interceptor，可以打印日志
               .addInterceptor(interceptor)
                // 连接超时时间设置
                .connectTimeout(10, TimeUnit.SECONDS)
                // 读取超时时间设置
                .readTimeout(60, TimeUnit.SECONDS)
                // 失败重试
                .retryOnConnectionFailure(true);
                // 支持Https需要加入SSLSocketFactory
            //    .sslSocketFactory(sslSocketFactory)
           //   .sslSocketFactory(sslSocketFactory, x509TrustManager)
                // 信任的主机名，返回true表示信任，可以根据主机名和SSLSession判断主机是否信任
//                .hostnameVerifier(new HostnameVerifier() {
//                    @Override
//                    public boolean verify(String hostname, SSLSession session) {
//                        return true;
//                    }
//                })
//                // 使用host name作为cookie保存的key
//                .cookieJar(new CookieJar() {
//                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
//
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                        cookieStore.put(HttpUrl.parse(url.host()), cookies);
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
//                        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(url.host()));
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
//                    }
//                })
          if(BuildConfig.DEBUG){
              builder.addInterceptor(interceptor);
          }
        OkHttpClient client = builder.build();


        Gson gson = new GsonBuilder().create();

        retrofit = new Retrofit.Builder()
                //.addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(NetConstants.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        apiService = retrofit.create(ApiInterface.class);
    }


    public static ApiInterface getApiService() {
        if (apiService != null) return apiService;
        init();
        return getApiService();
    }


    public static Retrofit getRetrofit() {
        if (retrofit != null) return retrofit;
        init();
        return getRetrofit();
    }


    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient != null) return okHttpClient;
        init();
        return getOkHttpClient();
    }


}
