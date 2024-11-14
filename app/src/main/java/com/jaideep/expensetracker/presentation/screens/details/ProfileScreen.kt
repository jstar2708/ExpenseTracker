package com.jaideep.expensetracker.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.MediumBoldText
import com.jaideep.expensetracker.presentation.component.SimpleSmallText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.dialog.CurrencyListDialog
import com.jaideep.expensetracker.presentation.component.dialog.ExpenseTrackerDialog
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.ProfileViewModel

@Preview
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(userName = "Jaideep Kumar Singh",
            mostFrequentlyUsedAcc = "PNB",
            avgMonthlyExpenditure = "434",
            totalExpenditure = "7896",
            totalTransactions = "123",
            onBackPress = {},
            toggleDialog = {},
            showDialog = false,
            hideDialog = {},
            clearAllData = {},
            navigateToNotificationScreen = {},
            navigateToAccountListScreen = {},
            hideCurrencyDialog = {},
            openCurrencyDialog = {},
            showCurrencyDialog = false)
    }
}


@Composable
fun ProfileScreenRoot(
    navController: NavController
) {
    val profileViewModel = hiltViewModel<ProfileViewModel>()
    LaunchedEffect(key1 = true) {
        profileViewModel.initData()
    }
    if (profileViewModel.isUsernameLoading || profileViewModel.isAvgMonthlyExpLoading || profileViewModel.isTotalTransactionsLoading || profileViewModel.isTotalExpenditureLoading || profileViewModel.isMostFrequentlyUsedAccLoading) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    } else if (profileViewModel.usernameRetrievalError || profileViewModel.avgMonthlyExpRetrievalError || profileViewModel.totalExpenditureRetrievalError || profileViewModel.mostFrequentlyUsedAccRetrievalError || profileViewModel.totalTransactionsRetrievalError) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            SimpleText(
                text = "Error loading user data", color = Color.Red
            )
        }
    } else {
        ProfileScreen(
            userName = profileViewModel.userName,
            mostFrequentlyUsedAcc = profileViewModel.mostFrequentlyUsedAccount,
            avgMonthlyExpenditure = profileViewModel.avgMonthlyExpenditure,
            totalExpenditure = profileViewModel.totalExpenditure,
            totalTransactions = profileViewModel.totalTransactions,
            showCurrencyDialog = profileViewModel.showCurrencyDialog,
            showDialog = profileViewModel.showDialog,
            onBackPress = { navController.popBackStack() },
            toggleDialog = profileViewModel::toggleDialog,
            hideDialog = profileViewModel::hideDialog,
            clearAllData = profileViewModel::clearAllData,
            navigateToNotificationScreen = {
                navController.navigate(DetailScreen.NOTIFICATION)
            },
            navigateToAccountListScreen = {
                navController.navigate(DetailScreen.ACCOUNT_LIST)
            },
            openCurrencyDialog = profileViewModel::openCurrencyDialog,
            hideCurrencyDialog = profileViewModel::hideDialog
        )
    }
}

@Composable
fun ProfileScreen(
    userName: String,
    totalExpenditure: String,
    totalTransactions: String,
    avgMonthlyExpenditure: String,
    mostFrequentlyUsedAcc: String,
    showDialog: Boolean,
    showCurrencyDialog: Boolean,
    toggleDialog: () -> Unit,
    hideDialog: () -> Unit,
    clearAllData: () -> Unit,
    onBackPress: () -> Unit,
    navigateToNotificationScreen: () -> Unit,
    navigateToAccountListScreen: () -> Unit,
    openCurrencyDialog: () -> Unit,
    hideCurrencyDialog: () -> Unit
) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val showSnackBar = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = showSnackBar.value) {
        if (showSnackBar.value) {
            snackBarHostState.showSnackbar("Coming soon")
            showSnackBar.value = false
        }
    }

    Scaffold(topBar = {
        ExpenseTrackerAppBar(
            title = "Profile",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Navigation Icon",
            onNavigationIconClick = onBackPress,
            actionIcon = null,
            actionDescription = ""
        ) {
            // Nothing to do
        }
    }, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) {
            Snackbar(snackbarData = it, containerColor = Color.DarkGray)
        }
    }) {
        if (showDialog) {
            ExpenseTrackerDialog(
                title = "Clear data",
                message = "Are you sure you want to clear all data ?",
                okButtonText = "Yes",
                cancelButtonText = "No",
                onOkClick = clearAllData,
                onCancelClicked = hideDialog
            )
        }
        if (showCurrencyDialog) {
            CurrencyListDialog({}, {})
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile image"
            )
            Spacer(modifier = Modifier.height(20.dp))
            MediumBoldText(text = userName)
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextBold(
                        text = "$$totalExpenditure", color = MaterialTheme.colorScheme.primary
                    )
                    SimpleSmallText(text = "Total Expenditure", textAlignment = TextAlign.Center)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextBold(
                        text = totalTransactions, color = MaterialTheme.colorScheme.primary
                    )
                    SimpleSmallText(text = "Total Transactions", textAlignment = TextAlign.Center)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextBold(
                        text = "$$avgMonthlyExpenditure", color = MaterialTheme.colorScheme.primary
                    )
                    SimpleSmallText(
                        text = "Average Monthly Expenditure", textAlignment = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier.weight(.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeadingTextBold(
                        text = mostFrequentlyUsedAcc, color = MaterialTheme.colorScheme.primary
                    )
                    SimpleSmallText(
                        text = "Most Frequently use account", textAlignment = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.size(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = .5.dp, end = .5.dp)
                    .border(width = 1.dp, color = Color.Gray)
                    .clickable(onClick = navigateToAccountListScreen),
                contentAlignment = Alignment.CenterStart
            ) {
                SimpleText(
                    text = "Accounts", modifier = Modifier.padding(start = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = .5.dp, end = .5.dp)
                    .border(width = 1.dp, color = Color.Gray)
                    .clickable {
                        showSnackBar.value = true
                    }, contentAlignment = Alignment.CenterStart
            ) {
                SimpleText(
                    text = "Upload your data", modifier = Modifier.padding(start = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = .5.dp, end = .5.dp)
                    .border(width = 1.dp, color = Color.Gray)
                    .clickable {
                        toggleDialog()
                    }, contentAlignment = Alignment.CenterStart
            ) {
                SimpleText(
                    text = "Clear all data", modifier = Modifier.padding(start = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = .5.dp, end = .5.dp)
                    .border(width = 1.dp, color = Color.Gray)
                    .clickable(onClick = navigateToNotificationScreen),
                contentAlignment = Alignment.CenterStart
            ) {
                SimpleText(
                    text = "Notifications", modifier = Modifier.padding(start = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = .5.dp, end = .5.dp)
                    .border(width = 1.dp, color = Color.Gray)
                    .clickable(onClick = openCurrencyDialog),
                contentAlignment = Alignment.CenterStart
            ) {
                SimpleText(
                    text = "Currency", modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

