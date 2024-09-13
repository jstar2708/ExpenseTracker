package com.jaideep.expensetracker.presentation.screens.details

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.model.DialogState
import com.jaideep.expensetracker.model.dto.TransactionDto
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.component.card.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.component.dialog.FilterDialog
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.viewmodel.CategoryDetailsViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
private fun CategoryDetailsScreenPreview() {
    CategoryDetailsScreen(categoryIconId = R.drawable.entertainment,
        categoryName = "Entertainment",
        accounts = persistentListOf("All Accounts", "PNB"),
        transactions = persistentListOf(),
        dialogState = DialogState(false, "", "", false, ""),
        selectedAccount = "All Accounts",
        toggleDialogVisibility = {},
        onBackPress = {},
        onAccountSpinnerValueChanged = {},
        updateTransactionList = {},
        clearDialogDate = {},
        updateFromDate = {},
        updateToDate = {},
        checkValidDate = { true })
}

@Composable
fun CategoryDetailsScreenRoot(navHostController: NavHostController, categoryName: String?) {
    val categoryDetailViewModel: CategoryDetailsViewModel = hiltViewModel()
    if (categoryName != null) {
        categoryDetailViewModel.initData(categoryName)

        if (categoryDetailViewModel.isCategoryLoading.value || categoryDetailViewModel.isAccountLoading.value || categoryDetailViewModel.isTransactionsLoading.value) {
            ExpenseTrackerProgressBar(Modifier.size(50.dp))
        }
        else if (categoryDetailViewModel.categoryRetrievalError.value || categoryDetailViewModel.transactionsRetrievalError.value || categoryDetailViewModel.accountRetrievalError.value) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Center
            ) {
                SimpleText(
                    text = "Error loading user data", color = Color.Red
                )
            }
        }
        else {
            CategoryDetailsScreen(
                categoryIconId = categoryDetailViewModel.category.collectAsState().value?.iconId ?: 0,
                categoryName = categoryName,
                dialogState = categoryDetailViewModel.dialogState.value,
                selectedAccount = categoryDetailViewModel.accountValue.value,
                accounts = categoryDetailViewModel.accounts.collectAsState().value.toImmutableList(),
                transactions = categoryDetailViewModel.transactions.collectAsState().value.toImmutableList(),
                toggleDialogVisibility = categoryDetailViewModel::toggleDialogVisibility,
                onBackPress = { navHostController.popBackStack() },
                onAccountSpinnerValueChanged = categoryDetailViewModel::onAccountSpinnerValueChanged,
                clearDialogDate = categoryDetailViewModel::clearDialogDate,
                updateTransactionList = categoryDetailViewModel::updateTransactionList,
                updateToDate = categoryDetailViewModel::updateToDate,
                updateFromDate = categoryDetailViewModel::updateFromDate,
                checkValidDate = categoryDetailViewModel::checkValidDate
            )
        }
    } else {
        // Display error message
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            SimpleText(
                text = "Error loading user data", color = Color.Red
            )
        }
    }
}


@Composable
fun CategoryDetailsScreen(
    categoryIconId: Int,
    categoryName: String,
    dialogState: DialogState,
    selectedAccount: String,
    accounts: ImmutableList<String>,
    transactions: ImmutableList<TransactionDto>,
    toggleDialogVisibility: () -> Unit,
    onAccountSpinnerValueChanged: (value: String) -> Unit,
    updateTransactionList: () -> Unit,
    checkValidDate: () -> Boolean,
    updateFromDate: (value: String) -> Unit,
    updateToDate: (value: String) -> Unit,
    clearDialogDate: () -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(topBar = {
        ExpenseTrackerAppBar(
            title = "Category Details",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Back button",
            onNavigationIconClick = onBackPress,
            actionIcon = Icons.Filled.Edit,
            actionDescription = "Edit icon"
        ) {

        }
    }) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if (dialogState.showDialog) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp),
                    painter = painterResource(id = categoryIconId),
                    contentDescription = "category icon"
                )
                HeadingTextBold(
                    modifier = Modifier.padding(8.dp), text = categoryName
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExpenseTrackerSpinner(
                    values = accounts,
                    initialValue = selectedAccount,
                    onValueChanged = onAccountSpinnerValueChanged
                )
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter",
                    modifier = Modifier
                        .weight(.2f)
                        .size(30.dp)
                        .clip(CircleShape)
                        .clickable(onClick = toggleDialogVisibility),
                    tint = Color.Unspecified
                )
            }

            SimpleTextBold(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                text = "Transactions"
            )

            if (transactions.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    items(transactions.size) { i ->
                        ExpenseTrackerTransactionCardItem(
                            iconId = transactions[i].categoryDto.iconId,
                            iconDescription = "Category icon",
                            categoryName = transactions[i].categoryDto.name,
                            transactionDescription = transactions[i].message,
                            amount = transactions[i].amount.toString(),
                            isCredit = transactions[i].isCredit
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
    }

}