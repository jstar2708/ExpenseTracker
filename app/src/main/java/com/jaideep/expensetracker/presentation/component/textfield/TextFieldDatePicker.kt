package com.jaideep.expensetracker.presentation.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
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
        DatePickerDialog(onDismissRequest = { showDatePicker.value = false }, confirmButton = {
            Button(onClick = {
                dateState.selectedDateMillis?.let { date ->
                    val dateString = LocalDate.ofEpochDay(date / 86_400_000L).toString()
                    onValueChanged(dateString)
                }
                showDatePicker.value = false
            }) {
                Text(text = "Ok")
            }
        }, dismissButton = {
            Button(onClick = {
                showDatePicker.value = false
            }) {
                Text(text = "Cancel")
            }
        }) {
            DatePicker(state = dateState)
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
