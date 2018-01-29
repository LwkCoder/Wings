package com.lwkandroid.wings.image.glide;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lwkandroid.wings.net.listener.OnProgressListener;
import com.lwkandroid.wings.net.manager.OkProgressManger;

/**
 * Created by LWK
 * TODO 封装Glide的Target实现加载过程分发
 */

public abstract class GlideImageViewTarget<T> extends DrawableImageViewTarget
        implements OnProgressListener
{
    private T model;

    public GlideImageViewTarget(T model, ImageView view)
    {
        super(view);
        this.model = model;
    }

    @Override
    public void onLoadStarted(Drawable placeholder)
    {
        super.onLoadStarted(placeholder);
        start();
    }

    @Override
    public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition)
    {
        super.onResourceReady(resource, transition);
        stop(true);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable)
    {
        super.onLoadFailed(errorDrawable);
        stop(false);
    }

    @Override
    public void onLoadCleared(Drawable placeholder)
    {
        super.onLoadCleared(placeholder);
    }

    private void start()
    {
        OkProgressManger.get().addDownloadListener(String.valueOf(model), this);
        onStarted();
    }

    private void stop(boolean success)
    {
        OkProgressManger.get().removeDownloadListener(String.valueOf(model));
        onResult(success);
        this.model = null;
    }

    @Override
    public void onDestroy()
    {
        OkProgressManger.get().removeDownloadListener(String.valueOf(model));
        super.onDestroy();
    }

    protected abstract void onStarted();

    protected abstract void onResult(boolean success);
}
