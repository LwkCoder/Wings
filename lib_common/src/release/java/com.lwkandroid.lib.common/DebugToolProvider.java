package com.lwkandroid.lib.common;

import android.app.ActivityThread;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.lwkandroid.lib.core.java.imageloader.glide.GlideOkClient;
import com.lwkandroid.lib.core.java.net.RxHttp;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.DisplayLeakService;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Description:Debug模式下辅助工具
 *
 * @author LWK
 * @date 2020/2/15
 */
public class DebugProvider extends ContentProvider
{
    private static RefWatcher mRefWatcher;

    @Override
    public boolean onCreate()
    {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        return 0;
    }
}
