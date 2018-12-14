package ${packageName};

import com.lwkandroid.wings.mvp.list.IMVPListContract;
import com.lwkandroid.wings.mvp.base.IMVPBaseView;
import com.lwkandroid.wings.mvp.base.MVPBasePresenter;

/**
 * Created by LWK
 * TODO 契约层
 */
interface ${fragmentClass}Contract
{
  interface IView<D> extends IMVPBaseView,IMVPListContract.IViewCommon<D>{}
  
  abstract class Model{}
  
  abstract class Presenter extends MVPBasePresenter<IView,Model>{}
}
