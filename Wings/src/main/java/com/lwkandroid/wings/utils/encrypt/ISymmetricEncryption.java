package com.lwkandroid.wings.utils.encrypt;

import java.nio.charset.Charset;

/**
 * Description:对称加密接口
 *
 * @author LWK
 * @date 2019/4/26
 */
public interface ISymmetricEncryption
{
    byte[] symmetricTemplate(byte[] data,
                             byte[] key,
                             String transformation,
                             byte[] iv,
                             boolean isEncrypt);

    byte[] encrypt(byte[] data, byte[] key);

    byte[] encrypt(byte[] data, byte[] key, String transformation, byte[] iv);

    String encryptToString(byte[] data, byte[] key);

    String encryptToString(byte[] data, byte[] key, String transformation, byte[] iv);

    String encryptToString(byte[] data, Charset charsets, byte[] key);

    String encryptToString(byte[] data, Charset charsets, byte[] key, String transformation, byte[] iv);

    String encryptToString(String data, byte[] key);

    String encryptToString(String data, Charset charset, byte[] key);

    String encryptToString(String data, byte[] key, String transformation, byte[] iv);

    String encryptToString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    byte[] encryptToBase64(byte[] data, byte[] key);

    byte[] encryptToBase64(byte[] data, byte[] key, String transformation, byte[] iv);

    byte[] encryptToBase64(String data, byte[] key);

    byte[] encryptToBase64(String data, Charset charset, byte[] key);

    byte[] encryptToBase64(String data, byte[] key, String transformation, byte[] iv);

    byte[] encryptToBase64(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    String encryptToBase64String(byte[] data, byte[] key);

    String encryptToBase64String(byte[] data, Charset charset, byte[] key);

    String encryptToBase64String(byte[] data, byte[] key, String transformation, byte[] iv);

    String encryptToBase64String(byte[] data, Charset charset, byte[] key, String transformation, byte[] iv);

    String encryptToBase64String(String data, byte[] key);

    String encryptToBase64String(String data, Charset charset, byte[] key);

    String encryptToBase64String(String data, byte[] key, String transformation, byte[] iv);

    String encryptToBase64String(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    String encryptToHexString(byte[] data, byte[] key);

    String encryptToHexString(byte[] data, byte[] key, String transformation, byte[] iv);

    String encryptToHexString(String data, byte[] key);

    String encryptToHexString(String data, Charset charset, byte[] key);

    String encryptToHexString(String data, byte[] key, String transformation, byte[] iv);

    String encryptToHexString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    byte[] decrypt(byte[] data, byte[] key);

    byte[] decrypt(byte[] data, byte[] key, String transformation, byte[] iv);

    byte[] decryptString(String data, byte[] key);

    byte[] decryptString(String data, Charset charset, byte[] key);

    byte[] decryptString(String data, byte[] key, String transformation, byte[] iv);

    byte[] decryptString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    String decryptStringToString(String data, byte[] key);

    String decryptStringToString(String data, Charset charset, byte[] key);

    String decryptStringToString(String data, byte[] key, String transformation, byte[] iv);

    String decryptStringToString(String data, Charset charset, byte[] key, String transformation, byte[] iv);

    byte[] decryptBase64(byte[] base64Data, byte[] key);

    byte[] decryptBase64(byte[] base64Data, byte[] key, String transformation, byte[] iv);

    byte[] decryptBase64String(String base64String, byte[] key);

    byte[] decryptBase64String(String base64String, Charset charset, byte[] key);

    byte[] decryptBase64String(String base64String, byte[] key, String transformation, byte[] iv);

    byte[] decryptBase64String(String base64String, Charset charset, byte[] key, String transformation, byte[] iv);

    String decryptBase64StringToString(String base64String, byte[] key);

    String decryptBase64StringToString(String base64String, Charset charset, byte[] key);

    String decryptBase64StringToString(String base64String, byte[] key, String transformation, byte[] iv);

    String decryptBase64StringToString(String base64String, Charset charset, byte[] key, String transformation, byte[] iv);

    byte[] decryptHexString(String hexString, byte[] key);

    byte[] decryptHexString(String hexString, byte[] key, String transformation, byte[] iv);

    String decryptHexStringToString(String hexString, byte[] key);

    String decryptHexStringToString(String hexString, byte[] key, String transformation, byte[] iv);

    byte[] generateKey();

    byte[] generateKey(int keyBit);

    String generateKeyAsBase64String();

    String generateKeyAsBase64String(int keyBit);

    String generateKeyAsHexString();

    String generateKeyAsHexString(int keyBit);
}
