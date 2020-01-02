package com.sources.javacode;

import com.lwkandroid.wings.utils.encode.EncodeUtils;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/1/2
 */
public class JavaTest
{
    public static void main(String[] args)
    {
        String ss = "abc123-=/!@#";
        String test1 = EncodeUtils.hex().encode(ss);
        System.out.println(test1);
        System.out.println(EncodeUtils.hex().decodeToString(test1));
    }
}
