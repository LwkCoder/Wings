package com.lwkandroid.demo2.test;

import com.lwkandroid.lib.common.mvp.MvpBaseModelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Description:Model层
 *
 * @author
 * @date
 */
class TestModel extends MvpBaseModelImpl implements TestContract.IModel
{
    @Override
    public Observable<List<String>> requestData(int pageIndex, int pageSize, long timeStamp, String... args)
    {
        //TODO 实现请求数据的方法
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 25; i++)
        {
            dataList.add("Data" + i);
        }
        return Observable.timer(2000, TimeUnit.MILLISECONDS)
                .map(aLong -> dataList);
    }
}
