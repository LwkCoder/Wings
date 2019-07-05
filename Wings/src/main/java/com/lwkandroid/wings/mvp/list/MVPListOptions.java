package com.lwkandroid.wings.mvp.list;

import com.lwkandroid.wings.utils.Utils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 列表界面配置
 *
 * @author LWK
 */

public class MVPListOptions
{
    //是否允许下拉刷新
    private boolean enableRefresh = true;
    //是否允许加载更多
    private boolean enableLoadMore = true;
    //每页数据量
    private int pageSize = 20;
    //第一页下标
    private int pageStartIndex = 0;
    //RecyclerView的LayoutManger
    private RecyclerView.LayoutManager layoutManager =
            new LinearLayoutManager(Utils.getContext(), RecyclerView.VERTICAL, false);

    public boolean isEnableRefresh()
    {
        return enableRefresh;
    }

    public void setEnableRefresh(boolean enableRefresh)
    {
        this.enableRefresh = enableRefresh;
    }

    public boolean isEnableLoadMore()
    {
        return enableLoadMore;
    }

    public void setEnableLoadMore(boolean enableLoadMore)
    {
        this.enableLoadMore = enableLoadMore;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getPageStartIndex()
    {
        return pageStartIndex;
    }

    public void setPageStartIndex(int pageStartIndex)
    {
        this.pageStartIndex = pageStartIndex;
    }

    public RecyclerView.LayoutManager getLayoutManager()
    {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager)
    {
        this.layoutManager = layoutManager;
    }

    public static class Builder
    {
        private MVPListOptions options;

        public Builder()
        {
            this.options = new MVPListOptions();
        }

        public Builder enableRefresh(boolean enable)
        {
            options.setEnableRefresh(enable);
            return this;
        }

        public Builder enableLoadMore(boolean enable)
        {
            options.setEnableLoadMore(enable);
            return this;
        }

        public Builder setPageSize(int pageSize)
        {
            options.setPageSize(pageSize);
            return this;
        }

        public Builder setPageStartIndex(int pageIndex)
        {
            options.setPageStartIndex(pageIndex);
            return this;
        }

        public Builder setLayoutManager(RecyclerView.LayoutManager layoutManager)
        {
            options.setLayoutManager(layoutManager);
            return this;
        }

        public MVPListOptions build()
        {
            return options;
        }
    }
}
