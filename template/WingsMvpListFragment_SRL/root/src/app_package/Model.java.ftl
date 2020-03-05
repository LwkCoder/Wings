package ${packageName};

import com.lwkandroid.lib.common.mvp.MvpBaseModelImpl;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Observable;

/**
 * Description:Model层
 * @author
 * @date
 */
class ${uiClassName}Model extends MvpBaseModelImpl implements ${uiClassName}Contract.IModel
{
	@Override
    public Observable<List<${dataSourceClass}>> requestData(int pageIndex, int pageSize, long timeStamp, String... args)
    {
        //TODO 实现请求数据的方法
        return null;
    }
}
