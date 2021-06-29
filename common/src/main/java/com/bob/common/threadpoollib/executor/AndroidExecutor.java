package com.bob.common.threadpoollib.executor;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public class AndroidExecutor implements Executor {

    private static AndroidExecutor instance = new AndroidExecutor();
    private Handler main = new Handler(Looper.getMainLooper());

    public static AndroidExecutor getInstance() {
        return instance;
    }

    @Override
    public void execute(@NonNull final Runnable runnable) {
        //返回应用程序的looper，它位于应用程序的主线程中。
        Looper mainLooper = Looper.getMainLooper();
        //如果当前looper就是当前主线程，那么调用run后不再执行下面的语句
        if (Looper.myLooper() == mainLooper && runnable != null) {
            runnable.run();
            return;
        }

        //开启子线程
        main.post(() -> {
            if (runnable != null) {
                runnable.run();
            }
        });
    }
}
