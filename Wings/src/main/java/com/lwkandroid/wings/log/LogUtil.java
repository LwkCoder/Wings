package com.lwkandroid.wings.log;

import android.text.TextUtils;
import android.util.Log;

/**
 * 日志工具类
 */
class LogUtil
{
    public static boolean isEmpty(String line)
    {
        return TextUtils.isEmpty(line) || line.equals("\n") || line.equals("\t") || TextUtils.isEmpty(line.trim());
    }

    static void printLine(String tag, boolean isTop)
    {
        if (isTop)
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        else
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
    }

}
