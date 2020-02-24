package com.lwkandroid.lib.common.widgets.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lwkandroid.lib.common.widgets.ui.helper.IRHelper;
import com.lwkandroid.lib.common.widgets.ui.helper.RTextViewHelper;


/**
 * REditText
 */
public class REditText extends androidx.appcompat.widget.AppCompatEditText implements IRHelper<RTextViewHelper>
{

    private RTextViewHelper mHelper;

    public REditText(Context context)
    {
        this(context, null);
    }

    public REditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mHelper = new RTextViewHelper(context, this, attrs);
    }

    @Override
    public RTextViewHelper getHelper()
    {
        return mHelper;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        super.setEnabled(enabled);
        if (mHelper != null)
        {
            mHelper.setEnabled(enabled);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (mHelper != null)
        {
            mHelper.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setSelected(boolean selected)
    {
        if (mHelper != null)
        {
            mHelper.setSelected(selected);
        }
        super.setSelected(selected);
    }

}
