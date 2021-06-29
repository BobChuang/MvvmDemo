package com.bob.common.utils;

import com.bob.common.base.web.HttpResponse;
import com.bob.common.config.HttpCode;

/**
 * Created by Jimmy on 2017/9/29 0029.
 */
public class ResponseUtils {

    public static <D> HttpResponse<D> success(D data) {
        return create(HttpCode.SUCCESS, null, data);
    }

    public static <D> HttpResponse<D> failed(String msg) {
        return create(HttpCode.FAILED, msg, null);
    }

    public static <D> HttpResponse<D> create(int code, String msg, D data) {
        HttpResponse<D> response = new HttpResponse<>();
        response.setCode(code);
        response.setMessage(msg);
        response.setData(data);
        return response;
    }

}
