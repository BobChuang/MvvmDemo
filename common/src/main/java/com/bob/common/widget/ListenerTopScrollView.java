package com.bob.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

/**
 * Description:
 *
 * @author GZDong
 * @date 2021/3/17
 */

public class ListenerTopScrollView extends NestedScrollView {

    private OnScrollToTopListener listener;
    private boolean isTop = true;

    public ListenerTopScrollView(@NonNull Context context) {
        this(context, null);
    }

    public ListenerTopScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListenerTopScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (clampedY) {
            if (listener != null && !isTop) {
                isTop = true;
                listener.onScrolling(true);
            }
        } else {
            if (listener != null && isTop) {
                isTop = false;
                listener.onScrolling(false);
            }
        }
    }

    public interface OnScrollToTopListener {
        void onScrolling(boolean isOnTop);
    }

    public void setScrollToTopListener(@NonNull OnScrollToTopListener listener) {
        this.listener = listener;
    }
}
