package com.lwkandroid.wingsdemo.project.list;

import com.lwkandroid.wings.mvp.list.IMVPListContract;
import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.IAppBaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 */

public interface ListDemoContract
{
    interface IView<D> extends IAppBaseView, IMVPListContract.IViewCommon<D>
    {
    }

    abstract class Model extends AppBaseModel
    {
        abstract Observable<List<String>> getRefreshData(long timeStamp, int pageIndex, int pageSize);

        abstract Observable<List<String>> getLoadMoreData(long timeStamp, int pageIndex, int pageSize, int currentDataCount);
    }

    abstract class Presenter extends AppBasePresenter<IView, Model>
    {
        abstract void doRefresh(long timeStamp, int pageIndex, int pageSize);

        abstract void doLoadMore(long timeStamp, int pageIndex, int pageSize, int currentDataCount);
    }
}
