package ${packageName};

import android.content.Context;
import com.lwkandroid.rcvadapter.RcvSingleAdapter;
import com.lwkandroid.rcvadapter.holder.RcvHolder;
import java.util.List;


/**
 * Created by LWK
 * TODO Adapter
 */
class ${fragmentClass}Adapter extends RcvSingleAdapter<${dataClass}>
{
    public ${fragmentClass}Adapter(Context context, List<${dataClass}> datas)
    {
		//TODO You can change the layout resource
        super(context, R.layout.adapter_item_${fragmentClass?lower_case}, datas);
    }

    @Override
    public void onBindView(RcvHolder holder, ${dataClass} itemData, int position)
    {
    }
}
