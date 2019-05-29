package com.lwkandroid.wings.utils.encode;

import java.nio.charset.Charset;

/**
 * Description:16进制编解码方法
 *
 * @author LWK
 * @date 2019/5/28
 */
public interface IHexStringCodec
{
    String encode(byte[] data);

    String encode(String data);

    String encode(String data, Charset charset);

    String decode(String hexString);

    String decode(String hexString, Charset charset);
}
