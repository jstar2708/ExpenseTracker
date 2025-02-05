package com.jaideep.expensetracker.presentation.screens.bottom.transaction

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.AddScreen
import com.jaideep.expensetracker.common.constant.AppConstants.CREATE_SCREEN
import com.jaideep.expensetracker.model.DialogState
import com.jaideep.expensetracker.model.dto.TransactionDto
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.component.card.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.component.dialog.ExpenseTrackerDialog
import com.jaideep.expensetracker.presentation.component.dialog.FilterDialog
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerTabLayout
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconId
import com.jaideep.expensetracker.presentation.utility.getStringFromDouble
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import com.jaideep.expensetracker.presentation.viewmodel.TransactionViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreenPreview() {
    AppTheme {
        TransactionScreen(
            navControllerRoot = NavController(Application()),
            backPress = {},
            accounts = persistentListOf("All accounts", "Cash"),
            onAccountSpinnerValueChanged = { _, _, _ -> },
            transactions = persistentListOf(
                TransactionDto(
                    transactionId = 0,
                    amount = 200.0,
                    categoryName = "Food",
                    iconName = "Food",
                    categoryId = 0,
                    message = "No Message",
                    createdOn = LocalDate.now(),
                    isCredit = true,
                    accountName = "PNB",
                ), TransactionDto(
                    transactionId = 0,
                    amount = 200.0,
                    categoryName = "Food",
                    iconName = "Food",
                    categoryId = 0,
                    message = "No Message",
                    createdOn = LocalDate.now(),
                    isCredit = true,
                    accountName = "PNB",
                ), TransactionDto(
                    transactionId = 0,
                    amount = 200.0,
                    categoryName = "Food",
                    iconName = "Food",
                    categoryId = 0,
                    message = "No Message",
                    createdOn = LocalDate.now(),
                    isCredit = true,
                    accountName = "PNB",
                ), TransactionDto(
                    transactionId = 0,
                    amount = 200.0,
                    categoryName = "Food",
                    iconName = "Food",
                    categoryId = 0,
                    message = "No Message",
                    createdOn = LocalDate.now(),
                    isCredit = true,
                    accountName = "PNB",
                ), TransactionDto(
                    transactionId = 0,
                    amount = 200.0,
                    categoryName = "Food",
                    iconName = "Food",
                    categoryId = 0,
                    message = "No Message",
                    createdOn = LocalDate.now(),
                    isCredit = true,
                    accountName = "PNB",
                )
            ),
            tabItemsList = persistentListOf("All", "Income", "Expense"),
            updateCurrentTabValue = {},
            showDeleteDialog = false,
            selectedAccount = "All Accounts",
            selectedTab = 0,
            toggleDialogVisibility = {},
            updateTransactionList = {},
            checkValidDate = { true },
            dialogState = DialogState(
                false, String(), String(), false, String()
            ),
            updateFromDate = {},
            updateToDate = {},
            clearDialogDate = {},
            onTransactionDeleteClicked = {},
            deleteTransaction = {},
            hideDeleteDialog = {},
            currency = "₹"
        )
    }
}

@Composable
fun TransactionScreenRoot(
    navHostControllerRoot: NavHostController,
    mainViewModel: MainViewModel,
    transactionViewModel: TransactionViewModel,
    backPress: () -> Unit
) {

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
    } else if (mainViewModel.isAccountsCountZero) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SimpleText(
                text = "You need to create a account to continue"
            )
            Spacer(modifier = Modifier.height(10.dp))
            SmallPrimaryColorButton(text = "Create Account") {
                navHostControllerRoot.navigate("${AddScreen.CREATE_UPDATE_ACCOUNT}/${CREATE_SCREEN}")
            }
        }
    } else {
        TransactionScreen(
            navControllerRoot = navHostControllerRoot,
            backPress = backPress,
            accounts = mainViewModel.accountsNames.collectAsState().value.toImmutableList(),
            onAccountSpinnerValueChanged = { accountName, isCredit, isDebit ->
                transactionViewModel.updateSelectedAccount(accountName)
                mainViewModel.updateTransactionMethod(
                    accountName,
                    isCredit,
                    isDebit,
                    transactionViewModel.dialogState.value.fromDate,
                    transactionViewModel.dialogState.value.toDate
                )
            },
            transactions = mainViewModel.pagedTransactionItems.collectAsState().value.collectAsLazyPagingItems().itemSnapshotList.items.toImmutableList(),
            tabItemsList = transactionViewModel.tabItemsList,
            showDeleteDialog = transactionViewModel.showDeleteDialog.value,
            updateCurrentTabValue = transactionViewModel::updateCurrentTab,
            selectedAccount = transactionViewModel.selectedAccount.value,
            selectedTab = transactionViewModel.selectedTabValue.intValue,
            currency = mainViewModel.currentCurrencySymbol.collectAsState().value,
            toggleDialogVisibility = transactionViewModel::toggleDialogVisibility,
            updateTransactionList = {
                mainViewModel.updateTransactionMethod(
                    transactionViewModel.selectedAccount.value,
                    transactionViewModel.selectedTabValue.intValue == 1,
                    transactionViewModel.selectedTabValue.intValue == 2,
                    transactionViewModel.dialogState.value.fromDate,
                    transactionViewModel.dialogState.value.toDate
                )
            },
            dialogState = transactionViewModel.dialogState.value,
            checkValidDate = transactionViewModel::checkValidDate,
            updateFromDate = transactionViewModel::updateFromDate,
            updateToDate = transactionViewModel::updateToDate,
            clearDialogDate = transactionViewModel::clearDialogDate,
            onTransactionDeleteClicked = transactionViewModel::onTransactionDeleteClicked,
            deleteTransaction = transactionViewModel::deleteTransaction,
            hideDeleteDialog = transactionViewModel::hideDeleteDialog
        )
    }
}

@Composable
fun TransactionScreen(
    navControllerRoot: NavController,
    accounts: ImmutableList<String>,
    transactions: ImmutableList<TransactionDto>,
    tabItemsList: ImmutableList<String>,
    selectedAccount: String,
    currency: String,
    selectedTab: Int,
    dialogState: DialogState,
    showDeleteDialog: Boolean,
    toggleDialogVisibility: () -> Unit,
    updateTransactionList: () -> Unit,
    checkValidDate: () -> Boolean,
    updateFromDate: (date: String) -> Unit,
    updateToDate: (date: String) -> Unit,
    clearDialogDate: () -> Unit,
    backPress: () -> Unit,
    deleteTransaction: () -> Unit,
    hideDeleteDialog: () -> Unit,
    onTransactionDeleteClicked: (id: Int) -> Unit,
    updateCurrentTabValue: (selectedTab: Int) -> Unit,
    onAccountSpinnerValueChanged: (value: String, isCredit: Boolean, isDebit: Boolean) -> Unit
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
            navControllerRoot.navigate("${AddScreen.CREATE_UPDATE_TRANSACTION}/${CREATE_SCREEN}")
        }
    }) { it ->
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (showDeleteDialog) {
                ExpenseTrackerDialog(
                    title = "Delete Transaction",
                    message = "Do you want to delete this transaction ?",
                    okButtonText = "Delete",
                    cancelButtonText = "Cancel",
                    onOkClick = deleteTransaction,
                    onCancelClicked = hideDeleteDialog
                )
            } else if (dialogState.showDialog) {
                FilterDialog(
                    dialogState = dialogState,
                    updateTransactionList = updateTransactionList,
                    checkValidDate = checkValidDate,
                    hideDialog = toggleDialogVisibility,
                    updateFromDate = updateFromDate,
                    updateToDate = updateToDate
                ) {
                    clearDialogDate()
                    updateTransactionList()

                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                ExpenseTrackerSpinner(values = accounts,
                    initialValue = selectedAccount,
                    onValueChanged = { accountName ->
                        onAccountSpinnerValueChanged(
                            accountName, selectedTab == 1, selectedTab == 2
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
                            toggleDialogVisibility()
                        },
                    tint = Color.Unspecified
                )
            }
            ExpenseTrackerTabLayout(values = tabItemsList, initialValue = selectedTab, onClick = {
                updateCurrentTabValue(it)
                onAccountSpinnerValueChanged(
                    selectedAccount, it == 1, it == 2
                )
            })

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
                            amount = "$currency${getStringFromDouble(transactions[it].amount)}",
                            isCredit = transactions[it].isCredit,
                            transactionId = transactions[it].transactionId,
                            onDeleteIconClicked = onTransactionDeleteClicked,
                            onEditIconClicked = { transactionId -> navControllerRoot.navigate("${AddScreen.CREATE_UPDATE_TRANSACTION}/${transactionId}") },
                            accountName = transactions[it].accountName,
                            transactionDate = transactions[it].createdOn.toString()
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



