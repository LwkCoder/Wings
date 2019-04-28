package com.lwkandroid.wings.utils.encrypt;

import android.util.Pair;

import java.nio.charset.Charset;

/**
 * Description:非对称加密接口
 *
 * @author LWK
 * @date 2019/4/28
 */
public interface IRsaEncryption
{
    byte[] rsaTemplate(byte[] data,
                       byte[] key,
                       boolean isPublicKey,
                       String transformation,
                       boolean isEncrypt);

    byte[] encrypt(byte[] data, byte[] key, boolean isPublicKey);

    byte[] encrypt(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    String encryptToString(byte[] data, byte[] key, boolean isPublicKey);

    String encryptToString(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    String encryptToString(byte[] data, Charset charsets, byte[] key, boolean isPublicKey);

    String encryptToString(byte[] data, Charset charsets, byte[] key, boolean isPublicKey, String transformation);

    String encryptToString(String data, byte[] key, boolean isPublicKey);

    String encryptToString(String data, byte[] key, boolean isPublicKey, String transformation);

    String encryptToString(String data, Charset charsets, byte[] key, boolean isPublicKey);

    String encryptToString(String data, Charset charsets, byte[] key, boolean isPublicKey, String transformation);

    byte[] encryptToBase64(byte[] data, byte[] key, boolean isPublicKey);

    byte[] encryptToBase64(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    byte[] encryptToBase64(String data, byte[] key, boolean isPublicKey);

    byte[] encryptToBase64(String data, byte[] key, boolean isPublicKey, String transformation);

    byte[] encryptToBase64(String data, Charset charset, byte[] key, boolean isPublicKey);

    byte[] encryptToBase64(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    String encryptToBase64String(byte[] data, byte[] key, boolean isPublicKey);

    String encryptToBase64String(byte[] data, Charset charset, byte[] key, boolean isPublicKey);

    String encryptToBase64String(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    String encryptToBase64String(byte[] data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    String encryptToBase64String(String data, byte[] key, boolean isPublicKey);

    String encryptToBase64String(String data, Charset charset, byte[] key, boolean isPublicKey);

    String encryptToBase64String(String data, byte[] key, boolean isPublicKey, String transformation);

    String encryptToBase64String(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    String encryptToHexString(byte[] data, byte[] key, boolean isPublicKey);

    String encryptToHexString(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    String encryptToHexString(String data, byte[] key, boolean isPublicKey);

    String encryptToHexString(String data, Charset charset, byte[] key, boolean isPublicKey);

    String encryptToHexString(String data, byte[] key, boolean isPublicKey, String transformation);

    String encryptToHexString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    byte[] decrypt(byte[] data, byte[] key, boolean isPublicKey);

    byte[] decrypt(byte[] data, byte[] key, boolean isPublicKey, String transformation);

    byte[] decryptString(String data, byte[] key, boolean isPublicKey);

    byte[] decryptString(String data, Charset charset, byte[] key, boolean isPublicKey);

    byte[] decryptString(String data, byte[] key, boolean isPublicKey, String transformation);

    byte[] decryptString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    String decryptStringToString(String data, byte[] key, boolean isPublicKey);

    String decryptStringToString(String data, Charset charset, byte[] key, boolean isPublicKey);

    String decryptStringToString(String data, byte[] key, boolean isPublicKey, String transformation);

    String decryptStringToString(String data, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    byte[] decryptBase64(byte[] base64Data, byte[] key, boolean isPublicKey);

    byte[] decryptBase64(byte[] base64Data, byte[] key, boolean isPublicKey, String transformation);

    byte[] decryptBase64String(String base64String, byte[] key, boolean isPublicKey);

    byte[] decryptBase64String(String base64String, Charset charset, byte[] key, boolean isPublicKey);

    byte[] decryptBase64String(String base64String, byte[] key, boolean isPublicKey, String transformation);

    byte[] decryptBase64String(String base64String, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    String decryptBase64StringToString(String base64String, byte[] key, boolean isPublicKey);

    String decryptBase64StringToString(String base64String, Charset charset, byte[] key, boolean isPublicKey);

    String decryptBase64StringToString(String base64String, byte[] key, boolean isPublicKey, String transformation);

    String decryptBase64StringToString(String base64String, Charset charset, byte[] key, boolean isPublicKey, String transformation);

    byte[] decryptHexString(String hexString, byte[] key, boolean isPublicKey);

    byte[] decryptHexString(String hexString, byte[] key, boolean isPublicKey, String transformation);

    String decryptHexStringToString(String hexString, byte[] key, boolean isPublicKey);

    String decryptHexStringToString(String hexString, byte[] key, boolean isPublicKey, String transformation);

    Pair<byte[], byte[]> generateRSAKeys();

    Pair<byte[], byte[]> generateRSAKeys(int keyBit);

    Pair<String, String> generateRSAKeysAsBase64String();

    Pair<String, String> generateRSAKeysAsBase64String(int keyBit);

    Pair<String, String> generateRSAKeysAsHexString();

    Pair<String, String> generateRSAKeysAsHexString(int keyBit);
}
