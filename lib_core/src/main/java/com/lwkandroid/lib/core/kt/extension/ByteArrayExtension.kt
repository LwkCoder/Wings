package com.lwkandroid.lib.core.kt.extension

import android.os.Build
import androidx.annotation.RequiresApi


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