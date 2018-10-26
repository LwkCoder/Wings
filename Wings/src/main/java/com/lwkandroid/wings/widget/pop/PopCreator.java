package com.lwkandroid.wings.widget.pop;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import androidx.core.widget.PopupWindowCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
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
        init(getActivityFromView(parent), options);
        mPopupWindow.showAtLocation(parent, gravity, x, y);
        applyProgressAffect();
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
        init(getActivityFromView(anchor), options);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mPopupWindow.showAsDropDown(anchor, xoff, yoff, Gravity.NO_GRAVITY);
        else
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
        applyProgressAffect();
        return this;
    }

    @Override
    public PopCreator showWithAnchor(final View anchor, final int xGravity, final int yGravity, final int xoff, final int yoff, PopOptions options)
    {
        init(getActivityFromView(anchor), options);
        mPopContentView.getRealContentView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                    mPopContentView.getRealContentView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                else
                    mPopContentView.getRealContentView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = mPopContentView.getRealContentView().getWidth();
                int height = mPopContentView.getRealContentView().getHeight();
                int realXOffset = calculateX(anchor, xGravity, width, xoff);
                int realYOffset = calculateY(anchor, yGravity, height, yoff);
                if (isShowing())
                    mPopupWindow.update(anchor, realXOffset, realYOffset, width, height);
            }
        });
        PopupWindowCompat.showAsDropDown(mPopupWindow, anchor.getRootView(), 0, 0, Gravity.NO_GRAVITY);
        applyProgressAffect();
        return this;
    }

    /**
     * 根据水平权重计算x偏移
     */
    private int calculateX(View anchor, int xGravity, int measuredW, int x)
    {
        switch (xGravity)
        {
            case XGravity.LEFT:
                //anchor view左侧
                x -= measuredW;
                break;
            case XGravity.ALIGN_RIGHT:
                //与anchor view右边对齐
                x -= measuredW - anchor.getWidth();
                break;
            case XGravity.CENTER:
                //anchor view水平居中
                x += anchor.getWidth() / 2 - measuredW / 2;
                break;
            case XGravity.ALIGN_LEFT:
                //与anchor view左边对齐
                // Default position.
                break;
            case XGravity.RIGHT:
                //anchor view右侧
                x += anchor.getWidth();
                break;
        }
        return x;
    }

    /**
     * 根据垂直权重计算y偏移
     */
    private int calculateY(View anchor, int yGravity, int measuredH, int y)
    {
        switch (yGravity)
        {
            case YGravity.ABOVE:
                //anchor view之上
                y -= measuredH + anchor.getHeight();
                break;
            case YGravity.ALIGN_BOTTOM:
                //anchor view底部对齐
                y -= measuredH;
                break;
            case YGravity.CENTER:
                //anchor view垂直居中
                y -= anchor.getHeight() / 2 + measuredH / 2;
                break;
            case YGravity.ALIGN_TOP:
                //anchor view顶部对齐
                y -= anchor.getHeight();
                break;
            case YGravity.BELOW:
                //anchor view之下
                // Default position.
                break;
        }
        return y;
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

    private Activity getActivityFromView(View view)
    {
        if (null != view)
        {
            Context context = view.getContext();
            while (!(context instanceof Activity))
                context = ((ContextWrapper) context).getBaseContext();
            return (Activity) context;
        }
        return null;
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
        applyDismissAffect();
        if (mOptions != null && mOptions.getDismissListener() != null)
            mOptions.getDismissListener().onDismiss();
        mPopupWindow = null;
        mContextReference = null;
    }

    //显示过程中的效果
    private void applyProgressAffect()
    {
        if (mOptions == null || mOptions.getAffect() == null)
            return;

        if (mAnimator != null)
            mAnimator.cancel();

        mAnimator = ValueAnimator.ofFloat(0f, 1.0f);
        mAnimator.setDuration(mOptions.getAffectDuration());
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                mOptions.getAffect().onShowingProgress(getContext(), mOptions, (Float) animation.getAnimatedValue());
            }
        });
        mAnimator.start();
    }

    //消失的效果
    private void applyDismissAffect()
    {
        if (mOptions == null || mOptions.getAffect() == null)
            return;
        if (mAnimator != null)
            mAnimator.cancel();

        mOptions.getAffect().onDismissed(getContext(), mOptions);
    }

    protected Context getContext()
    {
        return mContextReference != null ? mContextReference.get() : null;
    }
}
