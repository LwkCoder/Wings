package com.lwkandroid.lib.common.widgets.pop;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.lwkandroid.lib.common.widgets.pop2.XGravity;
import com.lwkandroid.lib.common.widgets.pop2.YGravity;

import androidx.annotation.StyleRes;

/**
 * PopupWindow参数
 *
 * @author LWK
 */
public class PopOptions implements IPopProxy
{
    private PopBaseContentView mPopContentView;
    private boolean mFocusable = true;
    private boolean mCanceledOnTouchOutside = true;
    private @StyleRes
    int mAnimStyle = -1;
    private IPopAffect mAffect;
    private long mAffectDuration = 200;
    private PopupWindow.OnDismissListener mDismissListener;
    private ViewGroup.LayoutParams mLayoutParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private SparseArray<OnPopChildClickListener> mChildClickListenerArray;

    public boolean isFocusable()
    {
        return mFocusable;
    }

    /**
     * 设置PopupWindow是否获取焦点，默认True
     *
     * @param focusable True：获取 False：不获取
     */
    public PopOptions setFocusable(boolean focusable)
    {
        this.mFocusable = focusable;
        return this;
    }

    public boolean isCanceledOnTouchOutside()
    {
        return mCanceledOnTouchOutside;
    }

    /**
     * 设置是否触摸外部关闭PopupWindow
     *
     * @param b True：是 False：否
     * @return
     */
    public PopOptions setCanceledOnTouchOutside(boolean b)
    {
        this.mCanceledOnTouchOutside = b;
        return this;
    }

    public int getAnimStyle()
    {
        return mAnimStyle;
    }

    /**
     * 设置显示/消失动画
     *
     * @param animStyle 动画对应style资源id
     * @return
     */
    public PopOptions setAnimStyle(@StyleRes int animStyle)
    {
        this.mAnimStyle = animStyle;
        return this;
    }

    public PopupWindow.OnDismissListener getDismissListener()
    {
        return mDismissListener;
    }

    /**
     * 添加消失监听
     *
     * @param dismissListener 监听
     */
    public PopOptions setDismissListener(PopupWindow.OnDismissListener dismissListener)
    {
        this.mDismissListener = dismissListener;
        return this;
    }

    public ViewGroup.LayoutParams getLayoutParams()
    {
        return mLayoutParams;
    }

    /**
     * 设置PopupWindow的LayoutParams
     *
     * @param layoutParams LayoutParams
     */
    public PopOptions setLayoutParams(ViewGroup.LayoutParams layoutParams)
    {
        this.mLayoutParams = layoutParams;
        return this;
    }

    /**
     * 设置PopupWindow的宽度和高度
     *
     * @param width  宽度
     * @param height 高度
     */
    public PopOptions setLayoutParams(int width, int height)
    {
        this.mLayoutParams = new ViewGroup.LayoutParams(width, height);
        return this;
    }

    /**
     * 设置UI层实现
     *
     * @param contentView UI层实现对象
     */
    public PopOptions setContentView(PopBaseContentView contentView)
    {
        this.mPopContentView = contentView;
        return this;
    }

    /**
     * 获取显示过程效果时长
     *
     * @return 显示过程效果时长
     */
    public long getAffectDuration()
    {
        return mAffectDuration;
    }

    /**
     * 设置显示效果
     *
     * @param affect   显示效果实现类对象
     * @param duration 过程时长，务必和AnimStyle动画时长保持一致
     * @return
     */
    public PopOptions setAffectParams(IPopAffect affect, long duration)
    {
        this.mAffect = affect;
        this.mAffectDuration = duration;
        return this;
    }

    public PopOptions addOnChildClickListener(int viewId, OnPopChildClickListener listener)
    {
        if (mChildClickListenerArray == null)
        {
            mChildClickListenerArray = new SparseArray<>();
        }
        mChildClickListenerArray.put(viewId, listener);
        return this;
    }

    public SparseArray<OnPopChildClickListener> getChildClickListenerArray()
    {
        return mChildClickListenerArray;
    }

    /**
     * 获取显示效果
     *
     * @return
     */
    public IPopAffect getAffect()
    {
        return mAffect;
    }

    public PopBaseContentView getPopContentView()
    {
        return mPopContentView;
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
