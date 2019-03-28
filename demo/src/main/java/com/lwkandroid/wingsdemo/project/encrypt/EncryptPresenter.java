package com.lwkandroid.wingsdemo.project.encrypt;

/**
 * Created by LWK
 * TODO Presenter层
 */
class EncryptPresenter extends EncryptContract.Presenter
{
    @Override
    public EncryptContract.Model createModel()
    {
        return new EncryptModel();
    }
}
