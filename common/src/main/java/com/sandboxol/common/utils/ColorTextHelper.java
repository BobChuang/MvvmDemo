package com.sandboxol.common.utils;

import android.os.Build;
import android.support.annotation.ColorRes;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.sandboxol.common.base.app.BaseApplication;

import java.util.Stack;

/**
 * Description:
 * 用于生成带颜色区分的文案生成工具
 * 用法示例：
 * 一：指定内容、颜色、顺序
 * ObservableField<Spanned> content = new ObservableField<>();
 * ColorTextHelper helper = new ColorTextHelper.Builder()
 *                  .appendText("test1", R.color.testColor) //可以指定颜色资源id
 *                  .appendText("test2", "#333333") //可以直接传颜色16进制字符串
 *                  .create();
 * content.set(helper.getColorText());
 * <p>
 * 二：指定内容、上色标签、颜色
 * ObservableField<Spanned> content = new ObservableField<>();
 * ColorTextHelper helper = new ColorTextHelper.Builder()
 *                 .appendTextWithTag("text#color text_text", "#", "_", "#333333", R.color.allBackgroundColor) //可以指定颜色资源id，或直接传颜色16进制字符串
 *                 .create();
 * content.set(helper.getColorText());
 *
 * @author GZDong
 * @date 2019/11/21
 */

public class ColorTextHelper {

    public static final String HTML_LABEL_FONT_BEGIN_START = "<font ";
    public static final String HTML_ATTR_COLOR = "color='";
    public static final String HTML_LABEL_FONT_BEGIN_END = "'>";
    public static final String HTML_LABEL_FONT_END = "</font>";
    public static final String HTML_LABEL_BR = "<br/>";
    public static final String HTML_SPACE = "&nbsp;";

    private StringBuilder result;

    public static class Builder {
        StringBuilder result;
        String startTag;
        String endTag;
        String normalColor;
        String highLightColor;

        public Builder() {
            result = new StringBuilder();
        }

        /**
         * 根据内容、颜色资源id生成文案
         *
         * @param content 内容
         * @param color   颜色资源id，比如R.color.bgText
         * @return
         */
        public Builder appendText(String content, @ColorRes int color) {
            appendText(content, colorResId2Str(color));
            return this;
        }

        /**
         * 根据内容、颜色16进制字符串生成文案
         *
         * @param content 内容
         * @param color   16进制颜色字符串，比如#33ff8u
         * @return
         */
        public Builder appendText(String content, String color) {
            result.append(HTML_LABEL_FONT_BEGIN_START)
                    .append(HTML_ATTR_COLOR)
                    .append(color)
                    .append(HTML_LABEL_FONT_BEGIN_END)
                    .append(content)
                    .append(HTML_LABEL_FONT_END);
            return this;
        }

        /**
         * 根据内容、指定标签和强调颜色16进制生成文案
         * 标签不能使用[]，会呗认为是正则表达式
         *
         * @param content        内容，必须有成对出现的开始标签和结束标签
         * @param startTag       开始标签（不和结束标签一样）
         * @param endTag         结束标签（不和开始标签一样）
         * @param normalColor    普通字体颜色
         * @param highLightColor 强调字体颜色
         * @return
         */
        public Builder appendTextWithTag(String content, String startTag, String endTag,
                                         String normalColor, String highLightColor) {
            this.result.append(content);
            this.startTag = startTag;
            this.endTag = endTag;
            this.normalColor = normalColor;
            this.highLightColor = highLightColor;
            return this;
        }

        public Builder appendTextWithTag(String content, String startTag, String endTag,
                                         String normalColor, @ColorRes int highLightColor) {
            return appendTextWithTag(content, startTag, endTag, normalColor, colorResId2Str(highLightColor));
        }

        public Builder appendTextWithTag(String content, String startTag, String endTag,
                                         @ColorRes int normalColor, String highLightColor) {
            return appendTextWithTag(content, startTag, endTag, colorResId2Str(normalColor), highLightColor);
        }

        public Builder appendTextWithTag(String content, String startTag, String endTag,
                                         @ColorRes int normalColor, @ColorRes int highLightColor) {
            return appendTextWithTag(content, startTag, endTag, colorResId2Str(normalColor), colorResId2Str(highLightColor));
        }

        /**
         * Builder将内容处理转化给Helper
         *
         * @param helper
         */
        void applyContent(ColorTextHelper helper) {
            helper.result = new StringBuilder();
            if (!TextUtils.isEmpty(this.startTag) && !TextUtils.isEmpty(this.endTag)
                    && !TextUtils.isEmpty(this.normalColor) && !TextUtils.isEmpty(highLightColor)) {
                if (checkValidity(this.result.toString(), startTag, endTag)) {
                    helper.result.append(generateStr(this.result, this.startTag, this.endTag,
                            this.normalColor, this.highLightColor));
                } else {
                    helper.result.append(this.result.toString().replace(startTag, "")
                            .replace(endTag, ""));
                }
            } else {
                helper.result.append(this.result.toString());
            }
        }

        public ColorTextHelper create() {
            ColorTextHelper helper = new ColorTextHelper();
            try {
                applyContent(helper);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return helper;
        }

        /**
         * 校验字符串开始标签和结束标签是否成对出现
         *
         * @param data
         * @return
         */
        private boolean checkValidity(String data, String startTag, String endTag) {
            Stack<String> left = new Stack<>();
            while (!data.isEmpty()) {
                String character = data.substring(0, 1);
                data = data.substring(1);
                if (character.equals(startTag)) {
                    left.push(character);
                } else if (character.equals(endTag)) {
                    if (left.isEmpty()) {
                        return false;
                    }
                    String leftChar = left.pop();
                    if (character.equals(endTag)) {
                        if (!leftChar.equals(startTag)) {
                            return false;
                        }
                    }
                }
            }
            return left.isEmpty();
        }

        /**
         * 根据标签生成文案的具体实现
         *
         * @param data
         * @param startTag
         * @param endTag
         * @param normalColor
         * @param highLightColor
         * @return
         */
        private String generateStr(StringBuilder data, String startTag, String endTag,
                                   String normalColor, String highLightColor) {
            StringBuilder result = new StringBuilder();
            String content = data.toString();
            String[] str1 = content.split(endTag);
            for (int i = 0; i < str1.length; i++) {
                String[] str2 = str1[i].split(startTag);
                if (str2.length == 1) {
                    if (str2[0].contains(startTag)) {
                        setValue(result, str2[0], highLightColor);
                    } else {
                        setValue(result, str2[0], normalColor);
                    }
                } else {
                    setValue(result, str2[0], normalColor);
                    setValue(result, str2[1], highLightColor);
                }
            }
            return result.toString().replace("\n", HTML_LABEL_BR);
        }

        private void setValue(StringBuilder object, String value, String color) {
            object.append(HTML_LABEL_FONT_BEGIN_START)
                    .append(HTML_ATTR_COLOR)
                    .append(color)
                    .append(HTML_LABEL_FONT_BEGIN_END)
                    .append(value)
                    .append(HTML_LABEL_FONT_END);
        }
    }

    /**
     * FROM_HTML_MODE_COMPACT：html 块元素之间使用一个换行符分隔
     *
     * @return
     */
    public Spanned getColorText() {
        if (result != null) {
            Spanned text;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    text = Html.fromHtml(result.toString(), Html.FROM_HTML_MODE_COMPACT);
                } catch (Exception e) {
                    e.printStackTrace();
                    text = Html.fromHtml("", Html.FROM_HTML_MODE_COMPACT);
                }
            } else {
                try {
                    text = Html.fromHtml(result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    text = Html.fromHtml("");
                }
            }
            return text;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml("", Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml("");
            }
        }
    }

    /**
     * 只取后6位，去除前2位透明度
     */
    public static String colorResId2Str(@ColorRes int resId) {
        String hexColor = "#333333";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String temp = Integer.toHexString(BaseApplication.getContext().getResources().getColor(resId, null));
                hexColor = "#" + temp.substring(temp.length() - 6);
            } else {
                String temp = Integer.toHexString(BaseApplication.getContext().getResources().getColor(resId));
                hexColor = "#" + temp.substring(temp.length() - 6);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return hexColor;
        }
    }
}
