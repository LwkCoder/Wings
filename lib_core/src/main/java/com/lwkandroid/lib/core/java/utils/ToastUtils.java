package com.lwkandroid.lib.core.java.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lwkandroid.lib.core.java.context.AppContext;
import com.lwkandroid.wings.utils.ScreenUtils;

import java.lang.reflect.Field;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationManagerCompat;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : utils about toast
 * </pre>
 */
public final class ToastUtils
{
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final String NULL = "null";

    private static IToast iToast;
    private static int sGravity = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;
    private static int sBgColor = COLOR_DEFAULT;
    private static int sBgResource = -1;
    private static int sMsgColor = COLOR_DEFAULT;
    private static int sMsgTextSize = -1;

    private ToastUtils()
    {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static void runOnUiThread(Runnable runnable)
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            runnable.run();
        } else
        {
            HANDLER.post(runnable);
        }
    }

    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset)
    {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     */
    public static void setBgColor(@ColorInt final int backgroundColor)
    {
        sBgColor = backgroundColor;
    }

    public static void setBgColorByResId(@ColorRes final int resId)
    {
        sBgColor = ResourceUtils.getColor(resId);
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     */
    public static void setBgDrawableResId(@DrawableRes final int bgResource)
    {
        sBgResource = bgResource;
    }

    /**
     * Set the color of message.
     *
     * @param msgColor The color of message.
     */
    public static void setTextColor(@ColorInt final int msgColor)
    {
        sMsgColor = msgColor;
    }

    public static void setTextColorByResId(@ColorRes final int resId)
    {
        sMsgColor = ResourceUtils.getColor(resId);
    }

    /**
     * Set the text size of message.
     *
     * @param spSize The text size of message,use TypedValue.COMPLEX_UNIT_SP.
     */
    public static void setTextSizeSP(final int spSize)
    {
        sMsgTextSize = spSize;
    }

    public static void setTextSizeByResId(final int resId)
    {
        sMsgTextSize = (int) (ResourceUtils.getDimenPixelSize(resId) / ScreenUtils.getScreenDensity());
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(final CharSequence text)
    {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId)
    {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args)
    {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(final String format, final Object... args)
    {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(final CharSequence text)
    {
        show(text == null ? NULL : text, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId)
    {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args)
    {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(final String format, final Object... args)
    {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * Show custom toast for a short period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomShort(@LayoutRes final int layoutId)
    {
        return showCustomShort(getView(layoutId));
    }

    /**
     * Show custom toast for a short period of time.
     *
     * @param view The view of toast.
     */
    public static View showCustomShort(final View view)
    {
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * Show custom toast for a long period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomLong(@LayoutRes final int layoutId)
    {
        return showCustomLong(getView(layoutId));
    }

    /**
     * Show custom toast for a long period of time.
     *
     * @param view The view of toast.
     */
    public static View showCustomLong(final View view)
    {
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * Cancel the toast.
     */
    public static void cancel()
    {
        if (iToast != null)
        {
            iToast.cancel();
        }
    }

    private static void show(final int resId, final int duration)
    {
        show(resId, duration, (Object) null);
    }

    private static void show(final int resId, final int duration, final Object... args)
    {
        try
        {
            CharSequence text = ResourceUtils.getString(resId);
            if (args != null)
            {
                text = String.format(text.toString(), args);
            }
            show(text, duration);
        } catch (Exception ignore)
        {
            show(String.valueOf(resId), duration);
        }
    }

    private static void show(final String format, final int duration, final Object... args)
    {
        String text = format;
        if (text == null)
        {
            text = NULL;
        } else
        {
            if (args != null)
            {
                text = String.format(format, args);
            }
        }
        show(text, duration);
    }

    private static void show(final CharSequence text, final int duration)
    {
        runOnUiThread(() -> {
            cancel();
            iToast = ToastFactory.makeToast(AppContext.get(), text, duration);
            final View toastView = iToast.getView();
            if (toastView == null)
            {
                return;
            }
            final TextView tvMessage = toastView.findViewById(android.R.id.message);
            if (sMsgColor != COLOR_DEFAULT)
            {
                tvMessage.setTextColor(sMsgColor);
            }
            if (sMsgTextSize != -1)
            {
                tvMessage.setTextSize(TypedValue.COMPLEX_UNIT_SP, sMsgTextSize);
            }
            if (sGravity != -1 || sXOffset != -1 || sYOffset != -1)
            {
                iToast.setGravity(sGravity, sXOffset, sYOffset);
            }
            setBg(tvMessage);
            iToast.show();
        });
    }

    private static void show(final View view, final int duration)
    {
        runOnUiThread(() -> {
            cancel();
            iToast = ToastFactory.newToast(AppContext.get());
            iToast.setView(view);
            iToast.setDuration(duration);
            if (sGravity != -1 || sXOffset != -1 || sYOffset != -1)
            {
                iToast.setGravity(sGravity, sXOffset, sYOffset);
            }
            setBg();
            iToast.show();
        });
    }

    private static void setBg()
    {
        if (sBgResource != -1)
        {
            final View toastView = iToast.getView();
            toastView.setBackgroundResource(sBgResource);
        } else if (sBgColor != COLOR_DEFAULT)
        {
            final View toastView = iToast.getView();
            Drawable background = toastView.getBackground();
            if (background != null)
            {
                background.setColorFilter(
                        new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                );
            } else
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    toastView.setBackground(new ColorDrawable(sBgColor));
                } else
                {
                    toastView.setBackgroundDrawable(new ColorDrawable(sBgColor));
                }
            }
        }
    }

    private static void setBg(final TextView tvMsg)
    {
        if (sBgResource != -1)
        {
            final View toastView = iToast.getView();
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (sBgColor != COLOR_DEFAULT)
        {
            final View toastView = iToast.getView();
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null)
            {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null)
            {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null)
            {
                msgBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else
            {
                toastView.setBackgroundColor(sBgColor);
            }
        }
    }

    private static View getView(@LayoutRes final int layoutId)
    {
        LayoutInflater inflate =
                (LayoutInflater) AppContext.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate.inflate(layoutId, null);
    }

    static class ToastFactory
    {

        static IToast makeToast(Context context, CharSequence text, int duration)
        {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled())
            {
                return new SystemToast(makeNormalToast(context, text, duration));
            }
            return null;
            //            return new ToastWithoutNotification(makeNormalToast(context, text, duration));
        }

        static IToast newToast(Context context)
        {
            if (NotificationManagerCompat.from(context).areNotificationsEnabled())
            {
                return new SystemToast(new Toast(context));
            }
            return null;
            //            return new ToastWithoutNotification(new Toast(context));
        }

        private static Toast makeNormalToast(Context context, CharSequence text, int duration)
        {
            @SuppressLint("ShowToast")
            Toast toast = Toast.makeText(context, "", duration);
            toast.setText(text);
            return toast;
        }
    }

    static class SystemToast extends AbsToast
    {

        SystemToast(Toast toast)
        {
            super(toast);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1)
            {
                try
                {
                    //noinspection JavaReflectionMemberAccess
                    Field mTNField = Toast.class.getDeclaredField("mTN");
                    mTNField.setAccessible(true);
                    Object mTN = mTNField.get(toast);
                    Field mTNmHandlerField = mTNField.getType().getDeclaredField("mHandler");
                    mTNmHandlerField.setAccessible(true);
                    Handler tnHandler = (Handler) mTNmHandlerField.get(mTN);
                    mTNmHandlerField.set(mTN, new SafeHandler(tnHandler));
                } catch (Exception ignored)
                {/**/}
            }
        }

        @Override
        public void show()
        {
            mToast.show();
        }

        @Override
        public void cancel()
        {
            mToast.cancel();
        }

        static class SafeHandler extends Handler
        {
            private Handler impl;

            SafeHandler(Handler impl)
            {
                this.impl = impl;
            }

            @Override
            public void handleMessage(Message msg)
            {
                impl.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(Message msg)
            {
                try
                {
                    impl.dispatchMessage(msg);
                } catch (Exception e)
                {
                    Log.e("ToastUtils", e.toString());
                }
            }
        }
    }

    static abstract class AbsToast implements IToast
    {

        Toast mToast;

        AbsToast(Toast toast)
        {
            mToast = toast;
        }

        @Override
        public void setView(View view)
        {
            mToast.setView(view);
        }

        @Override
        public View getView()
        {
            return mToast.getView();
        }

        @Override
        public void setDuration(int duration)
        {
            mToast.setDuration(duration);
        }

        @Override
        public void setGravity(int gravity, int xOffset, int yOffset)
        {
            mToast.setGravity(gravity, xOffset, yOffset);
        }

        @Override
        public void setText(int resId)
        {
            mToast.setText(resId);
        }

        @Override
        public void setText(CharSequence s)
        {
            mToast.setText(s);
        }
    }

    interface IToast
    {

        void show();

        void cancel();

        void setView(View view);

        View getView();

        void setDuration(int duration);

        void setGravity(int gravity, int xOffset, int yOffset);

        void setText(@StringRes int resId);

        void setText(CharSequence s);
    }
}