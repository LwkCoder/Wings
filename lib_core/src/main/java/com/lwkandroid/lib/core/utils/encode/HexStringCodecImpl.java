package com.lwkandroid.lib.core.utils.encode;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Description: 16进制编解码实现类
 *
 * @author LWK
 * @date 2019/5/28
 */
public final class HexStringCodecImpl implements IHexStringCodec
{
    private static final String HEX_STRING = "0123456789ABCDEF";
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final String DEFAULT_SPLIT = " ";
    private static final String EMPTY_STRING = "";

    @Override
    public String encode(byte[] data)
    {
        return encode(data, DEFAULT_SPLIT);
    }

    @Override
    public String encode(byte[] data, String spiltStr)
    {
        if (data == null || data.length == 0)
        {
            return EMPTY_STRING;
        }
        StringBuilder sb = new StringBuilder(data.length * 2);
        //将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0, length = data.length; i < data.length; i++)
        {
            byte b = data[i];
            sb.append(HEX_STRING.charAt((b & 0xf0) >> 4));
            sb.append(HEX_STRING.charAt((b & 0x0f)));
            if (i != length - 1)
            {
                sb.append(spiltStr);
            }
        }
        return sb.toString();
    }

    @Override
    public String encode(String data)
    {
        return encode(data, DEFAULT_SPLIT);
    }

    @Override
    public String encode(String data, String spiltStr)
    {
        return encode(data, spiltStr, UTF8);
    }

    @Override
    public String encode(String data, Charset charset)
    {
        return encode(data, DEFAULT_SPLIT, charset);
    }

    @Override
    public String encode(String data, String spiltStr, Charset charset)
    {
        if (data == null || data.length() == 0)
        {
            return EMPTY_STRING;
        }
        return encode(data.getBytes(charset), spiltStr);
    }

    @Override
    public String decodeToString(String hexString)
    {
        return decodeToString(hexString, DEFAULT_SPLIT);
    }

    @Override
    public String decodeToString(String hexString, String spiltStr)
    {
        return new String(decodeToBytes(hexString, spiltStr), UTF8);
    }

    @Override
    public byte[] decodeToBytes(String hexString)
    {
        return decodeToBytes(hexString, DEFAULT_SPLIT);
    }

    @Override
    public byte[] decodeToBytes(String hexString, String spiltStr)
    {
        return decodeToBytes(hexString, spiltStr, UTF8);
    }

    @Override
    public String decodeToString(String hexString, Charset charset)
    {
        return decodeToString(hexString, DEFAULT_SPLIT, charset);
    }

    @Override
    public String decodeToString(String hexString, String spiltStr, Charset charset)
    {
        return new String(decodeToBytes(hexString, spiltStr, charset), charset);
    }

    @Override
    public byte[] decodeToBytes(String hexString, Charset charset)
    {
        return decodeToBytes(hexString, DEFAULT_SPLIT, charset);
    }

    @Override
    public byte[] decodeToBytes(String hexString, String spiltStr, Charset charset)
    {
        if (hexString == null || hexString.length() == 0)
        {
            return new byte[0];
        }
        hexString = hexString.replaceAll(spiltStr, EMPTY_STRING);
        char[] chars = hexString.toCharArray();
        byte[] bytes = new byte[chars.length / 2];
        for (int i = 0, n, length = bytes.length; i < length; i++)
        {
            n = HEX_STRING.indexOf(chars[2 * i]) * 16 + HEX_STRING.indexOf(chars[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return bytes;
    }
}
