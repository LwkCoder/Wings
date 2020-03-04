package com.lwkandroid.demo2.test;

import android.content.Context;

import com.lwkandroid.demo2.R;
import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.rcvadapter.holder.RcvHolder;

/**
 * Description:
 *
 * @author 20180004
 * @date 2020/3/2
 */
public class TestAdapter extends RcvSingleAdapter<String>
{
    public TestAdapter(Context context)
    {
        super(context, R.layout.adapter_test, null);
    }

    @Override
    public void onBindView(RcvHolder holder, String itemData, int position)
    {
        holder.setTvText(R.id.tv_adapter_simple, itemData);
    }
}
