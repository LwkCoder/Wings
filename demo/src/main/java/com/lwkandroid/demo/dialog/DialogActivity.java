package com.lwkandroid.demo.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.lwkandroid.demo.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.widgets.dialog.DialogBuilder;
import com.lwkandroid.lib.common.widgets.dialog.IDialogUiController;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.common.widgets.ui.CommonDialogController;
import com.lwkandroid.lib.common.widgets.ui.LoadingDialogController;
import com.lwkandroid.lib.common.widgets.view.RTextView;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.FindView;
import com.lwkandroid.lib.core.annotation.ViewInjector;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

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
    @ClickViews(values = {R.id.btn_dialog_01, R.id.btn_dialog_02, R.id.btn_dialog_03})
    public void onClick(int id, View view)
    {
        switch (id)
        {
            case R.id.btn_dialog_01:
                DialogBuilder.with(new LoadingDialogController())
                        .build()
                        .show(this);
                break;
            case R.id.btn_dialog_02:
                CommonDialogController controller1 = new CommonDialogController()
                        .setTitle("我是Title")
                        .setContent("MessageMessageMessageMessageMessageMessage")
                        .setPositive("确定")
                        .setNegative("取消");
                DialogBuilder.with(controller1)
                        .build()
                        .addOnChildClickListener(controller1.getPositiveButtonId(),
                                (viewId, view1, contentView, dialog) -> dialog.dismiss())
                        .addOnChildClickListener(controller1.getNegativeButtonId(),
                                (viewId, view12, contentView, dialog) -> dialog.dismiss())
                        .show(this);
                break;
            case R.id.btn_dialog_03:
                CustomController controller2 = new CustomController();
                DialogBuilder.with(controller2)
                        .setCancelable(false)
                        .build()
                        .addOnChildClickListener(R.id.btn_dialog_custom_close,
                                (viewId, view13, contentView, dialog) -> dialog.dismiss())
                        .show(this);
                break;
            default:
                break;
        }
    }

    private static class CustomController implements IDialogUiController
    {
        @FindView(R.id.tv_dialog_custom_title)
        private RTextView mTvTitle;
        @FindView(R.id.tv_dialog_custom_content)
        private RTextView mTvContent;

        @Override
        public int getLayoutId()
        {
            return R.layout.dialog_custom;
        }

        @Override
        public void onCreateView(ViewGroup parentView, WingsDialog dialog)
        {
            ViewInjector.with(this, parentView);
            mTvTitle.setText("我是自定义的Dialog");
            mTvContent.setText("哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
        }

        @Override
        public void onShow(DialogInterface dialog)
        {

        }

        @Override
        public void onDismiss(DialogInterface dialog)
        {

        }

        @Override
        public void onCancel(DialogInterface dialog)
        {

        }

        @Override
        public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
        {

        }
    }

}
