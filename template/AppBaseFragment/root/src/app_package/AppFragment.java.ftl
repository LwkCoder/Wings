package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Description:View层
 * @author
 * @date
 */
public class ${fragmentClass}Fragment extends AppBaseFragment<${fragmentClass}Presenter> implements ${fragmentClass}Contract.IView {

	/**
     * 创建该Fragment的静态方法
     */
    public static ${fragmentClass}Fragment createInstance()
    {
        ${fragmentClass}Fragment fragment = new ${fragmentClass}Fragment();
        return fragment;
    }

	@Override
    protected void getArgumentsData(Bundle bundle, Bundle savedInstanceState)
    {

    }

    @Override
    protected int getContentViewId()
    {
		<#if generateLayout>
		return R.layout.${layoutName};
		<#else>	
		return 0;
		</#if>
    }
	
	@Override
    protected void initUI(View contentView)
    {

    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    public void onClick(int id, View view)
    {
		switch (id)
        {
            default:
                break;
        }
    }
	
}
