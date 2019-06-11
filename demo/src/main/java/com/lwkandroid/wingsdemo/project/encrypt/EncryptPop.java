package com.lwkandroid.wingsdemo.project.encrypt;

import android.view.View;

import com.lwkandroid.wings.widget.pop.PopBaseContentView;
import com.lwkandroid.wings.widget.pop.PopCreator;
import com.lwkandroid.wings.widget.pop.PopOptions;
import com.lwkandroid.wingsdemo.R;

/**
 * Description:
 *
 * @author LWK
 * @date 2019/5/29
 */
public class EncryptPop extends PopBaseContentView implements View.OnClickListener
{
    public static final int TYPE_AES = 1;
    public static final int TYPE_RSA = 2;
    private OnItemClickListener mListener;

    public EncryptPop(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    @Override
    public int getContentViewLayoutResId()
    {
        return R.layout.pop_encrypt;
    }

    @Override
    public void initUIAndData(View contentView, PopOptions options, PopCreator popCreator)
    {
        contentView.findViewById(R.id.tv_encrypt_aes).setOnClickListener(this);
        contentView.findViewById(R.id.tv_encrypt_rsa).setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_encrypt_aes:
                if (mListener != null)
                {
                    mListener.onEncryptTypeChanged(TYPE_AES);
                }
                getCreator().dismiss();
                break;
            case R.id.tv_encrypt_rsa:
                if (mListener != null)
                {
                    mListener.onEncryptTypeChanged(TYPE_RSA);
                }
                getCreator().dismiss();
                break;
            default:
                break;
        }
    }

    public interface OnItemClickListener
    {
        void onEncryptTypeChanged(int type);
    }
}
