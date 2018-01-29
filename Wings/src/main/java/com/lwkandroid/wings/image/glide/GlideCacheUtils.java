package com.lwkandroid.wings.image.glide;

import com.bumptech.glide.Glide;
import com.lwkandroid.wings.utils.SDCardUtils;
import com.lwkandroid.wings.utils.Utils;

/**
 * Created by LWK
 * TODO Glide缓存工具类
 */

public class GlideCacheUtils
{
    /**
     * 获取sd卡下缓存路径
     */
    public static String getCachePath()
    {
        return new StringBuffer()
                .append(SDCardUtils.getExternalCachePath())
                .append("glide/").toString();
    }

    /**
     * 清除缓存
     */
    public static void clearCache()
    {
        try
        {
            final Glide glide = GlideApp.get(Utils.getContext());
            glide.clearMemory();
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    glide.clearDiskCache();
                }
            }).start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
