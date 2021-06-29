package com.bob.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marvin on 2018/7/12 0012.
 */
public class AutoSlideScrollView extends NestedScrollView {

    private ViewGroup[] childs;
    private float lastX = 0, lastY = 0;
    private int moveTimes = 0;
    private boolean isMoving = false;

    public AutoSlideScrollView(@NonNull Context context) {
        super(context);
    }

    public AutoSlideScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSlideScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View root = getChildAt(0);
        if (root == null || !(root instanceof ViewGroup))
            throw new NullPointerException("child is empty!");
        ViewGroup vgRoot = (ViewGroup) root;
        List<ViewGroup> views = new ArrayList<>();
        findSlidableView(vgRoot, true, views);
        childs = new ViewGroup[views.size()];
        for (int i = 0; i < views.size(); i++) {
            childs[i] = views.get(i);
        }
    }

    private void findSlidableView(ViewGroup vp, boolean isRoot, List<ViewGroup> views) {
        for (int i = 0; i < vp.getChildCount(); i++) {
            View child = vp.getChildAt(i);
            if (!(child instanceof ViewGroup)) {
                continue;
            }
            if (child instanceof ScrollView
                    || child instanceof ScrollingView
                    || child instanceof HorizontalScrollView
                    || child instanceof AbsListView) {
                views.add((ViewGroup) child);
                if (!isRoot) {
                    break;
                }
            } else {
                findSlidableView((ViewGroup) child, false, views);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isMoving) {
            for (ViewGroup child : childs) {
                if (child.onInterceptTouchEvent(ev)) {
                    isMoving = true;
                    break;
                }
            }
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isMoving = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN)
            onTouchEvent(ev);
        return isMoving || super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (View child : childs) {
                    child.dispatchTouchEvent(ev);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(ev.getY() - lastY) > Math.abs(ev.getX() - lastX))
                    break;
                for (View child : childs) {
                    if (ev.getY() + getScrollY() > child.getY() && ev.getY() + getScrollY() < child.getY() + child.getHeight()) {
                        child.dispatchTouchEvent(ev);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isMoving = false;
                break;
        }
        lastX = ev.getX();
        lastY = ev.getY();
        return super.onTouchEvent(ev);
    }
}
