package com.lwkandroid.wings.app;

import android.util.SparseArray;
import android.view.View;

/**
 * findViewById utils
 */
public final class ViewFinder
{
    private final String ERROR = "ViewFinder error: must set up layout !";
    private View mLayout;
    private SparseArray<View> mViews;

    public ViewFinder(View layout)
    {
        this.mLayout = layout;
        if (mLayout == null)
            throw new IllegalArgumentException(ERROR);
        this.mViews = new SparseArray<>();
    }

    /**
     * getStrategy rootlayout
     */
    public View getLayout()
    {
        return mLayout;
    }

    /**
     * getStrategy view by id
     */
    public <T extends View> T findView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mLayout.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * set view onClickListener
     *
     * @param viewId   view id
     * @param listener onClickListener
     * @return view obj
     */
    public View addClick(int viewId, View.OnClickListener listener)
    {
        View view = findView(viewId);
        if (view != null)
            view.setOnClickListener(listener);
        return view;
    }

    /**
     * set view onClickListener
     *
     * @param view     view obj
     * @param listener onClickListener
     * @return view obj
     */
    public View addClick(View view, View.OnClickListener listener)
    {
        if (view != null)
            view.setOnClickListener(listener);
        return view;
    }
}
