package com.lwkandroid.wingsdemo.project.rxhttp;

import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.AppBaseView;
import com.lwkandroid.wingsdemo.bean.TabsBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO RxHttpDemo契约类
 */

public interface RxHttpDemoConstract
{
    interface View extends AppBaseView
    {
        void setHttpResultData(List<TabsBean> dataList);
    }

    abstract class Model extends AppBaseModel
    {
        /**
         * 获取的数据来自开源接口：https://github.com/jokermonn/-Api/blob/master/Neihan.md#recommend
         */
        abstract Observable<List<TabsBean>> requestData();

        abstract Observable<List<TabsBean>> requestDataByService();
    }

    abstract class Presenter extends AppBasePresenter<View, Model>
    {
        abstract void requestData();

        abstract void requestDataByService();
    }
}
