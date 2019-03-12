package com.lwkandroid.wings.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.lwkandroid.wings.R;
import com.lwkandroid.wings.utils.AppUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.Setting;

import java.io.File;
import java.util.List;

/**
 * Created by LWK
 * TODO 权限申请弹窗工具类
 */
public final class PermissionDialogUtils
{
    private PermissionDialogUtils()
    {
    }

    /**
     * 被拒绝权限的解释说明弹窗
     */
    public static Rationale<List<String>> showRuntimeRationaleDialog()
    {
        return new DefRuntimeRationaleDialog();
    }

    /**
     * 永不询问的被拒权限的解释说明弹窗
     *
     * @param context        Context
     * @param permissions    被拒权限
     * @param callBackAction 设置界面返回回调
     */
    public static void showSettingIfNeverAskDialog(final Context context, final List<String> permissions, final Setting.Action callBackAction)
    {
        if (!AndPermission.hasAlwaysDeniedPermission(context, permissions))
        {
            return;
        }
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.dialog_runtime_neverask_message,
                AppUtils.getAppName(), TextUtils.join("\n", permissionNames));
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.dialog_permission_title)
                .setMessage(message)
                .setPositiveButton(R.string.dialog_runtime_neverask_process, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        AndPermission.with(context)
                                .runtime()
                                .setting()
                                .onComeback(callBackAction)
                                .start();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 被拒绝安装权限的解释说明弹窗
     */
    public static Rationale<File> showInstallRationaleDialog()
    {
        return new DefInstallRationaleDialog();
    }

    /**
     * 被拒绝悬浮窗权限的解释说明弹窗
     */
    public static Rationale<Void> showOverlayRationaleDialog()
    {
        return new DefOverlayRationaleDialog();
    }
}
