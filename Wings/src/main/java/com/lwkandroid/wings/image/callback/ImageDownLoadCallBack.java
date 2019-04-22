package com.lwkandroid.wings.image.callback;

/**
 * Created by LWK
 *  下载通用回调
 */

public interface ImageDownLoadCallBack<T>
{
    void onImageDownloadStarted();

    void onImageDownloadSuccess(T data);

    void onImageDownloadFailed();
}
