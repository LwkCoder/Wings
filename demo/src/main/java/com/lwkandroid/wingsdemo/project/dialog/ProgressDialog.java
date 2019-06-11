package com.lwkandroid.wingsdemo.project.dialog;

import android.view.View;
import android.widget.TextView;

import com.lwkandroid.wings.utils.StringUtils;
import com.lwkandroid.wings.widget.dialog.DialogBaseContentView;
import com.lwkandroid.wings.widget.dialog.DialogCreator;
import com.lwkandroid.wings.widget.dialog.DialogOptions;
import com.lwkandroid.wingsdemo.R;

/**
 * Created by LWK
 */
public class ProgressDialog extends DialogBaseContentView
{
    private String mMessage;

    public ProgressDialog(String message)
    {
        this.mMessage = message;
    }

    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.dialog_progress;
    }

    @Override
    public void initUIAndData(View contentView, DialogOptions options, DialogCreator creator)
    {
        TextView textView = contentView.findViewById(R.id.tv_progress_message);
        if (StringUtils.isTrimNotEmpty(mMessage))
        {
            textView.setVisibility(View.VISIBLE);
            textView.setText(mMessage);
        } else
        {
            textView.setVisibility(View.GONE);
            textView.setText(mMessage);
        }
    }
}
