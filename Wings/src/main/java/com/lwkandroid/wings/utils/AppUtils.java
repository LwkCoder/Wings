package com.lwkandroid.wings.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;

import com.lwkandroid.wings.log.KLog;

import java.io.File;
import java.util.List;

/**
 * App相关工具类
 */

public final class AppUtils
{
    private AppUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 获取某个进程名字
     *
     * @param pid 进程id
     */
    public static String getProcessName(int pid)
    {
        ActivityManager am = (ActivityManager) Utils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null)
        {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps)
        {
            if (procInfo.pid == pid)
            {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取App包名
     *
     * @return 包名字符串
     */
    public static String getPackageName()
    {
        return Utils.getContext().getPackageName();
    }

    /**
     * 获取App名称
     *
     * @return App名称
     */
    public static String getAppName()
    {
        try
        {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本名称
     *
     * @return App版本名称
     */
    public static String getAppVersionName()
    {
        try
        {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static int getAppVersionCode()
    {
        try
        {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取App图标
     *
     * @return App图标
     */
    public static Drawable getAppIcon()
    {
        try
        {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App签名
     *
     * @return App签名
     */
    @SuppressLint("PackageManagerGetSignatures")
    public static Signature[] getAppSignature()
    {
        try
        {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取应用签名的的SHA1值
     *
     * @return 应用签名的SHA1字符串
     */
    public static String getAppSignatureSHA1()
    {
        Signature[] signature = getAppSignature();
        if (signature == null)
            return null;
        return EncryptUtils.encryptSHA1ToString(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 判断当前程序是否正在运行（需要权限android.permission.GET_TASKS）
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppRunning()
    {
        int pid = android.os.Process.myPid();
        String packageName = getPackageName();

        ActivityManager manager = (ActivityManager) Utils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0)
            return false;

        for (ActivityManager.RunningAppProcessInfo info : infos)
        {
            if (info != null && info.pid == pid && StringUtils.isEquals(packageName, info.processName))
                return true;
        }
        return false;
    }

    /**
     * 判断当前程序是否处于前台（需要权限android.permission.GET_TASKS）
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground()
    {
        int pid = android.os.Process.myPid();
        String packageName = getPackageName();

        ActivityManager manager = (ActivityManager) Utils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0)
            return false;

        for (ActivityManager.RunningAppProcessInfo info : infos)
        {
            if (info != null && info.pid == pid && StringUtils.isEquals(packageName, info.processName))
                return info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        }
        return false;
    }

    /**
     * 判断当前程序是否处于后台（需要权限android.permission.GET_TASKS）
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppBackground()
    {
        int pid = android.os.Process.myPid();
        String packageName = getPackageName();

        ActivityManager manager = (ActivityManager) Utils.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        if (infos == null || infos.size() == 0)
            return false;

        for (ActivityManager.RunningAppProcessInfo info : infos)
        {
            if (info != null && info.pid == pid && StringUtils.isEquals(packageName, info.processName))
                return info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
        }
        return false;
    }

    /**
     * 创建用于安装apk的Intent，兼容7.0
     *
     * @param apkPath apk路径
     * @return Intent
     */
    public static Intent createInstallIntent(String apkPath)
    {
        if (StringUtils.isEmpty(apkPath) || !new File(apkPath).exists())
        {
            KLog.e("Can't install apk due to an invaild path !!!");
            return null;
        }

        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
        {
            data = Uri.fromFile(file);
        } else
        {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(Utils.getContext()
                    , InstallApkFileProvider.getAuthorities(Utils.getContext()), file);
        }
        intent.setDataAndType(data, type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 安装Apk【兼容7.0】
     * 调用此方法时需要提前获取SD卡读取权限
     *
     * @param apkPath apk绝对路径
     */
    public static void installApk(String apkPath)
    {
        Intent intent = createInstallIntent(apkPath);
        if (intent != null)
            Utils.getContext().startActivity(intent);
    }

    /**
     * 跳转到App设置详情界面
     */
    public static void goToSettingDetail()
    {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + Utils.getContext().getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Utils.getContext().startActivity(intent);
    }

    /**
     * 安装apk授权的FileProvider
     */
    public final static class InstallApkFileProvider extends FileProvider
    {
        public static String getAuthorities(Context context)
        {
            return new StringBuilder()
                    .append(context.getPackageName())
                    .append("_install.provider")
                    .toString();
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param packageName：应用包名
     * @return
     */
    public static boolean isAppInstalled(String packageName)
    {
        final PackageManager packageManager = Utils.getContext().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null)
        {
            for (int i = 0; i < packageInfos.size(); i++)
            {
                String pkName = packageInfos.get(i).packageName;
                if (pkName.equals(packageName))
                    return true;
            }
        }
        return false;
    }
}
