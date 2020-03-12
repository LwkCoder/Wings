package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.util.AttributeSet;

import com.lwkandroid.lib.common.widgets.view.helper.IRHelper;
import com.lwkandroid.lib.common.widgets.view.helper.RBaseHelper;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * RConstraintLayout
 */
public class RConstraintLayout extends ConstraintLayout implements IRHelper<RBaseHelper>
{

    private RBaseHelper mHelper;

    public RConstraintLayout(Context context)
    {
        this(context, null);
    }

    public RConstraintLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public RConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr)
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
