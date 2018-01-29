package com.lwkandroid.wings.image;

/**
 * Created by LWK
 * TODO 向外暴露的图片加载监听
 */

public interface OnLoadingListener
{
    /**
     * 开始加载【UI回调】
     */
    void onStarted();

    /**
     * 加载中【UI回调】
     *
     * @param bytesRead   已读取数据长度
     * @param totalLength 数据总长度
     * @param percentage  已读取数据所占比例（满比例为100）
     */
    void onLoading(long bytesRead, long totalLength, int percentage);

    /**
     * 加载完成【UI回调】
     *
     * @param success 是否加载成功
     */
    void onResult(boolean success);
}
