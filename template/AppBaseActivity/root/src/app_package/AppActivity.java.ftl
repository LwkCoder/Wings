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
public class ${activityClass}Activity extends AppBaseActivity<${activityClass}Presenter> implements ${activityClass}Contract.IView {

	@Override
    protected void getIntentData(Intent intent, boolean newIntent)
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
