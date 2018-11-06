package com.lwkandroid.wingsdemo.project.pop;

import android.view.View;

import com.lwkandroid.wings.widget.pop.PopBaseContentView;
import com.lwkandroid.wings.widget.pop.PopCreator;
import com.lwkandroid.wings.widget.pop.PopOptions;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO 作为ActionBar菜单的弹窗示例
 */

public class ActionBarMenuPop extends PopBaseContentView implements View.OnClickListener
{

    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.pop_actionbar_menu;
    }

    @Override
    public <T extends PopOptions<T>> void initUIAndData(View contentView, T options, PopCreator popCreator)
    {
        contentView.findViewById(R.id.tv_pop_menu01).setOnClickListener(this);
        contentView.findViewById(R.id.tv_pop_menu02).setOnClickListener(this);
        contentView.findViewById(R.id.tv_pop_menu03).setOnClickListener(this);
        contentView.findViewById(R.id.tv_pop_menu04).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        getCreator().dismiss();
    }
}
