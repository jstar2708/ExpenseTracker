package com.jaideep.expensetracker.presentation.component.other

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.theme.md_theme_light_surface
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Preview
@Composable
private fun ExpenseTrackerSpinnerPreview() {
    AppTheme {
        ExpenseTrackerSpinner(
            values = persistentListOf("All Accounts", "Cash", "SBI"), initialValue = "All Accounts"
        ) {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerSpinner(
    modifier: Modifier = Modifier,
    initialValue: String = "Initial Value",
    values: ImmutableList<String>,
    onValueChanged: (value: String) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedAccount by remember {
        mutableStateOf(initialValue)
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }) {
        OutlinedTextField(value = selectedAccount,
            onValueChange = {},
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = md_theme_light_surface,
                unfocusedContainerColor = md_theme_light_surface
            ),
            modifier = Modifier
                .menuAnchor()
                .padding(8.dp),
            trailingIcon = {
                Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "Down arrow")
            })

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            if (values.isNotEmpty()) {
                values.forEach {
                    DropdownMenuItem(text = {
                        SimpleText(text = it)
                    }, onClick = {
                        selectedAccount = it
                        onValueChanged(it)
                        isExpanded = false
                    })
                }
            }
        }
    }
}