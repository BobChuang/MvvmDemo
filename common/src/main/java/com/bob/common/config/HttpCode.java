package com.bob.common.config;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public interface HttpCode {

    int SUCCESS = 1;
    int FAILED = 2;
    int PARAM_ERROR = 3;
    int INNER_ERROR = 4;
    int TIME_OUT = 5;
    int AUTH_FAILED = 401;//认证错误，eg.重复登录
    int BE_REPORT = 403;//被封号，没有权限访问
    int NOT_FOUND = 404;
    int NET_BUSY = 429;//服务器繁忙
    int SERVER_ERROR = 500;//服务器抛出异常
    int NET_CANT_USE = 503;//服务器繁忙
    int NET_ERROR = 504;//网络连接错误

    int NOT_NET_ERROR = -1;//非网络错误，主要针对RuntimeException和IOException


    int NO_CONNECTED = 10000;//网络连接
    int TIMEOUT = 10001;
    int UN_KNOW = 10002;

}
