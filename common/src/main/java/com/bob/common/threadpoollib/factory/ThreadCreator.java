package com.bob.common.threadpoollib.factory;

import java.util.concurrent.ThreadFactory;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public class ThreadCreator implements ThreadFactory {

    private int priority;

    public ThreadCreator(int priority) {
        this.priority = priority;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setPriority(priority);
        return thread;
    }
}
