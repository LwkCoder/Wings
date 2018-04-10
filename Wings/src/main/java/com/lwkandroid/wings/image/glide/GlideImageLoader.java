package com.lwkandroid.wings.image.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wings.image.bean.IImageLoader;
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
    public void test(){}
    @Override
    public GlideImageOptions load(String url)
    {
        return new GlideImageOptions().setResource(url);
    }

    @Override
    public GlideImageOptions load(Uri uri)
    {
        return new GlideImageOptions().setResource(uri);
    }

    @Override
    public GlideImageOptions load(Drawable drawable)
    {
        return new GlideImageOptions().setResource(drawable);
    }

    @Override
    public GlideImageOptions load(int resId)
    {
        return new GlideImageOptions().setResource(resId);
    }

    @Override
    public GlideImageOptions load(Bitmap bitmap)
    {
        return new GlideImageOptions().setResource(bitmap);
    }

    @Override
    public GlideImageOptions load(byte[] bytes)
    {
        return new GlideImageOptions().setResource(bytes);
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
