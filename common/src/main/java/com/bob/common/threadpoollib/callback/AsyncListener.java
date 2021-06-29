package com.bob.common.threadpoollib.callback;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public interface AsyncListener<T> {

    void onSuccess(T t);

    void onFailed(Throwable t);

    void onStart(String threadName);
}
