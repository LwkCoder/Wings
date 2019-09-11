package com.lwkandroid.wingsdemo.project.pop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lwkandroid.widget.comactionbar.ComActionBar;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;
import com.lwkandroid.wings.utils.ScreenUtils;
import com.lwkandroid.wings.widget.pop.PopCreator;
import com.lwkandroid.wings.widget.pop.PopDarkWindowAffect;
import com.lwkandroid.wings.widget.pop.XGravity;
import com.lwkandroid.wings.widget.pop.YGravity;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseActivity;

import androidx.annotation.Nullable;

/**
 * @author LWK
 */
public class PopDemoActivity extends AppBaseActivity<MVPBasePresenter>
{
    ComActionBar mActionBar;
    private Button mBtnMenuLeft;
    private Button mBtnMenuRight;
    private Button mBtnMenuAbove;
    private Button mBtnMenuBottom;

    @Override
    public int getContentViewId()
    {
        return R.layout.activity_pop_demo;
    }

    @Override
    protected void initUI(View contentView)
    {
        mActionBar = find(R.id.comactionbar);
        mActionBar.setRightClickListener01(this);
        mBtnMenuLeft = find(R.id.btn_pop_menu_left);
        mBtnMenuRight = find(R.id.btn_pop_menu_right);
        mBtnMenuAbove = find(R.id.btn_pop_menu_above);
        mBtnMenuBottom = find(R.id.btn_pop_menu_bottom);

        addClick(R.id.btn_pop_bottom_menu);
        addClick(R.id.btn_pop_dialog);
        addClick(R.id.btn_pop_menu_left);
        addClick(R.id.btn_pop_menu_right);
        addClick(R.id.btn_pop_menu_above);
        addClick(R.id.btn_pop_menu_bottom);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    public void onClick(int id, View v)
    {
        switch (id)
        {
            case R.id.btn_pop_menu_left:
                PopCreator.create(new ActionBarMenuPop())
                        .showWithAnchor(mBtnMenuLeft, XGravity.LEFT, YGravity.ALIGN_BOTTOM, 0, 0);
                break;
            case R.id.btn_pop_menu_right:
                PopCreator.create(new ActionBarMenuPop())
                        .showWithAnchor(mBtnMenuRight, XGravity.RIGHT, YGravity.ALIGN_BOTTOM, 0, 0);
                break;
            case R.id.btn_pop_menu_above:
                PopCreator.create(new ActionBarMenuPop())
                        .showWithAnchor(mBtnMenuAbove, XGravity.CENTER, YGravity.ABOVE, 0, 0);
                break;
            case R.id.btn_pop_menu_bottom:
                PopCreator.create(new ActionBarMenuPop())
                        .showWithAnchor(mBtnMenuBottom, XGravity.CENTER, YGravity.BELOW, 0, 0);
                break;
            case R.id.fl_comactionbar_right01:
                PopCreator.create(new ActionBarMenuPop())
                        .showAsDropDown(mActionBar.getRightAreaView01());
                break;
            case R.id.btn_pop_bottom_menu:
                PopCreator.create(new BottomMenuPop())
                        .addOnChildClickListener(R.id.tv_pop_bottom_menu01, (viewId, view, contentView, popCreator) -> popCreator.dismiss())
                        .addOnChildClickListener(R.id.tv_pop_bottom_menu02, (viewId, view, contentView, popCreator) -> popCreator.dismiss())
                        .setAffectParams(new PopDarkWindowAffect(0.5f), 200)
                        .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .setAnimStyle(R.style.PopBottomMenuStyle)
                        .showAtLocation(getContentView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btn_pop_dialog:
                PopCreator.create(new DialogPop())
                        .setCanceledOnTouchOutside(false)
                        .setAffectParams(new PopDarkWindowAffect(), 200)
                        .setLayoutParams(ScreenUtils.getScreenWidth() / 3 * 2, ViewGroup.LayoutParams.WRAP_CONTENT)
                        .showAtLocation(getContentView(), Gravity.CENTER, 0, 0);
                break;
            default:
                break;
        }
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }
}
