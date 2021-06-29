package com.sandboxol.common.threadpoollib.executor;

import java.util.concurrent.Executor;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/4
 */

public class JavaExecutor implements Executor {

    private static JavaExecutor instance = new JavaExecutor();

    public static JavaExecutor getInstance() {
        return instance;
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }
}
