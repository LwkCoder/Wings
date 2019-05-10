package com.lwkandroid.wingsdemo.project.element;

/**
 * Created by LWK
 *  Presenter层
 */

public class ImageGridPresenter extends ImageGridContract.Presenter
{
    @Override
    public ImageGridContract.Model createModel()
    {
        return new ImageGridModel();
    }
}
