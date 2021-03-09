package ${packageName};

import android.content.Context;
import com.sources.javacode.R;
import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.rcvadapter.holder.RcvHolder;

/**
 * Description:适配器
 *
 * @author 
 * @date 
 */
public class ${uiClassName}Adapter extends RcvSingleAdapter<${dataSourceClass}>
{
    public ${uiClassName}Adapter(Context context)
    {
        super(context, R.layout.adapter_item_${uiClassName?lower_case}, null);
    }

    @Override
    public void onBindView(RcvHolder holder, ${dataSourceClass} itemData, int position)
    {
        
    }
}

