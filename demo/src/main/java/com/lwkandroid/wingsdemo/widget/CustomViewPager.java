package com.lwkandroid.wingsdemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/8
 */
public class CustomViewPager extends ViewPager
{
    private int mScrollState = SCROLL_STATE_IDLE;

    public CustomViewPager(@NonNull Context context)
    {
        super(context);
        addOnPageChangeListener(mListener);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        addOnPageChangeListener(mListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        try
        {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private final ViewPager.OnPageChangeListener mListener = new OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {

        }

        @Override
        public void onPageSelected(int position)
        {

        }

        @Override
        public void onPageScrollStateChanged(int state)
        {
            mScrollState = state;
        }
    };

    public int getScrollState()
    {
        return mScrollState;
    }
}
