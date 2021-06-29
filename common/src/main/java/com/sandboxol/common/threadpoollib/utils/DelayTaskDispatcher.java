package com.sandboxol.common.threadpoollib.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

@SuppressWarnings("ALL")
public final class DelayTaskDispatcher {

    private ScheduledExecutorService dispatcher;

    private static DelayTaskDispatcher instance = new DelayTaskDispatcher();

    public static DelayTaskDispatcher get() {
        return instance;
    }

    private DelayTaskDispatcher() {
        dispatcher = Executors.newScheduledThreadPool(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("BM-Delay-Task-Dispatcher");
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        });
    }

    public void postDelay(long delay, final ExecutorService pool, final Runnable task) {
        if (delay == 0) {
            pool.execute(task);
            return;
        }
        dispatcher.schedule(new Runnable() {
            @Override
            public void run() {
                pool.execute(task);
            }
        }, delay, TimeUnit.MILLISECONDS);
    }
}
