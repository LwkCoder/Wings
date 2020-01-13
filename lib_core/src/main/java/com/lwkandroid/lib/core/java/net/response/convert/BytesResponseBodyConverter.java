package com.lwkandroid.lib.core.java.net.response.convert;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转Byte[]的方法
 *
 * @author LWK
 * @date 2019/5/13
 */
 final class BytesResponseBodyConverter extends AbsApiResponseBodyConverter<byte[]>
{
    @Override
    public byte[] convert(ResponseBody response) throws Exception
    {
        return response.bytes();
    }
}
