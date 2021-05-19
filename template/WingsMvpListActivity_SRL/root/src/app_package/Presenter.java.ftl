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
    public void requestPageData(boolean isRefresh, Object[] params)
    {
        int nextPageIndex = isRefresh ? PAGE_INDEX_DEFAULT : mCurrentIndex + 1;
        getModelImpl()
                .requestPageData(nextPageIndex, PAGE_SIZE, System.currentTimeMillis(), params)
                .compose(applyIo2MainUntilOnDestroy())
                .subscribe(new ApiObserver<List<${dataSourceClass}>>()
                {
                    @Override
                    public void onAccept(List<${dataSourceClass}> list)
                    {
                        mCurrentIndex = nextPageIndex;
                        getViewImpl().onPageRequestSuccess(isRefresh, list, list != null && list.size() >= PAGE_SIZE);
                    }

                    @Override
                    public void onError(ApiException e)
                    {
                        getViewImpl().onPageRequestFail(isRefresh, e.getDisplayMessage(), e);
                    }
                });
    }

}
