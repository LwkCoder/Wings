package com.lwkandroid.lib.core.kt

import android.app.Activity
import com.lwkandroid.lib.core.kt.encrypt.EncryptHelper
import com.lwkandroid.lib.core.kt.extension.aesDecrypt
import com.lwkandroid.lib.core.kt.extension.aesEncrypt
import com.lwkandroid.lib.core.kt.extension.hexDecode
import com.lwkandroid.lib.core.kt.extension.hexEncode

/**
 * Description:
 * @date 2019/12/2
 */

fun main() {
    val str = "abc123-=/!@#阿弥佗佛"
    val key = EncryptHelper.generateAesKey()

    val encryptResult = str.toByteArray().aesEncrypt(key).hexEncode()
    println(encryptResult)
    println(encryptResult.hexDecode().aesDecrypt(key).toString())
}