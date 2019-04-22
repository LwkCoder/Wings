package com.lwkandroid.wingsdemo.project;

import com.lwkandroid.wings.log.KLog;
import com.lwkandroid.wings.net.bean.ApiException;
import com.lwkandroid.wings.net.observer.ApiBaseObserver;

import io.reactivex.Observable;

/**
 * Created by LWK
 * RxHttpDemoActivity Presenter层
 *
 * @author IED_WENKANG
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
    }

    @Override
    public void test()
    {
        String[] array = new String[]{"AAA", "BBB", null, "CCC"};
        Observable.fromArray(array)
                .onErrorResumeNext(Observable.<String>empty())
                .compose(this.<String>applyIo2MainWithLifeCycle())
                .subscribe(new ApiBaseObserver<String>()
                {
                    @Override
                    public void subOnNext(String s)
                    {
                        KLog.e("呵呵：" + s);
                    }

                    @Override
                    public void subOnError(ApiException e)
                    {
                        KLog.e("Error:" + e.getDisplayMessage());
                    }

                    @Override
                    public void onComplete()
                    {
                        super.onComplete();
                        KLog.e("完成");
                    }
                });
    }

}
