package com.sources.javacode;

import com.lwkandroid.wings.utils.encrypt.EncryptUtils;

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
        String ss = "abc123-=/!@#阿弥佗佛";
        //        byte[] key = EncryptHelper.Companion.generateAesKey(128);
        //        byte[] bytes = ByteArrayExtensionKt.aesEncrypt(ss.getBytes(), key, null, EncryptHelper.AES_DEFAULT_TRANSFORMATION);
        //        System.out.println(EncodeUtils.hex().encode(bytes));
        //        byte[] result = ByteArrayExtensionKt.aesDecrypt(bytes, key, null, EncryptHelper.AES_DEFAULT_TRANSFORMATION);
        //        System.out.println(result);
        byte[] key = EncryptUtils.aes().generateKey();
        String hexString = EncryptUtils.aes().encryptToHexString(ss, key);
        System.out.println(hexString);
        String result = EncryptUtils.aes().decryptHexStringToString(hexString,key);
        System.out.println(result);
    }
}
