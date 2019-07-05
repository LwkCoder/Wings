package com.lwkandroid.wings.image.callback;

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
