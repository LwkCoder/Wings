package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;


/**
 * Description:View层
 * @author
 * @date
 */
public class ${fragmentClass}Fragment extends AppListFragment<${fragmentClass}Presenter,PTRLayout,${dataClass}> implements 
		${fragmentClass}Contract.IView<${dataClass}> 
{
	/**
     * 创建该Fragment的静态方法
     */
    public static ${fragmentClass}Fragment createInstance()
    {
        ${fragmentClass}Fragment fragment = new ${fragmentClass}Fragment();
        return fragment;
    }

	@Override
    public MVPListOptions setListOptions()
    {
        return new MVPListOptions.Builder()
                .enableRefresh(true)
                .enableLoadMore(true)
                .setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false))
                .build();
    }

	@Override
    public RcvMultiAdapter<${dataClass}> setAdapter()
    {
        return new ${fragmentClass}Adapter(getContext(), null);
    }
	
	@Override
    public IRefreshWrapper<PTRLayout> findRefreshWrapper(MVPListOptions options, View contentView)
    {
        PTRLayout layout = find(R.id.ptr_list);
        return new PTRWrapper(layout);
    }

    @Override
    public RecyclerView findRecyclerView(MVPListOptions options, View contentView)
    {
        return find(R.id.rcv_list);
    }

	@Override
    protected void getArgumentsData(Bundle bundle, Bundle savedInstanceState)
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
