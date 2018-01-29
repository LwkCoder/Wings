package com.lwkandroid.wings.image.glide;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lwkandroid.wings.net.listener.OnProgressListener;
import com.lwkandroid.wings.net.manager.OkProgressManger;

import java.io.File;

/**
 * Created by LWK
 * TODO 封装Glide的Target实现仅仅下载过程分发
 */

public abstract class GlideDownloadTarget<T> extends RequestFutureTarget<File>
        implements OnProgressListener
{
    private T model;

    public GlideDownloadTarget(T model, Handler mainHandler, int width, int height)
    {
        super(mainHandler, width, height);
        this.model = model;
    }

    @Override
    public void onLoadStarted(Drawable placeholder)
    {
        super.onLoadStarted(placeholder);
        start();
    }

    @Override
    public synchronized void onResourceReady(File resource, Transition<? super File> transition)
    {
        super.onResourceReady(resource, transition);
        stop(true, resource);
    }

    @Override
    public void onLoadFailed(@Nullable Drawable errorDrawable)
    {
        super.onLoadFailed(errorDrawable);
        stop(false, null);
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

    private void stop(boolean success, File resultFile)
    {
        OkProgressManger.get().removeDownloadListener(String.valueOf(model));
        onResult(success, resultFile);
        this.model = null;
    }

    @Override
    public void onDestroy()
    {
        OkProgressManger.get().removeDownloadListener(String.valueOf(model));
        cancel(true);
        super.onDestroy();
    }

    protected abstract void onStarted();

    protected abstract void onResult(boolean success, File resultFile);
}
