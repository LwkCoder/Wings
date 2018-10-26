package com.lwkandroid.wings.widget.pop;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.FloatRange;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by LWK
 * TODO 窗口亮度渐变效果
 */

public class PopDarkWindowAffect implements IPopAffect
{
    //Pop弹出后窗口透明度
    private float mDarkWindowDegree = 0.5f;

    public PopDarkWindowAffect()
    {
    }

    public PopDarkWindowAffect(@FloatRange(from = 0f, to = 1.0f) float darkWindowDegree)
    {
        this.mDarkWindowDegree = darkWindowDegree;
    }

    @Override
    public void onShowingProgress(Context context, PopOptions options, float progress)
    {
        //根据value计算透明度
        updateBgAlpha(context, 1.0f - (1.0f - mDarkWindowDegree) * progress);
    }

    @Override
    public void onDismissed(Context context, PopOptions options)
    {
        //恢复透明度
        updateBgAlpha(context, 1.0f);
    }

    // 此方法用于改变背景的透明度，从而达到“变暗”的效果
    private void updateBgAlpha(Context context, float bgAlpha)
    {
        if (context != null && context instanceof Activity)
        {
            Window window = ((Activity) context).getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            if (bgAlpha != 1.0f)
                window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            else
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            lp.alpha = bgAlpha; //0.0-1.0
            window.setAttributes(lp);
        }
    }
}
