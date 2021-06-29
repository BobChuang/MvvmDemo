package com.sandboxol.common.binding.adapter;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.sandboxol.common.command.ReplyCommand;
import com.sandboxol.common.command.ResponseCommand;
import com.sandboxol.common.listener.ClickProxy;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class ViewBindingAdapters {

    @BindingAdapter(value = {"onClickCommand", "isQuicklyClickCommand"}, requireAll = false)
    public static void clickCommand(View view, final ReplyCommand clickCommand, boolean isQuicklyClickCommand) {
        if (isQuicklyClickCommand) {
            view.setOnClickListener(v -> {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            });
        } else {
            view.setOnClickListener(new ClickProxy(v -> {
                if (clickCommand != null) {
                    clickCommand.execute();
                }
            }, 1500));
        }
    }

    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final ReplyCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener((v, hasFocus) -> {
            if (onFocusChangeCommand != null) {
                onFocusChangeCommand.execute(hasFocus);
            }
        });
    }

    @BindingAdapter({"onTouchCommand"})
    public static void onTouchCommand(View view, final ResponseCommand<MotionEvent, Boolean> onTouchCommand) {
        view.setOnTouchListener((v, event) -> {
            if (onTouchCommand != null) {
                return onTouchCommand.execute(event);
            }
            return false;
        });
    }

    @BindingAdapter({"onShowAnimate"})
    public static void showAnimate(View view, int visibility) {
        if (visibility == View.VISIBLE) {
            AnimationSet animationSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(100);
            animationSet.addAnimation(alphaAnimation);
            view.startAnimation(animationSet);
        } else if (visibility == View.GONE) {
            AnimationSet animationSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(100);
            animationSet.addAnimation(alphaAnimation);
            view.startAnimation(animationSet);
        }
    }

    /**
     * 点击后动画
     *
     * @param view
     * @param animateRes
     * @param isDoLastAnimate
     */
    @BindingAdapter(value = {"afterAnimate", "isDoLastAnimate"}, requireAll = false)
    public static void onIsAnimate(View view, int animateRes, boolean isDoLastAnimate) {
        try {
            if (isDoLastAnimate && animateRes != 0) {
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), animateRes);
                view.startAnimation(animation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"android:background"})
    public static void backgroundColor(View view, int backgroundColor) {
        view.setBackgroundColor(backgroundColor);
    }

    @BindingAdapter({"isEnable"})
    public static void isEnable(View view, boolean isEnable) {
        view.setEnabled(isEnable);
    }

    /**
     * 显示时控件动画效果
     *
     * @param view
     * @param animateRes
     * @param animateDelayTime
     */
    @BindingAdapter(value = {"firstAnimate", "animateDelayTime", "isDoFirstAnimate"}, requireAll = false)
    public static void onAnimate(View view, int animateRes, int animateDelayTime, boolean isDoFirstAnimate) {
        try {
            if (isDoFirstAnimate){
                if (animateRes != 0) {
                    Animation animation = AnimationUtils.loadAnimation(view.getContext(), animateRes);
                    if (animateDelayTime == 0)
                        view.startAnimation(animation);
                    else {
                        view.setVisibility(View.INVISIBLE);
                        new Handler().postDelayed(() -> {
                            view.setVisibility(View.VISIBLE);
                            view.startAnimation(animation);
                        }, animateDelayTime);
                    }
                }
            }else {
                view.clearAnimation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter(value = {"cornerRadius", "bgColor"}, requireAll = false)
    public static void setRoundBackground(View view, int radius, String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        if (!TextUtils.isEmpty(color))
            drawable.setColor(Color.parseColor(color));
        view.setBackground(drawable);
    }
}
