package com.lwkandroid.wings.utils;

import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.lwkandroid.wings.Wings;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

/**
 * Uri工具类
 */
public final class UriUtils
{
    private UriUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * File to uri.
     */
    public static Uri file2Uri(@NonNull final File file)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return FileProvider.getUriForFile(Utils.getContext(),
                    Wings.WingsFileProvider.createAuthorities(), file);
        else
            return Uri.fromFile(file);
    }

    /**
     * Uri to file.
     */
    public static File uri2File(@NonNull final Uri uri, final String columnName)
    {
        if (uri == null)
            return null;
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme()))
        {
            return new File(uri.getPath());
        }
        CursorLoader cl = new CursorLoader(Utils.getContext());
        cl.setUri(uri);
        cl.setProjection(new String[]{columnName});
        Cursor cursor = null;
        try
        {
            cursor = cl.loadInBackground();
            int columnIndex = cursor.getColumnIndexOrThrow(columnName);
            cursor.moveToFirst();
            return new File(cursor.getString(columnIndex));
        } finally
        {
            if (cursor != null)
                cursor.close();
        }
    }
}