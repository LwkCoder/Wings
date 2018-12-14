package ${packageName};


import com.sources.javacode.app.AppBaseModel;
import com.sources.javacode.app.AppBasePresenter;
import com.sources.javacode.app.IAppBaseView;

/**
 * Created by LWK
 * TODO 契约层
 */
interface ${activityClass}Contract
{
  interface IView<D> extends IAppBaseView,IMVPListContract.IViewCommon<D>{}
  
  abstract class Model extends AppBaseModel{}
  
  abstract class Presenter extends AppBasePresenter{}
}
