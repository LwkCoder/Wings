package com.lwkandroid.lib.core.net.response.func;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转String的方法
 *
 * @author LWK
 */
final class StringResponseConverter extends AbsResponseConverter<String>
{
    @Override
    public String convert(ResponseBody body) throws Exception
    {
        return body.string();
    }
}
