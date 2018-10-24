package com.lwkandroid.wings.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.lwkandroid.wings.R;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.io.File;

/**
 * Created by LWK
 * TODO 默认安装Apk权限提示框
 */
class DefInstallRationaleDialog implements Rationale<File>
{
    @Override
    public void showRationale(Context context, final File data, final RequestExecutor executor)
    {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.dialog_runtime_rationale_message)
                .setMessage(ResourceUtils.getString(R.string.dialog_install_rationale_message, AppUtils.getAppName()))
                .setPositiveButton(R.string.dialog_install_rationale_process, new DialogInterface.OnClickListener()
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
