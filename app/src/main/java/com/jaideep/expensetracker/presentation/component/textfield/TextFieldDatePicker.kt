package com.jaideep.expensetracker.presentation.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.theme.AppTheme
import java.time.LocalDate


@Preview
@Composable
private fun TextFieldWithDatePicker() {
    AppTheme {
        TextFieldDatePicker(modifier = Modifier,
            label = "Enter date",
            icon = Icons.Filled.CalendarMonth,
            iconColor = Color.Gray,
            borderColor = Color.Black,
            text = "",
            errorMessage = "",
            showErrorText = false,
            onValueChanged = {}) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDatePicker(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    iconColor: Color,
    borderColor: Color,
    text: String,
    isError: Boolean = false,
    errorMessage: String,
    showErrorText: Boolean,
    onValueChanged: (value: String) -> Unit,
    onErrorIconClick: () -> Unit
) {

    val showDatePicker = remember {
        mutableStateOf(false)
    }

    val dateState = rememberDatePickerState()

    if (showDatePicker.value) {
        DatePickerDialog(properties = DialogProperties(usePlatformDefaultWidth = true),
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                Spacer(modifier = Modifier.width(4.dp))
                SmallPrimaryColorButton(text = "Ok") {
                    dateState.selectedDateMillis?.let { date ->
                        val dateString = LocalDate.ofEpochDay(date / 86_400_000L).toString()
                        onValueChanged(dateString)
                    }
                    showDatePicker.value = false
                }
                Spacer(modifier = Modifier.width(4.dp))
            },
            dismissButton = {
                SmallPrimaryColorButton(text = "Cancel") {
                    showDatePicker.value = false
                }
                Spacer(modifier = Modifier.width(4.dp))
            }) {
            DatePicker(state = dateState, title = {
                SimpleTextBold(
                    modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 16.dp),
                    text = "Select Transaction Date"
                )
            })
        }
    }


    Column(
        horizontalAlignment = Alignment.End
    ) {
        TextFieldWithIconAndErrorPopUp(
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .clickable { showDatePicker.value = true },
            label = label,
            isReadOnly = true,
            icon = icon,
            enabled = false,
            iconColor = iconColor,
            borderColor = borderColor,
            text = text,
            isError = isError,
            errorMessage = errorMessage,
            showErrorText = showErrorText,
            onValueChange = onValueChanged,
            onErrorIconClick = onErrorIconClick
        )
    }
}
