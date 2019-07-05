package com.lwkandroid.wingsdemo.project.dialog;

import android.view.View;

import com.lwkandroid.wings.widget.dialog.DialogBaseContentView;
import com.lwkandroid.wings.widget.dialog.DialogCreator;
import com.lwkandroid.wings.widget.dialog.DialogOptions;
import com.lwkandroid.wingsdemo.R;

/**
 * @author LWK
 */
public class BottomMenuDialog extends DialogBaseContentView implements View.OnClickListener
{
    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.pop_bottom_menu;
    }

    @Override
    public void initUIAndData(View contentView, DialogOptions options, DialogCreator creator)
    {
    }

    @Override
    public void onClick(View v)
    {
        getCreator().dismiss();
    }
}
