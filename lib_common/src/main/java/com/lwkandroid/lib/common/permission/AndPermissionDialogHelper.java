package com.lwkandroid.lib.common.permission;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.Rationale;

import java.io.File;
import java.util.List;

/**
 * Description:AndPermission权限弹窗帮助类
 *
 * @author LWK
 * @date 2020/3/11
 */
public class AndPermissionDialogHelper
{
    private AndPermissionDialogHelper()
    {
    }

    public static final int REQUEST_CODE_SETTING = 100;

    private static Rationale<List<String>> mRuntimeDeniedRationale;

    private static Action<List<String>> mRuntimeDeniedAction;

    private static Rationale<File> mInstallDeniedRationale;

    private static Action<File> mInstallDeniedAction;

    private static Rationale<Void> mOverlayDeniedRationale;

    private static Action<Void> mOverlayDeniedAction;

    private static Rationale<Void> mNotificationShowDeniedRationale;

    private static Action<Void> mNotificationShowDeniedAction;

    private static Rationale<Void> mNotificationAccessDeniedRationale;

    private static Action<Void> mNotificationAccessDeniedAction;

    public static Rationale<List<String>> getRuntimeDeniedRationale()
    {
        if (mRuntimeDeniedRationale == null)
        {
            mRuntimeDeniedRationale = new DefaultRuntimeDeniedRationale();
        }
        return mRuntimeDeniedRationale;
    }

    public static void setRuntimeDeniedRationale(Rationale<List<String>> rationale)
    {
        AndPermissionDialogHelper.mRuntimeDeniedRationale = rationale;
    }

    public static Action<List<String>> getRuntimeDeniedAction()
    {
        if (mRuntimeDeniedAction == null)
        {
            mRuntimeDeniedAction = new DefaultRuntimeDeniedAction();
        }
        return mRuntimeDeniedAction;
    }

    public static void setRuntimeDeniedAction(Action<List<String>> action)
    {
        AndPermissionDialogHelper.mRuntimeDeniedAction = action;
    }

    public static Rationale<File> getInstallDeniedRationale()
    {
        if (mInstallDeniedRationale == null)
        {
            mInstallDeniedRationale = new DefaultInstallDeniedRationale();
        }
        return mInstallDeniedRationale;
    }

    public static void setInstallDeniedRationale(Rationale<File> rationale)
    {
        AndPermissionDialogHelper.mInstallDeniedRationale = rationale;
    }

    public static Action<File> getInstallDeniedAction()
    {
        if (mInstallDeniedAction == null)
        {
            mInstallDeniedAction = new DefaultInstallDeniedAction();
        }
        return mInstallDeniedAction;
    }

    public static void setInstallDeniedAction(Action<File> action)
    {
        AndPermissionDialogHelper.mInstallDeniedAction = action;
    }

    public static Rationale<Void> getOverlayDeniedRationale()
    {
        if (mOverlayDeniedRationale == null)
        {
            mOverlayDeniedRationale = new DefaultOverlayDeniedRationale();
        }
        return mOverlayDeniedRationale;
    }

    public static void setOverlayDeniedRationale(Rationale<Void> rationale)
    {
        AndPermissionDialogHelper.mOverlayDeniedRationale = rationale;
    }

    public static Action<Void> getOverlayDeniedAction()
    {
        if (mOverlayDeniedAction == null)
        {
            mOverlayDeniedAction = new DefaultOverlayDeniedAction();
        }
        return mOverlayDeniedAction;
    }

    public static void setOverlayDeniedAction(Action<Void> action)
    {
        AndPermissionDialogHelper.mOverlayDeniedAction = action;
    }

    public static Rationale<Void> getNotificationShowDeniedRationale()
    {
        if (mNotificationShowDeniedRationale == null)
        {
            mNotificationShowDeniedRationale = new DefaultShowNotificationDeniedRationale();
        }
        return mNotificationShowDeniedRationale;
    }

    public static void setNotificationShowDeniedRationale(Rationale<Void> rationale)
    {
        AndPermissionDialogHelper.mNotificationShowDeniedRationale = rationale;
    }

    public static Action<Void> getNotificationShowDeniedAction()
    {
        if (mNotificationShowDeniedAction == null)
        {
            mNotificationShowDeniedAction = new DefaultShowNotificationDeniedAction();
        }
        return mNotificationShowDeniedAction;
    }

    public static void setNotificationShowDeniedAction(Action<Void> action)
    {
        AndPermissionDialogHelper.mNotificationShowDeniedAction = action;
    }

    public static Rationale<Void> getNotificationAccessDeniedRationale()
    {
        if (mNotificationAccessDeniedRationale == null)
        {
            mNotificationAccessDeniedRationale = new DefaultAccessNotificationDeniedRationale();
        }
        return mNotificationAccessDeniedRationale;
    }

    public static void setNotificationAccessDeniedRationale(Rationale<Void> rationale)
    {
        AndPermissionDialogHelper.mNotificationAccessDeniedRationale = rationale;
    }

    public static Action<Void> getNotificationAccessDeniedAction()
    {
        if (mNotificationAccessDeniedAction == null)
        {
            mNotificationAccessDeniedAction = new DefaultAccessNotificationDeniedAction();
        }
        return mNotificationAccessDeniedAction;
    }

    public static void setNotificationAccessDeniedAction(Action<Void> action)
    {
        AndPermissionDialogHelper.mNotificationAccessDeniedAction = action;
    }
}
