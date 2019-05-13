package com.lwkandroid.wings.net.convert;

import okhttp3.ResponseBody;

/**
 * Description:ResponseBody转Byte[]的方法
 *
 * @author LWK
 * @date 2019/5/13
 */
 final class BytesConverter extends AbsConverter<byte[]>
{
    @Override
    public byte[] convert(ResponseBody response) throws Exception
    {
        return response.bytes();
    }
}
