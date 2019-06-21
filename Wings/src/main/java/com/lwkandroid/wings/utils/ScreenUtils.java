package com.lwkandroid.wings.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lwkandroid.wings.net.bean.ApiException;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * 屏幕相关工具类
 */
public final class ScreenUtils
{

    private ScreenUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    public static int getScreenWidth()
    {
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public static int getScreenHeight()
    {
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    public static float getScreenDensity()
    {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度Dpi
     *
     * @return 屏幕密度Dpi
     */
    public static int getScreenDensityDpi()
    {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    /**
     * 设置屏幕为横屏
     *
     * @param activity activity
     */
    public static void setLandscape(Activity activity)
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(Activity activity)
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape()
    {
        return Utils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait()
    {
        return Utils.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(Activity activity)
    {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation())
        {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * 截取当前屏幕为图片
     *
     * @param activity 当前屏幕Activity
     * @return 图片Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity)
    {
        return screenShot(activity, false);
    }

    /**
     * 截取当前屏幕为图片
     *
     * @param activity          当前屏幕Activity
     * @param isDeleteStatusBar 是否去除状态栏，true：是 false：否
     * @return 图片Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar)
    {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.setWillNotCacheDrawing(false);
        Bitmap bmp = decorView.getDrawingCache();
        if (bmp == null)
        {
            return null;
        }
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar)
        {
            int statusBarHeight = BarUtils.getStatusBarHeight();
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else
        {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createBitmapFromView(final Window window, final View view, final WingsCallBack<Bitmap> callBack)
    {
        if (view == null)
        {
            if (callBack != null)
            {
                callBack.onCallBackSuccess(null);
            }
            return;
        }

        view.post(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    int width = view.getWidth();
                    int height = view.getHeight();
                    int[] location = new int[2];
                    view.getLocationInWindow(location);
                    final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888, true);
                    PixelCopy.request(window, new Rect(location[0], location[1], location[0] + width, location[1] + height),
                            bitmap, new PixelCopy.OnPixelCopyFinishedListener()
                            {
                                @Override
                                public void onPixelCopyFinished(int copyResult)
                                {
                                    if (copyResult == PixelCopy.SUCCESS)
                                    {
                                        if (callBack != null)
                                        {
                                            callBack.onCallBackSuccess(bitmap);
                                        }
                                    } else
                                    {
                                        if (callBack != null)
                                        {
                                            callBack.onCallBackError(new ApiException(copyResult, "Fail to create bitmap from view"));
                                        }
                                    }
                                }
                            }, new Handler(Looper.getMainLooper()));
                } catch (Exception e)
                {
                    if (callBack != null)
                    {
                        callBack.onCallBackError(e);
                    }
                }
            }
        });
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock()
    {
        KeyguardManager km = (KeyguardManager) Utils.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        return km.isKeyguardLocked();
    }

}