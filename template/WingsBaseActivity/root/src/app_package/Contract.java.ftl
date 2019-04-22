package ${packageName};

import com.lwkandroid.wings.mvp.base.IMVPBaseView;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;

/**
 * Description:契约层
 * @author
 * @date
 */
interface ${activityClass}Contract
{
  interface IView extends IMVPBaseView{}
  
  abstract class Model{}
  
  abstract class Presenter extends MVPBasePresenter<IView,Model>{}
}
