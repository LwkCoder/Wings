package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;

import androidx.annotation.Nullable;

/**
 * Description:View层
 * @author
 * @date
 */
public class ${uiClassName}Activity extends MvpBaseActivity<${uiClassName}Presenter> implements ${uiClassName}Contract.IView<${uiClassName}Presenter> {

	@Override
    protected void setBarColor()
    {
        
    }
	
	@Override
    protected ${uiClassName}Presenter createPresenter()
    {
        return new ${uiClassName}Presenter(this, new ${uiClassName}Model());
    }


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
