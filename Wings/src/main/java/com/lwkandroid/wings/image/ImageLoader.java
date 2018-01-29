package com.lwkandroid.wings.image;

import com.lwkandroid.wings.image.glide.GlideImageLoader;

/**
 * Created by LWK
 * TODO 向外公布的图片加载类
 */
public final class ImageLoader
{
    private ImageLoader()
    {
    }

    static
    {
        IMPL = new GlideImageLoader();
    }

    private static final IImageLoaderStrategy IMPL;

    public static IImageLoaderStrategy get()
    {
        return IMPL;
    }

}