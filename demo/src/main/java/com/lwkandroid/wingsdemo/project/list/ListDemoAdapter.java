package com.lwkandroid.wingsdemo.project.list;

import android.content.Context;

import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.rcvadapter.holder.RcvHolder;
import com.lwkandroid.wingsdemo.R;

import java.util.List;

/**
 * Created by LWK
 */

public class ListDemoAdapter extends RcvSingleAdapter<String>
{
    public ListDemoAdapter(Context context, List<String> datas)
    {
        super(context, R.layout.adapter_list_demo, datas);
    }

    @Override
    public void onBindView(RcvHolder holder, String itemData, int position)
    {
        holder.setTvText(R.id.tv_listitem, itemData);
    }
}
