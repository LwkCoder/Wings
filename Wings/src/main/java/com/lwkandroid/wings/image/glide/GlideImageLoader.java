package com.lwkandroid.wings.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.lwkandroid.wings.image.bean.IImageLoader;
import com.lwkandroid.wings.image.bean.ImageBaseOptions;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wings.utils.SDCardUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.Utils;
import com.socks.library.KLog;

/**
 * Created by LWK
 * TODO Glide实现的图片加载
 */

public final class GlideImageLoader implements IImageLoader
{
    @Override
    public ImageBaseOptions load(String url)
    {
        GlideImageOptions options = new GlideImageOptions();
        options.setResource(url);
        return options;
    }

    @Override
    public ImageBaseOptions load(Uri uri)
    {
        GlideImageOptions options = new GlideImageOptions();
        options.setResource(uri);
        return options;
    }

    @Override
    public ImageBaseOptions load(Drawable drawable)
    {
        GlideImageOptions options = new GlideImageOptions();
        options.setResource(drawable);
        return options;
    }

    @Override
    public ImageBaseOptions load(int resId)
    {
        GlideImageOptions options = new GlideImageOptions();
        options.setResource(resId);
        return options;
    }

    @Override
    public ImageBaseOptions load(Bitmap bitmap)
    {
        GlideImageOptions options = new GlideImageOptions();
        options.setResource(bitmap);
        return options;
    }

    @Override
    public ImageBaseOptions load(byte[] bytes)
    {
        GlideImageOptions options = new GlideImageOptions();
        options.setResource(bytes);
        return options;
    }

    @Override
    public void pause(Context context)
    {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void resume(Context context)
    {
        Glide.with(context).resumeRequests();
    }

    @Override
    public void clearCache(Context context)
    {
        try
        {
            final Glide glide = Glide.get(Utils.getContext());
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
            KLog.e("ImageLoader can not clearCache:" + e.toString());
        }
    }

    @Override
    public String getCachePath()
    {
        if (StringUtils.isNotEmpty(ImageLoader.getGlobalOptions().getCachePath()))
            return ImageLoader.getGlobalOptions().getCachePath();
        else
            return new StringBuffer()
                    .append(SDCardUtils.getExternalCachePath())
                    .append("glide/").toString();
    }
}
