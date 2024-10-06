package com.jaideep.expensetracker.presentation.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.SimpleSmallText
import com.jaideep.expensetracker.presentation.theme.AppTheme

@Preview
@Composable
private fun PasswordTextFieldWithIconAndErrorPopUpPreview() {
    AppTheme {
        PasswordTextFieldWithIconAndErrorPopUp(label = "Enter account",
            icon = Icons.Filled.Wallet,
            iconColor = Color.White,
            borderColor = Color.Black,
            text = "",
            isError = true,
            errorMessage = "",
            showErrorText = false,
            onValueChange = {}) {

        }
    }
}

@Composable
fun PasswordTextFieldWithIconAndErrorPopUp(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    iconColor: Color,
    borderColor: Color,
    text: String,
    isError: Boolean,
    isReadOnly: Boolean = false,
    enabled: Boolean = true,
    keyBoardOptions: KeyboardOptions = KeyboardOptions(),
    errorMessage: String,
    showErrorText: Boolean,
    onValueChange: (value: String) -> Unit,
    onErrorIconClick: () -> Unit
) {

    val isFocused = remember {
        mutableStateOf(false)
    }

    val isPasswordVisible = remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        Box(contentAlignment = Alignment.CenterEnd) {
            TextField(modifier = modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused.value = it.hasFocus
                },
                value = text,
                readOnly = isReadOnly,
                onValueChange = {
                    onValueChange(it)
                },
                label = {
                    SimpleSmallText(
                        text = label,
                        color = if (isError) Color.Red else if (isFocused.value) MaterialTheme.colorScheme.secondary else Color.Gray,
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Icon",
                        tint = if (isError) Color.Red else iconColor
                    )
                },
                trailingIcon = {
                    Row {
                        Icon(imageVector = if (isPasswordVisible.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Password visibility",
                            tint = if (isError) Color.Red else iconColor,
                            modifier = Modifier
                                .padding(8.dp, 0.dp)
                                .clickable {
                                    isPasswordVisible.value = !isPasswordVisible.value
                                })
                        if (isError) {
                            Icon(imageVector = Icons.Filled.ErrorOutline,
                                contentDescription = "Error icon",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(8.dp, 0.dp)
                                    .clickable {
                                        onErrorIconClick()
                                    })
                        }
                    }
                },
                enabled = enabled,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    errorBorderColor = Color.Red,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    errorContainerColor = Color.White
                ),
                isError = isError,
                keyboardOptions = keyBoardOptions,
                visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
            )
        }
        AnimatedVisibility(
            visible = showErrorText, enter = fadeIn(), exit = fadeOut()
        ) {
            Column {
                Spacer(modifier = Modifier.height(4.dp))
                SimpleSmallText(
                    Modifier
                        .background(color = Color.White, RoundedCornerShape(8.dp))
                        .padding(8.dp), text = errorMessage, color = Color.Red
                )
            }
        }
    }
}