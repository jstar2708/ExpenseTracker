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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.SimpleSmallText

@Preview
@Composable
private fun TextFieldWithIconAndErrorPopUpPreview() {

}

@Composable
fun TextFieldWithIconAndErrorPopUp(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector,
    iconColor: Color,
    borderColor: Color,
    text: String,
    isError: Boolean,
    isReadOnly: Boolean = false,
    keyBoardOptions: KeyboardOptions = KeyboardOptions(),
    errorMessage: String,
    showErrorText: Boolean,
    onValueChange: (value: String) -> Unit,
    onErrorIconClick: () -> Unit
) {

    val isFocused = remember {
        mutableStateOf(false)
    }


    Column(
        horizontalAlignment = Alignment.End
    ) {
        TextField(modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused.value = it.hasFocus
            }, value = text, readOnly = isReadOnly, onValueChange = {
            onValueChange(it)
        }, label = {
            SimpleSmallText(
                text = label,
                color = if (isError) Color.Red else if (isFocused.value) MaterialTheme.colorScheme.secondary else Color.Gray,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            )
        }, leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                tint = if (isError) Color.Red else iconColor
            )
        }, colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            errorBorderColor = Color.Red,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            errorContainerColor = Color.White
        ), isError = isError, keyboardOptions = keyBoardOptions, trailingIcon = {
            if (isError) {
                Column {
                    Icon(imageVector = Icons.Filled.ErrorOutline,
                        contentDescription = "Error icon",
                        tint = Color.Red,
                        modifier = Modifier.clickable {
                            onErrorIconClick()
                        })
                }
            }
        })
        AnimatedVisibility(
            visible = showErrorText, enter = fadeIn(), exit = fadeOut()
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