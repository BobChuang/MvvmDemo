package com.bob.common.interfaces;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

/**
 * 数据统计
 */
public class ReportDataAdapter {
    private static IReportData reportData;

    static public void init(Context context, IReportData reportData) {
        reportData.init(context);
        ReportDataAdapter.reportData = reportData;
    }

    static public void onResume(Activity var0) {
        if (reportData != null) {

            reportData.onResume(var0);
        }

    }

    static public void onPause(Activity var0) {

        if (reportData != null) {

            reportData.onPause(var0);
        }
    }

    static public void onEvent(Context var0, String var1) {

        if (reportData != null) {

            reportData.onEvent(var0, var1);
        }
    }

    static public void onEvent(Context var0, String var1, String var2) {

        if (reportData != null) {

            reportData.onEvent(var0, var1, var2);
        }
    }

    static public void onEvent(Context var0, String var1, String var2, Map var3) {

        if (reportData != null) {

            reportData.onEvent(var0, var1, var2, var3);
        }
    }
}
