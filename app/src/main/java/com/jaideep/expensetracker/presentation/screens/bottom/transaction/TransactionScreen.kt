package com.jaideep.expensetracker.presentation.screens.bottom.transaction

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.model.CategoryDto
import com.jaideep.expensetracker.model.TransactionDto
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTabLayout
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import java.time.LocalDate

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreenPreview() {
    AppTheme {
        TransactionScreen(
            navControllerRoot = NavController(Application()),
            backPress = {},
            accounts = listOf("All accounts", "Cash"),
            transactions = listOf(
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
                TransactionDto(
                    200.0,
                    CategoryDto("Food", R.drawable.food),
                    "No Message",
                    LocalDate.now(),
                    true
                ),
            )
        )
    }
}

@Composable
fun TransactionScreenRoot(
    navHostControllerRoot: NavHostController, mainViewModel: MainViewModel, backPress: () -> Unit
) {
    TransactionScreen(navControllerRoot = navHostControllerRoot,
        backPress = backPress,
        accounts = mainViewModel.accounts.collectAsState().value.toMutableList().apply {
            this.add(0, "All Accounts")
        },
        transactions = mainViewModel.transactionItems.collectAsState().value.collectAsLazyPagingItems().itemSnapshotList.items.map { transaction ->
            TransactionDto(
                transaction.amount,
                mainViewModel.getCategoryDto(transaction.categoryId),
                transaction.message,
                LocalDate.ofEpochDay(transaction.createdTime / 86_400_000L),
                transaction.isCredit == 1
            )
        })
}

//@Preview(showSystemUi = true, showBackground = true)
@Composable

fun TransactionScreen(
    navControllerRoot: NavController,
    backPress: () -> Unit,
    accounts: List<String>,
    transactions: List<TransactionDto>
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
            ExpenseTrackerSpinner(values = accounts, onValueChanged = {

            })
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                ExpenseTrackerTabLayout(values = arrayOf("All", "Income", "Expense"), onClick = {})
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter",
                    modifier = Modifier
                        .weight(.2f)
                        .size(30.dp)
                        .clip(CircleShape)
                        .clickable {

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
                            amount = transactions[it].amount.toString()
                        )
                    }
                }
            }
            else {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    SimpleText(
                        modifier = Modifier
                            .fillMaxWidth(),
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
