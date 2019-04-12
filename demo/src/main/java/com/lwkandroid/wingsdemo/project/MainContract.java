package com.lwkandroid.wingsdemo.project;

import com.lwkandroid.wingsdemo.app.AppBaseModel;
import com.lwkandroid.wingsdemo.app.AppBasePresenter;
import com.lwkandroid.wingsdemo.app.IAppBaseView;
import com.lwkandroid.wingsdemo.bean.TestData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO MainActivity契约类
 */

public interface MainContract
{
    interface View extends IAppBaseView
    {
    }

    abstract class Model extends AppBaseModel
    {
        public abstract Observable<List<TestData>> queryTestDataList();

        public abstract Observable<String> queryTestData(String name);
    }

    abstract class Presenter extends AppBasePresenter<View, Model>
    {
        public abstract void queryTestDataList();

        public abstract void test();
    }
}
