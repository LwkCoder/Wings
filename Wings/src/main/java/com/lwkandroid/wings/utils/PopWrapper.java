package com.lwkandroid.wings.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;

/**
 * Created by LWK
 * TODO PopupWindow封装类
 */

public abstract class PopWrapper implements PopupWindow.OnDismissListener
{
    protected Activity mActivity;
    protected PopupWindow mPopupWindow;
    protected PopOptions mOptions = new PopOptions();
    private ValueAnimator mAnimator;

    protected PopWrapper()
    {
        mOptions = new PopOptions();
    }

    public PopWrapper createPopupWindow(Activity activity)
    {
        return createPopupWindow(activity, null);
    }

    public PopWrapper createPopupWindow(Activity activity, PopOptions options)
    {
        this.mActivity = activity;
        if (options == null)
            options = new PopOptions();
        this.mOptions = options;

        int contentViewId = getContentViewId();
        if (contentViewId <= 0)
            throw new IllegalArgumentException("You must set a contentViewId for PopupWindow !");

        //初始化布局
        View contentView = LayoutInflater.from(activity).inflate(contentViewId,
                (ViewGroup) activity.findViewById(android.R.id.content), false);
        initUI(activity, contentView);

        mPopupWindow = new PopupWindow(contentView);
        mPopupWindow.setWidth(mOptions.getLayoutParams().width);
        mPopupWindow.setHeight(mOptions.getLayoutParams().height);
        mPopupWindow.setFocusable(mOptions.isFocusable());
        if (mOptions.getAnimStyle() > 0)
            mPopupWindow.setAnimationStyle(mOptions.getAnimStyle());

        //针对外部点击是否消失需要额外处理
        mPopupWindow.setOutsideTouchable(mOptions.isCancelTouchOutside());
        boolean cancelOutside = mOptions.isCancelTouchOutside();
        if (cancelOutside)
        {
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } else
        {
            //下面这三个必须同时设置
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setBackgroundDrawable(null);
            //注意下面这三个是contentView 不是PopupWindow
            mPopupWindow.getContentView().setFocusable(true);
            mPopupWindow.getContentView().setFocusableInTouchMode(true);
            mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event)
                {
                    if (keyCode == KeyEvent.KEYCODE_BACK)
                    {
                        mPopupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });
            //在Android 6.0以上 ，只能通过拦截事件来解决
            mPopupWindow.setTouchInterceptor(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();

                    if ((event.getAction() == MotionEvent.ACTION_DOWN)
                            && ((x < 0) || (x >= mPopupWindow.getContentView().getWidth())
                            || (y < 0) || (y >= mPopupWindow.getContentView().getHeight())))
                    {
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    {
                        return true;
                    }
                    return false;
                }
            });
        }

        mPopupWindow.setOnDismissListener(this);
        //不让键盘遮挡PopupWindow
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return this;
    }

    public void showAtLocation(View parent, int gravity, int x, int y)
    {
        if (mPopupWindow == null)
            return;
        mPopupWindow.showAtLocation(parent, gravity, x, y);
        setWindowDark();
    }

    public void showAsDropDown(View anchor)
    {
        if (mPopupWindow == null)
            return;
        mPopupWindow.showAsDropDown(anchor);
        setWindowDark();
    }

    public void showAsDropDown(View anchor, int xoff, int yoff)
    {
        if (mPopupWindow == null)
            return;
        mPopupWindow.showAsDropDown(anchor, xoff, yoff);
        setWindowDark();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity)
    {
        if (mPopupWindow == null)
            return;
        mPopupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
        setWindowDark();
    }

    public boolean isShowing()
    {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    public void dismiss()
    {
        if (isShowing())
            mPopupWindow.dismiss();
    }

    @Override
    public void onDismiss()
    {
        setWindowLight();
        if (mOptions.getDismissListener() != null)
            mOptions.getDismissListener().onDismiss();
        mPopupWindow = null;
        mActivity = null;
    }

    //设置窗口逐渐变暗
    private void setWindowDark()
    {
        if (mOptions != null && !mOptions.isDarkWindow())
            return;

        if (mAnimator != null)
        {
            mAnimator.cancel();
            mAnimator = null;
        }

        mAnimator = ValueAnimator.ofFloat(0.5f, 1.0f);
        mAnimator.setDuration(mOptions.getDarkWindowDuration());//动画时间要和PopupWindow弹出动画的时间一致
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float value = (float) animation.getAnimatedValue();
                updateBgAlpha(1.5f - value);
            }
        });
        mAnimator.start();
    }

    //设置窗口变亮
    private void setWindowLight()
    {
        if (mOptions != null && !mOptions.isDarkWindow())
            return;
        updateBgAlpha(1.0f);
    }

    // 此方法用于改变背景的透明度，从而达到“变暗”的效果
    private void updateBgAlpha(float bgAlpha)
    {
        if (mActivity != null)
        {
            Window window = mActivity.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = bgAlpha; //0.0-1.0
            window.setAttributes(lp);
        }
    }

    /**
     * 子类实现该方法指定布局id
     */
    public abstract int getContentViewId();

    /**
     * 子类实现该方法实现初始化UI
     */
    public abstract void initUI(Activity activity, View contentView);


    /*************************************** 参数 ********************************************/

    public static class PopOptions
    {
        private boolean focusable = true;
        private boolean cancelTouchOutside = true;
        private PopupWindow.OnDismissListener dismissListener;
        private int animStyle;
        private ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                        , ViewGroup.LayoutParams.WRAP_CONTENT);
        private boolean darkWindow = false;
        private int darkWindowDuration = 200;

        public boolean isFocusable()
        {
            return focusable;
        }

        public void setFocusable(boolean focusable)
        {
            this.focusable = focusable;
        }

        public boolean isCancelTouchOutside()
        {
            return cancelTouchOutside;
        }

        public void setCancelTouchOutside(boolean cancelTouchOutside)
        {
            this.cancelTouchOutside = cancelTouchOutside;
        }

        public PopupWindow.OnDismissListener getDismissListener()
        {
            return dismissListener;
        }

        public void setDismissListener(PopupWindow.OnDismissListener dismissListener)
        {
            this.dismissListener = dismissListener;
        }

        public int getAnimStyle()
        {
            return animStyle;
        }

        public void setAnimStyle(int animStyle)
        {
            this.animStyle = animStyle;
        }

        public ViewGroup.LayoutParams getLayoutParams()
        {
            return layoutParams;
        }

        public void setLayoutParams(ViewGroup.LayoutParams layoutParams)
        {
            this.layoutParams = layoutParams;
        }

        public boolean isDarkWindow()
        {
            return darkWindow;
        }

        public void setDarkWindow(boolean darkWindow)
        {
            this.darkWindow = darkWindow;
        }

        public void setDarkWindow(boolean darkWindow, int duration)
        {
            this.darkWindow = darkWindow;
            this.darkWindowDuration = duration;
        }

        public int getDarkWindowDuration()
        {
            return darkWindowDuration;
        }

        //建造者模式
        public static class Builder
        {
            private PopOptions options;

            public Builder()
            {
                options = new PopOptions();
            }

            public Builder setFocusable(boolean focusable)
            {
                options.setFocusable(focusable);
                return this;
            }

            public Builder setCancelTouchOutside(boolean cancelTouchOutside)
            {
                options.setCancelTouchOutside(cancelTouchOutside);
                return this;
            }

            public Builder setDismissListener(PopupWindow.OnDismissListener listener)
            {
                options.setDismissListener(listener);
                return this;
            }

            public Builder setAnimStyle(int animStyle)
            {
                options.setAnimStyle(animStyle);
                return this;
            }

            public Builder setLayoutParams(ViewGroup.LayoutParams layoutParams)
            {
                options.setLayoutParams(layoutParams);
                return this;
            }

            public Builder setDarkWindow(boolean b)
            {
                options.setDarkWindow(b);
                return this;
            }

            public Builder setDarkWindow(boolean b, int duration)
            {
                options.setDarkWindow(b, duration);
                return this;
            }

            public PopOptions build()
            {
                return options;
            }
        }
    }
}
