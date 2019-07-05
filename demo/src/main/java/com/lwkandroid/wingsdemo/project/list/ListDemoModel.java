package com.lwkandroid.wingsdemo.project.list;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author LWK
 */

public class ListDemoModel extends ListDemoContract.Model
{
    @Override
    Observable<List<String>> getRefreshData(long timeStamp, int pageIndex, final int pageSize)
    {
        return Observable.create(new ObservableOnSubscribe<List<String>>()
        {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception
            {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < pageSize; i++)
                {
                    data.add("Data" + i);
                }
                emitter.onNext(data);
                emitter.onComplete();
            }
        });
    }

    @Override
    Observable<List<String>> getLoadMoreData(long timeStamp, final int pageIndex, final int pageSize, int currentDataCount)
    {
        return Observable.create(new ObservableOnSubscribe<List<String>>()
        {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception
            {

                List<String> data = new ArrayList<>();
                for (int i = 0; i < (pageIndex == 3 ? pageSize / 2 : pageSize); i++)
                {
                    data.add("NewData" + i);
                }
                emitter.onNext(data);
                emitter.onComplete();
            }
        });
    }
}
