package com.bob.common.threadpoollib.callback;

import java.util.concurrent.Executor;

/**
 * Description:
 * 这个监听器用于将 线程监听、异步监听 的调用放到主线程操作
 *
 * @author GZDong
 * @date 2020/1/4
 */

public final class NormalListener implements ThreadListener, AsyncListener {

    private ThreadListener listener;
    private AsyncListener asyncListener;
    private Executor mainExecutor; //主线程池、UI线程

    public NormalListener(ThreadListener listener, AsyncListener asyncListener, Executor executor) {
        this.listener = listener;
        this.asyncListener = asyncListener;
        this.mainExecutor = executor;
    }

    @Override
    public void onSuccess(Object o) {
        if (asyncListener == null) {
            return;
        }
        mainExecutor.execute(() -> {
            try {
                asyncListener.onSuccess(o);
            } catch (Throwable t) {
                onFailed(t);
            }
        });
    }

    @Override
    public void onFailed(Throwable t) {
        if (asyncListener == null) {
            return;
        }
        mainExecutor.execute(() -> asyncListener.onFailed(t));
    }

    @Override
    public void onError(String threadName, Throwable throwable) {
        onFailed(throwable);
        if (listener == null) {
            return;
        }
        mainExecutor.execute(() -> listener.onError(threadName, throwable));
    }

    @Override
    public void onFinished(String threadName) {
        if (listener == null) {
            return;
        }
        mainExecutor.execute(() -> listener.onFinished(threadName));
    }

    @Override
    public void onStart(String threadName) {
        if (listener == null) {
            return;
        }
        mainExecutor.execute(() -> listener.onStart(threadName));
    }
}
