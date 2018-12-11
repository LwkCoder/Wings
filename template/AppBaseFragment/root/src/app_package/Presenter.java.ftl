package ${packageName};

/**
 * Created by LWK
 * TODO Presenter层
 */
class Presenter extends Contract.Presenter
{
    @Override
    public Contract.Model createModel()
    {
        return new Model();
    }
}
