package com.lwkandroid.wings.utils;

/**
 * Description: 通用型回调
 *
 * @author LWK
 * @date 2019/5/20
 */
public interface WingsCallBack<T>
{
    void onCallBackSuccess(T t);

    void onCallBackError(Exception e);
}
