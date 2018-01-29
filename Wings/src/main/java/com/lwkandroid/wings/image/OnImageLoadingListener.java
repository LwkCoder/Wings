package com.lwkandroid.wings.image;

import com.lwkandroid.wings.net.listener.OnProgressListener;

/**
 * Created by LWK
 * TODO 图片加载过程监听
 */

public interface OnImageLoadingListener extends OnProgressListener
{
    /**
     * 开始加载【UI回调】
     */
    void onStarted();

    /**
     * 加载完成【UI回调】
     *
     * @param success 是否加载成功
     */
    void onResult(boolean success);
}
