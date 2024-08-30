package com.jaideep.expensetracker.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIcon
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.OpenSansFont
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDatePicker(
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
    errorMessage: String,
    onValueChanged: (value: String) -> Unit = {}
) {

    val isFocused = remember {
        mutableStateOf(false)
    }
    val showDatePicker = remember {
        mutableStateOf(false)
    }
    val showErrorText = remember {
        mutableStateOf(false)
    }

    val dateState = rememberDatePickerState()

    if (showDatePicker.value) {
        DatePickerDialog(onDismissRequest = { showDatePicker.value = false }, confirmButton = {
            Button(onClick = {
                dateState.selectedDateMillis?.let { date ->
                    val dateString = LocalDate.ofEpochDay(date / 86_400_000L).toString()
                    onValueChanged(dateString)
                    text.value = TextFieldValue(dateString)
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
        TextField(
            modifier = modifier
                .focusable()
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused.value = it.hasFocus
                }
                .clickable {
                    showDatePicker.value = true
                },
            value = text.value,
            onValueChange = { onValueChanged(it.text) },
            readOnly = true,
            enabled = false,
            label = {
                SimpleSmallText(
                    text = label,
                    color = if (isError.value) Color.Red else if (isFocused.value) MaterialTheme.colorScheme.secondary else Color.Gray,
                    modifier = Modifier.background(Color.White)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = if (isError.value) Color.Red else iconColor
                )
            },
            trailingIcon = {
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
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                disabledBorderColor = borderColor,
                errorBorderColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                disabledContainerColor = Color.White,
                disabledTextColor = Color.Black
            ),
            isError = isError.value,
        )

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
    onValueChanged: (value: String) -> Unit
) {

    val isFocused = remember {
        mutableStateOf(false)
    }
    val showDatePicker = remember {
        mutableStateOf(false)
    }
    val showErrorText = remember {
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
        TextField(
            modifier = modifier
                .focusable()
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused.value = it.hasFocus
                }
                .clickable {
                    showDatePicker.value = true
                },
            value = text,
            onValueChange = { },
            readOnly = true,
            enabled = false,
            label = {
                SimpleSmallText(
                    text = label,
                    color = if (isError) Color.Red else if (isFocused.value) MaterialTheme.colorScheme.secondary else Color.Gray,
                    modifier = Modifier.background(Color.White)
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
                if (isError) {
                    Column {
                        Icon(imageVector = Icons.Filled.ErrorOutline,
                            contentDescription = "Error icon",
                            tint = Color.Red,
                            modifier = Modifier.clickable {
                                if (isError) {
                                    showErrorText.value = !showErrorText.value
                                }
                            })
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                disabledBorderColor = borderColor,
                errorBorderColor = Color.Red,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color.White,
                disabledContainerColor = Color.White,
                disabledTextColor = Color.Black
            ),
            isError = isError,
        )

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
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        fontFamily = OpenSansFont.openSans,
        color = color,
        overflow = overflow
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
        style = MaterialTheme.typography.bodyLarge,
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
        style = MaterialTheme.typography.bodyMedium,
        fontFamily = OpenSansFont.openSans,
        color = color
    )
}
