package com.jaideep.expensetracker.presentation.screens.bottom.transaction

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.model.dto.TransactionDto
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTabLayout
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.TextFieldDatePicker
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import com.jaideep.expensetracker.presentation.viewmodel.TransactionViewModel
import java.time.LocalDate

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreenPreview() {
    AppTheme {
        TransactionScreen(navControllerRoot = NavController(Application()),
            backPress = {},
            accounts = listOf("All accounts", "Cash"),
            onAccountSpinnerValueChanged = { _, _, _, _, _ -> },
            transactions = listOf(
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
                TransactionDto(
                    200.0, CategoryDto("Food", R.drawable.food), "No Message", LocalDate.now(), true
                ),
            ),
            updateCurrentTabValue = {},
            selectedAccount = remember {
                mutableStateOf("All Accounts")
            },
            selectedTab = remember {
                mutableStateOf("")
            },
            toggleDialog = {},
            showDialog = remember {
                mutableStateOf(false)
            },
            updateTransactionList = {},
            fromDate = remember {
                mutableStateOf(TextFieldValue())
            },
            toDate = remember {
                mutableStateOf(TextFieldValue())
            },
            checkValidDate = { true }
        )
    }
}

@Composable
fun TransactionScreenRoot(
    navHostControllerRoot: NavHostController, mainViewModel: MainViewModel, backPress: () -> Unit
) {
    val transactionViewModel: TransactionViewModel = hiltViewModel()
    TransactionScreen(
        navControllerRoot = navHostControllerRoot,
        backPress = backPress,
        accounts = mainViewModel.accounts.collectAsState().value.toMutableList().apply {
            this.add(0, "All Accounts")
        },
        onAccountSpinnerValueChanged = { accountName, isCredit, isDebit, sDate, eDate ->
            transactionViewModel.updateSelectedAccount(accountName)
            mainViewModel.updateTransactionMethod(
                accountName, isCredit, isDebit, sDate, eDate
            )
        },
        transactions = mainViewModel.pagedTransactionItems.collectAsState().value.collectAsLazyPagingItems().itemSnapshotList.items.map { transaction ->
            TransactionDto(
                transaction.amount,
                mainViewModel.getCategoryDto(transaction.categoryId),
                transaction.message,
                LocalDate.ofEpochDay(transaction.createdTime / 86_400_000L),
                transaction.isCredit == 1
            )
        },
        updateCurrentTabValue = transactionViewModel::updateCurrentTab,
        selectedAccount = transactionViewModel.selectedAccount.collectAsState(),
        selectedTab = transactionViewModel.selectedTabValue.collectAsState(),
        toggleDialog = transactionViewModel::toggleDialog,
        showDialog = transactionViewModel.showDialog.collectAsState(),
        toDate = transactionViewModel.toDate,
        fromDate = transactionViewModel.fromDate,
        updateTransactionList = {
            mainViewModel.updateTransactionMethod(
                transactionViewModel.selectedAccount.value,
                transactionViewModel.selectedTabValue.value == "Income",
                transactionViewModel.selectedTabValue.value == "Expense",
                transactionViewModel.fromDate.value.text,
                transactionViewModel.toDate.value.text)
        },
        checkValidDate = transactionViewModel::checkValidDate
    )
}

@Composable
fun TransactionScreen(
    navControllerRoot: NavController,
    backPress: () -> Unit,
    accounts: List<String>,
    onAccountSpinnerValueChanged: (value: String, isCredit: Boolean, isDebit: Boolean, sDate: String?, eDate: String?) -> Unit,
    transactions: List<TransactionDto>,
    updateCurrentTabValue: (selectedTab: Int) -> Unit,
    selectedAccount: State<String>,
    selectedTab: State<String>,
    toggleDialog: () -> Unit,
    showDialog: State<Boolean>,
    fromDate: MutableState<TextFieldValue>,
    toDate: MutableState<TextFieldValue>,
    updateTransactionList: () -> Unit,
    checkValidDate: () -> Boolean
) {
    val savedStateHandle = navControllerRoot.currentBackStackEntry?.savedStateHandle
    val resultTransaction = savedStateHandle?.get<Boolean>("isTransactionSaved")

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) {
            Snackbar(
                snackbarData = it, containerColor = Color.DarkGray
            )
        }
    }, topBar = {
        ExpenseTrackerAppBar(
            title = "Transactions",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Back button",
            onNavigationIconClick = { backPress() },
            actionIcon = Icons.Filled.Add,
            actionDescription = "Add transaction icon"
        ) {
            navControllerRoot.navigate(DetailScreen.ADD_TRANSACTION)
        }
    }) { it ->
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (showDialog.value) {
                Row {
                    FilterDialog(fromDate, toDate, updateTransactionList, checkValidDate)
                }
            }
            ExpenseTrackerSpinner(values = accounts,
                initialValue = selectedAccount.value,
                onValueChanged = { accountName ->
                    onAccountSpinnerValueChanged(
                        accountName,
                        selectedTab.value == "Income",
                        selectedTab.value == "Expense",
                        null,
                        null
                    )
                })
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                ExpenseTrackerTabLayout(values = arrayOf("All", "Income", "Expense"), onClick = {
                    updateCurrentTabValue(it)
                    onAccountSpinnerValueChanged(
                        selectedAccount.value, it == 1, it == 2, null, null
                    )
                })
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter",
                    modifier = Modifier
                        .weight(.2f)
                        .size(30.dp)
                        .clip(CircleShape)
                        .clickable {
                            toggleDialog()
                        },
                    tint = Color.Unspecified
                )
            }

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
                            amount = transactions[it].amount.toString(),
                            isCredit = transactions[it].isCredit
                        )
                    }
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
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
        LaunchedEffect(key1 = resultTransaction) {
            if (resultTransaction != null) {
                if (resultTransaction) snackBarHostState.showSnackbar("Transaction saved successfully")
                else snackBarHostState.showSnackbar("Error while saving transaction")
                savedStateHandle["isTransactionSaved"] = null
            }
        }
    }
}

@Composable
private fun FilterDialog(
    fromDate: MutableState<TextFieldValue>,
    toDate: MutableState<TextFieldValue>,
    updateTransactionList: () -> Unit,
    checkValidDate: () -> Boolean
) {

    Dialog(
        onDismissRequest = {

        }, properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Card (
            modifier = Modifier.padding(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            SimpleTextBold(
                text = "Choose From and To date",
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
            TextFieldDatePicker(
                modifier = Modifier.padding(16.dp, 8.dp),
                text = fromDate,
                label = "Enter From Date",
                icon = Icons.Filled.CalendarMonth,
                iconColor = Color.Gray,
                borderColor = Color.LightGray,
                errorMessage = "Enter correct date",
            )

            SimpleTextBold(
                text = "To",
                color = Color.Black,
                modifier = Modifier.padding(16.dp, 8.dp)
            )

            TextFieldDatePicker(
                modifier = Modifier.padding(16.dp, 8.dp),
                text = toDate,
                label = "Enter From Date",
                icon = Icons.Filled.CalendarMonth,
                iconColor = Color.Gray,
                borderColor = Color.LightGray,
                errorMessage = "Enter correct date"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                Button(
                    onClick = {
                        if (checkValidDate()) {
                            updateTransactionList()
                        }
                        else {
                            // Show error message
                        }
                    },
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonColors(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    SimpleText(
                        text = "Apply",
                        textAlignment = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
