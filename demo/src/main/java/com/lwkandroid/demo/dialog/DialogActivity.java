package com.lwkandroid.demo.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.demo.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.ViewInjector;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class DialogActivity extends MvpBaseActivity<DialogPresenter> implements DialogContract.IView<DialogPresenter>
{
    @Override
    protected DialogPresenter createPresenter()
    {
        return new DialogPresenter(this, new DialogModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initUI(View contentView)
    {
        ViewInjector.with(this);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    @ClickViews(values = {})
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

}
