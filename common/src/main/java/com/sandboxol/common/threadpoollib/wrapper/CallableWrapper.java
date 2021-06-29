package com.sandboxol.common.threadpoollib.wrapper;

import com.sandboxol.common.threadpoollib.callback.NormalListener;
import com.sandboxol.common.threadpoollib.callback.ThreadListener;
import com.sandboxol.common.threadpoollib.config.ThreadConfigs;
import com.sandboxol.common.threadpoollib.utils.ThreadToolUtils;

import java.util.concurrent.Callable;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public final class CallableWrapper<T> implements Callable<T> {

    private String name;
    private ThreadListener listener;
    private Callable<T> proxy;

    public CallableWrapper(ThreadConfigs configs, Callable<T> proxy) {
        this.name = configs.threadName;
        this.proxy = proxy;
        this.listener = new NormalListener(configs.listener, configs.asyncListener, configs.executor);
    }

    @Override
    public T call() {
        ThreadToolUtils.configThread(Thread.currentThread(), name, listener);
        if (listener != null) {
            listener.onStart(name);
        }
        T t = null;
        try {
            t = proxy == null ? null : proxy.call();
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onError(name, e);
            }
        } finally {
            if (listener != null) {
                listener.onFinished(name);
            }
        }
        return t;
    }
}
