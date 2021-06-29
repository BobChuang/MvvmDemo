package com.sandboxol.common.utils;

import com.apkfuns.logutils.LogUtils;

/**
 * Description: Log打印工具
 * 集成https://github.com/pengwei1024/LogUtils，并进行了2次封装
 *
 * @author GZDong
 * @date 2019/10/23
 */

public class Logger {

    public static final String DEFAULT_TAG = "Logger";

    /**
     * 配置log的样式（可选）
     * configShowBorders 是否显示边框
     * configMethodOffset 根据方法栈往上追溯的层级
     * configFormatTag 打印的信息和格式
     *
     * @param type
     * @param isShowBorders
     */
    public static void config(TYPE type, boolean isShowBorders) {
        LogUtils.getLogConfig()
                .configShowBorders(isShowBorders)
                .configMethodOffset(1)
                .configFormatTag(type.getFormat());
    }

    public static void v(Object content) {
        LogUtils.tag(DEFAULT_TAG).v(content);
    }

    public static void v(String TAG, Object content) {
        LogUtils.tag(TAG).v(content);
    }

    public static void d(Object content) {
        LogUtils.tag(DEFAULT_TAG).d(content);
    }

    public static void d(String TAG, Object content) {
        LogUtils.tag(TAG).d(content);
    }

    public static void i(Object content) {
        LogUtils.tag(DEFAULT_TAG).i(content);
    }

    public static void i(String TAG, Object content) {
        LogUtils.tag(TAG).i(content);
    }

    public static void w(Object content) {
        LogUtils.tag(DEFAULT_TAG).w(content);
    }

    public static void w(String TAG, Object content) {
        LogUtils.tag(TAG).w(content);
    }

    public static void e(Object content) {
        LogUtils.tag(DEFAULT_TAG).e(content);
    }

    public static void e(String TAG, Object content) {
        LogUtils.tag(TAG).e(content);
    }

    /**
     * 默认使用debug级别打印
     *
     * @param content
     */
    public static void json(String content) {
        LogUtils.tag(DEFAULT_TAG).json(content);
    }

    public static void json(String TAG, String content) {
        LogUtils.tag(TAG).json(content);
    }

    /**
     * 配置其他显示信息以及相关的显示格式
     * %d{格式}定义时间，%t定义线程，%c{整数}定义log在代码中的路径
     *
     * 示例(打印“Hello World”):
     * NORMAL: " : Hello World"
     * WITH_PATH: "MainActivity.onCreate(MainActivity.java:30): Hello World"
     * WITH_THREAD: "main Thread: Hello World"
     * WITH_THREAD_PATH: "main Thread: MainActivity.onCreate(MainActivity.java:30): Hello World"
     * WITH_TIME_THREAD_PATH: "20:55:26 - main Thread: MainActivity.onCreate(MainActivity.java:30): Hello World"
     */
    public enum TYPE {

        NORMAL(" "),
        WITH_PATH("%c{-3}"),
        WITH_THREAD("%t Thread:"),
        WITH_THREAD_PATH("%t Thread: %c{-3}"),
        WITH_TIME_THREAD_PATH("%d{HH:mm:ss} - %t Thread: %c{-3}");

        private String format;

        TYPE(String format) {
            this.format = format;
        }

        public String getFormat() {
            return format;
        }
    }
}
