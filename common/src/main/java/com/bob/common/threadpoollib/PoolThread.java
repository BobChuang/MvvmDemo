package com.bob.common.threadpoollib;

import android.text.TextUtils;

import com.bob.common.threadpoollib.callback.AsyncListener;
import com.bob.common.threadpoollib.callback.ThreadListener;
import com.bob.common.threadpoollib.config.ThreadConfigs;
import com.bob.common.threadpoollib.executor.AndroidExecutor;
import com.bob.common.threadpoollib.factory.ThreadCreator;
import com.bob.common.threadpoollib.utils.DelayTaskDispatcher;
import com.bob.common.threadpoollib.wrapper.CallableWrapper;
import com.bob.common.threadpoollib.wrapper.RunnableWrapper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public class PoolThread implements Executor {

    private ExecutorService pool;

    private String defaultName;

    private Executor defaultExecutor;

    private ThreadListener defaultListener;

    private ThreadLocal<ThreadConfigs> local;

    private PoolThread(int type, int size, int priority, String name, ThreadListener listener, Executor executor, ExecutorService pool) {
        if (pool == null) {
            pool = createPool(type, size, priority);
        }
        this.pool = pool;
        this.defaultName = name;
        this.defaultExecutor = executor;
        this.defaultListener = listener;
        this.local = new ThreadLocal<>();
    }

    @Override
    public void execute(Runnable runnable) {
        ThreadConfigs configs = getLocalConfigs();
        runnable = new RunnableWrapper(configs).setRunnable(runnable);
        DelayTaskDispatcher.get().postDelay(configs.delay, pool, runnable);
        resetLocalConfigs();
    }

    public <T> void async(@NonNull Callable<T> callable, AsyncListener<T> listener) {
        ThreadConfigs configs = getLocalConfigs();
        configs.asyncListener = listener;
        Runnable runnable = new RunnableWrapper(configs).setCallable(callable);
        DelayTaskDispatcher.get().postDelay(configs.delay, pool, runnable);
        resetLocalConfigs();
    }

    public <T> Future<T> submit(Callable<T> callable) {
        Future<T> result;
        callable = new CallableWrapper<>(getLocalConfigs(), callable);
        result = pool.submit(callable);
        resetLocalConfigs();
        return result;
    }

    public PoolThread setName(String name) {
        getLocalConfigs().threadName = name;
        return this;
    }

    public PoolThread setListener(ThreadListener listener) {
        getLocalConfigs().listener = listener;
        return this;
    }

    public PoolThread setDelay(long time, TimeUnit unit) {
        long delay = unit.toMillis(time);
        getLocalConfigs().delay = Math.max(0, delay);
        return this;
    }

    public PoolThread setDeliver(Executor deliver) {
        getLocalConfigs().executor = deliver;
        return this;
    }

    public void stop() {
        try {
            pool.shutdown();
            if (!pool.awaitTermination(0, TimeUnit.MILLISECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdownNow();
        }
    }

    public ExecutorService getExecutor() {
        return pool;
    }

    public void close() {
        if (local != null) {
            local.remove();
            local = null;
        }
    }

    private synchronized ExecutorService createPool(int type, int size, int priority) {
        switch (type) {
            case Builder.TYPE_CACHE:
                //适合执行大量耗时小的任务
                return Executors.newCachedThreadPool(new ThreadCreator(priority));
            case Builder.TYPE_FIXED:
                //响应较快，不用担心线程会被回收
                return Executors.newFixedThreadPool(size, new ThreadCreator(priority));
            case Builder.TYPE_SCHEDULED:
                //适合用于执行定时任务和固定周期的重复任务
                return Executors.newScheduledThreadPool(size, new ThreadCreator(priority));
            case Builder.TYPE_SINGLE:
            default:
                //适合用于排队按顺序执行
                return Executors.newSingleThreadExecutor(new ThreadCreator(priority));
        }
    }

    private synchronized void resetLocalConfigs() {
        local.set(null);
    }

    private synchronized ThreadConfigs getLocalConfigs() {
        ThreadConfigs configs = local.get();
        if (configs == null) {
            configs = new ThreadConfigs();
            configs.threadName = defaultName;
            configs.listener = defaultListener;
            configs.executor = defaultExecutor;
            local.set(configs);
        }
        return configs;
    }

    public static class Builder {

        final static int TYPE_CACHE = 0;
        final static int TYPE_FIXED = 1;
        final static int TYPE_SINGLE = 2;
        final static int TYPE_SCHEDULED = 3;

        int type;
        int size;
        int priority = Thread.NORM_PRIORITY;
        String name;
        ThreadListener listener;
        Executor executor;
        ExecutorService pool;

        private Builder(int size, int type, ExecutorService pool) {
            this.size = Math.max(1, size);
            this.type = type;
            this.pool = pool;
        }

        public static Builder create(ExecutorService pool) {
            return new Builder(1, TYPE_SINGLE, pool);
        }

        public static Builder createCacheable() {
            return new Builder(0, TYPE_CACHE, null);
        }

        public static Builder createFixed(int size) {
            return new Builder(size, TYPE_FIXED, null);
        }

        public static Builder createScheduled(int size) {
            return new Builder(size, TYPE_SCHEDULED, null);
        }

        public static Builder createSingle() {
            return new Builder(0, TYPE_SINGLE, null);
        }

        public Builder setName(@NonNull String name) {
            if (name.length() > 0) {
                this.name = name;
            }
            return this;
        }

        public Builder setPriority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder setListener(ThreadListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setExecutor(Executor deliver) {
            this.executor = deliver;
            return this;
        }

        public PoolThread build() {
            priority = Math.max(Thread.MIN_PRIORITY, priority);
            priority = Math.min(Thread.MAX_PRIORITY, priority);
            size = Math.max(1, size);
            if (TextUtils.isEmpty(name)) {
                switch (type) {
                    case TYPE_CACHE:
                        name = "BM-CACHE";
                        break;
                    case TYPE_FIXED:
                        name = "BM-FIXED";
                        break;
                    case TYPE_SINGLE:
                        name = "BM-SINGLE";
                        break;
                    default:
                        name = "BM-POOL_THREAD";
                        break;
                }
            }

            if (executor == null) {
                executor = AndroidExecutor.getInstance();
            }

            return new PoolThread(type, size, priority, name, listener, executor, pool);
        }

    }

}
