package com.lwkandroid.lib.core.kt.extension

import android.os.Build
import androidx.annotation.RequiresApi
import com.lwkandroid.lib.core.kt.encrypt.EncryptHelper.Companion.HEX_STRING
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

/**
 * Description:String扩展类
 * @author LWK
 * @date 2020/1/2
 */

/**
 * 用android.util.Base64对字符串进行Base64编码，结果转String对象
 */
fun String.base64EncodeByAndroidToString(charset: Charset = StandardCharsets.UTF_8): String = String(toByteArray(charset).base64EncodeByAndroid(), charset)

/**
 * 用java.util.Base64对字符串进行Base64编码，结果转String对象
 */
@RequiresApi(Build.VERSION_CODES.O)
fun String.base64EncodeByJavaToString(charset: Charset = StandardCharsets.UTF_8): String = String(toByteArray(charset).base64EncodeByJava(), charset)

/**
 * 用android.util.Base64对字符串进行Base64解码，结果转String对象
 */
fun String.base64DecodeByAndroidToString(charset: Charset = StandardCharsets.UTF_8): String = String(toByteArray(charset).base64DecodeByAndroid(), charset)

/**
 * 用java.util.Base64对字符串进行Base64解码，结果转String对象
 */
@RequiresApi(Build.VERSION_CODES.O)
fun String.base64DecodeByJavaToString(charset: Charset = StandardCharsets.UTF_8): String = String(toByteArray(charset).base64DecodeByJava(), charset)

private const val DEF_SPLIT_CHAR = " "
/**
 * 转为ASCII码字符串（十进制字符串）
 */
fun String.asciiEncodeWithSplit(splitChar: String = DEF_SPLIT_CHAR): String {
    val builder = StringBuilder()
    val chars = toCharArray()
    chars.forEachIndexed { index, c ->
        run {
            builder.append(c.toInt().toString(10))
            if (index != chars.lastIndex)
                builder.append(splitChar)
        }
    }
    return builder.toString()
}

/**
 * 将ASCII（十进制字符串）转为普通字符串
 */
fun String.asciiDecodeWithSplit(splitChar: String = DEF_SPLIT_CHAR): String {
    val builder = StringBuilder()
    val charsList = split(splitChar)
    for (charValue in charsList) {
        builder.append(charValue.toInt(10).toChar())
    }
    return builder.toString()
}

/**
 * 转为二进制字符串
 */
fun String.binEncodeWithSplit(splitChar: String = DEF_SPLIT_CHAR): String {
    val builder = StringBuilder()
    val chars = toCharArray()
    chars.forEachIndexed { index, c ->
        run {
            builder.append(c.toInt().toString(2))
            if (index != chars.lastIndex)
                builder.append(splitChar)
        }
    }
    return builder.toString()
}

/**
 * 将二进制字符串转为普通字符串
 */
fun String.binDecodeWithSplit(splitChar: String = DEF_SPLIT_CHAR): String {
    val builder = StringBuilder()
    val charsList = split(splitChar)
    for (charValue in charsList) {
        builder.append(charValue.toInt(2).toChar())
    }
    return builder.toString()
}

/**
 * 转为十六进制字符串
 */
fun String.hexEncodeWithSplit(splitChar: String = DEF_SPLIT_CHAR): String {
    val builder = StringBuilder()
    val chars = toCharArray()
    chars.forEachIndexed { index, c ->
        run {
            builder.append(c.toInt().toString(16).toUpperCase())
            if (index != chars.lastIndex)
                builder.append(splitChar)
        }
    }
    return builder.toString()
}

/**
 * 将十六进制字符串转为普通字符串
 */
fun String.hexDecodeWithSplit(splitChar: String = DEF_SPLIT_CHAR): String {
    val builder = StringBuilder()
    val charsList = split(splitChar)
    for (charValue in charsList) {
        builder.append(charValue.toInt(16).toChar().toUpperCase())
    }
    return builder.toString()
}

/**
 * 将十六进制字符串转为ByteArray
 */
fun String.hexDecode(): ByteArray {
    val chars = toUpperCase().toCharArray()
    val bytes = ByteArray(length shr 2)
    for (i in bytes.indices) {
        val pos = i * 2
        bytes[i] = (HEX_STRING.indexOf(chars[pos]) shl 4 or HEX_STRING.indexOf(chars[pos + 1])).toByte()
    }
    return bytes
}

