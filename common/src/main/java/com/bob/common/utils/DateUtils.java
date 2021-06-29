package com.bob.common.utils;

import com.bob.common.R;
import com.bob.common.base.app.BaseApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jimmy on 2017/11/27 0027.
 */
public class DateUtils {

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param time   时间戳
     * @param format
     * @return
     */
    public static String timeStamp2Date(long time, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     */
    public static String DayFormatDuring(long mss) {
        long days = mss / 1000 / 60 / 60 / 24;
        String sDays = String.valueOf(days);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        String sHours = hours < 10 ? "0" + hours : String.valueOf(hours);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        String sMinutes = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        long seconds = (mss % (1000 * 60)) / 1000;
        String sSeconds = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        return sDays + BaseApplication.getContext().getString(R.string.day) + "  " + sHours + " : " + sMinutes + " : " + sSeconds;
    }

    /**
     * @return 该秒数转换为 * days * hours * minutes * seconds 后的格式
     */
    public static String DayFormatDuringBySeconds(long second) {
        long days = second / 60 / 60 / 24;
        String sDays = String.valueOf(days);
        long hours = (second % (60 * 60 * 24)) / (60 * 60);
        String sHours = hours < 10 ? "0" + hours : String.valueOf(hours);
        long minutes = (second % (60 * 60)) / 60;
        String sMinutes = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        long seconds = second % 60;
        String sSeconds = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        return sDays + BaseApplication.getContext().getString(R.string.day) + "  " + sHours + " : " + sMinutes + " : " + sSeconds;
    }

    /**
     * @return 该毫秒数转换为 hours * minutes * seconds 后的格式
     */
    public static String HoursFormatDuring(long mss) {
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        String sHours = hours < 10 ? "0" + hours : String.valueOf(hours);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        String sMinutes = minutes < 10 ? "0" + minutes : String.valueOf(minutes);
        long seconds = (mss % (1000 * 60)) / 1000;
        String sSeconds = seconds < 10 ? "0" + seconds : String.valueOf(seconds);
        return sHours + " : " + sMinutes + " : " + sSeconds;
    }

    /**
     * 获得天数
     */
    public static long getDayTime(long time) {
        return time / 1000 / 60 / 60 / 24;
    }

    /**
     * 获得小时
     */
    public static long getHourTime(long time) {
        return time / 1000 / 60 / 60 % 24;
    }

    /**
     * 获得分钟
     */
    public static long getMinuteTime(long time) {
        return time / 1000 / 60 % 60;
    }

    /**
     * 将时间戳转换成当天零点的时间戳
     *
     * @param milliseconds
     * @return
     */
    private static Calendar zeroFromHour(long milliseconds) {
        Calendar calendar = Calendar.getInstance(); // 获得一个日历

        calendar.setTimeInMillis(completMilliseconds(milliseconds));
        zeroFromHour(calendar);
        return calendar;
    }

    /**
     * 将时，分，秒，以及毫秒值设置为0
     *
     * @param calendar
     */
    private static void zeroFromHour(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 由于服务器返回的是10位，手机端使用需要补全3位
     *
     * @param milliseconds
     * @return
     */
    private static long completMilliseconds(long milliseconds) {
        String milStr = Long.toString(milliseconds);
        if (milStr.length() == 10) {
            milliseconds = milliseconds * 1000;
        }
        return milliseconds;
    }


    /**
     * 最终调用方法
     *
     * @param timeStamp
     * @return
     */

    public static boolean isWeekend(long timeStamp) {
        Calendar cal = zeroFromHour(timeStamp);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
            return true;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
            return true;
        return false;
    }

}
