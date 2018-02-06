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
    }

    private static final ImageGlobalOptions mGlobalOptions;

    private static final IImageLoader mLoader;

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
    public static IImageLoader getLoader()
    {
        return mLoader;
    }
}