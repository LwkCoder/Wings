package com.lwkandroid.lib.core.kt

import android.content.Context
import com.lwkandroid.lib.core.kt.provider.GlobalContextProvider

/**
 * Description:全局Context提供入口，静态内部类单例模式
 * @author LWK
 * @date 2019/12/9
 */
class AppContext private constructor() {

    companion object {
        val instance = InnerHolder.instance
    }

    private object InnerHolder {
        val instance = AppContext()
    }

    private val mContext: Context = GlobalContextProvider.ContextWrapper.context

    fun getContext(): Context {
        return mContext
    }
}
