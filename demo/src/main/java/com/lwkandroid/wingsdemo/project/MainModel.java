package com.lwkandroid.wingsdemo.project;

import com.lwkandroid.wingsdemo.bean.TestData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * RxHttpDemoActivity Model层
 *
 * @author LWK
 */

public class MainModel extends MainContract.Model
{

    @Override
    public Observable<List<TestData>> queryTestDataList()
    {
        return Observable.create(new ObservableOnSubscribe<List<TestData>>()
        {
            @Override
            public void subscribe(ObservableEmitter<List<TestData>> emitter) throws Exception
            {
                List<TestData> list = new ArrayList<>();
                for (int i = 0; i < 5; i++)
                {
                    TestData data = new TestData();
                    data.setName("Name" + i);
                    list.add(data);
                }
                emitter.onNext(list);
                emitter.onComplete();
            }
        });
    }

    @Override
    public Observable<String> queryTestData(String name)
    {
        return Observable.just("value" + name);
    }
}
