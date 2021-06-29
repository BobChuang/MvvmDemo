package com.bob.common.threadpoollib.wrapper;

import com.bob.common.threadpoollib.callback.NormalListener;
import com.bob.common.threadpoollib.config.ThreadConfigs;
import com.bob.common.threadpoollib.utils.ThreadToolUtils;

import java.util.concurrent.Callable;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public class RunnableWrapper implements Runnable{

    private String name;
    private NormalListener normalListener;
    private Runnable runnable;
    private Callable callable;

    public RunnableWrapper(ThreadConfigs configs) {
        this.name = configs.threadName;
        this.normalListener = new NormalListener(configs.listener, configs.asyncListener, configs.executor);
    }

    public RunnableWrapper setRunnable(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }

    public RunnableWrapper setCallable(Callable callable) {
        this.callable = callable;
        return this;
    }

    @Override
    public void run() {
        Thread current = Thread.currentThread();
        ThreadToolUtils.configThread(current, name, normalListener);
        normalListener.onStart(name);

        if (runnable != null) {
            runnable.run();
        } else if ((callable != null)) {
            try {
                Object result = callable.call();
                normalListener.onSuccess(result);
            } catch (Exception e) {
                normalListener.onError(name, e);
            }
        }
        normalListener.onFinished(name);
    }
}
