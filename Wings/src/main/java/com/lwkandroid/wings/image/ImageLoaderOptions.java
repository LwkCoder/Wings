package com.lwkandroid.wings.image;

import android.support.annotation.DrawableRes;

/**
 * Created by LWK
 * TODO 图片加载额外参数
 */

public class ImageLoaderOptions implements Cloneable
{
    static
    {
        DEAFULT_OPTIONS = new ImageLoaderOptions();
    }

    private static final ImageLoaderOptions DEAFULT_OPTIONS;

    public static ImageLoaderOptions getDefault()
    {
        return DEAFULT_OPTIONS;
    }

    private ImageLoaderOptions()
    {
    }

    @DrawableRes
    private int placeHolder = -1;

    @DrawableRes
    private int errorHolder = -1;

    private int width = -1;

    private int height = -1;

    @ImageDiskCacheType.Type
    private int diskCacheType = ImageDiskCacheType.DEFAULT;

    private boolean asGif = false;

    private boolean skipMemoryCache = false;

    private boolean crossFade = true;

    private int crossFadeDuration = 200;

    private float thumbRate = 0.0f;

    public int getPlaceHolder()
    {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder)
    {
        this.placeHolder = placeHolder;
    }

    public int getErrorHolder()
    {
        return errorHolder;
    }

    public void setErrorHolder(int errorHolder)
    {
        this.errorHolder = errorHolder;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getDiskCacheType()
    {
        return diskCacheType;
    }

    public void setDiskCacheType(int diskCacheType)
    {
        this.diskCacheType = diskCacheType;
    }

    public boolean isAsGif()
    {
        return asGif;
    }

    public void setAsGif(boolean asGif)
    {
        this.asGif = asGif;
    }

    public boolean isSkipMemoryCache()
    {
        return skipMemoryCache;
    }

    public void setSkipMemoryCache(boolean skipMemoryCache)
    {
        this.skipMemoryCache = skipMemoryCache;
    }

    public boolean isCrossFade()
    {
        return crossFade;
    }

    public void setCrossFade(boolean crossFade)
    {
        this.crossFade = crossFade;
    }

    public int getCrossFadeDuration()
    {
        return crossFadeDuration;
    }

    public void setCrossFadeDuration(int crossFadeDuration)
    {
        this.crossFadeDuration = crossFadeDuration;
    }

    public float getThumbRate()
    {
        return thumbRate;
    }

    public void setThumbRate(float thumbRate)
    {
        this.thumbRate = thumbRate;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

    public static final class Builder
    {
        private ImageLoaderOptions options;

        public Builder()
        {
            options = new ImageLoaderOptions();
        }

        public Builder placeHolder(@DrawableRes int resId)
        {
            options.setPlaceHolder(resId);
            return this;
        }

        public Builder errorHolder(@DrawableRes int resId)
        {
            options.setErrorHolder(resId);
            return this;
        }

        public Builder override(int width, int height)
        {
            options.setWidth(width);
            options.setHeight(height);
            return this;
        }

        public Builder diskCacheType(@ImageDiskCacheType.Type int type)
        {
            options.setDiskCacheType(type);
            return this;
        }

        public Builder asGif(boolean b)
        {
            options.setAsGif(b);
            return this;
        }

        public Builder skipMemoryCache(boolean b)
        {
            options.setSkipMemoryCache(b);
            return this;
        }

        public Builder crossFade(boolean b)
        {
            options.setCrossFade(b);
            return this;
        }

        public Builder crossFade(boolean b, int duration)
        {
            options.setCrossFade(b);
            options.setCrossFadeDuration(duration);
            return this;
        }

        public Builder thumbRate(float rate)
        {
            options.setThumbRate(rate);
            return this;
        }

        public Builder newBuilder()
        {
            Builder newBuilder = new Builder();
            newBuilder.placeHolder(options.getPlaceHolder());
            newBuilder.errorHolder(options.getErrorHolder());
            newBuilder.override(options.getWidth(), options.getHeight());
            newBuilder.diskCacheType(options.getDiskCacheType());
            newBuilder.asGif(options.isAsGif());
            newBuilder.skipMemoryCache(options.isSkipMemoryCache());
            newBuilder.crossFade(options.isCrossFade(), options.getCrossFadeDuration());
            newBuilder.thumbRate(options.getThumbRate());
            return newBuilder;
        }

        public ImageLoaderOptions build()
        {
            return options;
        }
    }
}
