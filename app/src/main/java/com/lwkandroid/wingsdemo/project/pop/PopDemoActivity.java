package com.lwkandroid.wingsdemo.project.pop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wings.mvp.BasePresenter;
import com.lwkandroid.wings.utils.ScreenUtils;
import com.lwkandroid.wings.utils.pop.PopCreator;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

public class PopDemoActivity extends AppBaseActivity
{
    ComActionBar mActionBar;

    @Override
    public int getContentViewId()
    {
        return R.layout.activity_pop_demo;
    }

    @Override
    protected void initUI(View contentView)
    {
        super.initUI(contentView);
        mActionBar = find(R.id.comactionbar);
        mActionBar.setRightClickListener01(this);

        addClick(R.id.btn_pop_bottom_menu);
        addClick(R.id.btn_pop_dialog);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    protected void onClick(int id, View v)
    {
        switch (id)
        {
            case R.id.fl_comactionbar_right01:
                PopCreator.create(new ActionBarMenuPop())
                        .showAsDropDown(mActionBar.getRightAreaView01());
                break;
            case R.id.btn_pop_bottom_menu:
                PopCreator.create(new BottomMenuPop())
                        .setDarkWindow(true)
                        .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setDarkWindowDuration(200)//务必和动画时间一致
                        .setAnimStyle(R.style.PopBottomMenuStyle)
                        .showAtLocation(mContentView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_pop_dialog:
                PopCreator.create(new DialogPop())
                        .setCancelOutsideTouched(false)
                        .setLayoutParams(ScreenUtils.getScreenWidth() / 3 * 2, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setDarkWindow(true)
                        .setDarkWindowDuration(200)
                        .showAtLocation(mContentView, Gravity.CENTER, 0, 0);
                break;
        }
    }

    @Override
    protected BasePresenter createPresenter()
    {
        return null;
    }
}
