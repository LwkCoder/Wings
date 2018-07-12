package com.lwkandroid.wings.utils.pop;

import android.support.annotation.StyleRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by LWK
 * TODO PopupWindow参数
 */

public class PopOptions<T extends PopOptions> implements IPopProxy
{
    private PopBaseContentView popContentView;
    private boolean focusable = true;
    private boolean cancelOutsideTouched = true;
    private @StyleRes
    int animStyle = -1;
    private boolean darkWindow = false;
    private long darkWindowDuration = 200;
    private PopupWindow.OnDismissListener dismissListener;
    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public boolean isFocusable()
    {
        return focusable;
    }

    public T setFocusable(boolean focusable)
    {
        this.focusable = focusable;
        return (T) this;
    }

    public boolean isCancelOutsideTouched()
    {
        return cancelOutsideTouched;
    }

    public T setCancelOutsideTouched(boolean cancelOutsideTouched)
    {
        this.cancelOutsideTouched = cancelOutsideTouched;
        return (T) this;
    }

    public int getAnimStyle()
    {
        return animStyle;
    }

    public T setAnimStyle(int animStyle)
    {
        this.animStyle = animStyle;
        return (T) this;
    }

    public boolean isDarkWindow()
    {
        return darkWindow;
    }

    public T setDarkWindow(boolean darkWindow)
    {
        this.darkWindow = darkWindow;
        return (T) this;
    }

    public long getDarkWindowDuration()
    {
        return darkWindowDuration;
    }

    public T setDarkWindowDuration(long darkWindowDuration)
    {
        this.darkWindowDuration = darkWindowDuration;
        return (T) this;
    }

    public PopupWindow.OnDismissListener getDismissListener()
    {
        return dismissListener;
    }

    public T setDismissListener(PopupWindow.OnDismissListener dismissListener)
    {
        this.dismissListener = dismissListener;
        return (T) this;
    }

    public ViewGroup.LayoutParams getLayoutParams()
    {
        return layoutParams;
    }

    public T setLayoutParams(ViewGroup.LayoutParams layoutParams)
    {
        this.layoutParams = layoutParams;
        return (T) this;
    }

    public T setLayoutParams(int width, int height)
    {
        this.layoutParams = new ViewGroup.LayoutParams(width, height);
        return (T) this;
    }

    public T setContentView(PopBaseContentView contentView)
    {
        this.popContentView = contentView;
        return (T) this;
    }

    public PopBaseContentView getPopContentView()
    {
        return popContentView;
    }

    @Override
    public PopCreator showAtLocation(View parent, int gravity, int x, int y)
    {
        return new PopCreator().showAtLocation(parent, gravity, x, y, this);
    }

    @Override
    public PopCreator showAsDropDown(View anchor)
    {
        return new PopCreator().showAsDropDown(anchor, this);
    }

    @Override
    public PopCreator showAsDropDown(View anchor, int xoff, int yoff)
    {
        return new PopCreator().showAsDropDown(anchor, xoff, yoff, this);
    }

    @Override
    public PopCreator showAsDropDown(View anchor, int xoff, int yoff, int gravity)
    {
        return new PopCreator().showAsDropDown(anchor, xoff, yoff, gravity, this);
    }
}
