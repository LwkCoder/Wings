package com.lwkandroid.wings.net.convert;

import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;

/**
 * Created by LWK
 * TODO 将网络请求结果得到的ResonpseBody转为其他对象的接口
 */

public interface IApiResponseConvert<O>
{
    ObservableTransformer<ResponseBody, O> convert();
}
