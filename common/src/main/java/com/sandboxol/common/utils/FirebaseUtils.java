package com.sandboxol.common.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Bob on 2019/11/15.
 */
public class FirebaseUtils {

    public static void onEvent(Context context, String event) {
        FirebaseAnalytics.getInstance(context).logEvent(event, null);
    }

    public static void onEvent(Context context, String event, String params) {
        Bundle bundle = new Bundle();
        bundle.putString("GameId", params);
        FirebaseAnalytics.getInstance(context).logEvent(event, bundle);
    }
}
