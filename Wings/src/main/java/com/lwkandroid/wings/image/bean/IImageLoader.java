package com.lwkandroid.wings.image.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;

/**
 * Created by LWK
 * TODO 定义图片加载方法入口的接口
 */

public interface IImageLoader
{
    /**
     * 加载图片
     */
    ImageBaseOptions load(String url);

    /**
     * 加载图片
     */
    ImageBaseOptions load(Uri uri);

    /**
     * 加载图片
     */
    ImageBaseOptions load(Drawable drawable);

    /**
     * 加载图片
     */
    ImageBaseOptions load(@DrawableRes int resId);

    /**
     * 加载图片
     */
    ImageBaseOptions load(Bitmap bitmap);

    /**
     * 加载图片
     */
    ImageBaseOptions load(byte[] bytes);

    /**
     * 暂停加载
     */
    void pause(Context context);

    /**
     * 恢复加载
     */
    void resume(Context context);

    /**
     * 清除缓存
     */
    void clearCache(Context context);

    /**
     * 获取缓存地址
     */
    String getCachePath();
}
