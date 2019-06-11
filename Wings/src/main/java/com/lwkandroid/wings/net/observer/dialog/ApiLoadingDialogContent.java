package com.lwkandroid.wings.net.observer.dialog;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lwkandroid.wings.R;
import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.widget.dialog.DialogBaseContentView;
import com.lwkandroid.wings.widget.dialog.DialogCreator;
import com.lwkandroid.wings.widget.dialog.DialogOptions;

/**
 * Description:配合网络请求显示Loading Dialog对象
 *
 * @author LWK
 * @date 2019/6/10
 */
public final class ApiLoadingDialogContent extends DialogBaseContentView
{
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private String mMessage;

    ApiLoadingDialogContent(String message)
    {
        this.mMessage = message;
    }

    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.dialog_api_loaing;
    }

    @Override
    public void initUIAndData(View contentView, DialogOptions options, DialogCreator creator)
    {
        mProgressBar = contentView.findViewById(R.id.pb_api_loading_dialog);
        mTextView = contentView.findViewById(R.id.tv_api_loading_dialog);
        if (StringUtils.isEmpty(mMessage))
        {
            mTextView.setText(null);
            mTextView.setVisibility(View.GONE);
        } else
        {
            mTextView.setText(mMessage);
            mTextView.setVisibility(View.VISIBLE);
        }
    }
}
