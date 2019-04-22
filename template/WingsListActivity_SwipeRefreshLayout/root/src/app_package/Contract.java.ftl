package ${packageName};

import com.lwkandroid.wings.mvp.list.IMVPListContract;
import com.lwkandroid.wings.mvp.base.IMVPBaseView;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;

/**
 * Description:契约层
 * @author
 * @date
 */
interface ${activityClass}Contract
{
  interface IView<D> extends IMVPBaseView,IMVPListContract.IViewCommon<D>{}
  
  abstract class Model{}
  
  abstract class Presenter extends MVPBasePresenter<IView,Model>{}
}
