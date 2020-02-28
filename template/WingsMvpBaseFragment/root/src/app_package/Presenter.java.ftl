package ${packageName};

import com.lwkandroid.lib.common.mvp.MvpBasePresenterImpl;

/**
 * Description:Presenter层
 * @author
 * @date
 */
class ${uiClassName}Presenter extends MvpBasePresenterImpl<${uiClassName}Contract.IView,${uiClassName}Contract.IModel>
        implements ${uiClassName}Contract.IPresenter<${uiClassName}Contract.IView,${uiClassName}Contract.IModel>
{
    public ${uiClassName}Presenter(${uiClassName}Contract.IView viewImpl, ${uiClassName}Contract.IModel modelImpl)
    {
        super(viewImpl, modelImpl);
    }
}

