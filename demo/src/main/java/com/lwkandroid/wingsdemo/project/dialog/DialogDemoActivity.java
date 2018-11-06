package com.lwkandroid.wingsdemo.project.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.widget.dialog.DialogCreator;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

/**
 * Created by LWK
 * TODO
 */
public class DialogDemoActivity extends AppBaseActivity<MVPBasePresenter>
{
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
        addClick(R.id.btn_show_progress, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogCreator.create(new ProgressDialog("Loading"))
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(false)
                        .setThemeStyle(R.style.BaseDialogStyle)
                        .setDarkWindowDegree(0.2f)
                        .show(DialogDemoActivity.this);
            }
        });

        addClick(R.id.btn_bottom_menu, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogCreator.create(new BottomMenuDialog())
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .setDarkWindowDegree(0.5f)
                        .setAnimStyle(R.style.PopBottomMenuStyle)
                        .setLayoutGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
                        .setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT))
                        .show(DialogDemoActivity.this);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {

    }
}
