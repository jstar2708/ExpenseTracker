package com.jaideep.expensetracker.presentation.component

import com.jaideep.expensetracker.presentation.theme.greenColor
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.theme.OpenSansFont
import com.jaideep.expensetracker.presentation.theme.md_theme_light_primary
import com.jaideep.expensetracker.presentation.theme.md_theme_light_surface
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerAppBar(
    title: String,
    navigationIcon: ImageVector,
    navigationDescription: String,
    onNavigationIconClick: () -> Unit,
    actionIcon: ImageVector,
    actionDescription: String,
    onActionIconClick: () -> Unit,
) {
    TopAppBar(title = {
        SimpleTextBold(
            modifier = Modifier.padding(start = 4.dp, end = 4.dp), text = title
        )
    }, navigationIcon = {
        IconButton(onClick = { onNavigationIconClick() }) {
            Icon(
                imageVector = navigationIcon,
                contentDescription = navigationDescription,
                modifier = Modifier.size(40.dp),
            )
        }
    }, actions = {
        IconButton(onClick = { onActionIconClick() }) {
            Icon(
                imageVector = actionIcon,
                contentDescription = actionDescription,
                modifier = Modifier.size(30.dp)
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTrackerSpinner(
    modifier: Modifier = Modifier,
    initialValue: String = "Initial Value",
    values: List<String>,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ExpenseTrackerCategoryCardPreview() {
    ExpenseTrackerCategoryCard(
        iconId = R.drawable.food,
        iconDescription = "Food icon",
        categoryName = "Food",
        spendValue = "$0 / $1000",
        progressValue = 0.8f,
        trackColor = Color.Yellow
    )
}

@Composable
fun ExpenseTrackerCategoryCard(
    iconId: Int,
    iconDescription: String,
    categoryName: String,
    spendValue: String,
    progressValue: Float,
    trackColor: Color
) {
    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            SimpleText(
                text = categoryName,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis
            )

            SimpleText(
                text = spendValue, modifier = Modifier.padding(8.dp), textAlignment = TextAlign.End
            )
        }

        LinearProgressIndicator(
            progress = { progressValue },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(10.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = trackColor
        )
    }
}

@Composable
fun ExpenseTrackerBlueButton(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containsIcon: Boolean = true
) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonColors(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        )
    ) {
        SimpleText(
            text = name,
            textAlignment = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(
            modifier = Modifier.size(5.dp)
        )
        if (containsIcon) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow icon",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ExpenseTrackerTransactionCardItemPreview() {
    ExpenseTrackerTransactionCardItem(
        iconId = R.drawable.fuel,
        iconDescription = "Fuel icon",
        categoryName = "Fuel",
        transactionDescription = "Petrol in scooter",
        amount = "$49"
    )
}

@Composable
fun ExpenseTrackerTransactionCardItem(
    iconId: Int,
    iconDescription: String,
    categoryName: String,
    transactionDescription: String,
    amount: String,
    isCredit: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(4.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = iconDescription,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(30.dp),
                tint = Color.Unspecified
            )

            Column(Modifier.weight(1f)) {
                SimpleTextBold(
                    text = categoryName,
                    modifier = Modifier.padding(4.dp),
                    overflow = TextOverflow.Ellipsis
                )

                SimpleText(
                    text = transactionDescription,
                    modifier = Modifier.padding(4.dp),
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray
                )
            }

            SimpleTextBold(
                text = amount,
                modifier = Modifier.padding(4.dp),
                overflow = TextOverflow.Ellipsis,
                color = if (isCredit) greenColor else Color.Black
            )
        }
    }
}

@Composable
fun ExpenseTrackerTabLayout(
    modifier: Modifier = Modifier, values: Array<String>, onClick: (selectedTab: Int) -> Unit
) {
    var selectedTab by remember {
        mutableIntStateOf(0)
    }
    TabRow(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(.8f),
        selectedTabIndex = selectedTab,
        divider = {},
        indicator = {},
        contentColor = Color.Black
    ) {
        values.forEachIndexed { index, title ->
            Tab(
                selected = index == selectedTab,
                modifier = if (index == selectedTab) Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(md_theme_light_primary)
                    .wrapContentWidth() else Modifier.clip(
                    RoundedCornerShape(10.dp)
                ),
                onClick = {
                    selectedTab = index
                    onClick(selectedTab)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Black
            ) {
                SimpleText(
                    text = title, modifier = Modifier.padding(8.dp), color = Color.Unspecified
                )
            }
        }
    }
}

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
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = isSelected, onClick = {
                onClick()
            }, colors = RadioButtonColors(
                selectedColor, unselectedColor, Color.Unspecified, Color.Unspecified
            )
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
        onExpandedChange = { if (!showErrorText.value) isExpanded = it }) {

        TextFieldWithIcon(
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

        ExposedDropdownMenu(
            expanded = isExpanded && !showErrorText.value,
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
    errorMessage: String
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
                    text.value = TextFieldValue(
                        LocalDate.ofEpochDay(date / 86_400_000L).toString()
                    )
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
            onValueChange = { text.value = it },
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
    errorMessage: MutableState<String> = remember {
        mutableStateOf("Error!")
    }
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
                        .padding(8.dp), text = errorMessage.value, color = Color.Red
                )
            }
        }
    }
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
    errorMessage: String
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
    errorMessage: String,
    showErrorText: MutableState<Boolean>
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
