package ${packageName};

import ${superClassFqcn};
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.sources.javacode.R;
import com.lwkandroid.lib.common.mvp.MvpBaseActivity;
import com.lwkandroid.lib.core.annotation.ClickViews;
import com.lwkandroid.lib.core.annotation.ViewInjector;

import androidx.annotation.Nullable;

/**
 * Description:View层
 * @author
 * @date
 */
public class ${uiClassName}Activity extends MvpBaseActivity<${uiClassName}Presenter> implements ${uiClassName}Contract.IView<${uiClassName}Presenter> {
	
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
		ViewInjector.with(this);
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState)
    {

    }

    @Override
    @ClickViews(values = {})
    public void onClick(int id, View view)
    {
		switch (id)
        {
            default:
                break;
        }
    }
	
}
