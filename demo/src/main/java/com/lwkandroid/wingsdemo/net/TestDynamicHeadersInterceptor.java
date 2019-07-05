package com.lwkandroid.wingsdemo.net;

import com.lwkandroid.wings.net.interceptor.ApiHeaderInterceptor;

import java.util.Map;
import java.util.TreeMap;

/**
 * 示例用的，模拟添加全局动态Header
 * @author LWK
 */
public class TestDynamicHeadersInterceptor extends ApiHeaderInterceptor
{

    @Override
    public Map<String, String> createHeaders()
    {
        Map<String, String> map = new TreeMap<>();
        map.put("InterceptorDynamicHeaderTag", "InterceptorDynamicHeaderValue");
        return map;
    }
}
