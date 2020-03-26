package com.lwkandroid.lib.common.permission;

import android.content.Context;

import com.lwkandroid.lib.common.R;
import com.lwkandroid.lib.common.widgets.dialog.DialogCreator;
import com.lwkandroid.lib.common.widgets.ui.CommonDialogContent;
import com.lwkandroid.lib.core.app.ActivityLifecycleHelper;
import com.lwkandroid.lib.core.utils.AppUtils;
import com.lwkandroid.lib.core.utils.ResourceUtils;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.io.File;

import androidx.fragment.app.FragmentActivity;

/**
 * Description:安装应用权限拒绝后解释性弹窗
 *
 * @author LWK
 * @date 2020/3/11
 */
public class DefaultInstallDeniedRationale implements Rationale<File>
{
    @Override
    public void showRationale(Context context, File data, RequestExecutor executor)
    {
        String content = ResourceUtils.getString(R.string.dialog_install_rationale_message, AppUtils.getAppName());
        DialogCreator.create(new CommonDialogContent()
                .setTitle(R.string.dialog_permission_title)
                .setContent(content)
                .setPositive(R.string.dialog_permission_auth)
                .setNegative(R.string.cancel))
                .addOnChildClickListener(R.id.tv_dialog_template_negative, (viewId, view, contentView, creator) -> {
                    creator.dismiss();
                    executor.cancel();
                })
                .addOnChildClickListener(R.id.tv_dialog_template_positive, (viewId, view, contentView, creator) -> {
                    creator.dismiss();
                    executor.execute();
                })
                .setCancelable(false)
                .setDarkWindowDegree(0.1f)
                .setCanceledOnTouchOutside(false)
                .show((FragmentActivity) ActivityLifecycleHelper.get().getTopActivity());
    }
}
