package com.bob.common.utils;

import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Description:
 *
 * @author GZDong
 * @date 2020/1/6
 */

public class TraceMethodTimer {

    public static void start() {
        Debug.startMethodTracing(new File(Environment.getExternalStorageDirectory(), "SandBoxOL/BlockMan").getPath());
    }

    public static void start(String name) {
        File file = new File(Environment.getExternalStorageDirectory(), "SandBoxOL/" + name);
        Debug.startMethodTracing(file.getPath());
    }

    public static void end() {
        Debug.stopMethodTracing();
    }

    public static void reportTime(int pointNo) {
        Log.e("TraceMethodPoint", pointNo + " ----  " + System.currentTimeMillis());
    }
}
