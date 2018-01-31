package com.lwkandroid.wings.net.interceptor;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by LWK
 * TODO 自定义Retrofit对象时用来添加全局参数的拦截器
 */

public final class RetrofitFormDataInterceptor extends ApiParamsInterceptor
{
    private Map<String, String> mNewParamsMap;

    public RetrofitFormDataInterceptor(Map<String, String> newParamsMap)
    {
        this.mNewParamsMap = newParamsMap;
    }

    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> oldParams)
    {
        if (mNewParamsMap != null && mNewParamsMap.size() > 0)
            oldParams.putAll(mNewParamsMap);
        return oldParams;
    }
}
