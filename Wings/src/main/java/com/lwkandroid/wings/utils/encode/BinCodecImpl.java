package com.lwkandroid.wings.utils.encode;

/**
 * Description: 二进制编解码方法
 *
 * @author LWK
 * @date 2019/5/27
 */
public final class BinCodecImpl implements IBinCodec
{
    private static final String SPLIT_CHAR = " ";

    @Override
    public String encode(String data)
    {
        return encode(data, SPLIT_CHAR);
    }

    @Override
    public String encode(String data, String splitChar)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }

        char[] chars = data.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0, length = chars.length; i < length; i++)
        {
            stringBuilder.append(Integer.toBinaryString(chars[i]));
            if (i != length - 1)
            {
                stringBuilder.append(splitChar);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public String decode(String data)
    {
        return decode(data, SPLIT_CHAR);
    }

    @Override
    public String decode(String data, String splitChar)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }

        String[] chars = data.split(splitChar);
        StringBuilder stringBuilder = new StringBuilder();
        for (String i : chars)
        {
            stringBuilder.append(((char) Integer.parseInt(i.replace(" ", ""), 2)));
        }
        return stringBuilder.toString();
    }
}
