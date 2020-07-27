package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lwkandroid.lib.common.widgets.view.helper.RBaseHelper;
import com.lwkandroid.lib.common.widgets.view.iface.RHelper;


/**
 * RLinearLayout
 */
public class RLinearLayout extends LinearLayout implements RHelper<RBaseHelper>
{

    private RBaseHelper mHelper;

    public RLinearLayout(Context context)
    {
        this(context, null);
    }

    public RLinearLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RLinearLayout(Context context, AttributeSet attrs, int defStyleAttr)
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
