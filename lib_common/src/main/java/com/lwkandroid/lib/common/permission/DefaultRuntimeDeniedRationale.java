package com.lwkandroid.lib.common.permission;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.DialogBaseContentView;
import com.lwkandroid.lib.common.widgets.dialog.DialogCreator;
import com.lwkandroid.lib.common.widgets.dialog.DialogOptions;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.utils.AppUtils;
import com.lwkandroid.lib.core.utils.ResourceUtils;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import androidx.fragment.app.FragmentActivity;

/**
 * Description:默认运行时权限拒绝后解释性弹窗
 *
 * @author LWK
 * @date 2020/3/11
 */
public class DefaultRuntimeDeniedRationale implements Rationale<List<String>>
{
    @Override
    public void showRationale(Context context, List<String> data, RequestExecutor executor)
    {
        String content = ResourceUtils.getString(R.string.dialog_runtime_rationale_message,
                AppUtils.getAppName(), TextUtils.join("\n", Permission.transformText(context, data)));
        DialogCreator.create(new DialogContent(content))
                .addOnChildClickListener(R.id.tv_dialog_permission_template_cancel, (viewId, view, contentView, creator) -> {
                    creator.dismiss();
                    executor.cancel();
                })
                .addOnChildClickListener(R.id.tv_dialog_permission_template_confirm, (viewId, view, contentView, creator) -> {
                    creator.dismiss();
                    executor.execute();
                })
                .setCancelable(false)
                .setDarkWindowDegree(0.1f)
                .setCanceledOnTouchOutside(false)
                .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
    }

    private class DialogContent extends DialogBaseContentView
    {
        private String content;

        public DialogContent(String content)
        {
            this.content = content;
        }

        @Override
        public int getContentViewLayoutResId()
        {
            return R.layout.dialog_permission_template;
        }

        @Override
        public void initUIAndData(View contentView, DialogOptions options, DialogCreator creator)
        {
            TextView tvTitle = contentView.findViewById(R.id.tv_dialog_permission_template_title);
            TextView tvContent = contentView.findViewById(R.id.tv_dialog_permission_template_content);
            TextView tvCancel = contentView.findViewById(R.id.tv_dialog_permission_template_cancel);
            TextView tvConfirm = contentView.findViewById(R.id.tv_dialog_permission_template_confirm);

            tvTitle.setText(R.string.dialog_permission_title);
            tvContent.setText(content);
            tvConfirm.setText(R.string.dialog_permission_auth);
            tvCancel.setText(R.string.cancel);
        }
    }
}
