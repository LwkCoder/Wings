package com.lwkandroid.wings.net.response.convert;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转String的方法
 *
 * @author LWK
 * @date 2019/5/13
 */
final class StringResponseBodyConverter extends AbsApiResponseBodyConverter<String>
{
    @Override
    public String convert(ResponseBody response) throws Exception
    {
        return response.string();
    }
}
