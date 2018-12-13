package ${packageName};


import com.sources.javacode.app.AppBaseModel;
import com.sources.javacode.app.AppBasePresenter;
import com.sources.javacode.app.IAppBaseView;

/**
 * Created by LWK
 * TODO 契约层
 */
interface ${fragmentClass}Contract
{
  interface IView<${fragmentClass}> extends IAppBaseView,IMVPListContract.IViewCommon<${fragmentClass}>{}
  
  abstract class Model extends AppBaseModel{}
  
  abstract class Presenter extends AppBasePresenter{}
}
