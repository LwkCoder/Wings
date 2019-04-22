package ${packageName};


import com.sources.javacode.app.AppBaseModel;
import com.sources.javacode.app.AppBasePresenter;
import com.sources.javacode.app.IAppBaseView;

/**
 * Description:契约层
 * @author
 * @date
 */
interface ${fragmentClass}Contract
{
  interface IView<D> extends IAppBaseView,IMVPListContract.IViewCommon<D>{}
  
  abstract class Model extends AppBaseModel{}
  
  abstract class Presenter extends AppBasePresenter<IView,Model>{}
}
