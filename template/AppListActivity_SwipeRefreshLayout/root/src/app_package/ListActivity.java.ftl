package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


/**
 * Description:View层
 * @author
 * @date
 */
public class ${activityClass}Activity extends AppListActivity<${activityClass}Presenter,SwipeRefreshLayout,${dataClass}> implements 
		${activityClass}Contract.IView<${dataClass}> 
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
        return new ${activityClass}Adapter(this, null);
    }
	
	@Override
    public IRefreshWrapper<SwipeRefreshLayout> findRefreshWrapper(MVPListOptions options, View contentView)
    {
        SwipeRefreshLayout layout = find(R.id.ptr_list);
        return new SRVWrapper(layout);
    }

    @Override
    public RecyclerView findRecyclerView(MVPListOptions options, View contentView)
    {
       return find(R.id.rcv_list);
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
    protected void subInitUI(View contentView)
    {
		
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {
		super.initData(savedInstanceState);
    }
	
	@Override
    public void doRefresh(long timeStamp, int pageIndex, int pageSize)
    {
        //TODO Make presenter do refresh operation
    }

    @Override
    public void doLoadMore(long timeStamp, int pageIndex, int pageSize, int currentDataCount)
    {
		//TODO Make presenter do load more operation
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
	
}
