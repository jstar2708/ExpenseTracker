package com.jaideep.expensetracker.presentation.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.MediumBoldText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldDatePicker
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun NotificationDialogPreview() {
    AppTheme {
        NotificationDialog(dateState = TextFieldWithIconAndErrorPopUpState(text = "",
            isError = false,
            showError = false,
            errorMessage = "",
            onValueChange = {},
            onErrorIconClick = {}),
            messageState = TextFieldWithIconAndErrorPopUpState(text = "",
                isError = false,
                showError = false,
                errorMessage = "",
                onValueChange = {},
                onErrorIconClick = {}),
            saveNotification = { _, _ -> },
            hideDialog = {},
            clearDialog = {},
            validateNotification = { true })
    }
}

@Composable
fun NotificationDialog(
    dateState: TextFieldWithIconAndErrorPopUpState,
    messageState: TextFieldWithIconAndErrorPopUpState,
    saveNotification: (message: String, date: String) -> Unit,
    clearDialog: () -> Unit,
    validateNotification: () -> Boolean,
    hideDialog: () -> Unit
) {
    Dialog(
        onDismissRequest = hideDialog, properties = DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true, usePlatformDefaultWidth = true
        )
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            MediumBoldText(
                modifier = Modifier.fillMaxWidth(), text = "Add Reminder"
            )
            Spacer(modifier = Modifier.height(8.dp))
            SimpleText(text = "Add message and date for your event")
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldWithIconAndErrorPopUp(
                label = "Message",
                icon = Icons.AutoMirrored.Filled.Message,
                iconColor = Color.Black,
                borderColor = Color.Gray,
                errorMessage = messageState.errorMessage,
                showErrorText = messageState.showError,
                text = messageState.text,
                isError = messageState.isError,
                onValueChange = messageState.onValueChange,
                onErrorIconClick = messageState.onErrorIconClick
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldDatePicker(
                label = "Choose Date",
                icon = Icons.Filled.CalendarMonth,
                iconColor = Color.Black,
                borderColor = Color.Black,
                text = dateState.text,
                isError = dateState.isError,
                errorMessage = dateState.errorMessage,
                showErrorText = dateState.showError,
                onValueChanged = dateState.onValueChange,
                onErrorIconClick = dateState.onErrorIconClick
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallPrimaryColorButton(text = "Cancel") {
                    hideDialog()
                }
                SmallPrimaryColorButton(text = "Add") {
                    if (validateNotification()) {
                        saveNotification(messageState.text, dateState.text)
                        hideDialog()
                        clearDialog()
                    }
                }
            }
        }
    }
}