package com.lwkandroid.wings.image;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by LWK
 * TODO 定义图片加载所需方法的接口
 */

public interface IImageLoaderStrategy
{
    void init(Context context);

    void show(Context context, String url, ImageView imageView);

    void show(Context context, String url, ImageView imageView, OnImageLoadingListener listener);

    void show(Context context, String url, ImageView imageView, ImageLoaderOptions options);

    void show(Context context, String url, ImageView imageView, ImageLoaderOptions options, OnImageLoadingListener listener);

    void show(Context context, @DrawableRes int resId, ImageView imageView);

    void show(Context context, @DrawableRes int resId, ImageView imageView, ImageLoaderOptions options);

    void show(Activity activity, String url, ImageView imageView);

    void show(Activity activity, String url, ImageView imageView, OnImageLoadingListener listener);

    void show(Activity activity, String url, ImageView imageView, ImageLoaderOptions options);

    void show(Activity activity, String url, ImageView imageView, ImageLoaderOptions options, OnImageLoadingListener listener);

    void show(Activity activity, @DrawableRes int resId, ImageView imageView);

    void show(Activity activity, @DrawableRes int resId, ImageView imageView, ImageLoaderOptions options);

    void show(Fragment fragment, String url, ImageView imageView);

    void show(Fragment fragment, String url, ImageView imageView, OnImageLoadingListener listener);

    void show(Fragment fragment, String url, ImageView imageView, ImageLoaderOptions options);

    void show(Fragment fragment, String url, ImageView imageView, ImageLoaderOptions options, OnImageLoadingListener listener);

    void show(Fragment fragment, @DrawableRes int resId, ImageView imageView);

    void show(Fragment fragment, @DrawableRes int resId, ImageView imageView, ImageLoaderOptions options);

    void download(Context context, String url, OnImageDownloadListener listener);

    void download(Context context, String url, int width, int height, OnImageDownloadListener listener);

    void clearCache(Context context);
}
