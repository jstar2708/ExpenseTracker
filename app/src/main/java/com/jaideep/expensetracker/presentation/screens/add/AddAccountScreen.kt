package com.jaideep.expensetracker.presentation.screens.add

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.TextFieldWithIcon
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.AddAccountViewModel

@Preview
@Composable
private fun AddAccountScreenPreview() {
    AppTheme {
        AddAccountScreen(
            screenTitle = "Add Account",
            screenDetail = "Please provide account details",
            accountName = remember {
                mutableStateOf(TextFieldValue(""))
            },
            initialBalance = remember {
                mutableStateOf(TextFieldValue(""))
            },
            isBalanceIncorrect = remember {
                mutableStateOf(false)
            },
            isAccountNameIncorrect = remember {
                mutableStateOf(false)
            },
            exitScreen = remember {
                mutableStateOf(false)
            },
            saveAccount = { _, _ ->  }
        ) {

        }
    }
}

@Composable
fun AddAccountScreenRoot(navController: NavHostController,
                         addAccountViewModel: AddAccountViewModel = hiltViewModel()
) {
    AddAccountScreen(
        addAccountViewModel.screenTitle,
        addAccountViewModel.screenDetail,
        addAccountViewModel.accountName,
        addAccountViewModel.initialBalance,
        addAccountViewModel.isBalanceIncorrect,
        addAccountViewModel.isAccountNameIncorrect,
        addAccountViewModel.exitScreen,
        addAccountViewModel::saveAccount
    ) {
        val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
        savedStateHandle?.set("isAccountSaved", addAccountViewModel.isAccountSaved.value)
        navController.popBackStack()
    }
}

@Composable
fun AddAccountScreen(
    screenTitle: String,
    screenDetail: String,
    accountName: MutableState<TextFieldValue>,
    initialBalance: MutableState<TextFieldValue>,
    isBalanceIncorrect: MutableState<Boolean>,
    isAccountNameIncorrect: MutableState<Boolean>,
    exitScreen: MutableState<Boolean>,
    saveAccount: (accountName: String, balance: String) -> Unit,
    backPress: () -> Unit
) {
    LaunchedEffect(key1 = exitScreen.value) {
        if (exitScreen.value) {
            backPress()
        }
    }
    Surface {
        Column(
            Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App logo",
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .defaultMinSize(minHeight = 150.dp)
                    .padding(4.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            BlueBackground(
                Modifier.wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    HeadingTextBold(
                        text = screenTitle, color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    SimpleText(
                        text = screenDetail,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextFieldWithIcon(
                        label = "Account Name",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = accountName,
                        isError = isAccountNameIncorrect,
                        errorMessage = "Name cannot be empty"
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextFieldWithIcon(
                        label = "Initial Balance",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = initialBalance,
                        isError = isBalanceIncorrect,
                        keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        errorMessage = "Enter a valid amount"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(60.dp),
                        colors = ButtonColors(Color.White, Color.Blue, Color.White, Color.White),
                        onClick = {
                            saveAccount(
                                accountName.value.text,
                                initialBalance.value.text
                            )
                        }) {
                        SimpleTextBold(
                            text = "Save", color = Color.Blue
                        )
                    }
                }
            }
        }
    }
}