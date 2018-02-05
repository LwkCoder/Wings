package com.lwkandroid.wings.image;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

/**
 * Created by LWK
 * TODO 图片加载参数最终实现加载功能的接口
 */

public interface IImageOptions
{
    void show(Context context, ImageView imageView);

    void show(Activity activity, ImageView imageView);

    void show(Fragment fragment, ImageView imageView);

    void show(android.support.v4.app.Fragment fragment, ImageView imageView);
}
