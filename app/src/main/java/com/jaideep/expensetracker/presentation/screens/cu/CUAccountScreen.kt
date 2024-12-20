package com.jaideep.expensetracker.presentation.screens.cu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.AddAccountViewModel

@Preview
@Composable
private fun AddAccountScreenPreview() {
    AppTheme {
        CUAccountScreen(screenTitle = "Add Account",
            screenDetail = "Please provide account details",
            accountState = TextFieldWithIconAndErrorPopUpState("",
                isError = false,
                showError = false,
                onValueChange = { _ -> },
                onErrorIconClick = {},
                errorMessage = ""
            ),
            amountState = TextFieldWithIconAndErrorPopUpState("",
                isError = false,
                showError = false,
                onValueChange = { _ -> },
                onErrorIconClick = {},
                errorMessage = ""
            ),
            exitScreen = false,
            saveAccount = {},
            isEdit = true,
            backPress = {},
            deleteAccount = {})
    }
}

@Composable
fun CUAccountScreenRoot(
    navController: NavHostController,
    accountId: Int,
    addAccountViewModel: AddAccountViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        addAccountViewModel.initData(accountId)
    }

    if (addAccountViewModel.isAccountListLoading || addAccountViewModel.isAccountByIdLoading) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    } else if (addAccountViewModel.accountListRetrievalError || addAccountViewModel.accountRetrievalError) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            SimpleText(
                text = "Error loading accounts data", color = Color.Red
            )
        }
    } else {
        CUAccountScreen(
            isEdit = addAccountViewModel.isEdit,
            screenTitle = addAccountViewModel.screenTitle,
            screenDetail = addAccountViewModel.screenDetail,
            exitScreen = addAccountViewModel.exitScreen.value,
            accountState = addAccountViewModel.accountState.value,
            amountState = addAccountViewModel.amountState.value,
            saveAccount = addAccountViewModel::validateAndSaveAccount,
            deleteAccount = addAccountViewModel::deleteAccount,
            backPress = {
                val savedStateHandle = navController.previousBackStackEntry?.savedStateHandle
                savedStateHandle?.set(
                    "isAccountSaved", addAccountViewModel.isAccountSaved.value
                )
                if (navController.previousBackStackEntry?.destination?.route == null) {
                    navController.navigate(Graph.MAIN, navOptions = navOptions {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    })
                } else {
                    navController.popBackStack()
                }
            },
        )
    }
}

@Composable
fun CUAccountScreen(
    isEdit: Boolean,
    screenTitle: String,
    screenDetail: String,
    accountState: TextFieldWithIconAndErrorPopUpState,
    amountState: TextFieldWithIconAndErrorPopUpState,
    exitScreen: Boolean,
    saveAccount: () -> Unit,
    backPress: () -> Unit,
    deleteAccount: () -> Unit
) {
    LaunchedEffect(key1 = exitScreen) {
        if (exitScreen) {
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
                        text = screenDetail, color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    TextFieldWithIconAndErrorPopUp(
                        label = "Account Name",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = accountState.text,
                        isError = accountState.isError,
                        errorMessage = accountState.errorMessage,
                        onErrorIconClick = accountState.onErrorIconClick,
                        onValueChange = accountState.onValueChange,
                        showErrorText = accountState.showError
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    TextFieldWithIconAndErrorPopUp(
                        label = "Initial Balance",
                        icon = Icons.Filled.AccountBalanceWallet,
                        iconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        borderColor = Color.Black,
                        text = amountState.text,
                        isError = amountState.isError,
                        keyBoardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        errorMessage = accountState.errorMessage,
                        onValueChange = amountState.onValueChange,
                        onErrorIconClick = amountState.onErrorIconClick,
                        showErrorText = amountState.showError
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(Modifier.fillMaxWidth()) {
                        if (isEdit) {
                            Button(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                                    .height(60.dp),
                                colors = ButtonColors(
                                    Color.White, Color.Blue, Color.White, Color.White
                                ),
                                onClick = deleteAccount
                            ) {
                                SimpleTextBold(
                                    text = "Delete",
                                    color = Color.Blue,
                                    textAlignment = TextAlign.End
                                )
                            }
                        }
                        Button(modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .height(60.dp),
                            colors = ButtonColors(
                                Color.White, Color.Blue, Color.White, Color.White
                            ),
                            onClick = {
                                saveAccount()
                            }) {
                            SimpleTextBold(
                                text = if (isEdit) "Update" else "Save",
                                color = Color.Blue,
                                textAlignment = TextAlign.End
                            )
                        }
                    }
                }
            }
        }
    }
}