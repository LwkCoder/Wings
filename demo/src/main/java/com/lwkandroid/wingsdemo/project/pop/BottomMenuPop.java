package com.lwkandroid.wingsdemo.project.pop;

import android.view.View;

import com.lwkandroid.wings.utils.pop.PopBaseContentView;
import com.lwkandroid.wings.utils.pop.PopCreator;
import com.lwkandroid.wings.utils.pop.PopOptions;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO 底部弹出式菜单
 */

public class BottomMenuPop extends PopBaseContentView implements View.OnClickListener
{
    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.pop_bottom_menu;
    }

    @Override
    public <T extends PopOptions<T>> void initUIAndData(View contentView, T options, PopCreator popCreator)
    {
        contentView.findViewById(R.id.tv_pop_bottom_menu01).setOnClickListener(this);
        contentView.findViewById(R.id.tv_pop_bottom_menu02).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        getPopCreator().dismiss();
    }
}
