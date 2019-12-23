package com.lwkandroid.lib.core.kt.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.graphics.drawable.Drawable
import android.os.Build
import com.lwkandroid.lib.core.kt.AppContext

/**
 * Description:
 * @author LWK
 * @date 2019/12/9
 */
class AppUtils {
    companion object {

        /**
         * 根据进程id获取进程名称
         * @return 进程名称
         */
        fun getProcessName(pid: Int): String? {
            val manager: ActivityManager = AppContext.instance.getContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (process in manager?.runningAppProcesses) {
                if (pid == process?.pid)
                    return process.processName
            }
            return null
        }

        fun getPackageName(): String = AppContext.instance.getContext().packageName

        /**
         * 获取App名称
         * @return App名称
         */
        fun getAppName(): String? {
            return try {
                val pm: PackageManager = AppContext.instance.getContext().packageManager
                val pi: PackageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS)
                pi?.applicationInfo?.loadLabel(pm).toString()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 获取App版本名称
         *
         * @return App版本名称
         */
        fun getAppVersionName(): String? {
            return try {
                val pm: PackageManager = AppContext.instance.getContext().packageManager
                val pi: PackageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS)
                pi?.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 获取App版本号
         *
         * @return App版本号
         */
        fun getAppVersionCode(): Long {
            return try {
                val pm: PackageManager = AppContext.instance.getContext().packageManager
                val pi: PackageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) pi?.longVersionCode else pi?.versionCode.toLong()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                -1
            }
        }

        /**
         * 获取App图标
         *
         * @return App图标
         */
        fun getAppIcon(): Drawable? {
            return try {
                val pm: PackageManager = AppContext.instance.getContext().packageManager
                val pi: PackageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS)
                pi?.applicationInfo?.loadIcon(pm)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 获取App签名
         *
         * @return App签名
         */
        @SuppressLint("PackageManagerGetSignatures")
        fun getAppSignature(): Array<Signature?>? {
            return try {
                val pm: PackageManager = AppContext.instance.getContext().packageManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val pi: PackageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES)
                    pi?.signingInfo?.apkContentsSigners
                } else {
                    val pi: PackageInfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES)
                    pi?.signatures
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                null
            }
        }
    }
}