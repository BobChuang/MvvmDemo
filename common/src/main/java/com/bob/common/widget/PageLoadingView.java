package com.bob.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bob.common.R;

/**
 * Created by Jimmy on 2017/4/25 0025.
 */
public class PageLoadingView extends FrameLayout {

    private ImageView ivLoading, ivLoadFailed;
    private TextView tvLoadFailed;
    private boolean isShowFailed;  //无数据时是否展示图标和文字

    private AnimationDrawable loadingAnim;

    public PageLoadingView(@NonNull Context context) {
        this(context, null);
    }

    public PageLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PageLoadingView);
        int bgColor = array.getColor(R.styleable.PageLoadingView_background_color, Color.parseColor("#00000000"));
        isShowFailed = array.getBoolean(R.styleable.PageLoadingView_isShowFailed, true);
        initView(bgColor);
        array.recycle();
    }

    private void initView(int bgColor) {
        View.inflate(getContext(), R.layout.view_page_loading, this);
        LinearLayout llBg = findViewById(R.id.llBg);
        ivLoading = findViewById(R.id.ivLoading);
        ivLoadFailed = findViewById(R.id.ivLoadFailed);
        tvLoadFailed = findViewById(R.id.tvLoadFailed);
        loadingAnim = (AnimationDrawable) ivLoading.getBackground();
        llBg.setBackgroundColor(bgColor);
        start(true);
    }

    private void start(boolean init) {
        if (!init) {
            setVisibility(VISIBLE);
        }
        if (!loadingAnim.isRunning()) {
            loadingAnim.start();
        }
        ivLoading.setVisibility(VISIBLE);
        ivLoadFailed.setVisibility(GONE);
    }

    public void start() {
        start(false);
    }

    public void stop() {
        if (loadingAnim.isRunning()) {
            loadingAnim.stop();
        }
        ivLoading.setVisibility(GONE);
        ivLoadFailed.setVisibility(VISIBLE);
    }

    public void setFailedHint(String failed) {
        tvLoadFailed.setText(failed);
        stop();
    }

    public void success() {
        if (loadingAnim.isRunning()) {
            loadingAnim.stop();
        }
        setVisibility(GONE);
    }

    public void failed() {
        failed("No data");
    }

    public void failed(String failed) {
        if (isShowFailed || (failed.equals(getContext().getString(R.string.connect_server_time_out))
                || failed.equals(getContext().getString(R.string.connect_server_no_connect))
                || failed.equals(getContext().getString(R.string.connect_server_un_know))
                || failed.equals(getContext().getString(R.string.connect_error_code))
                || failed.equals(getContext().getString(R.string.connect_repeat_login))
                || failed.equals(getContext().getString(R.string.inner_error))
                || failed.equals(getContext().getString(R.string.time_out))
                || failed.equals(getContext().getString(R.string.network_connection_failed))
                || failed.equals(getContext().getString(R.string.user_be_report)))
                || failed.equals(getContext().getString(R.string.error_tip_runtime))) {
            setVisibility(VISIBLE);
            setFailedHint(failed);
        } else {
            setVisibility(GONE);
        }
    }
}
