package com.lwkandroid.wings.utils;

import android.util.Base64;
import android.util.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 */
public final class EncryptUtils
{

    private EncryptUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final Charset UTF8 = Charset.forName("UTF-8");

    /**
     * AES加密默认Transformation
     * 算法/模式/填充                16字节加密后数据长度        不满16字节加密后长度
     * AES/CBC/NoPadding             16                          不支持
     * AES/CBC/PKCS5Padding          32                          16
     * AES/CBC/ISO10126Padding       32                          16
     * AES/CFB/NoPadding             16                          原始数据长度
     * AES/CFB/PKCS5Padding          32                          16
     * AES/CFB/ISO10126Padding       32                          16
     * AES/ECB/NoPadding             16                          不支持
     * AES/ECB/PKCS5Padding          32                          16
     * AES/ECB/ISO10126Padding       32                          16
     * AES/OFB/NoPadding             16                          原始数据长度
     * AES/OFB/PKCS5Padding          32                          16
     * AES/OFB/ISO10126Padding       32                          16
     * AES/PCBC/NoPadding            16                          不支持
     * AES/PCBC/PKCS5Padding         32                          16
     * AES/PCBC/ISO10126Padding      32                          16
     */
    public static final String AES_DEFAULT_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    /**
     * AES加密默认偏移量
     */
    public static final byte[] AES_DEFAULT_IV = "16-Bytes--String".getBytes(UTF8);

    /**
     * RSA加密默认Transformation
     */
    public static final String RSA_DEFAULT_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    /**
     * RSA最大加密明文大小
     */
    private static final int RSA_MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int RSA_MAX_DECRYPT_BLOCK = 128;

    ///////////////////////////////////////////////////////////////////////////
    // hash encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 返回MD2加密后的字符串
     *
     * @param data 待加密字符串
     * @return MD2加密字符串
     */
    public static String encryptMD2ToString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptMD2ToString(data.getBytes());
    }

    /**
     * 返回MD2加密后的字符串
     *
     * @param data 待加密字节数组
     * @return MD2加密字符串
     */
    public static String encryptMD2ToString(final byte[] data)
    {
        return bytes2String(encryptMD2(data));
    }

    /**
     * 返回MD2加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @return MD2加密的16进制字符串
     */
    public static String encryptMD2ToHexString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptMD2ToHexString(data.getBytes());
    }

    /**
     * 返回MD2加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @return MD2加密的16进制字符串
     */
    public static String encryptMD2ToHexString(final byte[] data)
    {
        return bytes2HexString(encryptMD2(data));
    }

    /**
     * 返回MD2加密后的字节数组
     *
     * @param data 待加密字节数组
     * @return MD2加密后的的字节数组
     */
    public static byte[] encryptMD2(final byte[] data)
    {
        return hashTemplate(data, "MD2");
    }

    /**
     * 返回MD5加密后的字符串
     *
     * @param data 待加密字符串
     * @return MD5加密后的字符串
     */
    public static String encryptMD5ToString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * 返回MD5加密后的字符串
     *
     * @param data 待加密字节数组
     * @return MD5加密后的字符串
     */
    public static String encryptMD5ToString(final byte[] data)
    {
        return bytes2String(encryptMD5(data));
    }

    /**
     * 返回MD5加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @return MD5加密后的16进制字符串
     */
    public static String encryptMD5ToHexString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptMD5ToHexString(data.getBytes());
    }

    /**
     * 返回MD5加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @return MD5加密后的16进制字符串
     */
    public static String encryptMD5ToHexString(final byte[] data)
    {
        return bytes2HexString(encryptMD5(data));
    }

    /**
     * 返回MD5加密后的字节数组
     *
     * @param data 待加密字节数组
     * @return MD5加密后的字节数组
     */
    public static byte[] encryptMD5(final byte[] data)
    {
        return hashTemplate(data, "MD5");
    }

    /**
     * 返回MD5加密后的文件的16进制字符串
     *
     * @param filePath 文件地址
     * @return MD5加密后的文件的16进制字符串
     */
    public static String encryptMD5File2HexString(final String filePath)
    {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File2HexString(file);
    }

    /**
     * 返回MD5加密后的文件的字符串
     *
     * @param filePath 文件地址
     * @return MD5加密后的文件的字符串
     */
    public static String encryptMD5File2String(final String filePath)
    {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File2String(file);
    }

    /**
     * 返回MD5加密后的文件的字符串
     *
     * @param file 文件对象
     * @return MD5加密后的文件的字符串
     */
    public static String encryptMD5File2String(final File file)
    {
        return bytes2String(encryptMD5File(file));
    }

    /**
     * 返回MD5加密后的文件的16进制字符串
     *
     * @param file 文件对象
     * @return MD5加密后的文件的16进制字符串
     */
    public static String encryptMD5File2HexString(final File file)
    {
        return bytes2HexString(encryptMD5File(file));
    }

    /**
     * 返回MD5加密后的文件的字节数组
     *
     * @param filePath 文件地址
     * @return MD5加密后的文件的字节数组
     */
    public static byte[] encryptMD5File(final String filePath)
    {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * 返回MD5加密后的文件的字节数组
     *
     * @param file 文件对象
     * @return MD5加密后的文件的字节数组
     */
    public static byte[] encryptMD5File(final File file)
    {
        if (file == null)
        {
            return null;
        }
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try
        {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true)
            {
                if (!(digestInputStream.read(buffer) > 0))
                {
                    break;
                }
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            try
            {
                if (fis != null)
                {
                    fis.close();
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * 返回SHA1加密后的字符串
     *
     * @param data 待加密字符串
     * @return SHA1加密后的16进制字符串
     */
    public static String encryptSHA1ToString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * 返回SHA1加密后的字符串
     *
     * @param data 待加密字节数组
     * @return SHA1加密后的字符串
     */
    public static String encryptSHA1ToString(final byte[] data)
    {
        return bytes2String(encryptSHA1(data));
    }

    /**
     * 返回SHA1加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @return SHA1加密后的16进制字符串
     */
    public static String encryptSHA1ToHexString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA1ToHexString(data.getBytes());
    }

    /**
     * 返回SHA1加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @return SHA1加密后的16进制字符串
     */
    public static String encryptSHA1ToHexString(final byte[] data)
    {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * 返回SHA1加密后的字节数组
     *
     * @param data 待加密字节数组
     * @return SHA1加密后的字节数组
     */
    public static byte[] encryptSHA1(final byte[] data)
    {
        return hashTemplate(data, "SHA-1");
    }

    /**
     * 返回SHA224加密后的字符串
     *
     * @param data 待加密字符串
     * @return SHA224加密后的字符串
     */
    public static String encryptSHA224ToString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * 返回SHA224加密后的字符串
     *
     * @param data 待加密字节数组
     * @return SHA224加密后的字符串
     */
    public static String encryptSHA224ToString(final byte[] data)
    {
        return bytes2String(encryptSHA224(data));
    }

    /**
     * 返回SHA224加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @return SHA224加密后的16进制字符串
     */
    public static String encryptSHA224ToHexString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA224ToHexString(data.getBytes());
    }

    /**
     * 返回SHA224加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @return SHA224加密后的16进制字符串
     */
    public static String encryptSHA224ToHexString(final byte[] data)
    {
        return bytes2HexString(encryptSHA224(data));
    }

    /**
     * 返回SHA224加密后的字节数组
     *
     * @param data 待加密字节数组
     * @return SHA224加密后的字节数组
     */
    public static byte[] encryptSHA224(final byte[] data)
    {
        return hashTemplate(data, "SHA224");
    }

    /**
     * 返回SHA256加密后的字符串
     *
     * @param data 待加密字符串
     * @return SHA256加密后的字符串
     */
    public static String encryptSHA256ToString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * 返回SHA256加密后的字符串
     *
     * @param data 待加密字节数组
     * @return SHA256加密后的字符串
     */
    public static String encryptSHA256ToString(final byte[] data)
    {
        return bytes2String(encryptSHA256(data));
    }

    /**
     * 返回SHA256加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @return SHA256加密后的16进制字符串
     */
    public static String encryptSHA256ToHexString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA256ToHexString(data.getBytes());
    }

    /**
     * 返回SHA256加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @return SHA256加密后的16进制字符串
     */
    public static String encryptSHA256ToHexString(final byte[] data)
    {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * 返回SHA256加密后的字节数组
     *
     * @param data 待加密字节数组
     * @return SHA256加密后的字节数组
     */
    public static byte[] encryptSHA256(final byte[] data)
    {
        return hashTemplate(data, "SHA-256");
    }

    /**
     * 返回SHA384加密后的字符串
     *
     * @param data 待加密字符串
     * @return SHA384加密后的字符串
     */
    public static String encryptSHA384ToString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * 返回SHA384加密后的字符串
     *
     * @param data 待加密字节数组
     * @return SHA384加密后的字符串
     */
    public static String encryptSHA384ToString(final byte[] data)
    {
        return bytes2String(encryptSHA384(data));
    }

    /**
     * 返回SHA384加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @return SHA384加密后的16进制字符串
     */
    public static String encryptSHA384ToHexString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA384ToHexString(data.getBytes());
    }

    /**
     * 返回SHA384加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @return SHA384加密后的16进制字符串
     */
    public static String encryptSHA384ToHexString(final byte[] data)
    {
        return bytes2HexString(encryptSHA384(data));
    }

    /**
     * 返回SHA384加密后的字节数组
     *
     * @param data 待加密字节数组
     * @return SHA384加密后的字节数组
     */
    public static byte[] encryptSHA384(final byte[] data)
    {
        return hashTemplate(data, "SHA-384");
    }

    /**
     * 返回SHA512加密后的字符串
     *
     * @param data 待加密字符串
     * @return SHA512加密后的字符串
     */
    public static String encryptSHA512ToString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * 返回SHA512加密后的字符串
     *
     * @param data 待加密字符数组
     * @return SHA512加密后的字符串
     */
    public static String encryptSHA512ToString(final byte[] data)
    {
        return bytes2String(encryptSHA512(data));
    }

    /**
     * 返回SHA512加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @return SHA512加密后的16进制字符串
     */
    public static String encryptSHA512ToHexString(final String data)
    {
        if (data == null || data.length() == 0)
        {
            return "";
        }
        return encryptSHA512ToHexString(data.getBytes());
    }

    /**
     * 返回SHA512加密后的16进制字符串
     *
     * @param data 待加密字符数组
     * @return SHA512加密后的16进制字符串
     */
    public static String encryptSHA512ToHexString(final byte[] data)
    {
        return bytes2HexString(encryptSHA512(data));
    }

    /**
     * 返回SHA512加密后的字符数组
     *
     * @param data 待加密字符数组
     * @return SHA512加密后的字符数组
     */
    public static byte[] encryptSHA512(final byte[] data)
    {
        return hashTemplate(data, "SHA-512");
    }

    /**
     * 返回加密算法结果
     *
     * @param data      待加密数据
     * @param algorithm 加密算法
     * @return 加密结果
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm)
    {
        if (data == null || data.length <= 0)
        {
            return null;
        }
        try
        {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // hmac encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 返回HmacMD5加密后的字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacMD5加密后的16进制字符串
     */
    public static String encryptHmacMD5ToString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacMD5加密后的字符串
     *
     * @param data 待加密字符数组
     * @param key  密钥
     * @return HmacMD5加密后的16进制字符串
     */
    public static String encryptHmacMD5ToString(final byte[] data, final byte[] key)
    {
        return bytes2String(encryptHmacMD5(data, key));
    }

    /**
     * 返回HmacMD5加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacMD5加密后的16进制字符串
     */
    public static String encryptHmacMD5ToHexString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacMD5ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacMD5加密后的16进制字符串
     *
     * @param data 待加密字符数组
     * @param key  密钥
     * @return HmacMD5加密后的16进制字符串
     */
    public static String encryptHmacMD5ToHexString(final byte[] data, final byte[] key)
    {
        return bytes2HexString(encryptHmacMD5(data, key));
    }

    /**
     * 返回HmacMD5加密后的字节数组
     *
     * @param data 待加密字符数组
     * @param key  密钥
     * @return HmacMD5加密后的字符数组
     */
    public static byte[] encryptHmacMD5(final byte[] data, final byte[] key)
    {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * 返回HmacSHA1加密后的字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA1加密后的字符串
     */
    public static String encryptHmacSHA1ToString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA1加密后的字符串
     *
     * @param data 待加密字符数组
     * @param key  密钥
     * @return HmacSHA1加密后的字符串
     */
    public static String encryptHmacSHA1ToString(final byte[] data, final byte[] key)
    {
        return bytes2String(encryptHmacSHA1(data, key));
    }

    /**
     * 返回HmacSHA1加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA1加密后的16进制字符串
     */
    public static String encryptHmacSHA1ToHexString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA1ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA1加密后的16进制字符串
     *
     * @param data 待加密字符数组
     * @param key  密钥
     * @return HmacSHA1加密后的16进制字符串
     */
    public static String encryptHmacSHA1ToHexString(final byte[] data, final byte[] key)
    {
        return bytes2HexString(encryptHmacSHA1(data, key));
    }

    /**
     * 返回HmacSHA1加密后的字节数组
     *
     * @param data 待加密字符数组
     * @param key  密钥
     * @return HmacSHA1加密后的字符数组
     */
    public static byte[] encryptHmacSHA1(final byte[] data, final byte[] key)
    {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * 返回HmacSHA224加密后的字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA224加密后的字符串
     */
    public static String encryptHmacSHA224ToString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA224加密后的字符串
     *
     * @param data 待加密字符数组
     * @param key  密钥
     * @return HmacSHA224加密后的字符串
     */
    public static String encryptHmacSHA224ToString(final byte[] data, final byte[] key)
    {
        return bytes2String(encryptHmacSHA224(data, key));
    }

    /**
     * 返回HmacSHA224加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA224加密后的16进制字符串
     */
    public static String encryptHmacSHA224ToHexString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA224ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA224加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA224加密后的16进制字符串
     */
    public static String encryptHmacSHA224ToHexString(final byte[] data, final byte[] key)
    {
        return bytes2HexString(encryptHmacSHA224(data, key));
    }

    /**
     * 返回HmacSHA224加密后的字节数组
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA224加密后的字节数组
     */
    public static byte[] encryptHmacSHA224(final byte[] data, final byte[] key)
    {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * 返回HmacSHA256加密后的字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA256加密后的字符串
     */
    public static String encryptHmacSHA256ToString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA256加密后的字符串
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA256加密后的字符串
     */
    public static String encryptHmacSHA256ToString(final byte[] data, final byte[] key)
    {
        return bytes2String(encryptHmacSHA256(data, key));
    }

    /**
     * 返回HmacSHA256加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA256加密后的16进制字符串
     */
    public static String encryptHmacSHA256ToHexString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA256ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA256加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA256加密后的字节数组
     */
    public static String encryptHmacSHA256ToHexString(final byte[] data, final byte[] key)
    {
        return bytes2HexString(encryptHmacSHA256(data, key));
    }

    /**
     * 返回HmacSHA256加密后的字节数组
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA256加密后的字节数组
     */
    public static byte[] encryptHmacSHA256(final byte[] data, final byte[] key)
    {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * 返回HmacSHA384加密后的字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA384加密后的字符串
     */
    public static String encryptHmacSHA384ToString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA384加密后的字符串
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA384加密后的字符串
     */
    public static String encryptHmacSHA384ToString(final byte[] data, final byte[] key)
    {
        return bytes2String(encryptHmacSHA384(data, key));
    }

    /**
     * 返回HmacSHA384加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA384加密后的16进制字符串
     */
    public static String encryptHmacSHA384ToHexString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA384ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA384加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA384加密后的16进制字符串
     */
    public static String encryptHmacSHA384ToHexString(final byte[] data, final byte[] key)
    {
        return bytes2HexString(encryptHmacSHA384(data, key));
    }

    /**
     * 返回HmacSHA384加密后的字节数组
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA384加密后的字节数组
     */
    public static byte[] encryptHmacSHA384(final byte[] data, final byte[] key)
    {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * 返回HmacSHA512加密后的字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA512加密后的16进制字符串
     */
    public static String encryptHmacSHA512ToString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA512加密后的字符串
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA512加密后的字符串
     */
    public static String encryptHmacSHA512ToString(final byte[] data, final byte[] key)
    {
        return bytes2String(encryptHmacSHA512(data, key));
    }

    /**
     * 返回HmacSHA512加密后的16进制字符串
     *
     * @param data 待加密字符串
     * @param key  密钥
     * @return HmacSHA512加密后的16进制字符串
     */
    public static String encryptHmacSHA512ToHexString(final String data, final String key)
    {
        if (data == null || data.length() == 0 || key == null || key.length() == 0)
        {
            return "";
        }
        return encryptHmacSHA512ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * 返回HmacSHA512加密后的16进制字符串
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA512加密后的16进制字符串
     */
    public static String encryptHmacSHA512ToHexString(final byte[] data, final byte[] key)
    {
        return bytes2HexString(encryptHmacSHA512(data, key));
    }

    /**
     * 返回HmacSHA512加密后的字节数组
     *
     * @param data 待加密字节数组
     * @param key  密钥
     * @return HmacSHA512加密后的字节数组
     */
    public static byte[] encryptHmacSHA512(final byte[] data, final byte[] key)
    {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    /**
     * 返回hmac加密算法结果
     *
     * @param data      待加密数据
     * @param key       密钥
     * @param algorithm 算法类型
     * @return 加密算法结果
     */
    private static byte[] hmacTemplate(final byte[] data,
                                       final byte[] key,
                                       final String algorithm)
    {
        if (data == null || data.length == 0 || key == null || key.length == 0)
        {
            return null;
        }
        try
        {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the Base64-encode bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of DES encryption
     */
    public static byte[] encryptDES2Base64(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv)
    {
        return base64Encode(encryptDES(data, key, transformation, iv));
    }

    /**
     * Return the hex string of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the hex string of DES encryption
     */
    public static String encryptDES2HexString(final byte[] data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv)
    {
        return bytes2HexString(encryptDES(data, key, transformation, iv));
    }

    public static String encryptDES2String(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv)
    {
        return bytes2String(encryptDES(data, key, transformation, iv));
    }

    /**
     * Return the bytes of DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES encryption
     */
    public static byte[] encryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv)
    {
        return symmetricTemplate(data, key, "DES", transformation, iv, true);
    }

    /**
     * Return the bytes of DES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption for Base64-encode bytes
     */
    public static byte[] decryptBase64DES(final byte[] data,
                                          final byte[] key,
                                          final String transformation,
                                          final byte[] iv)
    {
        return decryptDES(base64Decode(data), key, transformation, iv);
    }

    /**
     * Return the bytes of DES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption for hex string
     */
    public static byte[] decryptHexStringDES(final String data,
                                             final byte[] key,
                                             final String transformation,
                                             final byte[] iv)
    {
        return decryptDES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * Return the bytes of DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of DES decryption
     */
    public static byte[] decryptDES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv)
    {
        return symmetricTemplate(data, key, "DES", transformation, iv, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 3DES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the Base64-encode bytes of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the Base64-encode bytes of 3DES encryption
     */
    public static byte[] encrypt3DES2Base64(final byte[] data,
                                            final byte[] key,
                                            final String transformation,
                                            final byte[] iv)
    {
        return base64Encode(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * Return the hex string of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the hex string of 3DES encryption
     */
    public static String encrypt3DES2HexString(final byte[] data,
                                               final byte[] key,
                                               final String transformation,
                                               final byte[] iv)
    {
        return bytes2HexString(encrypt3DES(data, key, transformation, iv));
    }

    public static String encrypt3DES2String(final byte[] data,
                                            final byte[] key,
                                            final String transformation,
                                            final byte[] iv)
    {
        return bytes2String(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * Return the bytes of 3DES encryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES encryption
     */
    public static byte[] encrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv)
    {
        return symmetricTemplate(data, key, "DESede", transformation, iv, true);
    }

    /**
     * Return the bytes of 3DES decryption for Base64-encode bytes.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption for Base64-encode bytes
     */
    public static byte[] decryptBase64_3DES(final byte[] data,
                                            final byte[] key,
                                            final String transformation,
                                            final byte[] iv)
    {
        return decrypt3DES(base64Decode(data), key, transformation, iv);
    }

    /**
     * Return the bytes of 3DES decryption for hex string.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption for hex string
     */
    public static byte[] decryptHexString3DES(final String data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv)
    {
        return decrypt3DES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * Return the bytes of 3DES decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param iv             The buffer with the IV. The contents of the
     *                       buffer are copied to protect against subsequent modification.
     * @return the bytes of 3DES decryption
     */
    public static byte[] decrypt3DES(final byte[] data,
                                     final byte[] key,
                                     final String transformation,
                                     final byte[] iv)
    {
        return symmetricTemplate(data, key, "DESede", transformation, iv, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AES encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 使用UUID产生随机的AES密钥(这里产生密钥必须是16位)
     */
    public static byte[] generateAESKey()
    {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String key = StringUtils.string2HexString(uuid);

        try
        {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            //AES 要求密钥长度为 128
            kg.init(128, new SecureRandom(key.getBytes(UTF8)));
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            return keySpec.getEncoded();
            //            return new String(keySpec.getEncoded(), UTF8);
        } catch (NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密，结果转为Base64编码
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static byte[] encryptAES2Base64(final String data, final byte[] key)
    {
        if (StringUtils.isEmpty(data))
        {
            return null;
        }
        return encryptAES2Base64(data.getBytes(UTF8), key);
    }

    /**
     * AES加密，结果转为Base64编码
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static byte[] encryptAES2Base64(final byte[] data, final byte[] key)
    {
        return encryptAES2Base64(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * AES加密，结果转为Base64编码
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 加密结果
     */
    public static byte[] encryptAES2Base64(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv)
    {
        return base64Encode(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES加密，结果转为普通字符串
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String encryptAES2String(final String data, final byte[] key)
    {
        if (StringUtils.isEmpty(data))
        {
            return null;
        }

        return encryptAES2String(data.getBytes(UTF8), key);
    }

    /**
     * AES加密，结果转为普通字符串
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String encryptAES2String(final byte[] data, final byte[] key)
    {
        return encryptAES2String(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * AES加密，结果转为普通字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 加密结果
     */
    public static String encryptAES2String(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv)
    {
        return bytes2String(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES加密，结果转为16进制字符串
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String encryptAES2HexString(final String data, final byte[] key)
    {
        return encryptAES2HexString(data.getBytes(UTF8), key);
    }

    /**
     * AES加密，结果转为16进制字符串
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static String encryptAES2HexString(final byte[] data, final byte[] key)
    {
        return encryptAES2HexString(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * AES加密，结果转为16进制字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 加密结果
     */
    public static String encryptAES2HexString(final byte[] data,
                                              final byte[] key,
                                              final String transformation,
                                              final byte[] iv)
    {
        return bytes2HexString(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES加密，返回字节数组
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密结果
     */
    public static byte[] encryptAES(final byte[] data, final byte[] key)
    {
        return encryptAES(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * AES加密，返回字节数组
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 加密结果
     */
    public static byte[] encryptAES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv)
    {
        return symmetricTemplate(data, key, "AES", transformation, iv, true);
    }


    /**
     * 【不推荐】AES解密，返回普通字符串数据
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密结果
     */
    public static String decryptAES2String(final byte[] data, final byte[] key)
    {
        return decryptAES2String(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * 【不推荐】AES解密，返回普通字符串数据
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 解密结果
     */
    public static String decryptAES2String(final byte[] data,
                                           final byte[] key,
                                           final String transformation,
                                           final byte[] iv)
    {
        return new String(decryptAES(data, key, transformation, iv), UTF8);
    }

    /**
     * 【不推荐】AES解密Base64编码数据,返回普通字符串
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密结果
     */
    public static String decryptBase64AES2String(final byte[] data, final byte[] key)
    {
        return decryptBase64AES2String(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * 【不推荐】AES解密Base64编码数据,返回普通字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 解密结果
     */
    public static String decryptBase64AES2String(final byte[] data,
                                                 final byte[] key,
                                                 final String transformation,
                                                 final byte[] iv)
    {
        return new String(decryptBase64AES(data, key, transformation, iv), UTF8);
    }

    /**
     * AES解密Base64编码数据,返回字节数组
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密结果
     */
    public static byte[] decryptBase64AES(final byte[] data,
                                          final byte[] key)
    {
        return decryptBase64AES(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * AES解密Base64编码数据,返回字节数组
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 解密结果
     */
    public static byte[] decryptBase64AES(final byte[] data,
                                          final byte[] key,
                                          final String transformation,
                                          final byte[] iv)
    {
        return decryptAES(base64Decode(data), key, transformation, iv);
    }

    /**
     * 【不推荐】 AES解密16进制字符串数据,返回普通字符串
     * 使用默认的Transformation"AES/CBC/PKCS5Padding",AES代表加密算法，ECS代表工作模式，NoPadding代表填充方式
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密结果
     */
    public static String decryptHexStringAES2String(final String data, final byte[] key)
    {
        return decryptHexStringAES2String(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * 【不推荐】AES解密16进制字符串数据,返回普通字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 解密结果
     */
    public static String decryptHexStringAES2String(final String data,
                                                    final byte[] key,
                                                    final String transformation,
                                                    final byte[] iv)
    {
        return new String(decryptHexStringAES(data, key, transformation, iv), UTF8);
    }

    /**
     * AES解密16进制字符串数据,返回字节数组
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密结果
     */
    public static byte[] decryptHexStringAES(final String data,
                                             final byte[] key)
    {
        return decryptHexStringAES(data, key);
    }

    /**
     * AES解密16进制字符串数据,返回字节数组
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 解密结果
     */
    public static byte[] decryptHexStringAES(final String data,
                                             final byte[] key,
                                             final String transformation,
                                             final byte[] iv)
    {
        if (StringUtils.isEmpty(data))
        {
            return null;
        }
        return decryptAES(hexString2Bytes(data), key, transformation, iv);
    }

    /**
     * AES解密字节数组
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密结果
     */
    public static byte[] decryptAES(final byte[] data,
                                    final byte[] key)
    {
        return decryptAES(data, key, AES_DEFAULT_TRANSFORMATION, AES_DEFAULT_IV);
    }

    /**
     * AES解密字节数组
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation Transformation方式，例如"AES/CBC/PKCS5Padding"
     * @param iv             偏移量，例如"16-Bytes--String"
     * @return 解密结果
     */
    public static byte[] decryptAES(final byte[] data,
                                    final byte[] key,
                                    final String transformation,
                                    final byte[] iv)
    {
        return symmetricTemplate(data, key, "AES", transformation, iv, false);
    }

    /**
     * Return the bytes of symmetric encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param algorithm      The name of algorithm.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS5Padding</i>.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of symmetric encryption or decryption
     */
    private static byte[] symmetricTemplate(final byte[] data,
                                            final byte[] key,
                                            final String algorithm,
                                            final String transformation,
                                            final byte[] iv,
                                            final boolean isEncrypt)
    {
        if (data == null || data.length == 0 || key == null || key.length == 0)
        {
            return null;
        }
        try
        {
            SecretKey secretKey;
            if ("DES".equals(algorithm))
            {
                DESKeySpec desKey = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
                secretKey = keyFactory.generateSecret(desKey);
            } else
            {
                secretKey = new SecretKeySpec(key, algorithm);
            }
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0)
            {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
            } else
            {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // RSA encryption
    ///////////////////////////////////////////////////////////////////////////

    /**
     * RSA加密，返回普通字符串数据
     * 使用默认的Transformation"RSA/ECB/NoPadding"
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 加密结果
     */
    public static String encryptRSA2String(final byte[] data, final byte[] key, final boolean isPublicKey)
    {
        return encryptRSA2String(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA加密，返回普通字符串数据
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation Transformation模式
     * @return 加密结果
     */
    public static String encryptRSA2String(final byte[] data,
                                           final byte[] key,
                                           final boolean isPublicKey,
                                           final String transformation)
    {
        return bytes2String(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA加密，返回Base64编码的字节数组
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 加密结果
     */
    public static byte[] encryptRSA2Base64(final byte[] data,
                                           final byte[] key,
                                           final boolean isPublicKey)
    {
        return encryptRSA2Base64(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA加密，返回Base64编码的字节数组
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation Transformation模式
     * @return 加密结果
     */
    public static byte[] encryptRSA2Base64(final byte[] data,
                                           final byte[] key,
                                           final boolean isPublicKey,
                                           final String transformation)
    {
        return base64Encode(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA加密，返回16进制字符串
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 加密结果
     */
    public static String encryptRSA2HexString(final byte[] data,
                                              final byte[] key,
                                              final boolean isPublicKey)
    {
        return encryptRSA2HexString(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA加密，返回16进制字符串
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation Transformation模式
     * @return 加密结果
     */
    public static String encryptRSA2HexString(final byte[] data,
                                              final byte[] key,
                                              final boolean isPublicKey,
                                              final String transformation)
    {
        return bytes2HexString(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA加密，返回字节数组
     *
     * @param data        待加密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 加密结果
     */
    public static byte[] encryptRSA(final byte[] data,
                                    final byte[] key,
                                    final boolean isPublicKey)
    {
        return encryptRSA(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA加密，返回字节数组
     *
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation Transformation
     * @return 加密结果
     */
    public static byte[] encryptRSA(final byte[] data,
                                    final byte[] key,
                                    final boolean isPublicKey,
                                    final String transformation)
    {
        return rsaTemplate(data, key, isPublicKey, transformation, true);
    }

    /**
     * RSA解密Base64数据，返回字节数组
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 解密结果
     */
    public static byte[] decryptBase64RSA(final byte[] data,
                                          final byte[] key,
                                          final boolean isPublicKey)
    {
        return decryptBase64RSA(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA解密Base64数据，返回字节数组
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation transformation
     * @return 解密结果
     */
    public static byte[] decryptBase64RSA(final byte[] data,
                                          final byte[] key,
                                          final boolean isPublicKey,
                                          final String transformation)
    {
        return decryptRSA(base64Decode(data), key, isPublicKey, transformation);
    }

    /**
     * 【不推荐】RSA解密Base64数据，返回普通字符串
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 解密结果
     */
    public static String decryptBase64RSA2String(final byte[] data,
                                                 final byte[] key,
                                                 final boolean isPublicKey)
    {
        return decryptBase64RSA2String(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * 【不推荐】RSA解密Base64数据，返回普通字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation transformation
     * @return 解密结果
     */
    public static String decryptBase64RSA2String(final byte[] data,
                                                 final byte[] key,
                                                 final boolean isPublicKey,
                                                 final String transformation)
    {
        return new String(decryptBase64RSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA解密16进制HexString，返回字节数组
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 解密结果
     */
    public static byte[] decryptHexStringRSA(final String data,
                                             final byte[] key,
                                             final boolean isPublicKey)
    {
        return decryptHexStringRSA(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA解密16进制HexString，返回字节数组
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation Transformation
     * @return 解密结果
     */
    public static byte[] decryptHexStringRSA(final String data,
                                             final byte[] key,
                                             final boolean isPublicKey,
                                             final String transformation)
    {
        return decryptRSA(hexString2Bytes(data), key, isPublicKey, transformation);
    }

    /**
     * 【不推荐】RSA解密16进制HexString，返回普通字符串
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 解密结果
     */
    public static String decryptHexStringRSA2String(final String data,
                                                    final byte[] key,
                                                    final boolean isPublicKey)
    {
        return decryptHexStringRSA2String(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * RSA解密16进制HexString，返回普通字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation Transformation
     * @return 解密结果
     */
    public static String decryptHexStringRSA2String(final String data,
                                                    final byte[] key,
                                                    final boolean isPublicKey,
                                                    final String transformation)
    {
        return new String(decryptHexStringRSA(data, key, isPublicKey, transformation), UTF8);
    }

    /**
     * 【不推荐】RSA解密，返回普通字符串
     *
     * @param data        待解密数据
     * @param key         密钥
     * @param isPublicKey 是否为公钥
     * @return 解密结果
     */
    public static String decryptRSA2String(final byte[] data,
                                           final byte[] key,
                                           final boolean isPublicKey)
    {
        return decryptRSA2String(data, key, isPublicKey, RSA_DEFAULT_TRANSFORMATION);
    }

    /**
     * 【不推荐】RSA解密，返回普通字符串
     *
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    是否为公钥
     * @param transformation transformation
     * @return 解密结果
     */
    public static String decryptRSA2String(final byte[] data,
                                           final byte[] key,
                                           final boolean isPublicKey,
                                           final String transformation)
    {
        return new String(decryptRSA(data, key, isPublicKey, transformation), UTF8);
    }

    /**
     * 生成RSA公钥和私钥
     *
     * @return 公钥和私钥的pair对象
     */
    public static Pair<byte[], byte[]> generateRSAKeys() throws NoSuchAlgorithmException
    {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new Pair<>(publicKey.getEncoded(), privateKey.getEncoded());
    }

    /**
     * Return the bytes of RSA decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>RSA/CBC/PKCS1Padding</i>.
     * @return the bytes of RSA decryption
     */
    public static byte[] decryptRSA(final byte[] data,
                                    final byte[] key,
                                    final boolean isPublicKey,
                                    final String transformation)
    {
        return rsaTemplate(data, key, isPublicKey, transformation, false);
    }

    /**
     * Return the bytes of RSA encryption or decryption.
     *
     * @param data           The data.
     * @param key            The key.
     * @param isPublicKey    True to use public key, false to use private key.
     * @param transformation The name of the transformation, e.g., <i>DES/CBC/PKCS1Padding</i>.
     * @param isEncrypt      True to encrypt, false otherwise.
     * @return the bytes of RSA encryption or decryption
     */
    private static byte[] rsaTemplate(final byte[] data,
                                      final byte[] key,
                                      final boolean isPublicKey,
                                      final String transformation,
                                      final boolean isEncrypt)
    {
        if (data == null || data.length == 0 || key == null || key.length == 0)
        {
            return null;
        }
        try
        {
            Key rsaKey;
            if (isPublicKey)
            {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
                rsaKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            } else
            {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
                rsaKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
            }
            if (rsaKey == null)
            {
                return null;
            }
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
            int len = data.length;
            int maxLen = isEncrypt ? RSA_MAX_ENCRYPT_BLOCK : RSA_MAX_DECRYPT_BLOCK;
            int count = len / maxLen;
            if (count > 0)
            {
                byte[] ret = new byte[0];
                byte[] buff = new byte[maxLen];
                int index = 0;
                for (int i = 0; i < count; i++)
                {
                    System.arraycopy(data, index, buff, 0, maxLen);
                    ret = joins(ret, cipher.doFinal(buff));
                    index += maxLen;
                }
                if (index != len)
                {
                    int restLen = len - index;
                    buff = new byte[restLen];
                    System.arraycopy(data, index, buff, 0, restLen);
                    ret = joins(ret, cipher.doFinal(buff));
                }
                return ret;
            } else
            {
                return cipher.doFinal(data);
            }
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        } catch (InvalidKeyException e)
        {
            e.printStackTrace();
        } catch (BadPaddingException e)
        {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        } catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // other utils methods
    ///////////////////////////////////////////////////////////////////////////

    private static byte[] joins(final byte[] prefix, final byte[] suffix)
    {
        byte[] ret = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
        return ret;
    }

    private static String bytes2String(final byte[] bytes)
    {
        if (bytes == null || bytes.length <= 0)
        {
            return "";
        }
        return new String(bytes, UTF8);
    }

    private static final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytes2HexString(final byte[] bytes)
    {
        if (bytes == null)
        {
            return "";
        }
        int len = bytes.length;
        if (len <= 0)
        {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++)
        {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static byte[] hexString2Bytes(String hexString)
    {
        if (isSpace(hexString))
        {
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0)
        {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2)
        {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar)
    {
        if (hexChar >= '0' && hexChar <= '9')
        {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F')
        {
            return hexChar - 'A' + 10;
        } else
        {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] base64Encode(final byte[] input)
    {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input)
    {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    private static boolean isSpace(final String s)
    {
        if (s == null)
        {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i)
        {
            if (!Character.isWhitespace(s.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
}
