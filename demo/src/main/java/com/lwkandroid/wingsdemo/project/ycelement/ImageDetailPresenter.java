package com.lwkandroid.wingsdemo.project.ycelement;

/**
 * Description:Presenter层
 *
 * @author
 * @date
 */
class ImageDetailPresenter extends ImageDetailContract.Presenter
{
    @Override
    public ImageDetailContract.Model createModel()
    {
        return new ImageDetailModel();
    }
}
