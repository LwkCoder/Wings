package ${packageName};

import com.lwkandroid.lib.common.mvp.IMvpBaseModel;
import com.lwkandroid.lib.common.mvp.IMvpBasePresenter;
import com.lwkandroid.lib.common.mvp.IMvpBaseView;
import com.lwkandroid.lib.common.mvp.list.ILoadMoreWrapper;
import com.lwkandroid.lib.common.mvp.list.IRefreshWrapper;
import java.util.List;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.rxjava3.core.Observable;

/**
 * Description:契约层
 * @author
 * @date
 */
interface ${uiClassName}Contract
{
  interface IView<P extends LifecycleObserver> extends IMvpBaseView<P>
    {
        boolean isRefreshEnable();

        boolean isLoadMoreEnable();

        IRefreshWrapper getRefreshWrapper();

        ILoadMoreWrapper getLoadMoreWrapper();

        void autoRefresh();

        void onRefreshSuccess(List<${dataSourceClass}> dataList, boolean noMoreData);

        void onRefreshFail(String message, Throwable throwable);

        void onLoadMoreSuccess(List<${dataSourceClass}> dataList);

        void onLoadMoreFail(String message, Throwable throwable);

        void onLoadMoreNoMoreData();
    }

    interface IModel extends IMvpBaseModel
    {
        Observable<List<${dataSourceClass}>> requestData(int pageIndex, int pageSize, long timeStamp, Object... params);
    }

    interface IPresenter<V extends LifecycleOwner, M> extends IMvpBasePresenter<V, M>
    {
        void requestRefresh(Object... params);

        void requestLoadMore(Object... params);
    }
}
