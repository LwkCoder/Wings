package com.lwkandroid.wings.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.lwkandroid.wings.Wings;

import java.io.File;

import androidx.annotation.RequiresPermission;

/**
 * 手机设备相关工具类
 *
 * @author LWK
 */
public final class PhoneUtils
{

    private PhoneUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isRooted()
    {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/"
                , "/sbin/", "/system/sd/xbin/"
                , "/system/bin/failsafe/", "/data/local/xbin/"
                , "/data/local/bin/", "/data/local/"};
        for (String location : locations)
        {
            if (new File(location + su).exists())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion()
    {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID()
    {
        return Settings.Secure.getString(Wings.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备厂商
     *
     * @return 设备厂商
     */
    public static String getManufacturer()
    {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     *
     * @return 设备型号
     */
    public static String getModel()
    {
        String model = Build.MODEL;
        if (model != null)
        {
            model = model.trim().replaceAll("\\s*", "");
        } else
        {
            model = "";
        }
        return model;
    }

    private static TelephonyManager getTeleManager()
    {
        return (TelephonyManager) Wings.getContext().getSystemService(Context.TELEPHONY_SERVICE);
    }

    /**
     * 判断设置是否是手机
     */
    public static boolean isPhone()
    {
        TelephonyManager manager = getTeleManager();
        return manager != null && manager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 获取设备IMEI码
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI()
    {
        TelephonyManager manager = getTeleManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            return manager != null ? manager.getImei() : null;
        } else
        {
            return manager != null ? manager.getDeviceId() : null;
        }
    }

    /**
     * 获取设备MEID码
     */
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    public static String getMEID()
    {
        TelephonyManager manager = getTeleManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            return manager != null ? manager.getMeid() : null;
        } else
        {
            return manager != null ? manager.getDeviceId() : null;
        }
    }

    /**
     * 拨打电话【调用前需要申请权限android.permission.CALL_PHONE】
     *
     * @param phoneNumber 电话号码
     */
    public static void dial(String phoneNumber)
    {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Wings.getContext().startActivity(intent);
    }

    /**
     * 跳转拨号盘
     *
     * @param teleNumber 电话号码
     */
    public static void skip(String teleNumber)
    {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + teleNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Wings.getContext().startActivity(intent);
    }

    /**
     * 发送短信【调用前需要申请权限android.permission.SEND_SMS】
     *
     * @param teleNumner 电话号码
     * @param message    短信内容
     */
    public static void sendSMS(String teleNumner, String message)
    {
        Uri uri = Uri.parse("smsto:" + teleNumner);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Wings.getContext().startActivity(intent);
    }
}
