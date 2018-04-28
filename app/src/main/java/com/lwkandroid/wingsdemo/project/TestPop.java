package com.lwkandroid.wingsdemo.project;

import android.app.Activity;
import android.view.View;

import com.lwkandroid.wings.utils.PopWrapper;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 * TODO
 */

public class TestPop extends PopWrapper
{
    @Override
    public int getContentViewId()
    {
        return R.layout.pop_layout;
    }

    @Override
    public void initUI(Activity activity, View contentView)
    {

    }

    @Override
    public void onDismiss()
    {
        super.onDismiss();
    }
}
