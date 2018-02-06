package com.lwkandroid.wings.image.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.lwkandroid.wings.image.constants.ImageDiskCacheType;
import com.lwkandroid.wings.image.ImageLoader;

/**
 * Created by LWK
 * TODO 图片加载配置参数基类
 */

public abstract class ImageBaseOptions<T extends ImageBaseOptions> implements IImageOptions
{
    private String url;

    private Uri uri;

    private Drawable drawable;

    @DrawableRes
    private int resId = -1;

    private Bitmap bitmap;

    private byte[] bytes;

    @DrawableRes
    private int placeHolder = ImageLoader.getGlobalOptions().getPlaceHolder();

    private Drawable placeHolderDrawable = ImageLoader.getGlobalOptions().getPlaceHolderDrawable();

    @DrawableRes
    private int errorHolder = ImageLoader.getGlobalOptions().getErrorHolder();

    private Drawable errorHolderDrawable = ImageLoader.getGlobalOptions().getErrorHolderDrawable();

    private int width = -1;

    private int height = -1;

    @ImageDiskCacheType.Type
    private int diskCacheType = ImageLoader.getGlobalOptions().getDiskCacheType();

    private boolean asGif = false;

    private boolean skipMemoryCache = ImageLoader.getGlobalOptions().isSkipMemoryCache();

    private boolean crossFade = ImageLoader.getGlobalOptions().isCrossFade();

    private int crossFadeDuration = ImageLoader.getGlobalOptions().getCrossFadeDuration();

    private float thumbRate = 0.0f;

    public String getUrl()
    {
        return url;
    }

    public T setResource(String url)
    {
        this.url = url;
        return (T) this;
    }

    public Uri getUri()
    {
        return uri;
    }

    public T setResource(Uri uri)
    {
        this.uri = uri;
        return (T) this;
    }

    public Drawable getDrawable()
    {
        return drawable;
    }

    public T setResource(Drawable drawable)
    {
        this.drawable = drawable;
        return (T) this;
    }

    public int getResId()
    {
        return resId;
    }

    public T setResource(@DrawableRes int resId)
    {
        this.resId = resId;
        return (T) this;
    }

    public Bitmap getBitmap()
    {
        return bitmap;
    }

    public T setResource(Bitmap bitmap)
    {
        this.bitmap = bitmap;
        return (T) this;
    }

    public byte[] getBytes()
    {
        return bytes;
    }

    public T setResource(byte[] bytes)
    {
        this.bytes = bytes;
        return (T) this;
    }

    public int getPlaceHolder()
    {
        return placeHolder;
    }

    public T setPlaceHolder(int placeHolder)
    {
        this.placeHolder = placeHolder;
        return (T) this;
    }

    public Drawable getPlaceHolderDrawable()
    {
        return placeHolderDrawable;
    }

    public T setPlaceHolderDrawable(Drawable placeHolderDrawable)
    {
        this.placeHolderDrawable = placeHolderDrawable;
        return (T) this;
    }

    public Drawable getErrorHolderDrawable()
    {
        return errorHolderDrawable;
    }

    public T setErrorHolderDrawable(Drawable errorHolderDrawable)
    {
        this.errorHolderDrawable = errorHolderDrawable;
        return (T) this;
    }

    public int getErrorHolder()
    {
        return errorHolder;
    }

    public T setErrorHolder(int errorHolder)
    {
        this.errorHolder = errorHolder;
        return (T) this;
    }

    public T setSize(int width, int height)
    {
        this.width = width;
        this.height = height;
        return (T) this;
    }

    public T setSize(int size)
    {
        this.width = size;
        this.height = size;
        return (T) this;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getDiskCacheType()
    {
        return diskCacheType;
    }

    public T setDiskCacheType(@ImageDiskCacheType.Type int diskCacheType)
    {
        this.diskCacheType = diskCacheType;
        return (T) this;
    }

    public boolean isAsGif()
    {
        return asGif;
    }

    public T setAsGif(boolean asGif)
    {
        this.asGif = asGif;
        return (T) this;
    }

    public boolean isSkipMemoryCache()
    {
        return skipMemoryCache;
    }

    public T setSkipMemoryCache(boolean skipMemoryCache)
    {
        this.skipMemoryCache = skipMemoryCache;
        return (T) this;
    }

    public boolean isCrossFade()
    {
        return crossFade;
    }

    public T setCrossFade(boolean enable)
    {
        this.crossFade = enable;
        return (T) this;
    }

    public int getCrossFadeDuration()
    {
        return crossFadeDuration;
    }

    public T setCrossFadeDuration(int crossFadeDuration)
    {
        this.crossFadeDuration = crossFadeDuration;
        return (T) this;
    }

    public float getThumbRate()
    {
        return thumbRate;
    }

    public T setThumbRate(float thumbRate)
    {
        this.thumbRate = thumbRate;
        return (T) this;
    }
}
