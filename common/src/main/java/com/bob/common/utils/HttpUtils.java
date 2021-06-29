package com.bob.common.utils;

import android.content.Context;
import android.util.Log;

import com.bob.common.R;
import com.bob.common.base.web.OnResponseListener;
import com.bob.common.command.ReplyCommand;
import com.bob.common.config.HttpCode;

import retrofit2.HttpException;
import retrofit2.Response;
import rx.exceptions.Exceptions;
import rx.functions.Action0;

/**
 * Created by Jimmy on 2017/11/17 0017.
 */
public class HttpUtils {

    public static ReplyCommand getRetryCommand(OnResponseListener listener, Action0 action) {
        ReplyCommand command = null;
        if (listener.needRetry()) {
            command = new ReplyCommand(action);
        }
        return command;
    }

    public static String getHttpErrorMsg(Context context, int error) {
        switch (error) {
            case HttpCode.NO_CONNECTED:
            case HttpCode.NOT_FOUND:
                return context.getResources().getString(R.string.connect_server_no_connect);
            case HttpCode.TIMEOUT:
                return context.getResources().getString(R.string.connect_server_time_out);
            case HttpCode.UN_KNOW:
                return context.getResources().getString(R.string.connect_server_connect_failed);
            case HttpCode.AUTH_FAILED:
                return context.getResources().getString(R.string.connect_repeat_login);
            case HttpCode.BE_REPORT:
                return context.getResources().getString(R.string.user_be_report);
            case HttpCode.INNER_ERROR:
                return context.getResources().getString(R.string.inner_error);
            case HttpCode.TIME_OUT:
                return context.getResources().getString(R.string.time_out);
            case HttpCode.NET_ERROR:
                return context.getResources().getString(R.string.network_connection_failed);
            case HttpCode.NET_BUSY:
                return context.getResources().getString(R.string.service_busy);
            case HttpCode.NET_CANT_USE:
                return context.getResources().getString(R.string.service_cant_use);
            case HttpCode.NOT_NET_ERROR:
                return context.getResources().getString(R.string.error_tip_runtime);
            default:
                return context.getResources().getString(R.string.connect_error_code, error);
        }
    }

    public static void throwException(int code) {
        switch (code) {
            case 2:
                try {
                    Log.e("web.MiniGameDispatch", "server is busy. code = " + code);
                    throw new Exception("get dispatch failed");
                } catch (Throwable e) {
                    throw Exceptions.propagate(e);
                }
            case HttpCode.NET_CANT_USE:
                throw new HttpException(Response.error(HttpCode.NET_CANT_USE, null));
            case HttpCode.NET_BUSY:
                throw new HttpException(Response.error(HttpCode.NET_BUSY, null));
        }
    }

}
