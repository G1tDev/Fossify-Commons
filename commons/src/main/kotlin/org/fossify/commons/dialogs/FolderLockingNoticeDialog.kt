package org.fossify.commons.dialogs

import android.app.Activity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.fossify.commons.R
import org.fossify.commons.compose.alert_dialog.*
import org.fossify.commons.compose.extensions.MyDevices
import org.fossify.commons.compose.extensions.andThen
import org.fossify.commons.compose.theme.AppThemeSurface
import org.fossify.commons.databinding.DialogTextviewBinding
import org.fossify.commons.extensions.baseConfig
import org.fossify.commons.extensions.getAlertDialogBuilder
import org.fossify.commons.extensions.setupDialogStuff

class FolderLockingNoticeDialog(val activity: Activity, val callback: () -> Unit) {
    init {
        val view = DialogTextviewBinding.inflate(activity.layoutInflater, null, false).apply {
            textView.text = activity.getString(R.string.lock_folder_notice)
        }

        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.ok) { _, _ -> dialogConfirmed() }
            .setNegativeButton(R.string.cancel, null)
            .apply {
                activity.setupDialogStuff(view.root, this, R.string.disclaimer)
            }
    }

    private fun dialogConfirmed() {
        activity.baseConfig.wasFolderLockingNoticeShown = true
        callback()
    }
}

@Composable
fun FolderLockingNoticeAlertDialog(
    alertDialogState: AlertDialogState,
    modifier: Modifier = Modifier,
    callback: () -> Unit
) {
    AlertDialog(
        modifier = modifier.dialogBorder,
        shape = dialogShape,
        containerColor = dialogContainerColor,
        tonalElevation = dialogElevation,
        onDismissRequest = alertDialogState::hide,
        confirmButton = {
            TextButton(
                onClick = alertDialogState::hide andThen callback
            ) {
                Text(text = stringResource(id = R.string.ok),color = Color(0xFFFFDB58))
            }
        },
        dismissButton = {
            TextButton(
                onClick = alertDialogState::hide
            ) {
                Text(text = stringResource(id = R.string.cancel),color = Color(0xFFFFDB58))
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.disclaimer),
                color = dialogTextColor,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.lock_folder_notice),
                color = dialogTextColor,
            )
        }
    )
}

@Composable
@MyDevices
private fun FolderLockingNoticeAlertDialogPreview() {
    AppThemeSurface {
        FolderLockingNoticeAlertDialog(
            alertDialogState = rememberAlertDialogState(),
            callback = {},
        )
    }
}
