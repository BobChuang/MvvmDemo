package com.bob.common.interfaces;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

public interface IReportData {
    void init(Context var0);

    void onResume(Activity var0);

    void onPause(Activity var0);

    void onEvent(Context var0, String var1);

    void onEvent(Context var0, String var1, String var2);

    void onEvent(Context var0, String var1, Map var3);

    void onEvent(Context var0, String var1, String var2, Map var3);
}
