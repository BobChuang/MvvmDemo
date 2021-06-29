package com.bob.common.threadpoollib.config;

import com.bob.common.threadpoollib.callback.AsyncListener;
import com.bob.common.threadpoollib.callback.ThreadListener;

import java.util.concurrent.Executor;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public final class ThreadConfigs {

    public String threadName;
    public long delay;
    public Executor executor;
    public ThreadListener listener;
    public AsyncListener asyncListener;
}
