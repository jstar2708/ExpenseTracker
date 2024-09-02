package com.jaideep.expensetracker.presentation.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun TextFieldWithDropDownPreview() {
    AppTheme {
        TextFieldWithDropDown(values = listOf("All Accounts", "PNB"),
            label = "Select Account",
            icon = Icons.Filled.AccountBalanceWallet,
            iconColor = Color.LightGray,
            borderColor = Color.Black,
            text = "",
            isError = false,
            errorMessage = "",
            showErrorText = false,
            onTextFieldValueChange = {}) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithDropDown(
    modifier: Modifier = Modifier,
    values: List<String>,
    label: String,
    icon: ImageVector,
    iconColor: Color,
    borderColor: Color,
    text: String,
    isError: Boolean,
    errorMessage: String,
    showErrorText: Boolean,
    onTextFieldValueChange: (value: String) -> Unit,
    onErrorIconClick: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(modifier = modifier,
        expanded = isExpanded && !showErrorText,
        onExpandedChange = { isExpanded = it }) {

        TextFieldWithIconAndErrorPopUp(
            modifier = Modifier.menuAnchor(),
            label = label,
            icon = icon,
            iconColor = iconColor,
            borderColor = borderColor,
            isReadOnly = true,
            text = text,
            isError = isError,
            errorMessage = errorMessage,
            showErrorText = showErrorText,
            onValueChange = {},
            onErrorIconClick = onErrorIconClick

        )

        AnimatedVisibility(
            visible = isExpanded && !showErrorText, enter = fadeIn(), exit = fadeOut()
        ) {
            ExposedDropdownMenu(
                expanded = isExpanded && !showErrorText,
                onDismissRequest = { isExpanded = false }) {
                values.forEach {
                    DropdownMenuItem(text = {
                        SimpleText(text = it)
                    }, onClick = {
                        onTextFieldValueChange(it)
                        isExpanded = false
                    })
                }
            }
        }
    }
}