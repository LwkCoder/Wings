package com.lwkandroid.wingsdemo.project;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;
import com.lwkandroid.wingsdemo.bean.TestData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by LWK
 * TODO RxHttpDemoActivity Presenter层
 */

public class MainPresenter extends MainContract.Presenter
{

    @Override
    public MainContract.Model createModel()
    {
        return new MainModel();
    }

    @Override
    public void queryTestDataList()
    {
        final List<TestData> resultList = new ArrayList<>();
        getModelImpl().queryTestDataList()
                .flatMap(new Function<List<TestData>, ObservableSource<TestData>>()
                {
                    @Override
                    public ObservableSource<TestData> apply(List<TestData> testDatas) throws Exception
                    {
                        return Observable.fromIterable(testDatas);
                    }
                })
                .flatMap(new Function<TestData, ObservableSource<TestData>>()
                {
                    @Override
                    public ObservableSource<TestData> apply(TestData testData) throws Exception
                    {
                        return Observable.just(testData).zipWith(getModelImpl().queryTestData(testData.getName()), new BiFunction<TestData, String, TestData>()
                        {
                            @Override
                            public TestData apply(TestData testData, String s) throws Exception
                            {
                                testData.setValue(s);
                                return testData;
                            }
                        });
                    }
                })
                .compose(this.<TestData>applyComputation2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<TestData>()
                {
                    @Override
                    public void subOnNext(TestData testData)
                    {
                        KLog.e("接收到数据：" + testData.toString());
                        resultList.add(testData);
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        KLog.e("错误" + e.getDisplayMessage());
                    }

                    @Override
                    public void onComplete()
                    {
                        super.onComplete();
                        KLog.e("最终数据：" + resultList.toString());
                    }
                });
    }
}
