package com.lwkandroid.wingsdemo.project.ycelement;

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
