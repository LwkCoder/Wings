package ${packageName};

import android.content.Context;
import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.rcvadapter.holder.RcvHolder;
import java.util.List;


/**
 * Description:Adapter
 * @author
 * @date
 */
class ${activityClass}Adapter extends RcvSingleAdapter<${dataClass}>
{
    public ${activityClass}Adapter(Context context, List<${dataClass}> datas)
    {
		//TODO You can change the layout resource
        super(context, R.layout.adapter_item_${activityClass?lower_case}, datas);
    }

    @Override
    public void onBindView(RcvHolder holder, ${dataClass} itemData, int position)
    {
    }
}
