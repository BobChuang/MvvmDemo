package com.sandboxol.common.anim;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Bob on 2017/10/13.
 */
public class HorizontalMoveAnimation extends Animation {

    private int width = 0;
    private int direction;
    private boolean enter;

    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    public HorizontalMoveAnimation(int direction, boolean enter, long duration) {
        this.direction = direction;
        this.enter = enter;
        setDuration(duration);
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.width = width;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = enter ? (interpolatedTime - 1.0f) : interpolatedTime;
        if (direction == RIGHT) value *= -1.0f;
        float dx = -value * width;
        final Matrix m = t.getMatrix();
        m.postTranslate(dx, 0.0f);
    }

}