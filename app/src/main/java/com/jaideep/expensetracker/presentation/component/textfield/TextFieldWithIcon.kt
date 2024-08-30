package com.jaideep.expensetracker.presentation.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.SimpleSmallText

@Preview
@Composable
private fun TextFieldWithIconPreview() {
    TextFieldWithIcon(
        label = "Username",
        icon = Icons.Filled.Wallet,
        iconColor = Color.LightGray,
        borderColor = Color.Black
    )
}

@Composable
fun TextFieldWithIcon(
    modifier: Modifier = Modifier,
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
    isReadOnly: Boolean = false,
    keyBoardOptions: KeyboardOptions = KeyboardOptions(),
    errorMessage: String = String()
) {

    val isFocused = remember {
        mutableStateOf(false)
    }

    val showErrorText = remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        TextField(modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .onFocusChanged {
                isFocused.value = it.hasFocus
            }, value = text.value, readOnly = isReadOnly, onValueChange = {
            text.value = it
        }, label = {
            SimpleSmallText(
                text = label,
                color = if (isError.value) Color.Red else if (isFocused.value) MaterialTheme.colorScheme.secondary else Color.Gray,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            )
        }, leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                tint = if (isError.value) Color.Red else iconColor
            )
        }, colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            errorBorderColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White
        ), isError = isError.value, keyboardOptions = keyBoardOptions, trailingIcon = {
            if (isError.value) {
                Column {
                    Icon(imageVector = Icons.Filled.ErrorOutline,
                        contentDescription = "Error icon",
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            if (isError.value) {
                                showErrorText.value = !showErrorText.value
                            }
                        })
                }
            }
        })
        AnimatedVisibility(
            visible = showErrorText.value, enter = fadeIn(), exit = fadeOut()
        ) {
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                SimpleSmallText(
                    Modifier
                        .background(color = Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp), text = errorMessage, color = Color.Red
                )
            }
        }
    }
}
