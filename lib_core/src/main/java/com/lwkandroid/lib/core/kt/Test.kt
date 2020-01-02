package com.lwkandroid.lib.core.kt

import com.lwkandroid.lib.core.kt.extension.hexDecode
import com.lwkandroid.lib.core.kt.extension.hexEncode

/**
 * Description:
 * @date 2019/12/2
 */

fun main() {
    val str = "abc123-=/!@#阿弥佗佛"
    val test1 = str.hexEncode()
    println(test1)
    val test2 = test1.hexDecode()
    println(test2)
}