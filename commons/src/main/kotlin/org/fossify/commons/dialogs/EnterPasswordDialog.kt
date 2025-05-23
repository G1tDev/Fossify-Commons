package org.fossify.commons.dialogs

import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import org.fossify.commons.R
import org.fossify.commons.activities.BaseSimpleActivity
import org.fossify.commons.compose.alert_dialog.*
import org.fossify.commons.compose.extensions.MyDevices
import org.fossify.commons.compose.extensions.andThen
import org.fossify.commons.compose.theme.AppThemeSurface
import org.fossify.commons.databinding.DialogEnterPasswordBinding
import org.fossify.commons.extensions.*

class EnterPasswordDialog(
    val activity: BaseSimpleActivity,
    private val callback: (password: String) -> Unit,
    private val cancelCallback: () -> Unit
) {

    private var dialog: AlertDialog? = null
    private val view: DialogEnterPasswordBinding = DialogEnterPasswordBinding.inflate(activity.layoutInflater, null, false)

    init {
        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.ok, null)
            .setNegativeButton(R.string.cancel, null)
            .apply {
                activity.setupDialogStuff(view.root, this, R.string.enter_password) { alertDialog ->
                    dialog = alertDialog
                    alertDialog.showKeyboard(view.password)
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        val password = view.password.value

                        if (password.isEmpty()) {
                            activity.toast(R.string.empty_password)
                            return@setOnClickListener
                        }

                        callback(password)
                    }

                    alertDialog.setOnDismissListener {
                        cancelCallback()
                    }
                }
            }
    }

    fun dismiss(notify: Boolean = true) {
        if (!notify) {
            dialog?.setOnDismissListener(null)
        }
        dialog?.dismiss()
    }

    fun clearPassword() {
        view.password.text?.clear()
    }
}

@Composable
fun EnterPasswordAlertDialog(
    alertDialogState: AlertDialogState,
    modifier: Modifier = Modifier,
    callback: (password: String) -> Unit,
    cancelCallback: () -> Unit
) {
    val localContext = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val visualTransformation by remember {
        derivedStateOf {
            if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        }
    }
    AlertDialog(
        modifier = modifier.dialogBorder,
        shape = dialogShape,
        containerColor = dialogContainerColor,
        tonalElevation = dialogElevation,
        onDismissRequest = alertDialogState::hide andThen cancelCallback,
        confirmButton = {
            TextButton(
                onClick = {
                    if (password.isEmpty()) {
                        localContext.toast(R.string.empty_password)
                    } else {
                        alertDialogState.hide()
                        callback(password)
                    }
                }
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
                text = stringResource(id = R.string.enter_password),
                color = dialogTextColor,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            OutlinedTextField(
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = passwordImageVector(passwordVisible)
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, null)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text(text = stringResource(id = R.string.password))
                },
                singleLine = true
            )
        }
    )
    ShowKeyboardWhenDialogIsOpenedAndRequestFocus(focusRequester = focusRequester)
}

private fun passwordImageVector(passwordVisible: Boolean) = if (passwordVisible) {
    Icons.Filled.Visibility
} else {
    Icons.Filled.VisibilityOff
}

@MyDevices
@Composable
private fun EnterPasswordAlertDialogPreview() {
    AppThemeSurface {
        EnterPasswordAlertDialog(rememberAlertDialogState(), callback = {}, cancelCallback = {})
    }
}
