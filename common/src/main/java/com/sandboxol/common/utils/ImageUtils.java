package com.sandboxol.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;


import java.io.InputStream;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create by winson  on 19/8/12
 */
public class ImageUtils {
    private static LruCache<Integer, Bitmap> bitmapLruCache=null; //最大缓存10张图片
    static {
        int maxMemory =(int )Runtime.getRuntime().maxMemory()/1024;
        int cacheSize =maxMemory/8;
        bitmapLruCache =new LruCache<Integer, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(Integer key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }
    /**
     * 通过资源ID获取Bitmap,目前只在主线程使用，所以可能不考虑异步的问题
     *
     * @param context
     * @param resId
     * @return
     */
    public static void setBackgroundResource(Context context, ImageView view, int resId) {
        Bitmap bitmap =null;
        synchronized (ImageUtils.class){
            bitmap= bitmapLruCache.get(resId);
        }
        if (bitmap == null) {
            Observable.unsafeCreate((Observable.OnSubscribe<Bitmap>) subscriber -> {
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.RGB_565;
                opt.inPurgeable = true;
                opt.inInputShareable = true;
                //获取资源图片  
                InputStream is = context.getResources().openRawResource(resId);
                Bitmap bitmap1  = BitmapFactory.decodeStream(is, null, opt);
                synchronized (ImageUtils.class){
                    bitmapLruCache.put(resId, bitmap1);
                }
                subscriber.onNext(bitmap1);
                subscriber.onCompleted();
            }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Bitmap>() {
                @Override
                public void onCompleted() {
                }
                @Override
                public void onError(Throwable throwable) {
                }

                @Override
                public void onNext(Bitmap o) {
                    view.setImageBitmap(o);
                }
            });
        }else {
            view.setImageBitmap(bitmap);
        }
    }

}
