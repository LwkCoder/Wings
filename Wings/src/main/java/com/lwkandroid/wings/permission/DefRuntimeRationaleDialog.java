package com.lwkandroid.wings.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.lwkandroid.wings.R;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

/**
 * Created by LWK
 * TODO 默认运行时权限被拒提示框
 */
class DefRuntimeRationaleDialog implements Rationale<List<String>>
{
    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor)
    {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = ResourceUtils.getString(R.string.dialog_runtime_rationale_message,
                AppUtils.getAppName(), TextUtils.join("\n", permissionNames));
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.dialog_permission_title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_runtime_rationale_process, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        executor.execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        executor.cancel();
                    }
                })
                .show();
    }
}
