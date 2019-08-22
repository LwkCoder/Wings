package com.lwkandroid.wings.utils;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.lwkandroid.wings.Wings;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.loader.content.CursorLoader;

/**
 * Uri工具类
 *
 * @author LWK
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
        {
            return FileProvider.getUriForFile(Wings.getContext(),
                    Wings.WingsFileProvider.createAuthorities(), file);
        } else
        {
            return Uri.fromFile(file);
        }
    }

    /**
     * Uri to file.
     */
    public static File uri2File(@NonNull final Uri uri, final String columnName)
    {
        if (uri == null)
        {
            return null;
        }
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme()))
        {
            return new File(uri.getPath());
        }
        CursorLoader cl = new CursorLoader(Wings.getContext());
        cl.setUri(uri);
        cl.setProjection(new String[]{columnName});
        Cursor cursor = null;
        try
        {
            cursor = cl.loadInBackground();
            if (cursor != null)
            {
                int columnIndex = cursor.getColumnIndexOrThrow(columnName);
                cursor.moveToFirst();
                return new File(cursor.getString(columnIndex));
            }
            return null;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }
}
