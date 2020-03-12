package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.lwkandroid.lib.common.widgets.view.helper.IRHelper;
import com.lwkandroid.lib.common.widgets.view.helper.RBaseHelper;


/**
 * RRelativeLayout
 */
public class RRelativeLayout extends RelativeLayout implements IRHelper<RBaseHelper>
{

    private RBaseHelper mHelper;

    public RRelativeLayout(Context context)
    {
        this(context, null);
    }

    public RRelativeLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr)
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
