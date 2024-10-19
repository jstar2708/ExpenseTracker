package com.jaideep.expensetracker.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jaideep.expensetracker.model.DialogState
import com.jaideep.expensetracker.presentation.component.MediumBoldText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldDatePicker
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun FilterDialogPreview() {
    AppTheme {
        FilterDialog(dialogState = DialogState(
            showDialog = true, fromDate = "", toDate = "", showError = false, errorMessage = ""
        ),
            updateTransactionList = {},
            checkValidDate = { true },
            hideDialog = {},
            updateFromDate = {},
            updateToDate = {}) {

        }
    }
}

@Composable
fun FilterDialog(
    dialogState: DialogState,
    updateTransactionList: () -> Unit,
    checkValidDate: () -> Boolean,
    hideDialog: () -> Unit,
    updateFromDate: (date: String) -> Unit,
    updateToDate: (date: String) -> Unit,
    clearDialogDate: () -> Unit
) {

    Dialog(
        onDismissRequest = {
            hideDialog()
        }, properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Card(
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            MediumBoldText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = "Choose From and To date",
                color = Color.Black,
                textAlignment = TextAlign.Center,
            )
            TextFieldDatePicker(modifier = Modifier.padding(16.dp, 8.dp),
                text = dialogState.fromDate,
                label = if (dialogState.fromDate.isBlank()) "Enter From Date" else "From Date",
                icon = Icons.Filled.CalendarMonth,
                iconColor = Color.Gray,
                borderColor = Color.LightGray,
                errorMessage = "Enter correct date",
                showErrorText = false,
                onValueChanged = updateFromDate,
                onErrorIconClick = {})

            SimpleTextBold(
                modifier = Modifier.padding(16.dp, 8.dp),
                text = "To",
                color = Color.Black,
                textAlignment = TextAlign.End
            )

            TextFieldDatePicker(modifier = Modifier.padding(16.dp, 8.dp),
                text = dialogState.toDate,
                label = if (dialogState.toDate.isBlank()) "Enter To Date" else "To Date",
                icon = Icons.Filled.CalendarMonth,
                iconColor = Color.Gray,
                borderColor = Color.LightGray,
                errorMessage = "Enter correct date",
                showErrorText = false,
                onValueChanged = updateToDate,
                onErrorIconClick = {})

            if (dialogState.showError) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Absolute.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.weight(.15f),
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Error icon",
                        tint = Color.Red
                    )
                    SimpleText(
                        modifier = Modifier.weight(.85f),
                        text = dialogState.errorMessage,
                        color = Color.Red
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SmallPrimaryColorButton(text = "Apply") {
                    if (checkValidDate()) {
                        updateTransactionList()
                        hideDialog()
                    }
                }
                SmallPrimaryColorButton(text = "Clear", onClick = clearDialogDate)
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}