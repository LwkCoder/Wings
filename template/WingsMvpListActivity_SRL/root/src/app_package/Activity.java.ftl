package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import ${applicationPackage}.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.common.mvp.list.ILoadMoreWrapper;
import com.lwkandroid.lib.common.mvp.list.IRefreshWrapper;
import com.lwkandroid.lib.common.mvp.list.RcvLoadMoreWrapper;
import com.lwkandroid.lib.common.mvp.list.SwipeRefreshLayoutWrapper;
import com.lwkandroid.rcvadapter.RcvMultiAdapter;
import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.ViewInjector;
import com.lwkandroid.lib.core.annotation.FindView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * Description:View层
 * @author
 * @date
 */
public class ${uiClassName}Activity extends MvpBaseActivity<${uiClassName}Presenter> implements ${uiClassName}Contract.IView<${uiClassName}Presenter>,
        IRefreshWrapper.OnRefreshRequestedListener, ILoadMoreWrapper.OnLoadMoreRequestedListener 
{
	private IRefreshWrapper<SwipeRefreshLayout> mRefreshWrapper;
    private ILoadMoreWrapper<RcvMultiAdapter> mLoadMoreWrapper;
	@FindView(R.id.recyclerView)
    private RecyclerView mRecyclerView;
    private RcvSingleAdapter<${dataSourceClass}> mAdapter;
	
	@Override
    protected ${uiClassName}Presenter createPresenter()
    {
        return new ${uiClassName}Presenter(this, new ${uiClassName}Model());
    }


	@Override
    protected void getIntentData(Intent intent, boolean newIntent)
    {

    }

    @Override
    protected int getContentViewId()
    {
		<#if generateLayout>
		return R.layout.${layoutName};
		<#else>	
		return 0;
		</#if>
    }
	
	@Override
    protected void initUI(View contentView)
    {
		ViewInjector.with(this);
		//关联刷新控件，由包装类代理
        mRefreshWrapper = new SwipeRefreshLayoutWrapper(find(R.id.swipeRefreshLayout));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mAdapter = new ${uiClassName}Adapter(this);
        //关联自动加载，由包装类代理
        mLoadMoreWrapper = new RcvLoadMoreWrapper(mAdapter);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
		//默认开启下拉刷新功能
        mRefreshWrapper.enableRefresh(isRefreshEnable());
        mRefreshWrapper.setOnRefreshRequestedListener(this);
        //默认自动刷新一次
        autoRefresh();
    }

    @Override
	@ClickViews(values = {})
    public void onClick(int id, View view)
    {
        switch (id)
        {
            default:
                break;
        }
    }

    @Override
    public boolean isRefreshEnable()
    {
        return true;
    }

    @Override
    public boolean isLoadMoreEnable()
    {
        return true;
    }

    @Override
    public IRefreshWrapper getRefreshWrapper()
    {
        return mRefreshWrapper;
    }

    @Override
    public ILoadMoreWrapper getLoadMoreWrapper()
    {
        return mLoadMoreWrapper;
    }

    @Override
    public void autoRefresh()
    {
        mRefreshWrapper.autoRefresh();
    }

    @Override
    public void onRefreshRequested()
    {
        //下拉刷新的时候禁止自动加载
        mLoadMoreWrapper.enableLoadMore(false);
        getPresenter().requestRefresh();
    }

    @Override
    public void onRefreshSuccess(List<${dataSourceClass}> dataList, boolean noMoreData)
    {
        mRefreshWrapper.callRefreshSuccess();
        mAdapter.refreshDatas(dataList);
        //配置自动加载
        if (isLoadMoreEnable())
        {
            mLoadMoreWrapper.enableLoadMore(true);
            if (noMoreData)
            {
                mLoadMoreWrapper.setOnLoadMoreRequestedListener(null);
                mLoadMoreWrapper.callLoadMoreNoMoreData();
            } else
            {
                mLoadMoreWrapper.setOnLoadMoreRequestedListener(this);
            }
        } else
        {
            mLoadMoreWrapper.enableLoadMore(false);
            mLoadMoreWrapper.setOnLoadMoreRequestedListener(null);
        }
    }

    @Override
    public void onRefreshFail(String message, Throwable throwable)
    {
        mRefreshWrapper.callRefreshFail(throwable);
        //去做具体提示
        showShortToast(message);
    }

    @Override
    public void onLoadMoreRequested()
    {
        getPresenter().requestLoadMore();
    }

    @Override
    public void onLoadMoreSuccess(List<${dataSourceClass}> dataList)
    {
        mLoadMoreWrapper.callLoadMoreSuccess();
        //由于自动加载是Adapter实现的，没有只显示状态的方法
        //所以这里需要处理加载后的数据
        mAdapter.notifyLoadMoreSuccess(dataList, true);
    }

    @Override
    public void onLoadMoreFail(String message, Throwable throwable)
    {
        mLoadMoreWrapper.callLoadMoreFail(throwable);
        //去做具体提示
        showShortToast(message);
    }

    @Override
    public void onLoadMoreNoMoreData()
    {
        mLoadMoreWrapper.callLoadMoreNoMoreData();
    }
}
