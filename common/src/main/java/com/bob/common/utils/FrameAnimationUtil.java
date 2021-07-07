package com.bob.common.utils;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.app.AppCompatDelegate;
import android.widget.ImageView;

public class FrameAnimationUtil {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private boolean mIsRepeat;

    private AnimationListener mAnimationListener;

    private ImageView mImageView;

    private int[] mFrameRess;

    /**
     * 每帧动画的播放间隔数组
     */
    private int[] mDurations;

    /**
     * 每帧动画的播放间隔
     */
    private int mDuration;

    /**
     * 下一遍动画播放的延迟时间
     */
    private int mDelay;

    private int mLastFrame;

    private boolean mNext;

    private boolean mPause;

    private int mCurrentSelect;

    private int mCurrentFrame;

    private static final int SELECTED_A = 1;

    private static final int SELECTED_B = 2;

    private static final int SELECTED_C = 3;

    private static final int SELECTED_D = 4;


    /**
     * @param iv       播放动画的控件
     * @param array    播放的图片数组
     * @param duration 每帧动画的播放间隔(毫秒)
     * @param isRepeat 是否循环播放
     */
    public FrameAnimationUtil(Context context, ImageView iv, int array, int duration, boolean isRepeat) {
        this.mImageView = iv;
        this.mFrameRess=getRes(context,array);
        this.mDuration = duration;
        this.mLastFrame = mFrameRess.length - 1;
        this.mIsRepeat = isRepeat;
        play(context,0);
    }
    /**
     * 循环播放动画
     *
     * @param iv       播放动画的控件
     * @param array    播放的图片数组
     * @param duration 每帧动画的播放间隔(毫秒)
     * @param delay    循环播放的时间间隔
     */
    public FrameAnimationUtil(Context context, ImageView iv, int array, int duration, int delay) {
        this.mImageView = iv;
        this.mFrameRess = getRes(context, array);
        this.mDuration = duration;
        this.mDelay = delay;
        this.mLastFrame = mFrameRess.length - 1;
        playAndDelay(context, 0);
    }
    private void playByDurationsAndDelay(Context context,final int i) {
        mImageView.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mPause) {   // 暂停和播放需求
                    mCurrentSelect = SELECTED_A;
                    mCurrentFrame = i;
                    return;
                }
                if (0 == i) {
                    if (mAnimationListener != null) {
                        mAnimationListener.onAnimationStart();
                    }
                }
                ImageUtils.setBackgroundResource(context,mImageView,mFrameRess[i]);
                if (i == mLastFrame) {
                    if (mAnimationListener != null) {
                        mAnimationListener.onAnimationRepeat();
                    }
                    mNext = true;
                    playByDurationsAndDelay(context,0);
                } else {
                    playByDurationsAndDelay(context,i + 1);
                }
            }
        }, mNext && mDelay > 0 ? mDelay : mDurations[i]);

    }

    private void playAndDelay(Context context, final int i) {
        mImageView.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mPause) {
                    if (mPause) {
                        mCurrentSelect = SELECTED_B;
                        mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                mNext = false;
                if (0 == i) {
                    if (mAnimationListener != null) {
                        mAnimationListener.onAnimationStart();
                    }
                }
                ImageUtils.setBackgroundResource(context,mImageView,mFrameRess[i]);
                if (i == mLastFrame) {
                    if (mAnimationListener != null) {
                        mAnimationListener.onAnimationRepeat();
                    }
                    mNext = true;
                    playAndDelay(context, 0);
                } else {
                    playAndDelay(context, i + 1);
                }
            }
        }, mNext && mDelay > 0 ? mDelay : mDuration);

    }

    private void playByDurations(Context context,final int i) {
        mImageView.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mPause) {
                    if (mPause) {
                        mCurrentSelect = SELECTED_C;
                        mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                if (0 == i) {
                    if (mAnimationListener != null) {
                        mAnimationListener.onAnimationStart();
                    }
                }
                ImageUtils.setBackgroundResource(context,mImageView,mFrameRess[i]);
                if (i == mLastFrame) {
                    if (mIsRepeat) {
                        if (mAnimationListener != null) {
                            mAnimationListener.onAnimationRepeat();
                        }
                        playByDurations(context,0);
                    } else {
                        if (mAnimationListener != null) {
                            mAnimationListener.onAnimationEnd();
                        }
                    }
                } else {

                    playByDurations(context,i + 1);
                }
            }
        }, mDurations[i]);

    }

    private void play(Context context,final int i) {
        mImageView.postDelayed(new Runnable() {

            @Override
            public void run() {
                if (mPause) {
                    if (mPause) {
                        mCurrentSelect = SELECTED_D;
                        mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                if (0 == i) {
                    if (mAnimationListener != null) {
                        mAnimationListener.onAnimationStart();
                    }
                }
                ImageUtils.setBackgroundResource(context,mImageView,mFrameRess[i]);
                if (i == mLastFrame) {

                    if (mIsRepeat) {
                        if (mAnimationListener != null) {
                            mAnimationListener.onAnimationRepeat();
                        }
                        play(context,0);
                    } else {
                        if (mAnimationListener != null) {
                            mAnimationListener.onAnimationEnd();
                        }
                    }

                } else {

                    play(context,i + 1);
                }
            }
        }, mDuration);
    }

    public static interface AnimationListener {

        /**
         * <p>Notifies the start of the animation.</p>
         */
        void onAnimationStart();

        /**
         * <p>Notifies the end of the animation. This callback is not invoked
         * for animations with repeat count set to INFINITE.</p>
         */
        void onAnimationEnd();

        /**
         * <p>Notifies the repetition of the animation.</p>
         */
        void onAnimationRepeat();
    }

    /**
     * <p>Binds an animation listener to this animation. The animation listener
     * is notified of animation events such as the end of the animation or the
     * repetition of the animation.</p>
     *
     * @param listener the animation listener to be notified
     */
    public void setAnimationListener(AnimationListener listener) {
        this.mAnimationListener = listener;
    }

    public void release() {
        pauseAnimation();
    }

    public void pauseAnimation() {
        this.mPause = true;
    }

    public boolean isPause() {
        return this.mPause;
    }

    public void restartAnimation(Context context) {
        if (mPause) {
            mPause = false;
            switch (mCurrentSelect) {
                case SELECTED_A:
                    playByDurationsAndDelay(context,mCurrentFrame);
                    break;
                case SELECTED_B:
                    playAndDelay(null, mCurrentFrame);
                    break;
                case SELECTED_C:
                    playByDurations(context,mCurrentFrame);
                    break;
                case SELECTED_D:
                    play(context,mCurrentFrame);
                    break;
                default:
                    break;
            }
        }
    }
//    private Bitmap[] getResBitmap(Context context, int array) {
//        TypedArray typedArray = context.getResources().obtainTypedArray(array);
//        int len = typedArray.length();
//        Bitmap[] res = new Bitmap[len];
//        for (int i = 0; i < len; i++) {
//            res[i] = ImageUtils.readBitMap(context, typedArray.getResourceId(i, 0));
//        }
//        typedArray.recycle();
//        return res;
//    }
    private int[] getRes(Context context, int array) {
        TypedArray typedArray = context.getResources().obtainTypedArray(array);
        int len = typedArray.length();
        int[] resId = new int[len];
        for (int i = 0; i < len; i++) {
            resId[i] = typedArray.getResourceId(i, 0);
        }
        typedArray.recycle();
        return resId;
    }
}
