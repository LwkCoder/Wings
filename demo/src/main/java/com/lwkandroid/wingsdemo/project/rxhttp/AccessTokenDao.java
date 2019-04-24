package com.lwkandroid.wingsdemo.project.rxhttp;

import com.lwkandroid.wingsdemo.bean.AccessTokenBean;

/**
 * Description: 模拟AccessToken持久层
 *
 * @author LWK
 * @date 2019/4/24
 */
public class AccessTokenDao
{
    public static AccessTokenDao get()
    {
        return Holder.INSTANCE;
    }

    private AccessTokenDao()
    {
    }

    private static final class Holder
    {
        private static final AccessTokenDao INSTANCE = new AccessTokenDao();
    }

    private AccessTokenBean mAccessTokenBean;

    public void update(AccessTokenBean bean)
    {
        this.mAccessTokenBean = bean;
    }

    public AccessTokenBean getToken()
    {
        return mAccessTokenBean;
    }
}