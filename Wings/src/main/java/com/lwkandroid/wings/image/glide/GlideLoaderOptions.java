package com.lwkandroid.wings.image.glide;

import android.graphics.Bitmap;

import com.bumptech.glide.load.Transformation;
import com.lwkandroid.wings.image.bean.ImageOptions;

/**
 * Created by LWK
 *  Glide图片加载参数
 */

public final class GlideLoaderOptions extends ImageOptions<GlideLoaderOptions>
{
    private Transformation<Bitmap> mTransformation;

    /**
     * 设置Transformation
     */
    public GlideLoaderOptions setTransformation(Transformation<Bitmap> transformation)
    {
        this.mTransformation = transformation;
        return this;
    }

    public Transformation<Bitmap> getTransformation()
    {
        return mTransformation;
    }
}
