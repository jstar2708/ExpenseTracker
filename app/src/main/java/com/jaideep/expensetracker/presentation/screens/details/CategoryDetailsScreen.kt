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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconId
import com.jaideep.expensetracker.presentation.viewmodel.CategoryDetailsViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Preview
@Composable
private fun CategoryDetailsScreenPreview() {
    CategoryDetailsScreen(
        categoryIconId = R.drawable.entertainment,
        categoryName = "Entertainment",
        accounts = persistentListOf("All Accounts", "PNB"),
        transactions = persistentListOf(),
        dialogState = DialogState(false, "", "", false, ""),
        selectedAccount = "All Accounts",
        toggleDialogVisibility = {},
        onBackPress = {}
    )
}

@Composable
fun CategoryDetailsScreenRoot(navHostController: NavHostController, categoryName: String?) {
    val categoryDetailViewModel: CategoryDetailsViewModel = hiltViewModel()
    if (categoryName != null) {
        categoryDetailViewModel.initData(categoryName)
        val categoryIconName = categoryDetailViewModel.category.collectAsState().value?.iconName ?: ""
        CategoryDetailsScreen(
            categoryIconId = getCategoryIconId(categoryIconName),
            categoryName = categoryName,
            dialogState = categoryDetailViewModel.dialogState.value,
            selectedAccount = categoryDetailViewModel.accountValue.value,
            accounts = categoryDetailViewModel.,
            transactions = ,
            toggleDialogVisibility = { /*TODO*/ }) {

        }
    }
    else {
        // Display error message
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
    onBackPress: () -> Unit
) {
    Scaffold(
        topBar = {
            ExpenseTrackerAppBar(
                title = "Category Details",
                navigationIcon = Icons.Filled.ArrowBack,
                navigationDescription = "Back button",
                onNavigationIconClick = onBackPress,
                actionIcon = Icons.Filled.Edit,
                actionDescription = "Edit icon"
            ) {

            }
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.entertainment),
                    contentDescription = "category icon")
                HeadingTextBold(
                    modifier = Modifier.padding(8.dp),
                    text = "Entertainment"
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExpenseTrackerSpinner(
                    values = accounts,
                    initialValue = selectedAccount,
                    onValueChanged = { accountName ->

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