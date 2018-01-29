package com.lwkandroid.wingsdemo.net;

import com.lwkandroid.wings.net.interceptor.ApiParamsInterceptor;

import java.util.TreeMap;

/**
 * Created by LWK
 * TODO 示例用的，模拟对参数进行统一处理，例如添加加密验证...
 */

public class TestSignInterceptor extends ApiParamsInterceptor
{
    @Override
    public TreeMap<String, String> dynamic(TreeMap<String, String> oldParams)
    {
        //模拟添加密钥
        oldParams.put("secret", "secretValue");
        return oldParams;
    }
}
