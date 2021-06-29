package com.bob.common.retrofit;

import android.support.annotation.NonNull;

import com.bob.common.base.app.BaseApplication;
import com.bob.common.config.CommonMessageToken;
import com.tendcloud.tenddata.TCAgent;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseUrlInterceptor implements Interceptor {

    private int retryCount = 3;
    private String server;
    private String newServer;
    private String packageName;
    private int appVersion;

    public BaseUrlInterceptor(String server, String newServer) {
        this.server = server;
        this.newServer = newServer;
        retryCount = 3;
    }

    public BaseUrlInterceptor(String server, String newServer, String packageName, int appVersion) {
        this.server = server;
        this.newServer = newServer;
        this.packageName = packageName;
        this.appVersion = appVersion;
        retryCount = 3;
    }

    public BaseUrlInterceptor(String firsrIP, String sERVERS, int retryCount) {
        server = firsrIP;
        newServer = sERVERS;
        this.retryCount = retryCount;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        int tryCount = 0;
        Request.Builder builder = chain.request()
                .newBuilder()
                .addHeader("packageName", packageName)
                .addHeader("appVersion", String.valueOf(appVersion));
        Request request = builder.build();
        // try the request
        Response response = doRequest(chain, request);
        String url = request.url().toString();
        while (response == null && tryCount < retryCount) {
            Request newRequest = builder.url(url).build();
//            Log.e("changeUrl", url);
//            Log.d("intercept", "Request is not successful - " + tryCount);
            tryCount++;
            TCAgent.onEvent(BaseApplication.getContext(), CommonMessageToken.TOKEN_HTTPS_COUNT, tryCount + "--0--null");
            // 重新请求
            response = doRequest(chain, newRequest);
            // 请求成功跳出循环
            if (response != null) {
                break;
            }
        }
        while (response != null && !response.isSuccessful() && tryCount < retryCount) {
            Request newRequest = builder.url(url).build();
//            Log.e("changeUrl", url);
//            Log.d("intercept", "Request is not successful - " + tryCount);
            tryCount++;
            TCAgent.onEvent(BaseApplication.getContext(), CommonMessageToken.TOKEN_HTTPS_COUNT, tryCount + "--" + response.code() + "--" + response.message());
            // 重新请求
            response = doRequest(chain, newRequest);
            // 请求成功跳出循环
            if (response != null && response.isSuccessful()) {
                break;
            }
        }
        if (tryCount >= retryCount) {
            url = switchServer(url);
//            Log.e("changeUrl", url);
            Request newRequest = builder.url(url).build();
            response = doRequest(chain, newRequest);
        }
        if (response == null) {
            throw new IOException();
        }
        return response;
    }

    private Response doRequest(Chain chain, Request request) {
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            TCAgent.onEvent(BaseApplication.getContext(), CommonMessageToken.TOKEN_HTTPS_COUNT, e.getMessage());
        }
        return response;
    }

    private String switchServer(String url) {
        String newUrlString = url;
        if (url.contains(server)) {
            if (!server.equals(newServer)) {
                newUrlString = url.replace(server, newServer);
            }
        } else {
            if (url.contains(newServer)) {
                newUrlString = url.replace(newServer, server);
            }
        }
        return newUrlString;
    }
}
