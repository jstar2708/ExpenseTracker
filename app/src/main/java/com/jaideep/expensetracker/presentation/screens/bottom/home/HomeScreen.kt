package com.jaideep.expensetracker.presentation.screens.bottom.home


import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jaideep.expensetracker.common.AddScreen
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.common.constant.AppConstants.CREATE_SCREEN
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.dto.TransactionDto
import com.jaideep.expensetracker.presentation.component.MediumBoldText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.button.ExpenseTrackerBlueButton
import com.jaideep.expensetracker.presentation.component.card.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.component.card.SummaryCard
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconId
import com.jaideep.expensetracker.presentation.viewmodel.HomeViewModel
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            navControllerRoot = NavController(Application()),
            accounts = persistentListOf("All accounts", "Cash"),
            transactions = persistentListOf(),
            onAccountSpinnerValueChanged = {},
            accountBalance = 0.0,
            spentToday = 0.0,
            selectedAccount = "All Accounts",
            categoryCardData = CategoryCardData("Food", "Food", 0.0),
            amountSpentThisMonthFromAcc = 0.0,
            onBackPress = {}
        )
    }
}

@Composable
fun HomeScreenRoot(
    navControllerRoot: NavController, mainViewModel: MainViewModel, homeViewModel: HomeViewModel, onBackPress: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        homeViewModel.getInitialAccountData()
    }

    if (mainViewModel.isAccountLoading || mainViewModel.isTransactionLoading || mainViewModel.isCategoryLoading) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    } else if (mainViewModel.transactionRetrievalError || mainViewModel.accountRetrievalError || mainViewModel.categoryRetrievalError) {
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
        HomeScreen(
            navControllerRoot = navControllerRoot,
            accounts = mainViewModel.accounts.collectAsState().value.toImmutableList(),
            transactions = mainViewModel.transactions.collectAsState().value.toImmutableList(),
            onAccountSpinnerValueChanged = {
                homeViewModel.updateSelectedAccount(it)
                mainViewModel.updateInitialTransaction(it)
            },
            accountBalance = homeViewModel.selectedAccountBalance.collectAsState().value,
            spentToday = homeViewModel.spentToday.collectAsState().value,
            selectedAccount = homeViewModel.selectedAccount.collectAsState().value,
            categoryCardData = homeViewModel.getMaxSpentCategoryData.collectAsState().value,
            amountSpentThisMonthFromAcc = homeViewModel.amountSpentThisMonthFromAcc.collectAsState().value,
            onBackPress = onBackPress
        )
    }
}

@Composable
fun HomeScreen(
    navControllerRoot: NavController,
    accounts: ImmutableList<String>,
    transactions: ImmutableList<TransactionDto>,
    accountBalance: Double,
    spentToday: Double,
    selectedAccount: String,
    categoryCardData: CategoryCardData?,
    amountSpentThisMonthFromAcc: Double,
    onBackPress: () -> Unit,
    onAccountSpinnerValueChanged: (value: String) -> Unit,
) {
    val savedStateHandle = navControllerRoot.currentBackStackEntry?.savedStateHandle
    val resultAccount = savedStateHandle?.get<Boolean>("isAccountSaved")

    val resultTransaction = savedStateHandle?.get<Boolean>("isTransactionSaved")

    val snackBarHostState = remember {
        SnackbarHostState()
    }

//    BackHandler {
//        onBackPress()
//    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            ) {
                Snackbar(
                    snackbarData = it, containerColor = Color.DarkGray
                )
            }
        }, topBar = {
            ExpenseTrackerAppBar(title = "Hello Jaideep",
                navigationIcon = Icons.Outlined.AccountCircle,
                navigationDescription = "User icon",
                onNavigationIconClick = { navControllerRoot.navigate(DetailScreen.PROFILE) },
                actionIcon = Icons.Filled.Notifications,
                actionDescription = "Notification icon",
                onActionIconClick = { })
        }) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(it),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    ExpenseTrackerBlueButton(
                        name = "Add\nAccount",
                        onClick = { navControllerRoot.navigate("${AddScreen.CREATE_UPDATE_ACCOUNT}/${CREATE_SCREEN}") },
                        modifier = Modifier.weight(1f)
                    )
                    ExpenseTrackerBlueButton(
                        name = "Add\nTransaction",
                        onClick = { navControllerRoot.navigate("${AddScreen.CREATE_UPDATE_TRANSACTION}/${CREATE_SCREEN}") },
                        modifier = Modifier.weight(1f)
                    )
                }
                ExpenseTrackerSpinner(values = accounts,
                    initialValue = selectedAccount,
                    onValueChanged = { value ->
                        onAccountSpinnerValueChanged(value)
                    })

                SummaryCard(
                    accountBalance, spentToday, categoryCardData, amountSpentThisMonthFromAcc
                )

                TransactionSummary(transactions)
            }
        }
    }

    LaunchedEffect(key1 = resultAccount) {
        if (resultAccount != null) {
            if (resultAccount) snackBarHostState.showSnackbar("Account saved successfully")
            else snackBarHostState.showSnackbar("Error while saving account")
            savedStateHandle["isAccountSaved"] = null
        }
    }

    LaunchedEffect(key1 = resultTransaction) {
        if (resultTransaction != null) {
            if (resultTransaction) snackBarHostState.showSnackbar("Transaction saved successfully")
            else snackBarHostState.showSnackbar("Error while saving transaction")
            savedStateHandle["isTransactionSaved"] = null
        }
    }
}

@Composable
fun TransactionSummary(transactions: List<TransactionDto>) {
    MediumBoldText(
        modifier = Modifier.padding(8.dp), text = "Recent Transactions"
    )

    if (transactions.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(transactions.size) {
                ExpenseTrackerTransactionCardItem(
                    iconId = getCategoryIconId(transactions[it].iconName),
                    iconDescription = "Category icon",
                    categoryName = transactions[it].categoryName,
                    transactionDescription = transactions[it].message,
                    amount = transactions[it].amount.toString(),
                    isCredit = transactions[it].isCredit,
                    transactionId = transactions[it].transactionId,
                    hideDropDownIcon = true,
                    onDeleteIconClicked = {},
                    onEditIconClicked = {},
                    accountName = "",
                    transactionDate = transactions[it].createdOn.toString()
                )
            }
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxHeight()
        ) {
            SimpleText(
                modifier = Modifier.fillMaxWidth(),
                text = "No transactions done",
                textAlignment = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}
