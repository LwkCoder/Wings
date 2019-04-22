/*
 * Copyright 2018 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lwkandroid.wings.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.lwkandroid.wings.R;
import com.lwkandroid.wings.utils.AppUtils;
import com.lwkandroid.wings.utils.ResourceUtils;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

/**
 * Created by LWK
 *  默认悬浮窗权限被拒提示框
 */
class DefOverlayRationaleDialog implements Rationale<Void>
{
    @Override
    public void showRationale(Context context, final Void data, final RequestExecutor executor)
    {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.dialog_permission_title)
                .setMessage(ResourceUtils.getString(R.string.dialog_overlay_rationale_message, AppUtils.getAppName()))
                .setPositiveButton(R.string.dialog_overlay_rationale_process, new DialogInterface.OnClickListener()
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