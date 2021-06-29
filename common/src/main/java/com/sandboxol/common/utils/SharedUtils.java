package com.sandboxol.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public class SharedUtils {

    private static final String DEFAULT_FILE_NAME = "data";
    private static final String DEFAULT_MULTI_PROCESS_FILE_NAME = "multiProcessFile";

    public static void putString(Context context, String key, String value) {
        putString(context, DEFAULT_FILE_NAME, key, value);
    }

    public static void putString(Context context, String name, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 尝试解决跨进程SP不同步问题
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putMultiProcessString(Context context, String key, String value) {
        putMultiProcessString(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, value);
    }

    /**
     * 尝试解决跨进程SP不同步问题
     *
     * @param context
     * @param name
     * @param key
     * @param value
     */
    public static void putMultiProcessString(Context context, String name, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void putInt(Context context, String key, int value) {
        putInt(context, DEFAULT_FILE_NAME, key, value);
    }

    public static void putMultiProcessInt(Context context, String key, int value) {
        putMultiProcessInt(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, value);
    }

    public static void putInt(Context context, String name, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putMultiProcessInt(Context context, String name, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putLong(Context context, String key, long value) {
        putLong(context, DEFAULT_FILE_NAME, key, value);
    }

    public static void putLong(Context context, String name, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void putMultiProcessLong(Context context, String key, long value) {
        putMultiProcessLong(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, value);
    }

    public static void putMultiProcessLong(Context context, String name, String key, long value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void putBoolean(Context context, String key, boolean value) {
        putBoolean(context, DEFAULT_FILE_NAME, key, value);
    }

    public static void putBoolean(Context context, String name, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void putMultiProcessBoolean(Context context, String key, boolean value) {
        putMultiProcessBoolean(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, value);
    }

    public static void putMultiProcessBoolean(Context context, String name, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void putFloat(Context context, String key, float value) {
        putFloat(context, DEFAULT_FILE_NAME, key, value);
    }

    public static void putFloat(Context context, String name, String key, float value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static void putMultiProcessFloat(Context context, String key, float value) {
        putMultiProcessFloat(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, value);
    }

    public static void putMultiProcessFloat(Context context, String name, String key, float value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    public static String getString(Context context, String key, String dfValue) {
        return getString(context, DEFAULT_FILE_NAME, key, dfValue);
    }

    public static String getString(Context context, String name, String key, String dfValue) {
        SharedPreferences sharedPreferences = SharedUtils.getSharedPreferences(context, name);
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, dfValue);
        } else {
            return dfValue;
        }
    }

    public static String getMultiProcessString(Context context, String key) {
        return getMultiProcessString(context, key, null);
    }

    public static String getMultiProcessString(Context context, String key, String dfValue) {
        return getMultiProcessString(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, dfValue);
    }

    public static String getMultiProcessString(Context context, String name, String key, String dfValue) {
        return context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS).getString(key, dfValue);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, DEFAULT_FILE_NAME, key);
    }

    public static int getInt(Context context, String name, String key) {
        return getInt(context, name, key, 0);
    }

    public static int getInt(Context context, String key, int dfValue) {
        return getInt(context, DEFAULT_FILE_NAME, key, dfValue);
    }

    public static int getInt(Context context, String name, String key, int dfValue) {
        SharedPreferences sharedPreferences = SharedUtils.getSharedPreferences(context, name);
        if (sharedPreferences != null) {
            return sharedPreferences.getInt(key, dfValue);
        } else {
            return dfValue;
        }
    }


    public static int getMultiProcessInt(Context context, String key) {
        return getMultiProcessInt(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key);
    }

    public static int getMultiProcessInt(Context context, String name, String key) {
        return getMultiProcessInt(context, name, key, 0);
    }

    public static int getMultiProcessInt(Context context, String key, int dfValue) {
        return getMultiProcessInt(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, dfValue);
    }

    public static int getMultiProcessInt(Context context, String name, String key, int dfValue) {
        return context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS).getInt(key, dfValue);
    }

    public static long getLong(Context context, String key) {
        return getLong(context, DEFAULT_FILE_NAME, key);
    }

    public static long getLong(Context context, String name, String key) {
        return getLong(context, name, key, 0L);
    }

    public static long getLong(Context context, String key, long dfValue) {
        return getLong(context, DEFAULT_FILE_NAME, key, dfValue);
    }

    public static long getLong(Context context, String name, String key, long dfValue) {
        SharedPreferences sharedPreferences = SharedUtils.getSharedPreferences(context, name);
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, dfValue);
        } else {
            return dfValue;
        }
    }


    public static long getMultiProcessLong(Context context, String key) {
        return getMultiProcessLong(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key);
    }

    public static long getMultiProcessLong(Context context, String name, String key) {
        return getMultiProcessLong(context, name, key, 0L);
    }

    public static long getMultiProcessLong(Context context, String key, long dfValue) {
        return getMultiProcessLong(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, dfValue);
    }

    public static long getMultiProcessLong(Context context, String name, String key, long dfValue) {
        return context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS).getLong(key, dfValue);
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, DEFAULT_FILE_NAME, key);
    }

    public static boolean getBoolean(Context context, String name, String key) {
        return getBoolean(context, name, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean dfValue) {
        return getBoolean(context, DEFAULT_FILE_NAME, key, dfValue);
    }

    public static boolean getBoolean(Context context, String name, String key, boolean dfValue) {
        SharedPreferences sharedPreferences = SharedUtils.getSharedPreferences(context, name);
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(key, dfValue);
        } else {
            return dfValue;
        }
    }

    public static boolean getMultiProcessBoolean(Context context, String key) {
        return getMultiProcessBoolean(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key);
    }

    public static boolean getMultiProcessBoolean(Context context, String name, String key) {
        return getMultiProcessBoolean(context, name, key, false);
    }

    public static boolean getMultiProcessBoolean(Context context, String key, boolean dfValue) {
        return getMultiProcessBoolean(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, dfValue);
    }

    public static boolean getMultiProcessBoolean(Context context, String name, String key, boolean dfValue) {
        return context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS).getBoolean(key, dfValue);
    }


    public static float getFloat(Context context, String key) {
        return getFloat(context, DEFAULT_FILE_NAME, key);
    }

    public static float getFloat(Context context, String name, String key) {
        return getFloat(context, name, key, 0f);
    }

    public static float getFloat(Context context, String key, float dfValue) {
        return getFloat(context, DEFAULT_FILE_NAME, key, dfValue);
    }

    public static float getFloat(Context context, String name, String key, float dfValue) {
        SharedPreferences sharedPreferences = SharedUtils.getSharedPreferences(context, name);
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, dfValue);
        } else {
            return dfValue;
        }
    }


    public static float getMultiProcessFloat(Context context, String key) {
        return getMultiProcessFloat(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key);
    }

    public static float getMultiProcessFloat(Context context, String name, String key) {
        return getMultiProcessFloat(context, name, key, 0f);
    }

    public static float getMultiProcessFloat(Context context, String key, float dfValue) {
        return getMultiProcessFloat(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key, dfValue);
    }

    public static float getMultiProcessFloat(Context context, String name, String key, float dfValue) {
        return context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS).getFloat(key, dfValue);
    }

    public static void remove(Context context, String key) {
        remove(context, DEFAULT_FILE_NAME, key);
    }

    public static void remove(Context context, String name, String key) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }


    public static void removeMultiProcess(Context context, String key) {
        removeMultiProcess(context, DEFAULT_MULTI_PROCESS_FILE_NAME, key);
    }

    public static void removeMultiProcess(Context context, String name, String key) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clear(Context context) {
        clear(context, DEFAULT_FILE_NAME);
    }

    public static void clear(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public static void clearMultiProcess(Context context) {
        clearMultiProcess(context, DEFAULT_MULTI_PROCESS_FILE_NAME);
    }

    public static void clearMultiProcess(Context context, String name) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    private static SharedPreferences getSharedPreferences(Context context, String name) {
        if (context != null) {
            return context.getSharedPreferences(name, Context.MODE_PRIVATE);
        } else {
            return null;
        }
    }
}
