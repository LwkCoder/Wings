package com.lwkandroid.wings.image;

import com.lwkandroid.wings.net.listener.OnProgressListener;

import java.io.File;

/**
 * Created by LWK
 * TODO 向外暴露的图片纯下载监听
 */

public interface OnImageDownloadListener extends OnProgressListener
{
    /**
     * 开始加载【UI回调】
     */
    void onStarted();

    /**
     * 加载完成【UI回调】
     *
     * @param success 是否加载成功
     * @param file    加载成功后的文件，失败为null
     */
    void onResult(boolean success, File file);
}
