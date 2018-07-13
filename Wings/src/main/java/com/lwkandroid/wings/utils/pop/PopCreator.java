package com.lwkandroid.wings.utils.pop;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;

import com.lwkandroid.wings.utils.Utils;

import java.lang.ref.WeakReference;

/**
 * Created by LWK
 * TODO PopupWindow辅助类
 */

public class PopCreator implements IPopOperator, PopupWindow.OnDismissListener
{
    private static final String TAG = "PopCreator";
    private WeakReference<Context> mContextReference;
    private PopupWindow mPopupWindow;
    private ValueAnimator mAnimator;
    private PopOptions mOptions;
    private PopBaseContentView mPopContentView;

    public static PopOptions create(PopBaseContentView contentView)
    {
        return new PopOptions().setContentView(contentView);
    }

    @Override
    public PopCreator showAtLocation(View parent, int gravity, int x, int y, PopOptions options)
    {
        init(parent.getContext(), options);
        mPopupWindow.showAtLocation(parent, gravity, x, y);
        applyWindowDark();
        return this;
    }

    @Override
    public PopCreator showAsDropDown(View anchor, PopOptions options)
    {
        return showAsDropDown(anchor, 0, 0, options);
    }

    @Override
    public PopCreator showAsDropDown(View anchor, int xoff, int yoff, PopOptions options)
    {
        return showAsDropDown(anchor, xoff, yoff, Gravity.TOP | Gravity.START, options);
    }

    @Override
    public PopCreator showAsDropDown(View anchor, int xoff, int yoff, int gravity, PopOptions options)
    {
        init(anchor.getContext(), options);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mPopupWindow.showAsDropDown(anchor, xoff, yoff, gravity);
        else
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
        applyWindowDark();
        return this;
    }

    @Override
    public void update()
    {
        if (mPopupWindow != null)
            mPopupWindow.update();
    }

    @Override
    public void update(int width, int height)
    {
        if (mPopupWindow != null)
            mPopupWindow.update(width, height);
    }

    @Override
    public void update(int x, int y, int width, int height)
    {
        if (mPopupWindow != null)
            mPopupWindow.update(x, y, width, height);
    }

    @Override
    public void update(int x, int y, int width, int height, boolean force)
    {
        if (mPopupWindow != null)
            mPopupWindow.update(x, y, width, height, false);
    }

    @Override
    public void update(View anchor, int width, int height)
    {
        if (mPopupWindow != null)
            mPopupWindow.update(anchor, width, height);
    }

    @Override
    public void update(View anchor, int xoff, int yoff, int width, int height)
    {
        if (mPopupWindow != null)
            mPopupWindow.update(anchor, xoff, yoff, width, height);
    }

    @Override
    public PopupWindow getPopupWindow()
    {
        return mPopupWindow;
    }

    @Override
    public PopBaseContentView getContentView()
    {
        return mPopContentView;
    }

    //初始化配置
    private void init(Context context, PopOptions options)
    {
        this.mContextReference = new WeakReference<>(context != null ? context : Utils.getContext());
        this.mOptions = options != null ? options : new PopOptions();

        mPopContentView = mOptions.getPopContentView();
        if (mPopContentView == null)
            throw new IllegalArgumentException("You have to set a NonNull PopBaseContentView object!!!");
        mPopContentView.attach(context, options, this);

        mPopupWindow = new PopupWindow(mPopContentView.getRealContentView(), mOptions.getLayoutParams().width, mOptions.getLayoutParams().height);
        mPopupWindow.setFocusable(mOptions.isFocusable());
        if (mOptions.getAnimStyle() != -1)
            mPopupWindow.setAnimationStyle(mOptions.getAnimStyle());

        //针对外部点击是否消失需要额外处理
        boolean cancelOutsideTouched = mOptions.isCancelOutsideTouched();
        mPopupWindow.setOutsideTouchable(cancelOutsideTouched);
        if (cancelOutsideTouched)
        {
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setTouchInterceptor(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    final int x = (int) event.getX();
                    final int y = (int) event.getY();
                    if ((event.getAction() == MotionEvent.ACTION_DOWN)
                            && ((x < 0) || (x > mPopupWindow.getContentView().getWidth())
                            || (y < 0) || (y > mPopupWindow.getContentView().getHeight())))
                    {
                        mPopupWindow.dismiss();
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE)
                    {
                        mPopupWindow.dismiss();
                        return true;
                    }
                    return false;
                }
            });
        } else
        {
            //下面这三个必须同时设置
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setBackgroundDrawable(null);
            //注意下面这三个是contentView 不是PopupWindow
            mPopupWindow.getContentView().setFocusable(true);
            mPopupWindow.getContentView().setFocusableInTouchMode(true);
            //实测发现这个没用，因为内部的PopupDecorView已经消化了onKeyListener()，
            //点击Back一定会dismiss()
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

        //不让键盘遮挡PopupWindow
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupWindow.setOnDismissListener(this);
    }

    @Override
    public boolean isShowing()
    {
        return mPopupWindow != null && mPopupWindow.isShowing();
    }

    @Override
    public void dismiss()
    {
        if (isShowing())
            mPopupWindow.dismiss();
    }

    @Override
    public void onDismiss()
    {
        applyWindowLight();
        if (mOptions != null && mOptions.getDismissListener() != null)
            mOptions.getDismissListener().onDismiss();
        mPopupWindow = null;
        mContextReference = null;
    }

    //设置窗口逐渐变暗
    private void applyWindowDark()
    {
        if (mOptions != null && !mOptions.isDarkWindow())
            return;

        if (mAnimator != null)
        {
            mAnimator.cancel();
            mAnimator = null;
        }

        float degree = mOptions.getDarkWindowDegree();
        if (degree > 1.0f)
            mOptions.setDarkWindowDegree(1.0f);
        else if (degree < 0)
            mOptions.setDarkWindowDegree(0f);

        mAnimator = ValueAnimator.ofFloat(mOptions.getDarkWindowDegree(), 1.0f);
        mAnimator.setDuration(mOptions.getDarkWindowDuration());//动画时间要和PopupWindow弹出动画的时间一致
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                float value = (float) animation.getAnimatedValue();
                updateBgAlpha(mOptions.getDarkWindowDegree() + 1.0f - value);
            }
        });
        mAnimator.start();
    }

    //设置窗口变亮
    private void applyWindowLight()
    {
        mPopupWindow.update();
        if (mOptions != null && !mOptions.isDarkWindow())
            return;
        updateBgAlpha(1.0f);
    }

    // 此方法用于改变背景的透明度，从而达到“变暗”的效果
    private void updateBgAlpha(float bgAlpha)
    {
        if (getContext() != null && getContext() instanceof Activity)
        {
            Window window = ((Activity) getContext()).getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = bgAlpha; //0.0-1.0
            window.setAttributes(lp);
        }
    }

    protected Context getContext()
    {
        return mContextReference != null ? mContextReference.get() : null;
    }
}
