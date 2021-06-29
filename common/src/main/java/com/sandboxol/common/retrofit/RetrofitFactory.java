package com.sandboxol.common.retrofit;

import android.content.Context;

import com.sandboxol.common.BuildConfig;
import com.sandboxol.common.base.app.BaseApplication;
import com.sandboxol.common.utils.CommonHelper;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class RetrofitFactory {

    public static <T> T create(String baseUrl, Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        //支持所以https
                        .sslSocketFactory(getSSLSocketFactory(),new CustomTrustManager())
                        .hostnameVerifier(getHostnameVerifier())
                        .addInterceptor(getBaseUrlInterceptor(baseUrl, baseUrl))
                        .build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(clazz);
    }

    public static <T> T create(String baseUrl, Class<T> clazz, long timeout) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        //支持所以https
                        .sslSocketFactory(getSSLSocketFactory(),new CustomTrustManager())
                        .hostnameVerifier(getHostnameVerifier())
                        .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                        .readTimeout(timeout, TimeUnit.MILLISECONDS)
                        .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                        .build())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(clazz);
    }

    /**
     * HTTPS默认重试3次
     *
     * @param baseUrl
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T httpsCreate(String baseUrl, Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        //支持所以https
                        .sslSocketFactory(getSSLSocketFactory(),new CustomTrustManager())
                        .hostnameVerifier(getHostnameVerifier())
                        .addInterceptor(getBaseUrlInterceptor(baseUrl, ""))
                        .retryOnConnectionFailure(true)//允许失败重试
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .build())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(clazz);
    }

    public static <T> T cacheCreate(String baseUrl, Class<T> clazz, long cacheTime) {
        try {
            File file = new File(CommonHelper.getDiskCacheDir(BaseApplication.getContext()), "BlockyModsCache");
            if (file != null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(new OkHttpClient.Builder()
                                //支持所以https
                                .sslSocketFactory(getSSLSocketFactory(),new CustomTrustManager())
                                .hostnameVerifier(getHostnameVerifier())
                                .addNetworkInterceptor(chain -> {//网络缓存
                                    Request request = chain.request();
                                    Response response = chain.proceed(request);
                                    return response.newBuilder()
                                            .removeHeader("Pragma")
                                            .removeHeader("Cache-Control")
                                            .header("Cache-Control", "max-age=" + cacheTime)
                                            .build();
                                })
                                .addInterceptor(chain -> {//无网状态缓存
                                    Request request = chain.request();
                                    if (!CommonHelper.isNetworkConnected(BaseApplication.getContext())) {
                                        request = request.newBuilder()
                                                .header("Cache-Control", "public, only-if-cached, max-stale=" + cacheTime)
                                                .cacheControl(CacheControl.FORCE_CACHE)
                                                .build();
                                    }
                                    return chain.proceed(request);
                                })
                                .cache(new Cache(file, 1024 * 1024 * 30))
                                .build())
                        .baseUrl(baseUrl)
                        .build();
                return retrofit.create(clazz);
            } else {
                return create(baseUrl, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return create(baseUrl, clazz);
        }
    }

    public static BaseUrlInterceptor getBaseUrlInterceptor(String baseUrl, String switchUrl) {
        try {
            String packageName = "unknown";
            Context context = BaseApplication.getContext();
            if (context != null) {
                packageName = context.getPackageName();
            }
            String appName = packageName.substring(packageName.lastIndexOf(".") + 1);
            return new BaseUrlInterceptor(baseUrl, switchUrl, appName, BuildConfig.VERSION_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseUrlInterceptor(baseUrl, switchUrl);
        }
    }

    public static SSLSocketFactory getSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new CustomTrustManager()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier   hostnameVerifier= new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return hostnameVerifier;
    }
}
