package com.lwkandroid.lib.core.kt

import com.lwkandroid.lib.core.kt.extension.replaceAllBlank

/**
 * Description:
 * @author 20180004
 * @date 2019/12/2
 */

fun main() {
    val char1: CharSequence = " SDA56%^&     \r1\t2\n3\\s4898"
    println(char1.replaceAllBlank())
    println(char1.replaceAllBlank("~"))
//    println(char1.isNullOrEmpty())
}