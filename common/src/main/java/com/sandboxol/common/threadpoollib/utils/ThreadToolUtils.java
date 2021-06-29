package com.sandboxol.common.threadpoollib.utils;

import com.sandboxol.common.threadpoollib.callback.ThreadListener;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public final class ThreadToolUtils {

    public static boolean isAndroid;

    static {
        try {
            Class.forName("android.os.Build");
            isAndroid = true;
        } catch (Exception e) {
            isAndroid = false;
        }
    }

    public static void configThread(Thread thread, final String name, final ThreadListener listener) {
        thread.setUncaughtExceptionHandler((t, e) -> {
            if (listener != null) {
                listener.onError(name, e);
            }
        });
        thread.setName(name);
    }

    public static void sleepThread(long time) {
        if (time <= 0) {
            return;
        }
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread has been interrupted", e);
        }
    }
}
