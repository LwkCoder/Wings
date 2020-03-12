package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.lwkandroid.lib.common.widgets.view.helper.IRHelper;
import com.lwkandroid.lib.common.widgets.view.helper.RBaseHelper;


/**
 * RFrameLayout
 */
public class RFrameLayout extends FrameLayout implements IRHelper<RBaseHelper>
{

    private RBaseHelper mHelper;

    public RFrameLayout(Context context)
    {
        this(context, null);
    }

    public RFrameLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RFrameLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mHelper = new RBaseHelper(context, this, attrs);
    }

    @Override
    public RBaseHelper getHelper()
    {
        return mHelper;
    }
}
