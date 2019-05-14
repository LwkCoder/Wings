package com.lwkandroid.wingsdemo.net;

import com.lwkandroid.wings.net.interceptor.ApiParamsInterceptor;

import java.util.TreeMap;

/**
 * Created by LWK
 * 示例用的，模拟添加全局动态参数
 */

public class TestDynamicParamsInterceptor extends ApiParamsInterceptor
{
    @Override
    public TreeMap<String, Object> addParams(TreeMap<String, Object> oldParams)
    {
        //模拟添加密钥
        oldParams.put("InterceptorDynamicParamKey", "InterceptorDynamicParamValue");
        return oldParams;
    }
}
