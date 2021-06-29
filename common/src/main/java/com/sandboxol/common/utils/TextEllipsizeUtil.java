package com.sandboxol.common.utils;

/**
 * created by gaozhidong on 2019/4/11
 */
public class TextEllipsizeUtil {

    /**
     * 转化为缩写文字
     *
     * @param length 限制的长度
     * @param text   需加工的文字
     * @param end    省略符号
     * @return
     */
    public static String ellipsizeString(int length, String text, String end) {
        if (text.length() <= length) return text;
        StringBuilder stringBuilder = new StringBuilder();
        String result = text.substring(0, length - 1 - end.length());
        stringBuilder.append(result);
        stringBuilder.append(end);
        return stringBuilder.toString();
    }

    public static class SignLib{
        public static final String END_POINT = "...";
    }
}
