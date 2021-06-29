package com.sandboxol.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * Created by gjw on 2019/7/19 17:43
 */
public class BitmapWaterMark {

    /**
     * @param mBitmap
     * @param text
     * @param color
     * @param textSize
     * @return 给bitmap添加文字水印
     */
    public static Bitmap addTimeWatermark(Context context, Bitmap mBitmap, String text, int color, int textSize) {
        int size = (int) SizeUtil.sp2px(context, textSize);
        //获取原始图片与水印图片的宽与高
        int mBitmapWidth = mBitmap.getWidth();
        int mBitmapHeight = mBitmap.getHeight();
        Bitmap mNewBitmap = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mNewBitmap);
        //向位图中开始画入MBitmap原始图片
        mCanvas.drawBitmap(mBitmap, 0, 0, null);
        //添加文字
        Paint mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setTextSize(size);
        mPaint.setFakeBoldText(true);
        //水印的位置坐标
        mCanvas.drawText(text, (mBitmapWidth * 1) / 2, mBitmapHeight / 2, mPaint);
        mCanvas.save();
        mCanvas.restore();

        return mNewBitmap;
    }

    // bitmap添加图片水印
    public static Bitmap addWaterMaskImage(Bitmap bitmap, Bitmap watermark) {

        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(bitmap, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        cv.drawBitmap(watermark, w - ww, h - wh, null);// 在src的右下角画入水印
        // save all clip
        cv.save();// 保存
        // store
        cv.restore();// 存储
        return newb;
    }
}
