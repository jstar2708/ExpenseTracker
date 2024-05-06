package com.jaideep.expensetracker.presentation.utility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.theme.OpenSansFont

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
        SimpleText(text = text, color = textColor)
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

    val isFocused = remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged {
                isFocused.value = it.hasFocus
            },
        value = text.value,
        onValueChange = {
            text.value = it
        },
        label = {
            SimpleSmallText(
                text = label,
                color = if (isFocused.value) MaterialTheme.colorScheme.secondary else Color.Gray,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
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

@Preview(showSystemUi = true)
@Composable
private fun HeadingTextBoldPreview() {
    HeadingTextBold(text = "Add Account")
}
@Composable
fun HeadingTextBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textAlignment: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        textAlign = textAlignment,
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = OpenSansFont.openSans,
        color = color
    )
}

@Preview(showSystemUi = true)
@Composable
private fun HeadingTextPreview() {
    HeadingText(text = "Cash Account")
}
@Composable
fun HeadingText(text: String, color: Color = Color.Black) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        fontFamily = OpenSansFont.openSans,
        color = color
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SimpleTextBoldPreview() {
    SimpleTextBold(text = "This is a line.")
}
@Composable
fun SimpleTextBold(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        fontFamily = OpenSansFont.openSans,
        color = color
    )
}

@Preview(showSystemUi = true)
@Composable
private fun SimpleTextPreview() {
    SimpleText(text = "This is a line.", modifier = Modifier.padding(0.dp))
}
@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Black,
    textAlignment: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = OpenSansFont.openSans,
        color = color,
        textAlign = textAlignment,
        overflow = overflow
    )
}

@Composable
fun SimpleSmallText(modifier: Modifier = Modifier, text: String, color: Color = Color.Black) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodySmall,
        fontFamily = OpenSansFont.openSans,
        color = color
    )
}