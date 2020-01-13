package com.lwkandroid.lib.core.kt.extension

import android.os.Build
import androidx.annotation.RequiresApi
import com.lwkandroid.lib.core.kt.encrypt.EncryptHelper
import com.lwkandroid.lib.core.kt.encrypt.EncryptHelper.Companion.HEX_STRING
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.spec.AlgorithmParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


/**
 * Description:ByteArray扩展函数
 * @author LWK
 * @date 2019/12/9
 */

/**
 * 用android.util.Base64工具进行Base64编码
 */
fun ByteArray.base64EncodeByAndroid(): ByteArray = android.util.Base64.encode(this, android.util.Base64.NO_WRAP)

/**
 * 用android.util.Base64工具进行Base64解码
 */
fun ByteArray.base64DecodeByAndroid(): ByteArray = android.util.Base64.decode(this, android.util.Base64.NO_WRAP)

/**
 * 用java.util.Base64工具进行Base64编码
 */
@RequiresApi(Build.VERSION_CODES.O)
fun ByteArray.base64EncodeByJava(): ByteArray = java.util.Base64.getEncoder().encode(this)

/**
 * 用java.util.Base64工具进行Base64解码
 */
@RequiresApi(Build.VERSION_CODES.O)
fun ByteArray.base64DecodeByJava(): ByteArray = java.util.Base64.getDecoder().decode(this)

/**
 * 转16进制编码字符串
 */
fun ByteArray.hexEncode(): String {
    val builder = StringBuilder(this.size shl 1)
    for (b in this) {
        builder.append(HEX_STRING[b.toInt() and 0xf0 shr 4])
        builder.append(HEX_STRING[b.toInt() and 0x0f])
    }
    return builder.toString()
}

/**
 *Md2 Hash加密算法
 */
fun ByteArray.hashMd2Encrypt(): ByteArray = hashEncrypt(EncryptHelper.ALGORITHM_HASH_MD2)

/**
 *Md5 Hash加密算法
 */
fun ByteArray.hashMd5Encrypt(): ByteArray = hashEncrypt(EncryptHelper.ALGORITHM_HASH_MD5)

/**
 *Sha1 Hash加密算法
 */
fun ByteArray.hashSha1Encrypt(): ByteArray = hashEncrypt(EncryptHelper.ALGORITHM_HASH_SHA1)

/**
 *Sha224 Hash加密算法
 */
fun ByteArray.hashSha224Encrypt(): ByteArray = hashEncrypt(EncryptHelper.ALGORITHM_HASH_SHA224)

/**
 *Sha256 Hash加密算法
 */
fun ByteArray.hashSha256Encrypt(): ByteArray = hashEncrypt(EncryptHelper.ALGORITHM_HASH_SHA256)

/**
 *Sha384 Hash加密算法
 */
fun ByteArray.hashSha384Encrypt(): ByteArray = hashEncrypt(EncryptHelper.ALGORITHM_HASH_SHA384)

/**
 *Sha512 Hash加密算法
 */
fun ByteArray.hashSha512Encrypt(): ByteArray = hashEncrypt(EncryptHelper.ALGORITHM_HASH_SHA512)

/**
 * Hash加密
 * @param algorithm 加密算法
 */
fun ByteArray.hashEncrypt(algorithm: String): ByteArray {
    val md = MessageDigest.getInstance(algorithm)
    md.update(this)
    return md.digest()
}

/**
 *Md5 Hmac加密算法
 */
fun ByteArray.hmacMd5Encrypt(key: ByteArray) = hmacEncrypt(EncryptHelper.ALGORITHM_HMAC_MD5, key)

/**
 *Sha1 Hmac加密算法
 */
fun ByteArray.hmacSha1Encrypt(key: ByteArray) = hmacEncrypt(EncryptHelper.ALGORITHM_HMAC_SHA1, key)

/**
 *Sha224 Hmac加密算法
 */
fun ByteArray.hmacSha224Encrypt(key: ByteArray) = hmacEncrypt(EncryptHelper.ALGORITHM_HMAC_SHA224, key)

/**
 *Sha256 Hmac加密算法
 */
fun ByteArray.hmacSha256Encrypt(key: ByteArray) = hmacEncrypt(EncryptHelper.ALGORITHM_HMAC_SHA256, key)

/**
 *Sha384 Hmac加密算法
 */
fun ByteArray.hmacSha384Encrypt(key: ByteArray) = hmacEncrypt(EncryptHelper.ALGORITHM_HMAC_SHA384, key)

/**
 *Sha512 Hmac加密算法
 */
fun ByteArray.hmacSha512Encrypt(key: ByteArray) = hmacEncrypt(EncryptHelper.ALGORITHM_HMAC_SHA512, key)

/**
 *Hmac加密
 */
fun ByteArray.hmacEncrypt(algorithm: String, key: ByteArray): ByteArray {
    val secretKey = SecretKeySpec(key, algorithm)
    val mac = Mac.getInstance(algorithm)
    mac.init(secretKey)
    return mac.doFinal(this)
}

/**
 * Aes加密
 */
fun ByteArray.aesEncrypt(key: ByteArray, iv: ByteArray = EncryptHelper.AES_DEFAULT_IV.toByteArray(),
                         transformation: String = EncryptHelper.AES_DEFAULT_TRANSFORMATION): ByteArray =
        symmetricTemplate(EncryptHelper.ALGORITHM_AES, key, iv, transformation, true)

/**
 * Aes解密
 */
fun ByteArray.aesDecrypt(key: ByteArray, iv: ByteArray = EncryptHelper.AES_DEFAULT_IV.toByteArray(),
                         transformation: String = EncryptHelper.AES_DEFAULT_TRANSFORMATION): ByteArray =
        symmetricTemplate(EncryptHelper.ALGORITHM_AES, key, iv, transformation, false)

/**
 * Des加密
 */
fun ByteArray.desEncrypt(key: ByteArray, iv: ByteArray = EncryptHelper.DES_DEFAULT_IV.toByteArray(),
                         transformation: String = EncryptHelper.DES_DEFAULT_TRANSFORMATION): ByteArray =
        symmetricTemplate(EncryptHelper.ALGORITHM_DES, key, iv, transformation, true)

/**
 * Des解密
 */
fun ByteArray.desDecrypt(key: ByteArray, iv: ByteArray = EncryptHelper.DES_DEFAULT_IV.toByteArray(),
                         transformation: String = EncryptHelper.DES_DEFAULT_TRANSFORMATION): ByteArray =
        symmetricTemplate(EncryptHelper.ALGORITHM_DES, key, iv, transformation, false)

/**
 * tripleDes加密
 */
fun ByteArray.tripleDesEncrypt(key: ByteArray, iv: ByteArray = EncryptHelper.TRIPLE_DES_DEFAULT_IV.toByteArray(),
                               transformation: String = EncryptHelper.TRIPLE_DES_DEFAULT_TRANSFORMATION): ByteArray =
        symmetricTemplate(EncryptHelper.ALGORITHM_TRIPLE_DES, key, iv, transformation, true)

/**
 * tripleDes解密
 */
fun ByteArray.tripleDesDecrypt(key: ByteArray, iv: ByteArray = EncryptHelper.TRIPLE_DES_DEFAULT_IV.toByteArray(),
                               transformation: String = EncryptHelper.TRIPLE_DES_DEFAULT_TRANSFORMATION): ByteArray =
        symmetricTemplate(EncryptHelper.ALGORITHM_TRIPLE_DES, key, iv, transformation, false)

/**
 * 对称加/解密
 */
fun ByteArray.symmetricTemplate(algorithm: String, key: ByteArray, iv: ByteArray?, transformation: String, isEncrypt: Boolean): ByteArray {
    var secretKey: SecretKey = if (EncryptHelper.ALGORITHM_DES == algorithm) {
        val desKey = DESKeySpec(key)
        val keyFactory = SecretKeyFactory.getInstance(algorithm)
        keyFactory.generateSecret(desKey)
    } else {
        SecretKeySpec(key, algorithm)
    }
    val cipher = Cipher.getInstance(transformation)
    if (iv == null || iv.isEmpty()) {
        cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey)
    } else {
        val params: AlgorithmParameterSpec = IvParameterSpec(iv)
        cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey, params)
    }
    return cipher.doFinal(this)
}

/**
 * Rsa加密
 */
fun ByteArray.rasEncrypt(key: ByteArray, isPublicKey: Boolean, transformation: String = EncryptHelper.RSA_DEFAULT_TRANSFORMATION,
                         maxEncryptBlock: Int = EncryptHelper.RSA_MAX_ENCRYPT_BLOCK): ByteArray =
        rsaTemplate(key, transformation, isPublicKey, true, maxEncryptBlock, EncryptHelper.RSA_MAX_DECRYPT_BLOCK)

/**
 * Rsa解密
 */
fun ByteArray.rasDecrypt(key: ByteArray, isPublicKey: Boolean, transformation: String = EncryptHelper.RSA_DEFAULT_TRANSFORMATION,
                         maxDecryptBlock: Int = EncryptHelper.RSA_MAX_DECRYPT_BLOCK): ByteArray =
        rsaTemplate(key, transformation, isPublicKey, false, EncryptHelper.RSA_MAX_ENCRYPT_BLOCK, maxDecryptBlock)

/**
 * RSA加/解密
 */
fun ByteArray.rsaTemplate(key: ByteArray, transformation: String, isPublicKey: Boolean,
                          isEncrypt: Boolean, maxEncryptBlock: Int, maxDecryptBlock: Int): ByteArray {
    val rsaKey = (if (isPublicKey) {
        KeyFactory.getInstance(EncryptHelper.ALGORITHM_RSA).generatePublic(X509EncodedKeySpec(key))
    } else {
        KeyFactory.getInstance(EncryptHelper.ALGORITHM_RSA).generatePrivate(PKCS8EncodedKeySpec(key))
    }) ?: return ByteArray(0)

    val cipher = Cipher.getInstance(transformation)
    cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, rsaKey)
    val maxLen: Int = if (isEncrypt) maxEncryptBlock else maxDecryptBlock
    val count = size / maxLen
    return if (count > 0) {
        var ret = ByteArray(0)
        var buff = ByteArray(maxLen)
        var index = 0
        for (i in 0 until count) {
            System.arraycopy(this, index, buff, 0, maxLen)
            ret = joinsByteArray(ret, cipher.doFinal(buff))
            index += maxLen
        }
        if (index != size) {
            val restLen = size - index
            buff = ByteArray(restLen)
            System.arraycopy(this, index, buff, 0, restLen)
            ret = joinsByteArray(ret, cipher.doFinal(buff))
        }
        ret
    } else {
        cipher.doFinal(this)
    }
}

private fun joinsByteArray(prefix: ByteArray, suffix: ByteArray): ByteArray {
    val ret = ByteArray(prefix.size + suffix.size)
    System.arraycopy(prefix, 0, ret, 0, prefix.size)
    System.arraycopy(suffix, 0, ret, prefix.size, suffix.size)
    return ret
}