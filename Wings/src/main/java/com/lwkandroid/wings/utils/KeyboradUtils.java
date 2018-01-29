package com.lwkandroid.wings.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘工具类
 */
public final class KeyboradUtils
{
    private KeyboradUtils()
    {
        throw new UnsupportedOperationException("Can't instantiate this class !");
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInput(View view)
    {
        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && imm.isActive())
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * 显示软键盘
     */
    public static void showSoftInput(View v)
    {
        if (v != null)
        {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            v.requestFocus();

            InputMethodManager imm = (InputMethodManager) v.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && imm.isActive())
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 切换软键盘显示和隐藏
     */
    public static void toggleSoftInput()
    {
        InputMethodManager imm = (InputMethodManager) Utils.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive())
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
}
