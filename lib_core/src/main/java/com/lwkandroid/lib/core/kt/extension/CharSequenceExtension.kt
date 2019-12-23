package com.lwkandroid.lib.core.kt.extension

import java.util.regex.Pattern

/**
 * Description:CharSequence扩展函数
 * @author LWK
 * @date 2019/12/2
 */

/**
 * 判断字符串是否不为null且去除首尾空字符后不为empty
 */
fun CharSequence?.isNotNullAndTrimNotEmpty() = this != null && trim().isNotEmpty()

/**
 * 判断字符串是否为null或者去除首尾空字符后为empty
 */
fun CharSequence?.isNullOrTrimEmpty() = this == null || trim().isEmpty()

/**
 * 首字母大写
 */
fun CharSequence.upperCaseFirstLetter(): CharSequence {
    if (Character.isUpperCase(get(0)))
        return this
    return if (length == 1)
        get(0).toUpperCase().toString()
    else
        get(0).toUpperCase() + substring(1)
}

/**
 * 首字母小写
 */
fun CharSequence.lowerCaseFirstLetter(): CharSequence {
    if (Character.isLowerCase(get(0)))
        return this
    return if (length == 1)
        get(0).toLowerCase().toString()
    else
        get(0).toLowerCase() + substring(1)
}

/**
 * 转为全角字符
 */
fun CharSequence.toFullWidthCharacters(): CharSequence {
    val chars = toString().toCharArray()
    chars.forEachIndexed { index, c ->
        run {
            when (c.toInt()) {
                32 -> chars[index] = 12288.toChar()
                in 33..126 -> chars[index] = (c.toInt() + 65248).toChar()
            }
        }
    }
    return String(chars)
}

/**
 * 转为半角字符
 */
fun CharSequence.toHalfWidthCharacters(): CharSequence {
    val chars = toString().toCharArray()
    chars.forEachIndexed { index, c ->
        run {
            when (c.toInt()) {
                12288 -> chars[index] = ' '
                in 65281..65374 -> chars[index] = (c.toInt() - 65248).toChar()
            }
        }
    }
    return String(chars)
}

private val BLANK_PATTERN: Pattern = Pattern.compile("\\s*|\t|\r|\n")

fun CharSequence.replaceAllBlank(relpaceStr: String = ""): CharSequence = BLANK_PATTERN.matcher(this).replaceAll(relpaceStr)