package com.lwkandroid.demo.rxhttp;

import com.lwkandroid.lib.common.mvp.MvpBaseModelImpl;
import com.lwkandroid.lib.core.net.RxHttp;

import io.reactivex.rxjava3.core.Observable;


/**
 * Description:Model层
 *
 * @author
 * @date
 */
class RxHttpModel extends MvpBaseModelImpl implements RxHttpContract.IModel
{
    @Override
    public Observable<String> test01()
    {
        return RxHttp.POST(AppUrl.TEST01)
                .addHeader("access_token", AppTokenModel.getInstance().getAccessToken())
                .parseRestfulObject(String.class);
    }

    @Override
    public Observable<String> test02()
    {
        //模拟的操作
        //正常来讲自动刷新AccessToken后请求就会正常，
        //由于这里返回的是固定的数据，所以界面上还是提示密钥过期
        return RxHttp.POST(AppUrl.TEST02)
                .addHeader("access_token", AppTokenModel.getInstance().getAccessToken())
                .parseRestfulObject(String.class);
    }
}
