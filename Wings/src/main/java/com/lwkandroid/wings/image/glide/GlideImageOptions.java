package com.lwkandroid.wings.image.glide;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lwkandroid.wings.image.ImageDiskCacheType;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wings.image.ImageBaseOptions;
import com.lwkandroid.wings.utils.StringUtils;

/**
 * Created by LWK
 * TODO Glide实现的图片加载
 */

public final class GlideImageOptions extends ImageBaseOptions<GlideImageOptions>
{

    @Override
    public void show(Context context, ImageView imageView)
    {
        RequestBuilder requestBuilder = isAsGif() ?
                Glide.with(context).asGif() :
                Glide.with(context).asDrawable();

        loadResource(requestBuilder)
                .apply(getRequestOptions(requestBuilder))
                .into(imageView);
    }

    @Override
    public void show(Activity activity, ImageView imageView)
    {
        RequestBuilder requestBuilder = isAsGif() ?
                Glide.with(activity).asGif() :
                Glide.with(activity).asDrawable();

        loadResource(requestBuilder)
                .apply(getRequestOptions(requestBuilder))
                .into(imageView);
    }

    @Override
    public void show(Fragment fragment, ImageView imageView)
    {
        RequestBuilder requestBuilder = isAsGif() ?
                Glide.with(fragment).asGif() :
                Glide.with(fragment).asDrawable();

        loadResource(requestBuilder)
                .apply(getRequestOptions(requestBuilder))
                .into(imageView);
    }

    @Override
    public void show(android.support.v4.app.Fragment fragment, ImageView imageView)
    {
        RequestBuilder requestBuilder = isAsGif() ?
                Glide.with(fragment).asGif() :
                Glide.with(fragment).asDrawable();

        loadResource(requestBuilder)
                .apply(getRequestOptions(requestBuilder))
                .into(imageView);
    }

    private RequestBuilder loadResource(RequestBuilder builder)
    {
        if (StringUtils.isNotEmpty(getUrl()))
            return builder.load(getUrl());
        else if (getUri() != null)
            return builder.load(getUri());
        else if (getDrawable() != null)
            return builder.load(getDrawable());
        else if (getResId() != -1)
            return builder.load(getResId());
        else if (getBitmap() != null)
            return builder.load(getBitmap());
        else if (getBytes() != null)
            return builder.load(getBytes());
        else
            throw new IllegalArgumentException("You must set an resource to Glide");
    }

    private RequestOptions getRequestOptions(RequestBuilder builder)
    {
        RequestOptions options = new RequestOptions();

        //设置加载占位图
        if (getPlaceHolder() != -1)
            options.placeholder(getPlaceHolder());
        else if (getPlaceHolderDrawable() != null)
            options.placeholder(getPlaceHolderDrawable());
        else if (ImageLoader.getGlobalOptions().getPlaceHolder() != -1)
            options.placeholder(ImageLoader.getGlobalOptions().getPlaceHolder());
        else if (ImageLoader.getGlobalOptions().getPlaceHolderDrawable() != null)
            options.placeholder(ImageLoader.getGlobalOptions().getErrorHolderDrawable());
        //设置错误占位图
        if (getErrorHolder() != -1)
            options.placeholder(getErrorHolder());
        else if (getErrorHolderDrawable() != null)
            options.placeholder(getErrorHolderDrawable());
        else if (ImageLoader.getGlobalOptions().getErrorHolder() != -1)
            options.placeholder(ImageLoader.getGlobalOptions().getErrorHolder());
        else if (ImageLoader.getGlobalOptions().getErrorHolderDrawable() != null)
            options.placeholder(ImageLoader.getGlobalOptions().getErrorHolderDrawable());
        //设置加载大小
        if (getWidth() != 0 && getHeight() != 0)
            options.override(getWidth(), getHeight());
        //设置外部缓存策略
        int diskCacheType = getDiskCacheType() != ImageLoader.getGlobalOptions().getDiskCacheType() ?
                getDiskCacheType() : ImageLoader.getGlobalOptions().getDiskCacheType();
        if (diskCacheType == ImageDiskCacheType.ALL)
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
        else if (diskCacheType == ImageDiskCacheType.NONE)
            options.diskCacheStrategy(DiskCacheStrategy.NONE);
        else if (diskCacheType == ImageDiskCacheType.RESOURCE)
            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        else if (diskCacheType == ImageDiskCacheType.DATA)
            options.diskCacheStrategy(DiskCacheStrategy.DATA);
        else
            options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        //设置内存缓存策略
        options.skipMemoryCache(isSkipMemoryCache() != ImageLoader.getGlobalOptions().isSkipMemoryCache() ?
                isSkipMemoryCache() : ImageLoader.getGlobalOptions().isSkipMemoryCache());
        //设置显示动画
        if (isCrossFade() != ImageLoader.getGlobalOptions().isCrossFade() ?
                isCrossFade() : ImageLoader.getGlobalOptions().isCrossFade())
            builder.transition(DrawableTransitionOptions.withCrossFade(getCrossFadeDuration() != ImageLoader.getGlobalOptions().getCrossFadeDuration() ?
                    getCrossFadeDuration() : ImageLoader.getGlobalOptions().getCrossFadeDuration()));
        else
            options.dontAnimate();
        //设置缩略图比例
        if (getThumbRate() != 0)
            builder.thumbnail(getThumbRate());

        return options;
    }

}
