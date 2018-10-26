package com.lwkandroid.wingsdemo.project.pop;

import android.view.View;

import com.lwkandroid.wings.widget.pop.PopBaseContentView;
import com.lwkandroid.wings.widget.pop.PopCreator;
import com.lwkandroid.wings.widget.pop.PopOptions;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO
 */

public class DialogPop extends PopBaseContentView implements View.OnClickListener
{
    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.pop_dialog;
    }

    @Override
    public <T extends PopOptions<T>> void initUIAndData(View contentView, T options, PopCreator popCreator)
    {
        contentView.findViewById(R.id.tv_pop_dialog_dismiss).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        getPopCreator().dismiss();
    }
}
