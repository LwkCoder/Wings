package com.lwkandroid.wings.log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.lwkandroid.wings.utils.FileIOUtils;

import java.io.File;
import java.util.Random;

/**
 * 打印文件日志的工具
 */
class FileLog
{
    private static final String FILE_PREFIX = "KLog_";
    private static final String FILE_FORMAT = ".log";

    static void printFile(String tag, File targetDirectory, @Nullable String fileName, String headString, String msg)
    {
        fileName = (fileName == null) ? getFileName() : fileName;
        if (save(targetDirectory, fileName, msg))
        {
            Log.d(tag, headString + " save log success ! location is >>>" + targetDirectory.getAbsolutePath() + "/" + fileName);
        } else
        {
            Log.e(tag, headString + "save log fails !");
        }
    }

    private static boolean save(File dic, @NonNull String fileName, String msg)
    {
        File file = new File(dic, fileName);
        return FileIOUtils.writeFileFromString(file, msg);
    }

    private static String getFileName()
    {
        Random random = new Random();
        return FILE_PREFIX + Long.toString(System.currentTimeMillis() + random.nextInt(10000)).substring(4) + FILE_FORMAT;
    }
}
