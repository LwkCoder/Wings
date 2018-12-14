package ${packageName};

import com.lwkandroid.wings.mvp.base.IMVPBaseView;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;

/**
 * Created by LWK
 * TODO 契约层
 */
interface ${activityClass}Contract
{
  interface IView extends IMVPBaseView{}
  
  abstract class Model{}
  
  abstract class Presenter extends MVPBasePresenter<IView,Model>{}
}
