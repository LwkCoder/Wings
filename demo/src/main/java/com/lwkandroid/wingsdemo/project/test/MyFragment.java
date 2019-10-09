package com.lwkandroid.wingsdemo.project.test;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.rcvadapter.holder.RcvHolder;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppBaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Description:View层
 *
 * @author
 * @date
 */
public class MyFragment extends AppBaseFragment<MyFragmentPresenter> implements MyFragmentContract.IView
{

    private RecyclerView mRecyclerView;

    /**
     * 创建该Fragment的静态方法
     */
    public static MyFragment createInstance()
    {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected void getArgumentsData(Bundle bundle, Bundle savedInstanceState)
    {

    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.fragment_test;
    }

    @Override
    protected void initUI(View contentView)
    {
        mRecyclerView = find(R.id.rcv_test);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++)
        {
            list.add("AAAAAAAAA" + i);
        }
        MyAdapter adapter = new MyAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

    private class MyAdapter extends RcvSingleAdapter<String>
    {
        public MyAdapter(Context context, List<String> datas)
        {
            super(context, android.R.layout.simple_list_item_1, datas);
        }

        @Override
        public void onBindView(RcvHolder holder, String itemData, int position)
        {
            holder.setTvText(android.R.id.text1, itemData);
        }
    }
}
