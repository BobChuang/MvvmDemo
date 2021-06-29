package com.sandboxol.common.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

import com.sandboxol.common.widget.rv.datarv.DataRecyclerView;

/**
 * Created by marvin on 2018/7/13 0013.
 */
public class AutoHorizontalRecyclerView extends RecyclerView {

    private NestedScrollView svRoot;
    private float lastY;

    public AutoHorizontalRecyclerView(Context context) {
        super(context);
    }

    public AutoHorizontalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoHorizontalRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
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
