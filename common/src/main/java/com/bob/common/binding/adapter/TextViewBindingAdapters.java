package com.bob.common.binding.adapter;

import androidx.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.bob.common.command.ReplyCommand;

/**
 * Created by Bob on 2018/2/6
 */
public class TextViewBindingAdapters {

    @BindingAdapter({"centerLine"})
    public static void onLoadMoreCommand(final TextView textView, boolean centerLine) {
        if (centerLine) {
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        }
    }

    @BindingAdapter(value = {"colorText", "colorSpan", "textStart", "textEnd"}, requireAll = false)
    public static void onColorTextCommand(final TextView textView, String text, int color, int start, int end) {
        try {
            if (text == null)
                text = "";
            if (color == 0)
                color = Color.BLACK;
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText(text);
        }

    }

    @BindingAdapter({"isSelect"})
    public static void onSelect(TextView textView, boolean isSelect) {
        textView.setSelected(isSelect);
    }

    @BindingAdapter({"tvEnable"})
    public static void isEnable(TextView textView, boolean tvEnable) {
        textView.setEnabled(tvEnable);
    }

    @BindingAdapter({"bgText"})
    public static void setBackground(TextView textView, Drawable res) {
        textView.setBackground(res);
    }

    @BindingAdapter({"leftDrawable"})
    public static void setLeftDrawable(TextView textView, Drawable res) {
        textView.setCompoundDrawablesWithIntrinsicBounds(res, null, null, null);
    }

    @BindingAdapter(value = {"bgRadius", "bgColorStr"}, requireAll = false)
    public static void setRoundBackground(TextView view, int radius, String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        if (!TextUtils.isEmpty(color))
            drawable.setColor(Color.parseColor(color));
        view.setBackground(drawable);
    }

    @BindingAdapter(value = {"beforeTextChangedCommand", "onTextChangedCommand", "afterTextChangedCommand"}, requireAll = false)
    public static void editTextCommand(TextView textView,
                                       final ReplyCommand<EditTextBindingAdapters.TextChangeDataWrapper> beforeTextChangedCommand,
                                       final ReplyCommand<EditTextBindingAdapters.TextChangeDataWrapper> onTextChangedCommand,
                                       final ReplyCommand<String> afterTextChangedCommand) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (beforeTextChangedCommand != null) {
                    beforeTextChangedCommand.execute(new EditTextBindingAdapters.TextChangeDataWrapper(s, start, count, count));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (onTextChangedCommand != null) {
                    onTextChangedCommand.execute(new EditTextBindingAdapters.TextChangeDataWrapper(s, start, before, count));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (afterTextChangedCommand != null) {
                    afterTextChangedCommand.execute(s.toString());
                }
            }
        });
    }
}
