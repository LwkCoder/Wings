package com.lwkandroid.wings.mvp;

/**
 * MVP模版中Presenter基类
 */
public abstract class BasePresenter<V extends IBaseView, M>
{
    protected V mViewImpl;

    protected M mModelImpl;

    /**
     * Attach to view and create model obj
     */
    public void attachToView(V v)
    {
        this.mViewImpl = v;
        this.mModelImpl = createModel();
    }

    /**
     * Detach from Activity/Fragment
     */
    public void onDestory()
    {
        if (mModelImpl != null)
            mModelImpl = null;
        if (mViewImpl != null)
            mViewImpl = null;
    }

    /**
     * Create and return the model obj
     */
    protected abstract M createModel();
}
