package com.example.expensetracker.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

//@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RadioButtonWithTextPreview() {
    RadioButtonWithText(
        isSelected = true,
        text = "Income",
        selectedColor = Color.Blue,
        unselectedColor = Color.Gray,
        textColor = Color.Black
    ) {

    }
}

@Composable
fun RadioButtonWithText(
    isSelected: Boolean,
    text: String,
    selectedColor: Color,
    unselectedColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Row (
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected,
            onClick = {
                onClick()
            },
            colors = RadioButtonColors(
                selectedColor,
                unselectedColor,
                Color.Unspecified,
                Color.Unspecified)
        )
        Text(
            text = text,
            color = textColor,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TextFieldWithIconPreview() {
    TextFieldWithIcon(
        label = "Username",
        icon = Icons.Filled.Wallet,
        iconColor = Color.Blue,
        borderColor = Color.Black
    )
}

@Composable
fun TextFieldWithIcon(
    label: String,
    icon: ImageVector,
    iconColor: Color,
    borderColor: Color
) {
    val text = remember {
        mutableStateOf(TextFieldValue(""))
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text.value,
        onValueChange = {
            text.value = it
        },
        label = {
            Text(
                text = label
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                tint = iconColor
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}