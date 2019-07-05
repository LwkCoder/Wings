package com.lwkandroid.wingsdemo.project.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.widget.dialog.DialogCreator;
import com.lwkandroid.wings.widget.dialog.OnDialogChildClickListener;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

/**
 * @author LWK
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
                        .addOnChildClickListener(R.id.tv_pop_bottom_menu01, new OnDialogChildClickListener()
                        {
                            @Override
                            public void onDialogChildClicked(int viewId, View view, View contentView, DialogCreator creator)
                            {
                                creator.dismiss();
                            }
                        })
                        .addOnChildClickListener(R.id.tv_pop_bottom_menu02, new OnDialogChildClickListener()
                        {
                            @Override
                            public void onDialogChildClicked(int viewId, View view, View contentView, DialogCreator creator)
                            {
                                creator.dismiss();
                            }
                        })
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

        addClick(R.id.btn_input, new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogCreator.create(new InputDialog())
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(false)
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
