package com.lwkandroid.lib.common.widgets.ui;

import android.content.DialogInterface;
import android.view.ViewGroup;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.IDialogUiController;
import com.lwkandroid.lib.common.widgets.dialog.WingsDialog;
import com.lwkandroid.lib.common.widgets.view.RTextView;
import com.lwkandroid.lib.core.utils.common.ResourceUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

/**
 * Description:通用Dialog样式控制层
 *
 * @author LWK
 * @date 2020/4/7
 */
public class CommonDialogController implements IDialogUiController
{
    private String mTitle;
    private String mContent;
    private String mPositive;
    private String mNegative;
    private RTextView mTvTitle;
    private RTextView mTvContent;
    private RTextView mTvPositive;
    private RTextView mTvNegative;

    public CommonDialogController setTitle(String title)
    {
        this.mTitle = title;
        return this;
    }

    public CommonDialogController setTitle(int resId)
    {
        this.mTitle = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setContent(int resId)
    {
        this.mContent = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setContent(String content)
    {
        this.mContent = content;
        return this;
    }

    public CommonDialogController setPositive(String positive)
    {
        this.mPositive = positive;
        return this;
    }

    public CommonDialogController setPositive(int resId)
    {
        this.mPositive = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setNegative(int resId)
    {
        this.mNegative = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogController setNegative(String negative)
    {
        this.mNegative = negative;
        return this;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.dialog_content_template;
    }

    @Override
    public void onCreateView(ViewGroup parentView, WingsDialog dialog)
    {
        mTvTitle = parentView.findViewById(R.id.tv_dialog_template_title);
        mTvContent = parentView.findViewById(R.id.tv_dialog_template_content);
        mTvPositive = parentView.findViewById(R.id.tv_dialog_template_positive);
        mTvNegative = parentView.findViewById(R.id.tv_dialog_template_negative);

        mTvTitle.setText(mTitle);
        mTvContent.setText(mContent);
        mTvPositive.setText(mPositive);
        mTvNegative.setText(mNegative);
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

    public int getPositiveButtonId()
    {
        return R.id.tv_dialog_template_positive;
    }

    public int getNegativeButtonId()
    {
        return R.id.tv_dialog_template_negative;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event)
    {

    }
}
