package com.lwkandroid.wingsdemo.project.rxhttp;

import com.lwkandroid.wings.net.RxHttp;
import com.lwkandroid.wings.net.parser.ApiResponseConvert;
import com.lwkandroid.wings.net.utils.FormDataMap;
import com.lwkandroid.wings.net.utils.RetrofitUtils;
import com.lwkandroid.wingsdemo.bean.TabsBean;
import com.lwkandroid.wingsdemo.net.ApiURL;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by LWK
 * TODO RxHttpDemo Model层
 */

public class RxHttpDemoModel extends RxHttpDemoConstract.Model
{
    @Override
    Observable<List<TabsBean>> requestData()
    {
        return RxHttp.GET(ApiURL.TEST)
                .addFormData("webp", "1")
                .addFormData("essence", "1")
                .addFormData("message_cursor", "1")
                .addFormData("double_col_mode", "1")
                .parseAsList(TabsBean.class);
    }

    @Override
    Observable<List<TabsBean>> requestDataByService()
    {
        return RetrofitUtils.createWithGlobalOptions()
                .create(CustomService.class)
                .customGet(ApiURL.TEST, new FormDataMap().addParam("webp", "1"))
                .compose(ApiResponseConvert.responseToString())//先将ResponseBody转为String结果的数据
                .compose(RxHttp.getGlobalOptions().getApiStringParser().parseDataAsList(TabsBean.class));//再将String数据解析为所需数据集合
    }
}
