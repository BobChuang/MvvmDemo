package com.sandboxol.common.base.web;

import com.sandboxol.common.config.HttpCode;
import java.util.concurrent.TimeUnit;
import retrofit2.HttpException;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by marvin on 2019/9/25
 */
public class RetryWithDelay implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWithDelay(final int maxRetries, final int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryCount = 0;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> attempts) {
        return attempts.flatMap((Func1<Throwable, Observable<?>>) throwable -> {
                    if (++retryCount < maxRetries) {
                        if (throwable instanceof HttpException && checkCode((HttpException) throwable)) {
                            return Observable.error(throwable);
                        } else {
                            //延时重试
                            return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                        }
                    }
                    //终止重试
                    return Observable.error(throwable);
                });
    }

    private boolean checkCode(HttpException  throwable) {
        switch (throwable.code()) {
            case HttpCode.NET_BUSY:
            case HttpCode.NET_ERROR:
            case HttpCode.SERVER_ERROR:
            return true;
            default:
                return false;
        }
    }
}