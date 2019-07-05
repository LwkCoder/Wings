package com.lwkandroid.wings.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * 状态栏、导航栏相关工具类
 *
 * @author LWK
 */
public final class BarUtils
{
    private static final String FAKE_STATUS_BAR_TAG = "FakeStatusBar";
    private static final Pattern PATTERN_FLYME = Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE);
    private static final int MIN_API = Build.VERSION_CODES.KITKAT;

    /**
     * 默认透明度为1.0f（不透明）
     */
    private static final float DEFAULT_ALPHA = 1.0f;

    private BarUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /******************************************以下代码属于状态栏**********************************************************/

    /**
     * 判断状态栏是否存在
     */
    public static boolean isStatusBarExists(Activity activity)
    {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 获取状态栏高度【获取不到就返回24dp的高度】
     */
    public static int getStatusBarHeight()
    {
        int result = 24;
        Resources resources = Utils.getContext().getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0)
        {
            result = resources.getDimensionPixelSize(resId);
        } else
        {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarColor(Activity activity, @ColorInt int color)
    {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, true);
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarColor(Activity activity, @ColorInt int color, boolean fitSystemWindow)
    {
        setStatusBarColor(activity, color, DEFAULT_ALPHA, fitSystemWindow);
    }

    /**
     * 改变状态栏颜色的方法
     *
     * @param activity 依附的Activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度【不是颜色透明度】
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        setStatusBarColor(activity, color, alpha, true);
    }

    /**
     * 改变状态栏颜色的方法
     *
     * @param activity        依附的Activity
     * @param color           状态栏颜色值
     * @param alpha           状态栏透明度【不是颜色透明度】
     * @param firSystemWindow 是否自动调整布局间距
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha, boolean firSystemWindow)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(mixtureColor(color, alpha));
        } else if (Build.VERSION.SDK_INT >= MIN_API)
        {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setStatusBarColorForKitkat((ViewGroup) window.getDecorView(), color, alpha);
        }
        //设置窗口根布局自动调整间距
        fitSystemWindow(activity, firSystemWindow);
    }

    //4.4创建假的透明栏
    private static void setStatusBarColorForKitkat(ViewGroup container, int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        int mixtureColor = mixtureColor(color, alpha);
        View statusView = container.findViewWithTag(FAKE_STATUS_BAR_TAG);
        if (statusView != null)
        {
            statusView.setBackgroundColor(mixtureColor);
            statusView.setVisibility(View.VISIBLE);
        } else
        {
            statusView = new View(container.getContext());
            statusView.setBackgroundColor(mixtureColor);
            statusView.setTag(FAKE_STATUS_BAR_TAG);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            container.addView(statusView, lp);
        }
    }

    //调配透明度的方法
    private static int mixtureColor(int color, @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        if (color == Color.TRANSPARENT)
        {
            return Color.TRANSPARENT;
        }

        int a = (color & 0xff000000) == 0 ? 0xff : color >>> 24;
        return (color & 0x00ffffff) | (((int) (a * alpha)) << 24);
    }

    //设置根布局fitSystemWindow属性
    private static void fitSystemWindow(Activity activity, boolean fitSystemWindow)
    {
        ViewGroup parent = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        for (int i = 0, count = parent.getChildCount(); i < count; i++)
        {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup)
            {
                childView.setFitsSystemWindows(fitSystemWindow);
                ((ViewGroup) childView).setClipToPadding(fitSystemWindow);
            }
        }
    }

    /**
     * 设置状态栏为暗色（文字图标变为暗色调）
     */
    public static void setStatusBarDarkMode(Activity activity, boolean dark)
    {
        if (isFlyMe4Later())
        {
            darkModeForFlyme4(activity.getWindow(), dark);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            darkModeForM(activity.getWindow(), dark);
        } else if (isMIUI6Later())
        {
            darkModeForMIUI6(activity.getWindow(), dark);
        }
    }

    /**
     * 判断是否Flyme4以上
     */
    private static boolean isFlyMe4Later()
    {
        return Build.FINGERPRINT.contains("Flyme_OS_4")
                || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                || PATTERN_FLYME.matcher(Build.DISPLAY).find();
    }

    /**
     * 设置Flyme4+的darkMode,darkMode时候字体颜色及icon变黑
     * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
     */
    private static boolean darkModeForFlyme4(Window window, boolean dark)
    {
        boolean result = false;
        if (window != null)
        {
            try
            {
                WindowManager.LayoutParams e = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(e);
                if (dark)
                {
                    value |= bit;
                } else
                {
                    value &= ~bit;
                }

                meizuFlags.setInt(e, value);
                window.setAttributes(e);
                result = true;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 判断是否为MIUI6以上
     */
    private static boolean isMIUI6Later()
    {
        try
        {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method mtd = clz.getMethod("get", String.class);
            String val = (String) mtd.invoke(null, "ro.miui.ui.version.name");
            val = val.replaceAll("[vV]", "");
            int version = Integer.parseInt(val);
            return version >= 6;
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 设置MIUI6+的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
     * http://dev.xiaomi.com/doc/p=4769/
     */
    private static boolean darkModeForMIUI6(Window window, boolean darkmode)
    {
        Class<? extends Window> clazz = window.getClass();
        try
        {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, darkmode ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * android 6.0设置字体颜色
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private static void darkModeForM(Window window, boolean dark)
    {
        int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
        if (dark)
        {
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else
        {
            systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        window.getDecorView().setSystemUiVisibility(systemUiVisibility);
    }

    /**
     * 为了配合状态栏而增加View的paddingTop,增加的值为状态栏高度
     */
    public static void compatPaddingWithStatusBar(final View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API || view == null)
        {
            return;
        }

        final int statusBarHeight = getStatusBarHeight();
        final ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp.height > 0)
        {
            lp.height += statusBarHeight;//增高
            view.setLayoutParams(lp);
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight,
                    view.getPaddingRight(), view.getPaddingBottom());
        } else
        {
            view.post(new Runnable()
            {
                @Override
                public void run()
                {
                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                    int height = view.getHeight();
                    lp.height = height + statusBarHeight;
                    view.setLayoutParams(lp);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + statusBarHeight,
                            view.getPaddingRight(), view.getPaddingBottom());
                }
            });
        }
    }

    /**
     * 为了配合状态栏而增加View的marginTop,增加的值为状态栏高度
     * 【一般是给宽度为wrap_content的View设置的】
     */
    public static void compatMarginWithStatusBar(View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp instanceof ViewGroup.MarginLayoutParams)
        {
            ((ViewGroup.MarginLayoutParams) lp).topMargin += getStatusBarHeight();//增高
            view.setLayoutParams(lp);
        }
    }

    /*******************************************以下代码属于导航栏*******************************************************************/

    /**
     * 检查是否有导航栏
     */
    public static boolean isNavigationBarExist(Context context)
    {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0)
        {
            hasNavigationBar = rs.getBoolean(id);
        }
        try
        {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("getStrategy", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride))
            {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride))
            {
                hasNavigationBar = true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return hasNavigationBar;
    }

    /**
     * 获取导航栏的高度
     * 【先判断有没有导航栏，有就获取，没有返回0】
     */
    public static int getNavigationBarHeight()
    {
        int height = 0;
        if (isNavigationBarExist(Utils.getContext()))
        {
            Resources rs = Utils.getContext().getResources();
            int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
            if (id > 0)
            {
                height = rs.getDimensionPixelSize(id);
            }
        }
        return height;
    }

    /**
     * 是否显示了虚拟导航栏
     */
    public static boolean isNavigationBarShowing(Activity activity)
    {
        if (ROMUtils.isMiui() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            //小米系统判断全面屏手势是否开启,true为开启（不显示导航栏），false为关闭（显示导航栏）
            if (Settings.Global.getInt(activity.getContentResolver(), "force_fsg_nav_bar", 0) != 0)
            {
                //开启手势，不显示虚拟键
                return false;
            } else
            {
                return true;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else
        {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            return !(menu || back);
        }
    }


    /**
     * 判断虚拟导航栏是否显示
     *
     * @param context 上下文对象
     * @param window  当前窗口
     * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean checkNavigationBarShow(@NonNull Context context, @NonNull Window window)
    {
        boolean show;
        Display display = window.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getRealSize(point);

        View decorView = window.getDecorView();
        Configuration conf = context.getResources().getConfiguration();
        if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation)
        {
            View contentView = decorView.findViewById(android.R.id.content);
            show = (point.x != contentView.getWidth());
        } else
        {
            Rect rect = new Rect();
            decorView.getWindowVisibleDisplayFrame(rect);
            show = (rect.bottom != point.y);
        }
        return show;
    }

    /**
     * 5.0以上切换导航栏颜色
     */
    public static void setNavigationBarColor(Activity activity, @ColorInt int color)
    {
        setNavigationBarColor(activity, color, DEFAULT_ALPHA);
    }

    /**
     * 5.0以上切换NavigationBar颜色
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setNavigationBarColor(Activity activity, @ColorInt int color
            , @FloatRange(from = 0.0, to = 1.0) float alpha)
    {
        if (!isNavigationBarExist(activity))
        {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(mixtureColor(color, alpha));
        }
    }

    /**
     * 为了配合状态栏而增加View的paddingBottom,增加的值为导航栏高度
     */
    public static void compatPaddingWithNavigationBar(View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        int navigationBarHeight = getNavigationBarHeight();
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp.height > 0)
        {
            lp.height += navigationBarHeight;//增高
        }
        view.setLayoutParams(lp);
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(),
                view.getPaddingRight(), view.getPaddingBottom() + navigationBarHeight);
    }

    /**
     * 为了配合状态栏而增加View的marginBottom,增加的值为导航栏高度
     * 【一般是给宽度为wrap_content的View设置的】
     */
    public static void compatMarginWithNavigationBar(View view)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp instanceof ViewGroup.MarginLayoutParams)
        {
            ((ViewGroup.MarginLayoutParams) lp).bottomMargin += getNavigationBarHeight();//增高
        }
        view.setLayoutParams(lp);
    }

    /********************************************其他方法****************************************************/

    /**
     * 设置全屏【隐藏状态栏、导航栏半透明】
     */
    //    public static void setFullScreen(Activity activity)
    //    {
    //        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    //        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
    //                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    //    }

    /**
     * 设置状态栏透明
     * 【xml不要设置"fitsystemwindow=true"】
     */
    public static void setStatusBarTransparent(Activity activity)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //改变状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
        } else
        {
            //设置状态栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏和导航栏全透明
     * 【xml不要设置"fitsystemwindow=true"】
     */
    public static void setAllBarTransparent(Activity activity)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //改变状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
            //改变导航栏颜色
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else
        {
            //设置状态栏和导航栏透明
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 设置沉浸式模式，需要重写Activity的onWindowFocusChanged方法再调用
     */
    public static void immsiveMode(Activity activity, boolean hasFocus)
    {
        if (Build.VERSION.SDK_INT < MIN_API)
        {
            return;
        }

        if (hasFocus)
        {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
