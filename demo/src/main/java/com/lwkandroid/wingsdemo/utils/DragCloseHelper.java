package com.lwkandroid.wingsdemo.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.utils.ConvertUtils;
import com.lwkandroid.wingsdemo.R;

import androidx.annotation.FloatRange;

/**
 * 向下拉动关闭界面的帮助类
 */
public class DragCloseHelper
{
    private ViewConfiguration mViewConfiguration;

    /**
     * 动画执行时间
     */
    private final static long DURATION = 100;
    /**
     * 滑动边界距离
     */
    private final static int MAX_EXIT_Y = ConvertUtils.dp2px(150);
    private int mMaxExitY = MAX_EXIT_Y;
    /**
     * 最小的缩放尺寸
     */
    private static final float MIN_SCALE = 0.3F;
    private float mMinScale = MIN_SCALE;
    /**
     * 是否在滑动关闭中，手指还在触摸中
     */
    private boolean mIsSwipingToClose;
    /**
     * 上次触摸坐标
     */
    private float mLastY, mLastRawY, mLastX, mLastRawX;
    /**
     * 上次触摸手指id
     */
    private int mLastPointerId;
    /**
     * 当前位移距离
     */
    private float mCurrentTranslationY, mCurrentTranslationX;
    /**
     * 上次位移距离
     */
    private float mLastTranslationY, mLastTranslationX;
    /**
     * 正在恢复原位中
     */
    private boolean mIsResetingAnimate = false;

    private Context mContext;
    private View mParentV, mChildV;
    private OnDragCloseListener mOnDragCloseListener;

    public DragCloseHelper(Context mContext)
    {
        this.mContext = mContext;
        mViewConfiguration = ViewConfiguration.get(mContext);
    }

    public void setOnDragCloseListener(OnDragCloseListener onDragCloseListener)
    {
        this.mOnDragCloseListener = onDragCloseListener;
    }

    /**
     * 设置拖拽关闭的view
     *
     * @param parentV
     * @param childV
     */
    public void setDragCloseViews(View parentV, View childV)
    {
        this.mParentV = parentV;
        this.mChildV = childV;
    }

    /**
     * 设置最大退出距离
     *
     * @param maxExitY
     */
    public void setMaxExitY(int maxExitY)
    {
        this.mMaxExitY = maxExitY;
    }

    /**
     * 设置最小缩放尺寸
     *
     * @param minScale
     */
    public void setMinScale(@FloatRange(from = 0.1f, to = 1.0f) float minScale)
    {
        this.mMinScale = minScale;
    }

    /**
     * 处理touch事件
     */
    public boolean handleTouchEvent(MotionEvent event)
    {
        if (mOnDragCloseListener != null && mOnDragCloseListener.shouldIgnoreTouchEvent())
        {
            //拦截
            log("action dispatch--->");
            mIsSwipingToClose = false;
            return false;
        } else
        {
            //不拦截
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                log("action down--->");
                //初始化数据
                mLastPointerId = event.getPointerId(0);
                reset(event);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE)
            {
                log("action move--->" + event.getPointerCount() + "---" + mIsSwipingToClose);
                if (event.getPointerCount() > 1)
                {
                    //如果有多个手指
                    if (mIsSwipingToClose)
                    {
                        //已经开始滑动关闭，恢复原状，否则需要派发事件
                        mIsSwipingToClose = false;
                        resetCallBackAnimation();
                        return true;
                    }
                    reset(event);
                    return false;
                }
                if (mLastPointerId != event.getPointerId(0))
                {
                    //手指不一致，恢复原状
                    if (mIsSwipingToClose)
                    {
                        resetCallBackAnimation();
                    }
                    reset(event);
                    return true;
                }
                float currentY = event.getY();
                float currentX = event.getX();
                if (mIsSwipingToClose || (Math.abs(currentY - mLastY) > 2 * mViewConfiguration.getScaledTouchSlop()
                        && Math.abs(currentY - mLastY) > Math.abs(currentX - mLastX) * 1.5))
                {
                    //已经触发或者开始触发，更新view
                    mLastY = currentY;
                    mLastX = currentX;
                    log("action move---> start close");
                    float currentRawY = event.getRawY();
                    float currentRawX = event.getRawX();
                    if (!mIsSwipingToClose)
                    {
                        //准备开始
                        mIsSwipingToClose = true;
                        if (mOnDragCloseListener != null)
                        {
                            mOnDragCloseListener.onDragStart();
                        }
                    }
                    //已经开始，更新view
                    mCurrentTranslationY = currentRawY - mLastRawY + mLastTranslationY;
                    mCurrentTranslationX = currentRawX - mLastRawX + mLastTranslationX;
                    float percent = 1 - Math.abs(mCurrentTranslationY / (mMaxExitY + mChildV.getHeight()));
                    if (percent > 1)
                    {
                        percent = 1;
                    } else if (percent < 0)
                    {
                        percent = 0;
                    }
                    mParentV.getBackground().mutate().setAlpha((int) (percent * 255));
                    if (mOnDragCloseListener != null)
                    {
                        mOnDragCloseListener.onDragging(percent);
                    }
                    mChildV.setTranslationY(mCurrentTranslationY);
                    mChildV.setTranslationX(mCurrentTranslationX);
                    if (percent < mMinScale)
                    {
                        percent = mMinScale;
                    }
                    mChildV.setScaleX(percent);
                    mChildV.setScaleY(percent);
                    return true;
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP)
            {
                log("action up--->" + mIsSwipingToClose);
                //手指抬起事件
                if (mIsSwipingToClose)
                {
                    if (mCurrentTranslationY > mMaxExitY)
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                        {
                            //会执行共享元素的离开动画
                            if (mOnDragCloseListener != null)
                            {
                                mOnDragCloseListener.onDragToClose();
                            }
                            ((Activity) mContext).onBackPressed();
                        } else
                        {
                            //会执行定制的离开动画
                            exitWithTranslation(mCurrentTranslationY);
                        }
                    } else
                    {
                        resetCallBackAnimation();
                    }
                    mIsSwipingToClose = false;
                    return true;
                }
            } else if (event.getAction() == MotionEvent.ACTION_CANCEL)
            {
                //取消事件
                if (mIsSwipingToClose)
                {
                    resetCallBackAnimation();
                    mIsSwipingToClose = false;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 退出动画
     *
     * @param currentY
     */
    private void exitWithTranslation(float currentY)
    {
        int targetValue = currentY > 0 ? mChildV.getHeight() : -mChildV.getHeight();
        ValueAnimator anim = ValueAnimator.ofFloat(mCurrentTranslationY, targetValue);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                DragCloseHelper.this.updateChildView(mCurrentTranslationX, (float) animation.getAnimatedValue());
            }
        });
        anim.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {

            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if (mOnDragCloseListener != null)
                {
                    mOnDragCloseListener.onDragToClose();
                }
                ((Activity) mContext).finish();
                ((Activity) mContext).overridePendingTransition(R.anim.act_fade_in, R.anim.act_fade_out);
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        anim.setDuration(DURATION);
        anim.setInterpolator(new LinearInterpolator());
        anim.start();
    }

    /**
     * 重置数据
     *
     * @param event
     */
    private void reset(MotionEvent event)
    {
        mIsSwipingToClose = false;
        mLastY = event.getY();
        mLastX = event.getX();
        mLastRawY = event.getRawY();
        mLastRawX = event.getRawX();
        mLastTranslationY = 0;
        mLastTranslationX = 0;
    }

    /**
     * 更新缩放的view
     */
    private void updateChildView(float transX, float transY)
    {
        mChildV.setTranslationY(transY);
        mChildV.setTranslationX(transX);
        float percent = Math.abs(transY / (mMaxExitY + mChildV.getHeight()));
        float scale = 1 - percent;
        if (scale < mMinScale)
        {
            scale = mMinScale;
        }
        mChildV.setScaleX(scale);
        mChildV.setScaleY(scale);
    }

    /**
     * 恢复到原位动画
     */
    private void resetCallBackAnimation()
    {
        if (mIsResetingAnimate || mCurrentTranslationY == 0)
        {
            return;
        }
        final float ratio = mCurrentTranslationX / mCurrentTranslationY;
        ValueAnimator animatorY = ValueAnimator.ofFloat(mCurrentTranslationY, 0);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                if (mIsResetingAnimate)
                {
                    mCurrentTranslationY = (float) valueAnimator.getAnimatedValue();
                    mCurrentTranslationX = ratio * mCurrentTranslationY;
                    mLastTranslationY = mCurrentTranslationY;
                    mLastTranslationX = mCurrentTranslationX;
                    DragCloseHelper.this.updateChildView(mLastTranslationX, mCurrentTranslationY);
                }
            }
        });
        animatorY.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                mIsResetingAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if (mIsResetingAnimate)
                {
                    mParentV.getBackground().mutate().setAlpha(255);
                    mCurrentTranslationY = 0;
                    mCurrentTranslationX = 0;
                    mIsResetingAnimate = false;
                    if (mOnDragCloseListener != null)
                    {
                        mOnDragCloseListener.onDragCancel();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation)
            {

            }

            @Override
            public void onAnimationRepeat(Animator animation)
            {

            }
        });
        animatorY.setDuration(DURATION).start();
    }

    /**
     * 打印日志
     *
     * @param msg
     */
    private void log(String msg)
    {
        KLog.d(getClass().getName(), msg);
    }

    public interface OnDragCloseListener
    {
        /**
         * 是否不拦截触摸事件
         */
        boolean shouldIgnoreTouchEvent();

        /**
         * 开始拖拽
         */
        void onDragStart();

        /**
         * 拖拽中
         *
         * @param percent
         */
        void onDragging(float percent);

        /**
         * 取消拖拽
         */
        void onDragCancel();

        /**
         * 拖拽结束并即将关闭界面
         */
        void onDragToClose();
    }
}
