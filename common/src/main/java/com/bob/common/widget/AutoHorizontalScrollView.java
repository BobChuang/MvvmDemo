package com.bob.common.widget;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;

/**
 * Created by marvin on 2018/7/13 0013.
 */
public class AutoHorizontalScrollView extends HorizontalScrollView {

    private NestedScrollView svRoot;
    private float lastY;

    public AutoHorizontalScrollView(Context context) {
        super(context);
    }

    public AutoHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        findRootSV();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        findRootSV();
    }

    private void findRootSV() {
        ViewParent parent = this;
        while (svRoot == null && parent != null) {
            parent = parent.getParent();
            if (parent != null && parent instanceof NestedScrollView) {
                svRoot = (NestedScrollView) parent;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        System.out.println(" ------  onTouchEvent  ------  " +
//                "\n onTouchEvent:lastY------" + (int) lastY +
//                "\n onTouchEvent:getY-------" + (int) ev.getRawY() +
//                "\n onTouchEvent:distance---" + (int) (lastY - ev.getRawY()));
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            lastY = 0;
        if (lastY != 0) {
            int distance = (int) (lastY - ev.getRawY());
            int adbDis = Math.abs(distance);
            if (svRoot != null && adbDis != 0 && adbDis < 65) {
                svRoot.scrollBy(0, distance);
            }
        }
        lastY = ev.getRawY();
        return super.onTouchEvent(ev);
    }
}
