package com.bob.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.util.Log;

import com.bob.common.BuildConfig;
import com.bob.common.R;
import com.tendcloud.tenddata.TCAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Fungo_Xiaoke on 2017/5/4 14:18.
 * eamil:luoxiaoke@yuntutv.net
 * 权限工具类
 */
public class PermissionUtils {


    /**
     * @param activity    上下文
     * @param activity    activity
     * @param permissions 权限数组
     * @param requestCode 申请码
     * @return true 有权限  false 无权限
     */
    public static boolean checkAndApplyfPermissionActivity(Activity activity, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions = checkPermissions(activity, permissions);
            if (permissions != null && permissions.length > 0) {
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * @param mFragment   fragment
     * @param permissions 权限数组
     * @param requestCode 申请码
     * @return true 有权限  false 无权限
     */
    public static boolean checkAndApplyfPermissionFragment(Fragment mFragment, String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions = checkPermissions(mFragment.getActivity(), permissions);
            if (permissions != null && permissions.length > 0) {
                if (mFragment.getActivity() != null) {
                    mFragment.requestPermissions(permissions, requestCode);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * @param context     上下文
     * @param permissions 权限数组
     * @return 还需要申请的权限
     */
    public static String[] checkPermissions(Context context, String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return new String[0];
        }
        ArrayList<String> permissionLists = new ArrayList<>();
        permissionLists.addAll(Arrays.asList(permissions));
        for (int i = permissionLists.size() - 1; i >= 0; i--) {
            if (ContextCompat.checkSelfPermission(context, permissionLists.get(i)) == PackageManager.PERMISSION_GRANTED) {
                permissionLists.remove(i);
            }
        }

        String[] temps = new String[permissionLists.size()];
        for (int i = 0; i < permissionLists.size(); i++) {
            temps[i] = permissionLists.get(i);
        }
        return temps;
    }


    /**
     * 检查申请的权限是否全部允许
     */
    public static boolean checkPermission(int[] grantResults) {
        if (grantResults == null || grantResults.length == 0) {
            return true;
        } else {
            int temp = 0;
            for (int i : grantResults) {
                if (i == PackageManager.PERMISSION_GRANTED) {
                    temp++;
                }
            }
            return temp == grantResults.length;
        }
    }

    /**
     * 没有获取到权限的提示
     *
     * @param permissions 权限名字数组
     */
    public static void showPermissionsToast(Activity activity, @NonNull String[] permissions) {
        if (permissions.length > 0) {
            for (String permission : permissions) {
                showPermissionToast(activity, permission);
            }
        }
    }

    /**
     * 没有获取到权限的提示
     *
     * @param permission 权限名字
     */
    private static void showPermissionToast(Activity activity, @NonNull String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            //用户勾选了不再询问,提示用户手动打开权限
            switch (permission) {
                case Manifest.permission.CAMERA:
                    ToastUtils.showShortToast(activity, "相机权限已被禁止，请在应用管理中打开权限");
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    ToastUtils.showShortToast(activity, R.string.permission_sdcard);
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    ToastUtils.showShortToast(activity, "录制音频权限已被禁止，请在应用管理中打开权限");
                    break;
                case Manifest.permission.ACCESS_FINE_LOCATION:
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                    TCAgent.onEvent(activity, "position_accept_time_refuse");
                    ToastUtils.showShortToast(activity, R.string.permission_location);
                    break;
                default:
                    break;
            }
        } else {
            //用户没有勾选了不再询问,拒绝了权限申请
            switch (permission) {
                case Manifest.permission.CAMERA:
                    ToastUtils.showShortToast(activity, "没有相机权限");
                    break;
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    ToastUtils.showShortToast(activity, "没有文件读取权限");
                    break;
                case Manifest.permission.RECORD_AUDIO:
                    ToastUtils.showShortToast(activity, "没有录制音频权限");
                    break;
                case Manifest.permission.ACCESS_FINE_LOCATION:
                    ToastUtils.showShortToast(activity, "没有位置权限");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 去应用权限管理界面
     */
    public static void gotoPermissionManager(Context context) {
        Intent intent;
        ComponentName comp;
        //防止刷机出现的问题
        try {
            switch (Build.MANUFACTURER) {
                case "Huawei":
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
                    intent.setComponent(comp);
                    context.startActivity(intent);
                    break;
                case "Meizu":
                    intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                    context.startActivity(intent);
                    break;
                case "Xiaomi":
                    String rom = getSystemProperty("ro.miui.ui.version.name");
                    if ("v5".equals(rom)) {
                        Uri packageURI = Uri.parse("package:" + context.getApplicationInfo().packageName);
                        intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    } else {//if ("v6".equals(rom) || "v7".equals(rom)) {
                        intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                        intent.putExtra("extra_pkgname", context.getPackageName());
                    }
                    context.startActivity(intent);
                    break;
                case "Sony":
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                    comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
                    intent.setComponent(comp);
                    context.startActivity(intent);
                    break;
                case "OPPO":
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                    comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
                    intent.setComponent(comp);
                    context.startActivity(intent);
                    break;
                case "LG":
                    intent = new Intent("android.intent.action.MAIN");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                    comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
                    intent.setComponent(comp);
                    context.startActivity(intent);
                    break;
                case "Letv":
                    intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
                    comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
                    intent.setComponent(comp);
                    context.startActivity(intent);
                    break;
                default:
                    getAppDetailSettingIntent(context);
                    break;
            }
        } catch (Exception e) {
            getAppDetailSettingIntent(context);
        }
    }

    /**
     * 获取系统属性值#
     */
    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e("getSystemProperty", "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e("getSystemProperty", "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    //以下代码可以跳转到应用详情，可以通过应用详情跳转到权限界面(6.0系统测试可用)
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        launchApp(context, localIntent);
    }

    /**
     * 安全的启动APP
     */
    public static boolean launchApp(Context ctx, Intent intent) {
        try {
            ctx.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
