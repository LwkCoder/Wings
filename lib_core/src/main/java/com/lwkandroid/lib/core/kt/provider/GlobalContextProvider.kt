package com.lwkandroid.lib.core.kt.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

/**
 * Description:无侵入式全局Context提供者
 * @author LWK
 * @date 2019/12/9
 */
class GlobalContextProvider : ContentProvider() {

    object ContextWrapper {
        lateinit var context: Context
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        throw IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context")
    }

    override fun onCreate(): Boolean {
        ContextWrapper.context = context!!
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context")
    }

    override fun getType(uri: Uri): String? {
        throw IllegalAccessError("Not Implemented.This provider only provides the global variable of Application context")
    }
}