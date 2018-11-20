package com.lwkandroid.wingsdemo.project.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.wings.mvp.list.MVPListOptions;
import com.lwkandroid.wings.widget.ptr.PTRDefaultRefreshHeader;
import com.lwkandroid.wingsdemo.R;
import com.lwkandroid.wingsdemo.app.AppListActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by LWK
 * TODO 列表Demo
 */

public class ListDemoActivity extends AppListActivity<ListDemoPresenter, String> implements
        ListDemoContract.IView<String>
{
    @Override
    public MVPListOptions setListOptions()
    {
        return new MVPListOptions.Builder()
                .enableRefresh(true)
                .enableLoadMore(true)
                .setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false))
                .build();
    }

    @Override
    public RcvMultiAdapter<String> setAdapter()
    {
        TextView emptyHolder = new TextView(this);
        emptyHolder.setText("暂时没有数据~~~");
        emptyHolder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ListDemoAdapter adapter = new ListDemoAdapter(this, null);
        adapter.setEmptyView(emptyHolder);
        return adapter;
    }

    @Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {
    }

    @Override
    protected int getContentViewId()
    {
        return R.layout.activity_list_demo;
    }


    @Override
    protected void _initUI(View contentView)
    {
        PTRDefaultRefreshHeader refreshView = new PTRDefaultRefreshHeader(this);
        refreshView.setColorSchemeResources(R.color.deep_darker, R.color.colorAccent);
        getRefreshWrapper().getRefreshView().setRefreshHeader(refreshView);
    }

    @Override
    protected void initData(Bundle savedInstanceState)
    {
        super.initData(savedInstanceState);
    }

    @Override
    public void onClick(int id, View view)
    {

    }

    @Override
    public void doRefresh(long timeStamp, int pageIndex, int pageSize)
    {
        getPresenter().doRefresh(timeStamp, pageIndex, pageSize);
    }

    @Override
    public void doLoadMore(long timeStamp, int pageIndex, int pageSize, int currentDataCount)
    {
        getPresenter().doLoadMore(timeStamp, pageIndex, pageSize, currentDataCount);
    }
}
