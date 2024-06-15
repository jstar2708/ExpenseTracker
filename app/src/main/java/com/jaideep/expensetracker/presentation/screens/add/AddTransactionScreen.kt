package com.jaideep.expensetracker.presentation.screens.add

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.RadioButtonWithText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.TextFieldWithDropDown
import com.jaideep.expensetracker.presentation.component.TextFieldWithIcon
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.AddTransactionViewModel
import java.util.stream.Collectors

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddTransactionPreview() {
    AppTheme {
        AddTransactionScreen(radioButtonValue = "Income",
            detailsMessage = "Please provide transaction details",
            screenTitle = "Add Transaction",
            accountName = remember {
                mutableStateOf(TextFieldValue(""))
            },
            categoryName = remember {
                mutableStateOf(TextFieldValue(""))
            },
            amount = remember {
                mutableStateOf(TextFieldValue(""))
            },
            date = remember {
                mutableStateOf(TextFieldValue(""))
            },
            note = remember {
                mutableStateOf(TextFieldValue(""))
            },
            isDateIncorrect = remember {
                mutableStateOf(false)
            },
            isAmountIncorrect = remember {
                mutableStateOf(false)
            },
            toggleRadioButton = {},
            accounts = listOf(),
            categories = listOf(),
            saveTransaction = { _, _, _, _, _, _ ->

            })
    }
}

@Composable
fun AddTransactionScreenRoot(
    navController: NavHostController,
    viewModel: AddTransactionViewModel = hiltViewModel()
) {
    AddTransactionScreen(
        radioButtonValue = viewModel.radioButtonValue,
        detailsMessage = viewModel.screenDetail,
        screenTitle = viewModel.screenTitle,
        toggleRadioButton = viewModel::toggleRadioButton,
        accountName = viewModel.accountName,
        categoryName = viewModel.categoryName,
        amount = viewModel.amount,
        date = viewModel.date,
        note = viewModel.note,
        isAmountIncorrect = viewModel.isAmountIncorrect,
        isDateIncorrect = viewModel.isDateIncorrect,
        accounts = viewModel.accounts.collectAsState().value.stream().map { it.accountName }
            .collect(Collectors.toList()),
        categories = viewModel.categories.collectAsState().value.stream().map { it.categoryName }
            .collect(Collectors.toList()),
        saveTransaction = viewModel::saveTransaction
    )
}

@Composable
fun AddTransactionScreen(
    radioButtonValue: String,
    detailsMessage: String,
    screenTitle: String,
    accountName: MutableState<TextFieldValue>,
    categoryName: MutableState<TextFieldValue>,
    amount: MutableState<TextFieldValue>,
    date: MutableState<TextFieldValue>,
    note: MutableState<TextFieldValue>,
    isAmountIncorrect: MutableState<Boolean>,
    isDateIncorrect: MutableState<Boolean>,
    toggleRadioButton: (value: String) -> Unit,
    accounts: List<String>,
    categories: List<String>,
    saveTransaction: (
        accountName: String,
        categoryName: String,
        amount: String,
        date: String,
        note: String,
        isCredit: Boolean
    ) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App logo",
                alignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .defaultMinSize(minHeight = 150.dp)
                    .padding(4.dp)
            )
            BlueBackground(Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeadingTextBold(
                        text = screenTitle, color = MaterialTheme.colorScheme.onPrimary
                    )
                    SimpleText(
                        text = detailsMessage, color = MaterialTheme.colorScheme.onPrimary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        RadioButtonWithText(
                            isSelected = radioButtonValue == "Income",
                            text = "Income",
                            selectedColor = Color.White,
                            unselectedColor = Color.White,
                            textColor = Color.White
                        ) {
                            toggleRadioButton(radioButtonValue)
                        }
                        RadioButtonWithText(
                            isSelected = radioButtonValue == "Expense",
                            text = "Expense",
                            selectedColor = Color.White,
                            unselectedColor = Color.White,
                            textColor = Color.White
                        ) {
                            toggleRadioButton(radioButtonValue)
                        }
                    }

                    TextFieldWithDropDown(
                        values = accounts,
                        label = "Choose Account",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = accountName
                    )
                    TextFieldWithDropDown(
                        values = categories,
                        label = "Choose Category",
                        icon = Icons.Filled.Category,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = categoryName
                    )
                    TextFieldWithIcon(
                        label = "Amount",
                        icon = Icons.Filled.CurrencyRupee,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        text = amount,
                        isError = isAmountIncorrect
                    )
                    TextFieldWithIcon(
                        label = "Date",
                        icon = Icons.Filled.CalendarMonth,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = date,
                        isError = isDateIncorrect
                    )
                    TextFieldWithIcon(
                        label = "Note",
                        icon = Icons.AutoMirrored.Filled.Note,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = note
                    )

                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(60.dp),
                        colors = ButtonColors(
                            Color.White, Color.Blue, Color.White, Color.White
                        ),
                        onClick = {
                            saveTransaction(
                                accountName.value.text,
                                categoryName.value.text,
                                amount.value.text,
                                date.value.text,
                                note.value.text,
                                radioButtonValue == "Income"
                            )
                        }) {
                        Text(
                            text = "Save", color = Color.Blue
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun BlueBackground(
    modifier: Modifier, content: @Composable () -> Unit
) {
    Box(
        modifier
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp, topEnd = 30.dp
                )
            )
            .fillMaxWidth(1f)
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.BottomCenter
    ) {
        content()
    }
}