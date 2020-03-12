package com.lwkandroid.lib.common.widgets.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.DialogBaseContentView;
import com.lwkandroid.lib.common.widgets.dialog.DialogCreator;
import com.lwkandroid.lib.common.widgets.dialog.DialogOptions;
import com.lwkandroid.lib.core.utils.ResourceUtils;
import com.lwkandroid.lib.core.utils.StringUtils;

import androidx.annotation.StringRes;

/**
 * Description:加载过程中显示的dialog内容
 *
 * @author LWK
 * @date 2020/3/12
 */
public class LoadingDialogContent extends DialogBaseContentView
{
    private String mMessage;
    private ViewGroup mViewGroupRoot;
    private LoadingView mLoadingView;
    private TextView mTvMessage;

    public LoadingDialogContent()
    {
    }

    public LoadingDialogContent(String message)
    {
        this.mMessage = message;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public void setMessage(@StringRes int resId)
    {
        setMessage(ResourceUtils.getString(resId));
    }

    public void setMessage(String message)
    {
        this.mMessage = message;
        //没有文字显示的时候，padding大一点
        //有文字显示的时候，padding左右稍大，上下稍小
        if (StringUtils.isTrimEmpty(mMessage))
        {
            if (mTvMessage != null)
            {
                mTvMessage.setText(null);
                mTvMessage.setVisibility(View.GONE);
            }
            if (mViewGroupRoot != null)
            {
                int padding = ResourceUtils.getDimenPixelSize(R.dimen.spacing_large);
                mViewGroupRoot.setPadding(padding, padding, padding, padding);
            }
        } else
        {
            if (mTvMessage != null)
            {
                mTvMessage.setText(mMessage);
                mTvMessage.setVisibility(View.VISIBLE);
            }
            if (mViewGroupRoot != null)
            {
                int padding = ResourceUtils.getDimenPixelSize(R.dimen.spacing_normal);
                mViewGroupRoot.setPadding(padding * 2, padding, padding * 2, padding);
            }
        }
    }

    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.dialog_loading;
    }

    @Override
    public void initUIAndData(View contentView, DialogOptions options, DialogCreator creator)
    {
        mViewGroupRoot = contentView.findViewById(R.id.ll_loading_dialog_root);
        mLoadingView = contentView.findViewById(R.id.lv_loading_dialog);
        mTvMessage = contentView.findViewById(R.id.tv_loading_dialog);
        setMessage(mMessage);
    }
}
