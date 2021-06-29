package com.sandboxol.common.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.sandboxol.common.R;
import com.sandboxol.common.base.app.BaseApplication;
import com.sandboxol.common.config.CommonMessageToken;
import com.sandboxol.common.config.CommonSharedConstant;
import com.sandboxol.common.messenger.Messenger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by Bob on 2017/10/19
 */
public class CommonHelper {

    public static void hideSoftInputFromWindow(Context context) {
        if (((Activity) context).getCurrentFocus() != null && ((Activity) context).getCurrentFocus().getWindowToken() != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static boolean isTablet(Context context) {
        try {
            context = context.getApplicationContext();
            return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDeviceId(Context context) {
        String imei = UUID.randomUUID().toString();
        if (imei == null) {
            imei = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return imei;
    }

    /**
     * 获取Android ID
     *
     * @param context
     * @return
     */
    public static String getAndroidId(Context context) {
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidId == null || "".equals(androidId))
            androidId = getUUId(context);
        else
            Messenger.getDefault().send("android_id", CommonMessageToken.TOKEN_ANDROID_DEVICE_ID_TYPE);
        return androidId;
    }

    public static String getSignature(Context context) {
        return SHA1(getAndroidId(context));
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUId(Context context) {
        String uuIdPath = getSDCardPath(context) + "/uuId.txt";
        File file = new File(uuIdPath);
        if (!file.exists()) {
            return createUUId(context);
        }
        BufferedReader reader = null;
        String tempString = null;
        String uuID = SharedUtils.getString(context, CommonSharedConstant.APP_VISITOR_UUID);
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((tempString = reader.readLine()) != null) {
                uuID = tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return uuID;
    }

    /**
     * 生成UUID并存起来
     *
     * @param context
     * @return
     */
    public static String createUUId(Context context) {
        String uuId;
        if (SharedUtils.getString(context, CommonSharedConstant.APP_VISITOR_UUID) == null) {
            UUID uuid = UUID.randomUUID();
            uuId = uuid.toString();
            SharedUtils.putString(context, CommonSharedConstant.APP_VISITOR_UUID, uuId);
        } else
            uuId = SharedUtils.getString(context, CommonSharedConstant.APP_VISITOR_UUID);

        try {
            File file = new File(getSDCardPath(context) + "/uuId.txt");
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(uuId.getBytes());
            outStream.close();
            Messenger.getDefault().send("uuId", CommonMessageToken.TOKEN_ANDROID_DEVICE_ID_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            Messenger.getDefault().send("uuId_cache", CommonMessageToken.TOKEN_ANDROID_DEVICE_ID_TYPE);
        }
        return uuId;
    }

    /**
     * 获取SD卡路径
     *
     * @param context
     * @return
     */
    private static String getSDCardPath(Context context) {
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        return (sdCardExist ? new File(Environment.getExternalStorageDirectory(), "SandBoxOL/BlockMan").getPath() : context.getDir("BlockMan", Context.MODE_PRIVATE).getPath());
    }

    public static String getLanguage() {
        try {
            Locale locale = BaseApplication.getContext().getResources().getConfiguration().locale;
            return String.format("%s_%s", locale.getLanguage(), locale.getCountry());
        } catch (Exception e) {
            return "en_US";
        }
    }

    /**
     * 获取用户手机语言
     *
     * @return
     */
    public static String getUserLanguage() {
        try {
            Locale locale = BaseApplication.getContext().getResources().getConfiguration().locale;
            return locale.getLanguage();
        } catch (Exception e) {
            return "en";
        }
    }


    public static String getGameLanguage() {
        String lang = getLanguage();
        if (lang.equals("uk_UA")) {
            return "ru_RU";
        } else {
            return lang;
        }
    }

    public static String getCountry() {
        Locale locale = BaseApplication.getContext().getResources().getConfiguration().locale;
        return locale.getCountry();
    }

    public static <T> T formatObject(String json, Class<T> tclass) {
        Gson gson = new Gson();
        return gson.fromJson(json, tclass);
    }

    public static int checkProcessIsRunning(Context ctx, String packageName) {
        try {
            ctx = ctx.getApplicationContext();
            ActivityManager am = (ActivityManager) ctx.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : am.getRunningAppProcesses()) {
                if (appProcess.processName.equals(packageName)) {
                    return appProcess.pid;
                }
            }
        } catch (Exception e) {

        }
        return 0;
    }

    //这种方法状态栏是空白，显示不了状态栏的信息
    public static Bitmap getViewBitmap(View rootView) {
        //获取当前屏幕的大小
        int width = rootView.getWidth();
        int height = rootView.getHeight();
        if (width == 0 || height == 0)
            return null;
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的跟布局
        //设置缓存
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = rootView.getDrawingCache();

        return temBitmap;
    }

    public static void screenPic(Context context, String name, View rootView) {
        Bitmap bitmap = getViewBitmap(rootView);
        if (bitmap != null)
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
                MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, name, context.getResources().getString(R.string.app_name));
            else
                ToastUtils.showShortToast(context, R.string.no_sdcard);

    }

    /**
     * 邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null)
            return false;
        String regEx = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 判断是否安装App
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }


    /**
     * App是否已经关闭
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean checkApkExist(Context context, String packageName) {
        context = context.getApplicationContext();
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /** */
    /**
     * 文件重命名
     *
     * @param path    文件目录
     * @param oldName 原来的文件名
     * @param newName 新文件名
     */
    public static void renameFile(String path, String oldName, String newName) {
        if (!oldName.equals(newName)) {//新的文件名和以前文件名不同时,才有必要进行重命名
            File oldFile = new File(path + "/" + oldName);
            File newFile = new File(path + "/" + newName);
            if (!oldFile.exists()) {
                return;//重命名文件不存在
            }
            if (newFile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                System.out.println(newName + "已经存在！");
            else {
                oldFile.renameTo(newFile);
            }
        } else {
            System.out.println("新文件名和旧文件名相同...");
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 获得手机可用的内存
     *
     * @return
     */
    public static long getLessMemorySize() {
        //获取可用内存大小
        StatFs statfs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //获取可用区块的个数
        long count = statfs.getAvailableBlocks();
        //获取区块大小
        long size = statfs.getBlockSize();
        //可用空间总大小
        return count * size;
    }

    /**
     * 通过字段获取对应的资源ID
     *
     * @param context
     * @param prefix      前缀
     * @param paramString 字段
     * @param Suffix      后缀
     * @return
     */
    public static int getResourcesById(Context context, String prefix, String paramString, String Suffix) {
        return context.getResources().getIdentifier(prefix + paramString, Suffix, context.getPackageName());
    }

    /**
     * 获取缓存地址
     *
     * @param context
     * @return
     */
    public static String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否有写入权限
     *
     * @param context
     * @return
     */
    public static boolean hasWriteExternalStorage(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取写入权限
     */
    public static void requestWriteStorage(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    public static String SHA1(String str) {
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2)
                    hexString.append(0);
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (Exception e) {
            Log.e("SHA1 error, str:{}", str, e);
        }
        return "";
    }

    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    /**
     * 下载应用内资源路径（统一路径！SO、APK、resources都要下载到这个路径）
     *
     * @param context
     * @return
     */
    public static String getApkOrSoDownloadPath(Context context) {
        return context.getApplicationContext().getDir("download", Context.MODE_PRIVATE).getPath();
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * 计算字符长度
     *
     * @param value
     * @return
     */
    public static int countString(String value) {
        value = value.replaceAll(" ", "");
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static String getLimitString(String value, int limit) {
        value = value.replaceAll(" ", "");
        int typesLength = countString(value);
        if (typesLength > limit) {
            return value.substring(0, limit) + "...";
        } else {
            return value;
        }
    }

    public static String getLanguageDetail(String language) {
        if (!TextUtils.isEmpty(language)) {
            switch (language) {
                case "en":
                    return BaseApplication.getContext().getString(R.string.english);
                case "fr":
                    return BaseApplication.getContext().getString(R.string.french);
                case "de":
                    return BaseApplication.getContext().getString(R.string.german);
                case "it":
                    return BaseApplication.getContext().getString(R.string.italian);
                case "ja":
                    return BaseApplication.getContext().getString(R.string.japanese);
                case "ko":
                    return BaseApplication.getContext().getString(R.string.korean);
                case "zh":
                    return BaseApplication.getContext().getString(R.string.chinese);
                case "ar":
                    return BaseApplication.getContext().getString(R.string.arabic);
                case "ca":
                    return BaseApplication.getContext().getString(R.string.catalan);
                case "cdo":
                    return BaseApplication.getContext().getString(R.string.min_dong);
                case "cs":
                    return BaseApplication.getContext().getString(R.string.czech);
                case "cy":
                    return BaseApplication.getContext().getString(R.string.cyprus);
                case "da":
                    return BaseApplication.getContext().getString(R.string.danish);
                case "el":
                    return BaseApplication.getContext().getString(R.string.greek);
                case "eo":
                    return BaseApplication.getContext().getString(R.string.esperanto);
                case "es":
                    return BaseApplication.getContext().getString(R.string.spanish);
                case "eu":
                    return BaseApplication.getContext().getString(R.string.basque);
                case "fa":
                    return BaseApplication.getContext().getString(R.string.farsi);
                case "fi":
                    return BaseApplication.getContext().getString(R.string.finnish);
//                case "gl":
//                    return BaseApplication.getContext().getString(R.string.galego‎);
                case "he":
                    return BaseApplication.getContext().getString(R.string.hebrew);
                case "hr":
                    return BaseApplication.getContext().getString(R.string.croatian);
                case "hu":
                    return BaseApplication.getContext().getString(R.string.hungarian);
//                case "id":
//                    return BaseApplication.getContext().getString(R.string.bahasa_indonesia‎);
//                case "ka":
//                    return BaseApplication.getContext().getString(R.string.kartuli‎);
                case "lt":
                    return BaseApplication.getContext().getString(R.string.lithuanian);
                case "mk":
                    return BaseApplication.getContext().getString(R.string.macedonian);
                case "ml":
                    return BaseApplication.getContext().getString(R.string.aramaic);
                case "ms":
                    return BaseApplication.getContext().getString(R.string.malaysia);
                case "nl":
                    return BaseApplication.getContext().getString(R.string.dutch);
                case "no":
                    return BaseApplication.getContext().getString(R.string.norwegian);
                case "pl":
                    return BaseApplication.getContext().getString(R.string.polish);
                case "pt":
                    return BaseApplication.getContext().getString(R.string.portugal);
                case "ro":
                    return BaseApplication.getContext().getString(R.string.romanian);
                case "ru":
                    return BaseApplication.getContext().getString(R.string.russian);
                case "sl":
                    return BaseApplication.getContext().getString(R.string.slovenian);
                case "sv":
                    return BaseApplication.getContext().getString(R.string.swedish);
                case "ta":
                    return BaseApplication.getContext().getString(R.string.tamil);
                case "th":
                    return BaseApplication.getContext().getString(R.string.thai);
                case "tl":
                    return BaseApplication.getContext().getString(R.string.tagalog);
                case "tr":
                    return BaseApplication.getContext().getString(R.string.turkish);
                case "uk":
                    return BaseApplication.getContext().getString(R.string.ukrainian);
                case "vi":
                    return BaseApplication.getContext().getString(R.string.vietnamese);
                case "za":
                    return BaseApplication.getContext().getString(R.string.zhuang);
                case "in":
                    return BaseApplication.getContext().getString(R.string.hindi);
                default:
                    return language;
            }
        } else {
            return "";
        }
    }

    /**
     * 获取国家详情
     *
     * @param country
     * @return
     */
    public static String getCountryDetail(String country) {
        if (!TextUtils.isEmpty(country)) {
            switch (country) {
                case "AD":
                    return BaseApplication.getContext().getString(R.string.country_andorra);
                case "AE":
                    return BaseApplication.getContext().getString(R.string.country_united_arab_emirates);
                case "AF":
                    return BaseApplication.getContext().getString(R.string.country_afghanistan);
                case "AG":
                    return BaseApplication.getContext().getString(R.string.country_antigua_and_barbuda);
                case "AI":
                    return BaseApplication.getContext().getString(R.string.country_anguilla);
                case "AL":
                    return BaseApplication.getContext().getString(R.string.country_albania);
                case "AM":
                    return BaseApplication.getContext().getString(R.string.country_armenia);
                case "AO":
                    return BaseApplication.getContext().getString(R.string.country_angola);
                case "AR":
                    return BaseApplication.getContext().getString(R.string.country_argentina);
                case "AT":
                    return BaseApplication.getContext().getString(R.string.country_austria);
                case "AU":
                    return BaseApplication.getContext().getString(R.string.country_australia);
                case "AZ":
                    return BaseApplication.getContext().getString(R.string.country_azerbaijan);
                case "BB":
                    return BaseApplication.getContext().getString(R.string.country_barbados);
                case "BD":
                    return BaseApplication.getContext().getString(R.string.country_bangladesh);
                case "BE":
                    return BaseApplication.getContext().getString(R.string.country_belgium);
                case "BF":
                    return BaseApplication.getContext().getString(R.string.country_burkina_faso);
                case "BG":
                    return BaseApplication.getContext().getString(R.string.country_bulgaria);
                case "BH":
                    return BaseApplication.getContext().getString(R.string.country_bahrain);
                case "BI":
                    return BaseApplication.getContext().getString(R.string.country_burundi);
                case "BJ":
                    return BaseApplication.getContext().getString(R.string.country_benin);
                case "BL":
                    return BaseApplication.getContext().getString(R.string.country_palestine);
                case "BM":
                    return BaseApplication.getContext().getString(R.string.country_bermuda_is);
                case "BN":
                    return BaseApplication.getContext().getString(R.string.country_brunei);
                case "BO":
                    return BaseApplication.getContext().getString(R.string.country_bolivia);
                case "BR":
                    return BaseApplication.getContext().getString(R.string.country_brazil);
                case "BS":
                    return BaseApplication.getContext().getString(R.string.country_bahamas);
                case "BW":
                    return BaseApplication.getContext().getString(R.string.country_botswana);
                case "BY":
                    return BaseApplication.getContext().getString(R.string.country_belarus);
                case "BZ":
                    return BaseApplication.getContext().getString(R.string.country_belize);
                case "CA":
                    return BaseApplication.getContext().getString(R.string.country_canada);
                case "CF":
                    return BaseApplication.getContext().getString(R.string.country_central_african_republic);
                case "CG":
                    return BaseApplication.getContext().getString(R.string.country_congo);
                case "CH":
                    return BaseApplication.getContext().getString(R.string.country_switzerland);
                case "CK":
                    return BaseApplication.getContext().getString(R.string.country_cook_is);
                case "CL":
                    return BaseApplication.getContext().getString(R.string.country_chile);
                case "CM":
                    return BaseApplication.getContext().getString(R.string.country_cameroon);
                case "CN":
                    return BaseApplication.getContext().getString(R.string.country_china);
                case "CO":
                    return BaseApplication.getContext().getString(R.string.country_colombia);
                case "CR":
                    return BaseApplication.getContext().getString(R.string.country_costa_rica);
                case "CS":
                    return BaseApplication.getContext().getString(R.string.country_czech);
                case "CU":
                    return BaseApplication.getContext().getString(R.string.country_cuba);
                case "CY":
                    return BaseApplication.getContext().getString(R.string.country_cyprus);
                case "CZ":
                    return BaseApplication.getContext().getString(R.string.country_czech_republic);
                case "DE":
                    return BaseApplication.getContext().getString(R.string.country_germany);
                case "DJ":
                    return BaseApplication.getContext().getString(R.string.country_djibouti);
                case "DK":
                    return BaseApplication.getContext().getString(R.string.country_denmark);
                case "DO":
                    return BaseApplication.getContext().getString(R.string.country_dominica_rep);
                case "DZ":
                    return BaseApplication.getContext().getString(R.string.country_algeria);
                case "EC":
                    return BaseApplication.getContext().getString(R.string.country_ecuador);
                case "EE":
                    return BaseApplication.getContext().getString(R.string.country_estonia);
                case "EG":
                    return BaseApplication.getContext().getString(R.string.country_egypt);
                case "ES":
                    return BaseApplication.getContext().getString(R.string.country_spain);
                case "ET":
                    return BaseApplication.getContext().getString(R.string.country_ethiopia);
                case "FI":
                    return BaseApplication.getContext().getString(R.string.country_finland);
                case "FJ":
                    return BaseApplication.getContext().getString(R.string.country_fiji);
                case "FR":
                    return BaseApplication.getContext().getString(R.string.country_france);
                case "GA":
                    return BaseApplication.getContext().getString(R.string.country_gabon);
                case "GB":
                    return BaseApplication.getContext().getString(R.string.country_united_kiongdom);
                case "GD":
                    return BaseApplication.getContext().getString(R.string.country_grenada);
                case "GE":
                    return BaseApplication.getContext().getString(R.string.country_georgia);
                case "GF":
                    return BaseApplication.getContext().getString(R.string.country_french_guiana);
                case "GH":
                    return BaseApplication.getContext().getString(R.string.country_ghana);
                case "GI":
                    return BaseApplication.getContext().getString(R.string.country_gibraltar);
                case "GM":
                    return BaseApplication.getContext().getString(R.string.country_gambia);
                case "GN":
                    return BaseApplication.getContext().getString(R.string.country_guinea);
                case "GR":
                    return BaseApplication.getContext().getString(R.string.country_greece);
                case "GT":
                    return BaseApplication.getContext().getString(R.string.country_guatemala);
                case "GU":
                    return BaseApplication.getContext().getString(R.string.country_guam);
                case "GY":
                    return BaseApplication.getContext().getString(R.string.country_guyana);
                case "HK":
                    return BaseApplication.getContext().getString(R.string.country_hongkong);
                case "HN":
                    return BaseApplication.getContext().getString(R.string.country_honduras);
                case "HT":
                    return BaseApplication.getContext().getString(R.string.country_haiti);
                case "HU":
                    return BaseApplication.getContext().getString(R.string.country_hungary);
                case "ID":
                    return BaseApplication.getContext().getString(R.string.country_indonesia);
                case "IE":
                    return BaseApplication.getContext().getString(R.string.country_ireland);
                case "IL":
                    return BaseApplication.getContext().getString(R.string.country_israel);
                case "IN":
                    return BaseApplication.getContext().getString(R.string.country_india);
                case "IQ":
                    return BaseApplication.getContext().getString(R.string.country_iraq);
                case "IR":
                    return BaseApplication.getContext().getString(R.string.country_iran);
                case "IS":
                    return BaseApplication.getContext().getString(R.string.country_iceland);
                case "IT":
                    return BaseApplication.getContext().getString(R.string.country_italy);
                case "JM":
                    return BaseApplication.getContext().getString(R.string.country_jamaica);
                case "JO":
                    return BaseApplication.getContext().getString(R.string.country_jordan);
                case "JP":
                    return BaseApplication.getContext().getString(R.string.country_japan);
                case "KE":
                    return BaseApplication.getContext().getString(R.string.country_kenya);
                case "KG":
                    return BaseApplication.getContext().getString(R.string.country_kyrgyzstan);
                case "KH":
                    return BaseApplication.getContext().getString(R.string.country_kampuchea_cambodia);
                case "KP":
                    return BaseApplication.getContext().getString(R.string.country_north_korea);
                case "KR":
                    return BaseApplication.getContext().getString(R.string.country_korea);
                case "KT":
                    return BaseApplication.getContext().getString(R.string.country_republic_of_ivory_coast);
                case "KW":
                    return BaseApplication.getContext().getString(R.string.country_kuwait);
                case "KZ":
                    return BaseApplication.getContext().getString(R.string.country_kazakstan);
                case "LA":
                    return BaseApplication.getContext().getString(R.string.country_laos);
                case "LB":
                    return BaseApplication.getContext().getString(R.string.country_lebanon);
                case "LC":
                    return BaseApplication.getContext().getString(R.string.country_stlucia);
                case "LI":
                    return BaseApplication.getContext().getString(R.string.country_liechtenstein);
                case "LK":
                    return BaseApplication.getContext().getString(R.string.country_sri_lanka);
                case "LR":
                    return BaseApplication.getContext().getString(R.string.country_liberia);
                case "LS":
                    return BaseApplication.getContext().getString(R.string.country_lesotho);
                case "LT":
                    return BaseApplication.getContext().getString(R.string.country_lithuania);
                case "LU":
                    return BaseApplication.getContext().getString(R.string.country_luxembourg);
                case "LV":
                    return BaseApplication.getContext().getString(R.string.country_latvia);
                case "LY":
                    return BaseApplication.getContext().getString(R.string.country_libya);
                case "MA":
                    return BaseApplication.getContext().getString(R.string.country_morocco);
                case "MC":
                    return BaseApplication.getContext().getString(R.string.country_monaco);
                case "MD":
                    return BaseApplication.getContext().getString(R.string.country_moldova_republic_of);
                case "MG":
                    return BaseApplication.getContext().getString(R.string.country_madagascar);
                case "ML":
                    return BaseApplication.getContext().getString(R.string.country_mali);
                case "MM":
                    return BaseApplication.getContext().getString(R.string.country_burma);
                case "MN":
                    return BaseApplication.getContext().getString(R.string.country_mongolia);
                case "MO":
                    return BaseApplication.getContext().getString(R.string.country_macao);
                case "MS":
                    return BaseApplication.getContext().getString(R.string.country_montserrat_is);
                case "MT":
                    return BaseApplication.getContext().getString(R.string.country_malta);
                case "MU":
                    return BaseApplication.getContext().getString(R.string.country_mauritius);
                case "MV":
                    return BaseApplication.getContext().getString(R.string.country_maldives);
                case "MW":
                    return BaseApplication.getContext().getString(R.string.country_malawi);
                case "MX":
                    return BaseApplication.getContext().getString(R.string.country_mexico);
                case "MY":
                    return BaseApplication.getContext().getString(R.string.country_malaysia);
                case "MZ":
                    return BaseApplication.getContext().getString(R.string.country_mozambique);
                case "NA":
                    return BaseApplication.getContext().getString(R.string.country_namibia);
                case "NE":
                    return BaseApplication.getContext().getString(R.string.country_niger);
                case "NG":
                    return BaseApplication.getContext().getString(R.string.country_nigeria);
                case "NI":
                    return BaseApplication.getContext().getString(R.string.country_nicaragua);
                case "NL":
                    return BaseApplication.getContext().getString(R.string.country_netherlands);
                case "NO":
                    return BaseApplication.getContext().getString(R.string.country_norway);
                case "NP":
                    return BaseApplication.getContext().getString(R.string.country_nepal);
                case "NR":
                    return BaseApplication.getContext().getString(R.string.country_nauru);
                case "NZ":
                    return BaseApplication.getContext().getString(R.string.country_new_zealand);
                case "OM":
                    return BaseApplication.getContext().getString(R.string.country_oman);
                case "PA":
                    return BaseApplication.getContext().getString(R.string.country_panama);
                case "PE":
                    return BaseApplication.getContext().getString(R.string.country_peru);
                case "PF":
                    return BaseApplication.getContext().getString(R.string.country_french_polynesia);
                case "PG":
                    return BaseApplication.getContext().getString(R.string.country_papua_new_cuinea);
                case "PH":
                    return BaseApplication.getContext().getString(R.string.country_philippines);
                case "PK":
                    return BaseApplication.getContext().getString(R.string.country_pakistan);
                case "PL":
                    return BaseApplication.getContext().getString(R.string.country_poland);
                case "PR":
                    return BaseApplication.getContext().getString(R.string.country_puerto_rico);
                case "PT":
                    return BaseApplication.getContext().getString(R.string.country_portugal);
                case "PY":
                    return BaseApplication.getContext().getString(R.string.country_paraguay);
                case "QA":
                    return BaseApplication.getContext().getString(R.string.country_qatar);
                case "RO":
                    return BaseApplication.getContext().getString(R.string.country_romania);
                case "RU":
                    return BaseApplication.getContext().getString(R.string.country_russia);
                case "SA":
                    return BaseApplication.getContext().getString(R.string.country_saudi_arabia);
                case "SB":
                    return BaseApplication.getContext().getString(R.string.country_solomon_is);
                case "SC":
                    return BaseApplication.getContext().getString(R.string.country_seychelles);
                case "SD":
                    return BaseApplication.getContext().getString(R.string.country_sudan);
                case "SE":
                    return BaseApplication.getContext().getString(R.string.country_sweden);
                case "SG":
                    return BaseApplication.getContext().getString(R.string.country_singapore);
                case "SI":
                    return BaseApplication.getContext().getString(R.string.country_slovenia);
                case "SK":
                    return BaseApplication.getContext().getString(R.string.country_slovakia);
                case "SL":
                    return BaseApplication.getContext().getString(R.string.country_sierra_leone);
                case "SM":
                    return BaseApplication.getContext().getString(R.string.country_san_marino);
                case "SN":
                    return BaseApplication.getContext().getString(R.string.country_senegal);
                case "SO":
                    return BaseApplication.getContext().getString(R.string.country_somali);
                case "SR":
                    return BaseApplication.getContext().getString(R.string.country_suriname);
                case "ST":
                    return BaseApplication.getContext().getString(R.string.country_sao_tome_and_principe);
                case "SV":
                    return BaseApplication.getContext().getString(R.string.country_ei_salvador);
                case "SY":
                    return BaseApplication.getContext().getString(R.string.country_syria);
                case "SZ":
                    return BaseApplication.getContext().getString(R.string.country_swaziland);
                case "TD":
                    return BaseApplication.getContext().getString(R.string.country_chad);
                case "TG":
                    return BaseApplication.getContext().getString(R.string.country_togo);
                case "TH":
                    return BaseApplication.getContext().getString(R.string.country_thailand);
                case "TJ":
                    return BaseApplication.getContext().getString(R.string.country_tajikstan);
                case "TM":
                    return BaseApplication.getContext().getString(R.string.country_turkmenistan);
                case "TN":
                    return BaseApplication.getContext().getString(R.string.country_tunisia);
                case "TO":
                    return BaseApplication.getContext().getString(R.string.country_tonga);
                case "TR":
                    return BaseApplication.getContext().getString(R.string.country_turkey);
                case "TT":
                    return BaseApplication.getContext().getString(R.string.country_trinidad_and_tobago);
                case "TW":
                    return BaseApplication.getContext().getString(R.string.country_taiwan);
                case "TZ":
                    return BaseApplication.getContext().getString(R.string.country_tanzania);
                case "UA":
                    return BaseApplication.getContext().getString(R.string.country_ukraine);
                case "UG":
                    return BaseApplication.getContext().getString(R.string.country_uganda);
                case "US":
                    return BaseApplication.getContext().getString(R.string.country_united_states_of_america);
                case "UY":
                    return BaseApplication.getContext().getString(R.string.country_uruguay);
                case "UZ":
                    return BaseApplication.getContext().getString(R.string.country_uzbekistan);
                case "VC":
                    return BaseApplication.getContext().getString(R.string.country_saint_vincent);
                case "VE":
                    return BaseApplication.getContext().getString(R.string.country_venezuela);
                case "VN":
                    return BaseApplication.getContext().getString(R.string.country_vietnam);
                case "YE":
                    return BaseApplication.getContext().getString(R.string.country_yemen);
                case "YU":
                    return BaseApplication.getContext().getString(R.string.country_yugoslavia);
                case "ZA":
                    return BaseApplication.getContext().getString(R.string.country_south_africa);
                case "ZM":
                    return BaseApplication.getContext().getString(R.string.country_zambia);
                case "ZR":
                    return BaseApplication.getContext().getString(R.string.country_zaire);
                case "ZW":
                    return BaseApplication.getContext().getString(R.string.country_zimbabwe);
                default:
                    return country;
            }
        } else {
            return "";
        }

    }

    /**
     * 保存bitmap到本地
     *
     * @param bitmap
     * @return
     */
    public static void saveBitmap(Context context, Bitmap bitmap, String fileName) {
        File filePic;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

        } else {
            Log.d("xxx", "saveBitmap: 1return");
            return;
        }
        try {
            filePic = new File(Environment.getExternalStorageDirectory(), "DCIM/" + fileName + ".png");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(filePic)));//更新系统相册
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("xxx", "saveBitmap: 2return");
            return;
        }
        Log.d("xxx", "saveBitmap: " + filePic.getAbsolutePath());
    }

    /**
     * 限制仅输入中文和数字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter2(String str) throws PatternSyntaxException {
        //只允许数字和汉字
        String regEx = "[^0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
