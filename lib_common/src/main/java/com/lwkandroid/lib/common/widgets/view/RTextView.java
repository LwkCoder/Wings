package com.lwkandroid.lib.common.widgets.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lwkandroid.lib.common.widgets.view.helper.IRHelper;
import com.lwkandroid.lib.common.widgets.view.helper.RTextViewHelper;


/**
 * RTextView
 */
public class RTextView extends androidx.appcompat.widget.AppCompatTextView implements IRHelper<RTextViewHelper>
{

    private RTextViewHelper mHelper;

    public RTextView(Context context)
    {
        this(context, null);
    }

    public RTextView(Context context, AttributeSet attrs)
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
