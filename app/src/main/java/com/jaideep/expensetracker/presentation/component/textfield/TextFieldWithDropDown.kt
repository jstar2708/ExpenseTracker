package com.jaideep.expensetracker.presentation.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import com.jaideep.expensetracker.presentation.component.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldWithDropDown(
    modifier: Modifier = Modifier,
    values: List<String>,
    label: String,
    icon: ImageVector,
    iconColor: Color,
    borderColor: Color,
    text: MutableState<TextFieldValue> = remember {
        mutableStateOf(TextFieldValue(""))
    },
    isError: MutableState<Boolean> = remember {
        mutableStateOf(false)
    },
    errorMessage: String
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val showErrorText = remember {
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(modifier = modifier,
        expanded = isExpanded && !showErrorText.value,
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
            showErrorText = showErrorText
        )

        AnimatedVisibility(
            visible = isExpanded && !showErrorText.value, enter = fadeIn(), exit = fadeOut()
        ) {
            ExposedDropdownMenu(expanded = isExpanded && !showErrorText.value,
                onDismissRequest = { isExpanded = false }) {
                values.forEach {
                    DropdownMenuItem(text = {
                        SimpleText(text = it)
                    }, onClick = {
                        text.value = TextFieldValue(it)
                        isExpanded = false
                    })
                }
            }
        }
    }
}