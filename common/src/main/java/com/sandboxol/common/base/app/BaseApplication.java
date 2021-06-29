package com.sandboxol.common.base.app;

import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.sandboxol.common.threadpoollib.PoolThread;
import com.sandboxol.common.threadpoollib.callback.ThreadListener;
import com.sandboxol.common.utils.Logger;

/**
 * Created by Jimmy on 2016/7/31.
 */
public class BaseApplication extends Application {

    private static BaseApplication application;
    private static Context context;
    private static PoolThread threadPool;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        application = this;
        initLogger();
        initThreadPool();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static BaseApplication getApp() {
        return application;
    }

    public static Context getContext() {
        return context;
    }

    private void initLogger() {
        Logger.config(Logger.TYPE.WITH_THREAD, false);
    }

    //mark:init a thread pool;init in here for other modules can use
    private void initThreadPool() {
        threadPool = PoolThread.Builder
                .createFixed(5)
                .setPriority(Process.THREAD_PRIORITY_BACKGROUND)
                .setListener(new ThreadPoolLogListener())
                .build();
    }

    public static PoolThread getThreadPool() {
        return threadPool;
    }

    //mark:record information of all threads executing in thread pool(debug)
    public class ThreadPoolLogListener implements ThreadListener {

        private final String TAG = "AppThreadPoolLog";

        @Override
        public void onError(String name, Throwable t) {
            Logger.e(TAG, "ThreadLog" + "-----" + name + "------onError" + "----" + t.getMessage());
        }

        @Override
        public void onFinished(String name) {
            Logger.d(TAG, "ThreadLog" + "-----" + name + "------onFinished");
        }

        @Override
        public void onStart(String name) {
            Logger.d(TAG, "ThreadLog" + "-----" + name + "------onStart");
        }
    }

}
