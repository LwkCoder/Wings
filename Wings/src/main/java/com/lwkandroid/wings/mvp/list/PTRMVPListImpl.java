package com.lwkandroid.wings.mvp.list;

import android.view.View;

import com.lwkandroid.wings.R;
import com.lwkandroid.wings.widget.ptr.PTRLayout;

/**
 * Created by LWK
 * TODO PTRLayout列表界面公共部分实现类
 */
public class PTRMVPListImpl<D> extends MVPListImpl<D, PTRLayout>
{
    public PTRMVPListImpl(Listener listener)
    {
        super(listener);
    }

    @Override
    public IRefreshWrapper<PTRLayout> findRefreshView(MVPListOptions options, View contentView)
    {
        PTRLayout layout = contentView.findViewById(R.id.id_common_refresh_view);
        return new PTRWrapper(layout);
    }

}
