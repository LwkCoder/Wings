package com.lwkandroid.lib.common.widgets.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.lwkandroid.lib.common.widgets.ui.helper.IRHelper;
import com.lwkandroid.lib.common.widgets.ui.helper.RBaseHelper;


/**
 * RView
 */
public class RView extends View implements IRHelper<RBaseHelper>
{


    private RBaseHelper mHelper;

    public RView(Context context)
    {
        this(context, null);
    }

    public RView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RView(Context context, AttributeSet attrs, int defStyleAttr)
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
