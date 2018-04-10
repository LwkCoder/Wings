package com.lwkandroid.wings.image;

import com.lwkandroid.wings.image.bean.IImageLoader;
import com.lwkandroid.wings.image.bean.ImageGlobalOptions;
import com.lwkandroid.wings.image.glide.GlideImageLoader;

/**
 * Created by LWK
 * TODO 向外公布的图片加载类
 */
public final class ImageLoader
{
    static
    {
        mGlobalOptions = new ImageGlobalOptions();
        mLoader = new GlideImageLoader();
        mGlideLoader = new GlideImageLoader();
    }

    private static final ImageGlobalOptions mGlobalOptions;

    private static final IImageLoader mLoader;

    private static final GlideImageLoader mGlideLoader;

    private ImageLoader()
    {
    }

    /**
     * 获取全局配置
     */
    public static ImageGlobalOptions getGlobalOptions()
    {
        return mGlobalOptions;
    }

    /**
     * 获取图片加载器对象
     */
    public static IImageLoader getDefaultLoader()
    {
        return mLoader;
    }

    /**
     * 获取Glide图片加载对象
     * 【可以用Glide独有的方法】
     */
    public static GlideImageLoader getGlideLoader()
    {
        return mGlideLoader;
    }
}