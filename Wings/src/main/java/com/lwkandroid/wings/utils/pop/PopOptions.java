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
    private IPopAffect affect;
    private long affectDuration = 200;
    private PopupWindow.OnDismissListener dismissListener;
    private ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public boolean isFocusable()
    {
        return focusable;
    }

    /**
     * 设置PopupWindow是否获取焦点，默认True
     *
     * @param focusable True：获取 False：不获取
     */
    public T setFocusable(boolean focusable)
    {
        this.focusable = focusable;
        return (T) this;
    }

    public boolean isCancelOutsideTouched()
    {
        return cancelOutsideTouched;
    }

    /**
     * 设置是否触摸外部关闭PopupWindow
     *
     * @param b True：是 False：否
     * @return
     */
    public T setCancelOutsideTouched(boolean b)
    {
        this.cancelOutsideTouched = b;
        return (T) this;
    }

    public int getAnimStyle()
    {
        return animStyle;
    }

    /**
     * 设置显示/消失动画
     *
     * @param animStyle 动画对应style资源id
     * @return
     */
    public T setAnimStyle(@StyleRes int animStyle)
    {
        this.animStyle = animStyle;
        return (T) this;
    }

    public PopupWindow.OnDismissListener getDismissListener()
    {
        return dismissListener;
    }

    /**
     * 添加消失监听
     *
     * @param dismissListener 监听
     */
    public T setDismissListener(PopupWindow.OnDismissListener dismissListener)
    {
        this.dismissListener = dismissListener;
        return (T) this;
    }

    public ViewGroup.LayoutParams getLayoutParams()
    {
        return layoutParams;
    }

    /**
     * 设置PopupWindow的LayoutParams
     *
     * @param layoutParams LayoutParams
     */
    public T setLayoutParams(ViewGroup.LayoutParams layoutParams)
    {
        this.layoutParams = layoutParams;
        return (T) this;
    }

    /**
     * 设置PopupWindow的宽度和高度
     *
     * @param width  宽度
     * @param height 高度
     */
    public T setLayoutParams(int width, int height)
    {
        this.layoutParams = new ViewGroup.LayoutParams(width, height);
        return (T) this;
    }

    /**
     * 设置UI层实现
     *
     * @param contentView UI层实现对象
     */
    public T setContentView(PopBaseContentView contentView)
    {
        this.popContentView = contentView;
        return (T) this;
    }

    /**
     * 获取显示过程效果时长
     *
     * @return 显示过程效果时长
     */
    public long getAffectDuration()
    {
        return affectDuration;
    }

    /**
     * 设置显示效果
     *
     * @param affect   显示效果实现类对象
     * @param duration 过程时长，务必和AnimStyle动画时长保持一致
     * @return
     */
    public T setAffectParams(IPopAffect affect, long duration)
    {
        this.affect = affect;
        this.affectDuration = duration;
        return (T) this;
    }

    /**
     * 获取显示效果
     *
     * @return
     */
    public IPopAffect getAffect()
    {
        return affect;
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

    @Override
    public PopCreator showWithAnchor(View anchor, @XGravity int xGravity, @YGravity int yGravity, int xoff, int yoff)
    {
        return new PopCreator().showWithAnchor(anchor, xGravity, yGravity, xoff, yoff, this);
    }
}
