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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.model.TextFieldWithIconState
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.button.RadioButtonWithText
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldDatePicker
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithDropDown
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.AddTransactionViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddTransactionPreview() {
    AppTheme {
        AddTransactionScreen(radioButtonValue = 0,
            detailsMessage = "Please provide transaction details",
            screenTitle = "Add Transaction",
            accounts = persistentListOf("All Accounts", "PNB"),
            categories = persistentListOf("Food", "Shopping"),
            accountState = TextFieldWithIconState("",
                isError = false,
                showError = false,
                { _ -> },
                {}),
            amountState = TextFieldWithIconState("",
                isError = false,
                showError = false,
                { _ -> },
                {}),
            noteState = TextFieldWithIconState("",
                isError = false,
                showError = false,
                { _ -> },
                {}),
            dateState = TextFieldWithIconState("",
                isError = false,
                showError = false,
                { _ -> },
                {}),
            categoryState = TextFieldWithIconState("",
                isError = false,
                showError = false,
                { _ -> },
                {}),
            toggleRadioButton = {},
            saveTransaction = {},
            backPress = {},
            exitScreen = false,
            dataRetrievalError = false,
            isLoading = false
        )
    }
}

@Composable
fun AddTransactionScreenRoot(
    navControllerRoot: NavHostController, viewModel: AddTransactionViewModel = hiltViewModel()
) {
    AddTransactionScreen(radioButtonValue = viewModel.radioButtonValue.intValue,
        detailsMessage = viewModel.screenDetail,
        screenTitle = viewModel.screenTitle,
        toggleRadioButton = viewModel::toggleRadioButton,
        exitScreen = viewModel.exitScreen,
        accounts = viewModel.accounts.collectAsState().value.toImmutableList(),
        categories = viewModel.categories.collectAsState().value.toImmutableList(),
        dataRetrievalError = viewModel.accountRetrievalError && viewModel.categoryRetrievalError,
        isLoading = viewModel.isAccountLoading && viewModel.isCategoryLoading,
        saveTransaction = viewModel::validateAndSaveTransaction,
        accountState = viewModel.accountState.value,
        noteState = viewModel.noteState.value,
        amountState = viewModel.amountState.value,
        categoryState = viewModel.categoryState.value,
        dateState = viewModel.dateState.value,
        backPress = {
            val savedStateHandle = navControllerRoot.previousBackStackEntry?.savedStateHandle
            savedStateHandle?.set("isTransactionSaved", viewModel.isTransactionSaved)
            navControllerRoot.popBackStack()
        })
}

@Composable
fun AddTransactionScreen(
    radioButtonValue: Int,
    detailsMessage: String,
    screenTitle: String,
    exitScreen: Boolean,
    dataRetrievalError: Boolean,
    isLoading: Boolean,
    accounts: ImmutableList<String>,
    categories: ImmutableList<String>,
    accountState: TextFieldWithIconState,
    noteState: TextFieldWithIconState,
    amountState: TextFieldWithIconState,
    categoryState: TextFieldWithIconState,
    dateState: TextFieldWithIconState,
    toggleRadioButton: (value: Int) -> Unit,
    saveTransaction: () -> Unit,
    backPress: () -> Unit
) {

    LaunchedEffect(key1 = exitScreen) {
        if (exitScreen) {
            backPress()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (dataRetrievalError) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SimpleText(text = "Unable to load user data")
            }
        } else if (isLoading) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ExpenseTrackerProgressBar(
                    modifier = Modifier.size(50.dp)
                )
            }
        } else {
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
                                isSelected = radioButtonValue == 0,
                                text = "Income",
                                selectedColor = Color.White,
                                unselectedColor = Color.White,
                                textColor = Color.White
                            ) {
                                toggleRadioButton(0)
                            }
                            RadioButtonWithText(
                                isSelected = radioButtonValue == 1,
                                text = "Expense",
                                selectedColor = Color.White,
                                unselectedColor = Color.White,
                                textColor = Color.White
                            ) {
                                toggleRadioButton(1)
                            }
                        }

                        TextFieldWithDropDown(
                            values = accounts,
                            label = "Choose Account",
                            icon = Icons.Filled.AccountBalanceWallet,
                            iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            borderColor = Color.Black,
                            text = accountState.text,
                            isError = accountState.isError,
                            errorMessage = "Account name cannot be blank",
                            showErrorText = accountState.showError,
                            onTextFieldValueChange = accountState.onValueChange,
                            onErrorIconClick = accountState.onErrorIconClick
                        )
                        TextFieldWithDropDown(
                            values = categories,
                            label = "Choose Category",
                            icon = Icons.Filled.Category,
                            iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            borderColor = Color.Black,
                            text = categoryState.text,
                            isError = categoryState.isError,
                            errorMessage = "Category name cannot be blank",
                            showErrorText = categoryState.showError,
                            onTextFieldValueChange = categoryState.onValueChange,
                            onErrorIconClick = categoryState.onErrorIconClick
                        )
                        TextFieldWithIconAndErrorPopUp(
                            label = "Amount",
                            icon = Icons.Filled.CurrencyRupee,
                            iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            borderColor = Color.Black,
                            keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            text = amountState.text,
                            isError = amountState.isError,
                            errorMessage = "Enter a valid amount",
                            showErrorText = amountState.showError,
                            onValueChange = amountState.onValueChange,
                            onErrorIconClick = amountState.onErrorIconClick
                        )
                        TextFieldDatePicker(
                            label = "Date",
                            icon = Icons.Filled.CalendarMonth,
                            iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            borderColor = Color.Black,
                            text = dateState.text,
                            isError = dateState.isError,
                            errorMessage = "Enter a valid date",
                            showErrorText = dateState.showError,
                            onValueChanged = dateState.onValueChange,
                            onErrorIconClick = dateState.onErrorIconClick
                        )
                        TextFieldWithIconAndErrorPopUp(
                            label = "Note",
                            icon = Icons.AutoMirrored.Filled.Note,
                            iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            borderColor = Color.Black,
                            text = noteState.text,
                            isError = false,
                            showErrorText = false,
                            errorMessage = "",
                            onValueChange = noteState.onValueChange,
                            onErrorIconClick = noteState.onErrorIconClick
                        )

                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .height(60.dp),
                            colors = ButtonColors(
                                Color.White, Color.Blue, Color.White, Color.White
                            ),
                            onClick = saveTransaction
                        ) {
                            Text(
                                text = "Save", color = Color.Blue
                            )
                        }
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