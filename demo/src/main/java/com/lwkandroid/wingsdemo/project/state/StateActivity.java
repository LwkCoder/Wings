package com.lwkandroid.wingsdemo.project.state;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.widget.StateFrameLayout;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class StateActivity extends AppBaseActivity<StatePresenter> implements StateContract.IView
{
    private StateFrameLayout mStateFrameLayout;

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_state;
    }

    @Override
    protected void initUI(View contentView)
    {
        mStateFrameLayout = find(R.id.sfl_state_container);

        addClick(R.id.btn_error_state_retry);
        addClick(R.id.btn_request_empty);
        addClick(R.id.btn_request_error);
        addClick(R.id.btn_request_content);
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
            case R.id.btn_request_empty:
                getPresenter().requestEmpty();
                break;
            case R.id.btn_request_error:
                getPresenter().requestError();
                break;
            case R.id.btn_request_content:
                getPresenter().requestContent();
                break;
            case R.id.btn_error_state_retry:
                getPresenter().requestContent();
                break;
            default:
                break;
        }
    }

    @Override
    public StateFrameLayout getStateLayout()
    {
        return mStateFrameLayout;
    }
}
