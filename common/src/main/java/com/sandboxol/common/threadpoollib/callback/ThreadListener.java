package com.sandboxol.common.threadpoollib.callback;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public interface ThreadListener {

    void onError(String threadName, Throwable throwable);

    void onFinished(String threadName);

    void onStart(String threadName);
}
