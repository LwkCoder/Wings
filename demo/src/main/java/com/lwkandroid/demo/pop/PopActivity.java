package com.lwkandroid.demo.pop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lwkandroid.demo.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class PopActivity extends MvpBaseActivity<PopPresenter> implements PopContract.IView<PopPresenter>
{
    private Button mButton;

    @Override
    protected PopPresenter createPresenter()
    {
        return new PopPresenter(this, new PopModel());
    }


    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_pop;
    }

    @Override
    protected void initUI(View contentView)
    {
        mButton = find(R.id.btn_pop);
        addClick(mButton);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_pop:
                break;
            default:
                break;
        }
    }
}
