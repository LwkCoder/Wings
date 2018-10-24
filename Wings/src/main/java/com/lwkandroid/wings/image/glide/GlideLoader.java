package com.lwkandroid.wings.image.glide;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lwkandroid.wings.image.ILoaderStrategy;
import com.lwkandroid.wings.image.ImageLoader;
import com.lwkandroid.wings.image.callback.ImageDownLoadCallBack;
import com.lwkandroid.wings.image.constants.ImageDiskCacheType;
import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.utils.SDCardUtils;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.utils.Utils;

import java.io.File;

/**
 * Created by LWK
 * TODO Glide实现的图片加载模块
 */

public class GlideLoader implements ILoaderStrategy<GlideLoaderOptions>
{
    @Override
    public void show(Context context, ImageView imageView, GlideLoaderOptions options)
    {
        RequestBuilder requestBuilder = options.isAsGif() ?
                Glide.with(context).asGif() : Glide.with(context).asBitmap();

        loadResource(requestBuilder, options)
                .apply(getRequestOptions(requestBuilder, options))
                .into(imageView);
    }

    @Override
    public void show(Activity context, ImageView imageView, GlideLoaderOptions options)
    {
        RequestBuilder requestBuilder = options.isAsGif() ?
                Glide.with(context).asGif() : Glide.with(context).asBitmap();

        loadResource(requestBuilder, options)
                .apply(getRequestOptions(requestBuilder, options))
                .into(imageView);
    }

    @Override
    public void show(Fragment context, ImageView imageView, GlideLoaderOptions options)
    {
        RequestBuilder requestBuilder = options.isAsGif() ?
                Glide.with(context).asGif() : Glide.with(context).asBitmap();

        loadResource(requestBuilder, options)
                .apply(getRequestOptions(requestBuilder, options))
                .into(imageView);
    }

    @Override
    public void show(android.support.v4.app.Fragment context, ImageView imageView, GlideLoaderOptions options)
    {
        RequestBuilder requestBuilder = options.isAsGif() ?
                Glide.with(context).asGif() : Glide.with(context).asBitmap();

        loadResource(requestBuilder, options)
                .apply(getRequestOptions(requestBuilder, options))
                .into(imageView);
    }

    @Override
    public void downloadFile(Context context, String url, ImageDownLoadCallBack<File> callBack)
    {
        Glide.with(context)
                .downloadOnly()
                .load(url)
                .into(new DownLoadTarget<>(callBack));
    }

    @Override
    public void downloadFile(Context context, String url, int maxWidth, int maxHeight, ImageDownLoadCallBack<File> callBack)
    {
        Glide.with(context)
                .downloadOnly()
                .load(url)
                .apply(new RequestOptions().override(maxWidth, maxHeight))
                .into(new DownLoadTarget<>(maxWidth, maxHeight, callBack));
    }

    @Override
    public void downloadBitmap(Context context, String url, ImageDownLoadCallBack<Bitmap> callBack)
    {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(new DownLoadTarget<>(callBack));
    }

    @Override
    public void downloadBitmap(Context context, String url, int maxWidth, int maxHeight, ImageDownLoadCallBack<Bitmap> callBack)
    {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().override(maxWidth, maxHeight))
                .into(new DownLoadTarget<>(maxWidth, maxHeight, callBack));
    }

    private <T> RequestBuilder<T> loadResource(RequestBuilder<T> builder, GlideLoaderOptions options)
    {
        if (StringUtils.isNotEmpty(options.getUrl()))
            return builder.load(options.getUrl());
        else if (options.getUri() != null)
            return builder.load(options.getUri());
        else if (options.getDrawable() != null)
            return builder.load(options.getDrawable());
        else if (options.getResId() != -1)
            return builder.load(options.getResId());
        else if (options.getBitmap() != null)
            return builder.load(options.getBitmap());
        else if (options.getBytes() != null)
            return builder.load(options.getBytes());
        else
            throw new IllegalArgumentException("You must set an resource to Glide");
    }

    private RequestOptions getRequestOptions(RequestBuilder builder, GlideLoaderOptions options)
    {
        RequestOptions requestOptions = new RequestOptions();

        //设置加载占位图
        if (options.getPlaceHolder() != -1)
            requestOptions.placeholder(options.getPlaceHolder());
        else if (options.getPlaceHolderDrawable() != null)
            requestOptions.placeholder(options.getPlaceHolderDrawable());
        else if (ImageLoader.getGlobalOptions().getPlaceHolder() != -1)
            requestOptions.placeholder(ImageLoader.getGlobalOptions().getPlaceHolder());
        else if (ImageLoader.getGlobalOptions().getPlaceHolderDrawable() != null)
            requestOptions.placeholder(ImageLoader.getGlobalOptions().getErrorHolderDrawable());
        //设置错误占位图
        if (options.getErrorHolder() != -1)
            requestOptions.error(options.getErrorHolder());
        else if (options.getErrorHolderDrawable() != null)
            requestOptions.error(options.getErrorHolderDrawable());
        else if (ImageLoader.getGlobalOptions().getErrorHolder() != -1)
            requestOptions.error(ImageLoader.getGlobalOptions().getErrorHolder());
        else if (ImageLoader.getGlobalOptions().getErrorHolderDrawable() != null)
            requestOptions.error(ImageLoader.getGlobalOptions().getErrorHolderDrawable());
        //设置加载大小
        if (options.getWidth() != 0 && options.getHeight() != 0)
            requestOptions.override(options.getWidth(), options.getHeight());
        //设置外部缓存策略
        int diskCacheType = options.getDiskCacheType();
        if (diskCacheType == ImageDiskCacheType.ALL)
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        else if (diskCacheType == ImageDiskCacheType.NONE)
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        else if (diskCacheType == ImageDiskCacheType.RESOURCE)
            requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        else if (diskCacheType == ImageDiskCacheType.DATA)
            requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
        else
            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        //设置内存缓存策略
        requestOptions.skipMemoryCache(options.isSkipMemoryCache());
        //设置Transformation
        if (options.getTransformation() != null)
            requestOptions.transform(options.getTransformation());
        //设置显示动画
        if (options.isCrossFade())
        {
            builder.transition(BitmapTransitionOptions.withCrossFade(options.getCrossFadeDuration()));
        } else if (!options.isAsGif())
        {
            //gif的图片不能调用.dontAnimate()
            requestOptions.dontAnimate();
        }
        //设置缩略图比例
        if (options.getThumbRate() != 0)
            builder.thumbnail(options.getThumbRate());

        return requestOptions;
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
