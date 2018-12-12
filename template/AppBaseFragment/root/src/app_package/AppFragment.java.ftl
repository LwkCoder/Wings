package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created by LWK
 * TODO View层
 */
public class ${className}Fragment extends AppBaseFragment<${className}Presenter> implements ${className}Contract.IView {

	@Override
    protected void getArgumentsData(Bundle bundle, Bundle savedInstanceState)
    {

    }

    @Override
    protected int getContentViewId()
    {
		<#if includeLayout>
		return R.layout.${fragmentName};
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

    }
	
}
