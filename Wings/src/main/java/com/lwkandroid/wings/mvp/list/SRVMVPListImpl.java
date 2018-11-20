package com.lwkandroid.wings.mvp.list;

import android.view.View;

import com.lwkandroid.wings.R;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Created by LWK
 * TODO SwipeRefreshLayout列表界面公共部分实现类
 */
public class SRVMVPListImpl<D> extends MVPListImpl<D, SwipeRefreshLayout>
{
    public SRVMVPListImpl(Listener listener)
    {
        super(listener);
    }

    @Override
    public IRefreshWrapper<SwipeRefreshLayout> findRefreshView(MVPListOptions options, View contentView)
    {
        SwipeRefreshLayout layout = contentView.findViewById(R.id.id_common_refresh_view);
        return new SRVWrapper(layout);
    }
}
