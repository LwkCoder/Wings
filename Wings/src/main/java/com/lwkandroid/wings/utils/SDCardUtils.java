package com.lwkandroid.wings.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * SD卡相关工具类
 */
public final class SDCardUtils
{

    private SDCardUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 判断SD卡是否可用
     */
    public static boolean isSDCardEnable()
    {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径（一般是：/storage/emulated/0/）
     */
    public static String getSDCardPath()
    {
        if (!isSDCardEnable())
            return null;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        return path.endsWith(File.separator) ? path : path + File.separatorChar;
    }

    /**
     * 获取默认缓存路径（一般是：/storage/emulated/0/Android/data/package-name/data/）
     *
     * @return 缓存路径
     */
    public static String getExternalCachePath()
    {
        if (!isSDCardEnable())
            return null;

        File file = Utils.getContext().getExternalCacheDir();
        if (file == null)
        {
            //部分ROM获取不到地址就自行拼接
            StringBuffer buffer = new StringBuffer();
            buffer.append(getSDCardPath())
                    .append("Android/data/")
                    .append(AppUtils.getPackageName())
                    .append("/cache/");
            file = new File(buffer.toString());
            return file.mkdirs() ? file.getAbsolutePath() : null;
        }

        String path = file.getAbsolutePath();
        return path.endsWith(File.separator) ? path : path + File.separatorChar;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间（单位：byte）
     */
    public static long getFreeSpace()
    {
        if (!isSDCardEnable())
            return 0;
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocks();
        blockSize = stat.getBlockSize();
        return availableBlocks * blockSize;
    }
}