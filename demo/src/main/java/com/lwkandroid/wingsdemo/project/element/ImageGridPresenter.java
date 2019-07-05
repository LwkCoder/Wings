package com.lwkandroid.wingsdemo.project.element;

/**
 *  Presenter层
 * @author LWK
 */

public class ImageGridPresenter extends ImageGridContract.Presenter
{
    @Override
    public ImageGridContract.Model createModel()
    {
        return new ImageGridModel();
    }
}
