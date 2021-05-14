package ${packageName};

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;
import com.lwkandroid.lib.core.net.bean.ApiException;
import com.lwkandroid.lib.core.net.observer.ApiObserver;

import java.util.List;

/**
 * Description:Presenter层
 * @author
 * @date
 */
class ${uiClassName}Presenter extends MvpBasePresenterImpl<${uiClassName}Contract.IView,${uiClassName}Contract.IModel>
        implements ${uiClassName}Contract.IPresenter<${uiClassName}Contract.IView,${uiClassName}Contract.IModel>
{
	/**
     * 默认起始页码
     */
    private final int PAGE_INDEX_DEFAULT = 1;
    /**
     * 每页请求数量
     */
    private final int PAGE_SIZE = 20;
    /**
     * 当前页码
     */
    private int mCurrentIndex = PAGE_INDEX_DEFAULT;
	
    public ${uiClassName}Presenter(${uiClassName}Contract.IView viewImpl, ${uiClassName}Contract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
	
	@Override
    public void requestRefresh(Object... args)
    {
        //下拉刷新
        getModelImpl()
                .requestData(PAGE_INDEX_DEFAULT, PAGE_SIZE, System.currentTimeMillis(), args)
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiObserver<List<${dataSourceClass}>>()
                {
                    @Override
                    public void onAccept(List<${dataSourceClass}> list)
                    {
                        //刷新成功后重置页码
                        mCurrentIndex = PAGE_INDEX_DEFAULT;
                        getViewImpl().onRefreshSuccess(list, list.size() < PAGE_SIZE);
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        getViewImpl().onRefreshFail(e.getDisplayMessage(), e);
                    }
                });

    }

    @Override
    public void requestLoadMore(Object... args)
    {
        //自动加载
        final int nextPageIndex = mCurrentIndex + 1;
        getModelImpl()
                .requestData(nextPageIndex, PAGE_SIZE, System.currentTimeMillis(), args)
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiObserver<List<${dataSourceClass}>>()
                {
                    @Override
                    public void onAccept(List<${dataSourceClass}> list)
                    {
                        //同步页码
                        mCurrentIndex = nextPageIndex;
                        getViewImpl().onLoadMoreSuccess(list);
                        if (list == null || list.size() < PAGE_SIZE)
                        {
                            getViewImpl().onLoadMoreNoMoreData();
                        }
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        getViewImpl().onLoadMoreFail(e.getDisplayMessage(), e);
                    }
                });
    }
}
