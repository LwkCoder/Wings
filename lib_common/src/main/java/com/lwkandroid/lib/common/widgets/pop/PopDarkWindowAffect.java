package com.lwkandroid.lib.common.widgets.pop;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.FloatRange;

/**
 * 窗口亮度渐变效果
 *
 * @author LWK
 */
public final class PopDarkWindowAffect implements IPopAffect
{
    /**
     * Pop弹出后窗口透明度
     */
    private float mDarkWindowDegree = 0.5f;
    private final float CHA = 0.0001f;

    public PopDarkWindowAffect()
    {
    }

    public PopDarkWindowAffect(@FloatRange(from = 0f, to = 1.0f) float darkWindowDegree)
    {
        this.mDarkWindowDegree = darkWindowDegree;
    }

    @Override
    public void onShowingProgress(Context context, PopCreator creator, PopOptions options, float progress)
    {
        //根据value计算透明度
        updateBgAlpha(context, 1.0f - (1.0f - mDarkWindowDegree) * progress);
    }

    @Override
    public void onDismissed(Context context, PopCreator creator, PopOptions options)
    {
        //恢复透明度
        updateBgAlpha(context, 1.0f);
    }

    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     *
     * @param context
     * @param bgAlpha
     */
    private void updateBgAlpha(Context context, @FloatRange(from = 0.0f, to = 1.0f) float bgAlpha)
    {
        if (context != null && context instanceof Activity)
        {
            Window window = ((Activity) context).getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            float cha = Math.abs(1.0f - bgAlpha);
            //精度小于这个误差，代表相等
            if (cha <= CHA)
            {
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            } else
            {
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
            lp.alpha = bgAlpha;
            window.setAttributes(lp);
        }
    }
}
