package com.lwkandroid.wingsdemo.project.pop;

import android.view.View;

import com.lwkandroid.wings.widget.pop.PopBaseContentView;
import com.lwkandroid.wings.widget.pop.PopCreator;
import com.lwkandroid.wings.widget.pop.PopOptions;
import com.lwkandroid.wingsdemo.R;

/**
 * @author LWK
 */
public class DialogPop extends PopBaseContentView implements View.OnClickListener
{
    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.pop_dialog;
    }

    @Override
    public void initUIAndData(View contentView, PopOptions options, PopCreator popCreator)
    {
        contentView.findViewById(R.id.tv_pop_dialog_dismiss).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        getCreator().dismiss();
    }
}
