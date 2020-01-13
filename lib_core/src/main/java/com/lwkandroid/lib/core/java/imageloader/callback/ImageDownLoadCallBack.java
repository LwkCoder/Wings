package com.lwkandroid.lib.core.java.imageloader.callback;

/**
 * 下载通用回调
 *
 * @author LWK
 */
public interface ImageDownLoadCallBack<T>
{
    void onImageDownloadStarted();

    void onImageDownloadSuccess(T data);

    void onImageDownloadFailed();
}
