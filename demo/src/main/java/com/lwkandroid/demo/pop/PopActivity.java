package com.lwkandroid.demo.pop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lwkandroid.demo.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.widgets.pop.IPopUiController;
import com.lwkandroid.lib.common.widgets.pop.PopBuilder;
import com.lwkandroid.lib.common.widgets.pop.WingsPopupWindow;
import com.lwkandroid.lib.common.widgets.pop.XGravity;
import com.lwkandroid.lib.common.widgets.pop.YGravity;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.FindView;
import com.lwkandroid.lib.core.annotation.ViewInjector;
import com.lwkandroid.widget.comactionbar.ComActionBar;

import androidx.annotation.Nullable;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class PopActivity extends MvpBaseActivity<PopPresenter> implements PopContract.IView<PopPresenter>
{
    @FindView(R.id.tv_pop_target)
    private View mTargetView;
    @FindView(R.id.rg_pop_x)
    private RadioGroup mRadioGroupX;
    @FindView(R.id.rg_pop_y)
    private RadioGroup mRadioGroupY;
    @FindView(R.id.cab_pop)
    ComActionBar actionBar;

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
        ViewInjector.with(this);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
    }

    @Override
    @ClickViews(values = {R.id.fl_comactionbar_right01, R.id.btn_pop_01, R.id.btn_pop_02})
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.fl_comactionbar_right01:
                PopBuilder.with(new MenuController())
                        .build()
                        .addOnChildClickListener(R.id.tv_pop_menu01, (viewId, view1, contentView, popupWindow) -> {
                            popupWindow.dismiss();
                            showShortToast("CLICK MENU01");
                        })
                        .addOnChildClickListener(R.id.tv_pop_menu02, (viewId, view1, contentView, popupWindow) -> {
                            popupWindow.dismiss();
                            showShortToast("CLICK MENU02");
                        })
                        .addOnChildClickListener(R.id.tv_pop_menu03, (viewId, view1, contentView, popupWindow) -> {
                            popupWindow.dismiss();
                            showShortToast("CLICK MENU03");
                        })
                        .showAsDropDown(actionBar.getRightAreaView01());
                break;
            case R.id.btn_pop_01:
                PopBuilder.with(new TestController())
                        .setCanceledOnTouchOutside(true)
                        .build()
                        .showAtLocation(getContentView(), Gravity.CENTER, 0, 0);
                break;
            case R.id.btn_pop_02:
                PopBuilder.with(new TestController())
                        .setCanceledOnTouchOutside(true)
                        .build()
                        .showWithAnchor(mTargetView, getXGravity(), getYGravity());
                break;
            default:
                break;
        }
    }

    private int getXGravity()
    {
        switch (mRadioGroupX.getCheckedRadioButtonId())
        {
            case R.id.rb_pop_x_left:
                return XGravity.LEFT;
            case R.id.rb_pop_x_align_left:
                return XGravity.ALIGN_LEFT;
            case R.id.rb_pop_x_center:
                return XGravity.CENTER;
            case R.id.rb_pop_x_right:
                return XGravity.RIGHT;
            case R.id.rb_pop_x_align_right:
                return XGravity.ALIGN_RIGHT;
            default:
                return XGravity.CENTER;
        }
    }

    private int getYGravity()
    {
        switch (mRadioGroupY.getCheckedRadioButtonId())
        {
            case R.id.rb_pop_y_above:
                return YGravity.ABOVE;
            case R.id.rb_pop_y_align_top:
                return YGravity.ALIGN_TOP;
            case R.id.rb_pop_y_center:
                return YGravity.CENTER;
            case R.id.rb_pop_y_below:
                return YGravity.BELOW;
            case R.id.rb_pop_y_align_bottom:
                return YGravity.ALIGN_BOTTOM;
            default:
                return YGravity.ABOVE;
        }
    }

    private static class TestController implements IPopUiController
    {

        @Override
        public int getLayoutId()
        {
            return R.layout.pop_demo_target;
        }

        @Override
        public void onCreateView(ViewGroup parentView, WingsPopupWindow popupWindow)
        {

        }

        @Override
        public void onDismiss()
        {

        }
    }

    private static class MenuController implements IPopUiController
    {

        @Override
        public int getLayoutId()
        {
            return R.layout.pop_demo_menu;
        }

        @Override
        public void onCreateView(ViewGroup parentView, WingsPopupWindow popupWindow)
        {

        }

        @Override
        public void onDismiss()
        {

        }
    }
}
