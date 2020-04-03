package com.lwkandroid.lib.common.widgets.ui;

import android.view.View;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.DialogBaseContentView;
import com.lwkandroid.lib.common.widgets.dialog.DialogCreator;
import com.lwkandroid.lib.common.widgets.dialog.DialogOptions;
import com.lwkandroid.lib.common.widgets.view.RTextView;
import com.lwkandroid.lib.core.utils.ResourceUtils;

/**
 * Description:通用Dialog样式模版
 *
 * @author LWK
 * @date 2020/3/26
 */
public class CommonDialogContent extends DialogBaseContentView
{
    private String mTitle;
    private String mContent;
    private String mPositive;
    private String mNegative;
    private RTextView mTvTitle;
    private RTextView mTvContent;
    private RTextView mTvPositive;
    private RTextView mTvNegative;

    public CommonDialogContent setTitle(String title)
    {
        this.mTitle = title;
        return this;
    }

    public CommonDialogContent setTitle(int resId)
    {
        this.mTitle = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogContent setContent(int resId)
    {
        this.mContent = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogContent setContent(String content)
    {
        this.mContent = content;
        return this;
    }

    public CommonDialogContent setPositive(String positive)
    {
        this.mPositive = positive;
        return this;
    }

    public CommonDialogContent setPositive(int resId)
    {
        this.mPositive = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogContent setNegative(int resId)
    {
        this.mNegative = ResourceUtils.getString(resId);
        return this;
    }

    public CommonDialogContent setNegative(String negative)
    {
        this.mNegative = negative;
        return this;
    }

    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.dialog_content_template;
    }

    @Override
    public void initUIAndData(View contentView, DialogOptions options, DialogCreator creator)
    {
        mTvTitle = find(R.id.tv_dialog_template_title);
        mTvContent = find(R.id.tv_dialog_template_content);
        mTvPositive = find(R.id.tv_dialog_template_positive);
        mTvNegative = find(R.id.tv_dialog_template_negative);

        mTvTitle.setText(mTitle);
        mTvContent.setText(mContent);
        mTvPositive.setText(mPositive);
        mTvNegative.setText(mNegative);
    }

    public int getPositiveButtonId()
    {
        return R.id.tv_dialog_template_positive;
    }

    public int getNegativeButtonId()
    {
        return R.id.tv_dialog_template_negative;
    }
}
