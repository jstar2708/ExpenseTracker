package com.jaideep.expensetracker.presentation.screens.bottom.home


import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.dto.TransactionDto
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerBlueButton
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerCategoryCard
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.utility.Utility
import com.jaideep.expensetracker.presentation.viewmodel.HomeViewModel
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(navControllerRoot = NavController(Application()),
            accounts = listOf("All accounts", "Cash"),
            transactions = listOf(),
            onAccountSpinnerValueChanged = {},
            accountBalance = remember { mutableDoubleStateOf(0.0) },
            spentToday = remember { mutableDoubleStateOf(0.0) },
            selectedAccount = remember { mutableStateOf("All Accounts") },
            getInitialAccountSpecificData = {},
            categoryCardData = remember {
                mutableStateOf(CategoryCardData("Food", "Food", 0.0))
            },
            amountSpentThisMonthFromAcc = remember {
                mutableDoubleStateOf(0.0)
            })
    }
}

@Composable
fun HomeScreenRoot(
    navControllerRoot: NavController, mainViewModel: MainViewModel
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    HomeScreen(
        navControllerRoot = navControllerRoot,
        accounts = mainViewModel.accounts.collectAsState().value.toMutableList().apply {
            this.add(0, "All Accounts")
        },
        transactions = mainViewModel.transactions.collectAsState().value,
        onAccountSpinnerValueChanged = {
            homeViewModel.updateSelectedAccount(it)
            mainViewModel.updateInitialTransaction(it)
        },
        accountBalance = homeViewModel.selectedAccountBalance.collectAsState(),
        spentToday = homeViewModel.spentToday.collectAsState(),
        selectedAccount = homeViewModel.selectedAccount.collectAsState(),
        getInitialAccountSpecificData = homeViewModel::getInitialAccountData,
        categoryCardData = homeViewModel.getMaxSpentCategoryData.collectAsState(),
        amountSpentThisMonthFromAcc = homeViewModel.amountSpentThisMonthFromAcc.collectAsState()
    )
}

@Composable
fun HomeScreen(
    navControllerRoot: NavController,
    accounts: List<String>,
    transactions: List<TransactionDto>,
    onAccountSpinnerValueChanged: (value: String) -> Unit,
    getInitialAccountSpecificData: () -> Unit,
    accountBalance: State<Double>,
    spentToday: State<Double>,
    selectedAccount: State<String>,
    categoryCardData: State<CategoryCardData>,
    amountSpentThisMonthFromAcc: State<Double>
) {
    val savedStateHandle = navControllerRoot.currentBackStackEntry?.savedStateHandle
    val resultAccount = savedStateHandle?.get<Boolean>("isAccountSaved")

    val resultTransaction = savedStateHandle?.get<Boolean>("isTransactionSaved")

    LaunchedEffect(key1 = true) {
        getInitialAccountSpecificData()
    }

    val snackBarHostState = remember {
        SnackbarHostState()

    }
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
                onNavigationIconClick = { },
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
                        onClick = { navControllerRoot.navigate(DetailScreen.ADD_ACCOUNT) },
                        modifier = Modifier.weight(1f)
                    )
                    ExpenseTrackerBlueButton(
                        name = "Add\nTransaction",
                        onClick = { navControllerRoot.navigate(DetailScreen.ADD_TRANSACTION) },
                        modifier = Modifier.weight(1f)
                    )
                }
                ExpenseTrackerSpinner(values = accounts,
                    initialValue = selectedAccount.value,
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

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SpentAccordingToDuration() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Spent today",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
        )
        Text(
            text = "$157",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
        )
    }
}

@Composable
fun TransactionSummary(transactions: List<TransactionDto>) {
    SimpleTextBold(
        modifier = Modifier.padding(8.dp), text = "Last Transactions"
    )

    if (transactions.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            items(transactions.size) {
                ExpenseTrackerTransactionCardItem(
                    iconId = transactions[it].categoryDto.iconId,
                    iconDescription = "Category icon",
                    categoryName = transactions[it].categoryDto.name,
                    transactionDescription = transactions[it].message,
                    amount = transactions[it].amount.toString()
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

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SummaryCard(
    accountBalance: State<Double>,
    spentToday: State<Double>,
    categoryCardData: State<CategoryCardData>,
    amountSpentThisMonthFromAcc: State<Double>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = CardDefaults.elevatedShape,
        border = BorderStroke(.5.dp, Color.Black),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Column {
                    HeadingTextBold(
                        text = "Balance", modifier = Modifier
                            .wrapContentWidth()
                            .padding(8.dp)
                    )
                    SimpleText(
                        text = "Spent Today \$${spentToday.value}",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                }
                HeadingTextBold(
                    text = "$${accountBalance.value}",
                    modifier = Modifier.weight(1f),
                    textAlignment = TextAlign.Center
                )
            }
            ExpenseTrackerCategoryCard(
                iconId = Utility.getCategoryIconId(categoryCardData.value.iconName),
                iconDescription = "${categoryCardData.value.categoryName} icon",
                categoryName = categoryCardData.value.categoryName,
                spendValue = "${categoryCardData.value.amountSpent} / ${amountSpentThisMonthFromAcc.value}",
                progressValue = (categoryCardData.value.amountSpent / amountSpentThisMonthFromAcc.value).toFloat(),
                trackColor = Color.Yellow
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

