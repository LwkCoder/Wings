package com.lwkandroid.wings.image.glide;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lwkandroid.wings.image.callback.ImageDownLoadCallBack;

/**
 * Created by LWK
 * TODO 下载Target
 */

public class DownLoadTarget<R> extends SimpleTarget<R>
{
    private ImageDownLoadCallBack<R> mCallBack;

    public DownLoadTarget(ImageDownLoadCallBack<R> callBack)
    {
        super();
        this.mCallBack = callBack;
    }

    public DownLoadTarget(int width, int height, ImageDownLoadCallBack<R> callBack)
    {
        super(width, height);
        this.mCallBack = callBack;
    }

    private ImageDownLoadCallBack<R> getCallBack()
    {
        return mCallBack;
    }

    @Override
    public void onLoadStarted(@Nullable Drawable placeholder)
    {
        if (getCallBack() != null)
            getCallBack().onImageDownloadStarted();
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable)
    {
        if (getCallBack() != null)
            getCallBack().onImageDownloadFailed();
    }

    @Override
    public void onResourceReady(@NonNull R resource, @Nullable Transition<? super R> transition)
    {
        if (getCallBack() != null)
            getCallBack().onImageDownloadSuccess(resource);
    }

    @Override
    public void onDestroy()
    {
        if (mCallBack != null)
            mCallBack = null;
    }
}
