package com.lwkandroid.lib.core.kt.encrypt

import android.util.Pair
import com.lwkandroid.lib.core.kt.extension.hexEncode
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec

/**
 * Description:加解密常量、静态方法
 * @author LWK
 * @date 2020/1/3
 */
class EncryptHelper {

    companion object {
        const val HEX_STRING = "0123456789ABCDEF"
        const val ALGORITHM_HASH_MD2 = "MD2"
        const val ALGORITHM_HASH_MD5 = "MD5"
        const val ALGORITHM_HASH_SHA1 = "SHA-1"
        const val ALGORITHM_HASH_SHA224 = "SHA-224"
        const val ALGORITHM_HASH_SHA256 = "SHA-256"
        const val ALGORITHM_HASH_SHA384 = "SHA-384"
        const val ALGORITHM_HASH_SHA512 = "SHA-512"
        const val ALGORITHM_HMAC_MD5 = "HmacMD5"
        const val ALGORITHM_HMAC_SHA1 = "HmacSHA1"
        const val ALGORITHM_HMAC_SHA224 = "HmacSHA224"
        const val ALGORITHM_HMAC_SHA256 = "HmacSHA256"
        const val ALGORITHM_HMAC_SHA384 = "HmacSHA384"
        const val ALGORITHM_HMAC_SHA512 = "HmacSHA512"
        const val ALGORITHM_AES = "AES"
        const val ALGORITHM_DES = "DES"
        const val ALGORITHM_TRIPLE_DES = "DESede"
        const val ALGORITHM_RSA = "RSA"

        /**
         * 对称加密默认Transformation
         * ECB模式下不用偏移量
         *
         *
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
        const val AES_DEFAULT_TRANSFORMATION = "AES/CBC/PKCS7Padding"
        const val DES_DEFAULT_TRANSFORMATION = "DES/CBC/PKCS7Padding"
        const val TRIPLE_DES_DEFAULT_TRANSFORMATION = "DESede/CBC/PKCS7Padding"
        //对称加密默认偏移量
        const val DES_DEFAULT_IV = "12345678"
        const val TRIPLE_DES_DEFAULT_IV = "12345678"
        const val AES_DEFAULT_IV = "123456789abcdefg"
        //RSA加密默认Transformation
        const val RSA_DEFAULT_TRANSFORMATION = "RSA/NONE/PKCS1Padding"
        //RSA最大加密明文大小
        const val RSA_MAX_ENCRYPT_BLOCK = 117
        //RSA最大解密密文大小
        const val RSA_MAX_DECRYPT_BLOCK = 128
        //RSA密钥默认长度
        const val RSA_DEFAULT_KEY_BIT = 1024


        /**
         * 创建Des密钥
         */
        fun generateDesKey(keyBit: Int = 56) = generateSymmetricKey(ALGORITHM_AES, keyBit)

        /**
         * 创建Aes密钥
         */
        fun generateAesKey(keyBit: Int = 128) = generateSymmetricKey(ALGORITHM_AES, keyBit)

        /**
         * 创建TripleDes密钥
         */
        fun generateTripleDesKey(keyBit: Int = 168) = generateSymmetricKey(ALGORITHM_AES, keyBit)

        /**
         * 创建对称加密密钥
         * @param algorithm 算法名称
         * @param keyBit 密钥长度（位）
         */
        fun generateSymmetricKey(algorithm: String, keyBit: Int): ByteArray {
            val key = UUID.randomUUID().toString().replace("-", "")
                    .substring(0, 16).toByteArray().hexEncode().toByteArray()
            val kg: KeyGenerator = KeyGenerator.getInstance(algorithm)
            kg.init(keyBit, SecureRandom(key))
            val secretKey = kg.generateKey()
            val keySpec = SecretKeySpec(secretKey.encoded, algorithm)
            return keySpec.encoded
        }

        /**
         * 创建RSA密钥对
         */
        fun generateRsaKeys(keyBit: Int = RSA_DEFAULT_KEY_BIT): Pair<ByteArray, ByteArray> {
            val keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA)
            keyPairGen.initialize(keyBit)
            val keyPair = keyPairGen.generateKeyPair()
            val publicKey = keyPair.public as RSAPublicKey
            val privateKey = keyPair.private as RSAPrivateKey
            return Pair(publicKey.encoded, privateKey.encoded)
        }

        fun joinsByteArray(prefix: ByteArray, suffix: ByteArray): ByteArray {
            val ret = ByteArray(prefix.size + suffix.size)
            System.arraycopy(prefix, 0, ret, 0, prefix.size)
            System.arraycopy(suffix, 0, ret, prefix.size, suffix.size)
            return ret
        }
    }
}