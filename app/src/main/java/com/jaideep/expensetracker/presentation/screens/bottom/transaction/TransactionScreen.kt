package com.jaideep.expensetracker.presentation.screens.bottom.transaction

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTabLayout
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import com.jaideep.expensetracker.presentation.viewmodel.TransactionViewModel

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreenPreview() {
    AppTheme {
        TransactionScreen(NavController(Application()))
    }
}
//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreen(navController: NavController, viewModel: MainViewModel = hiltViewModel()) {
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val resultTransaction = savedStateHandle?.get<Boolean>("isTransactionSaved")

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        snackbarHost = {
                       SnackbarHost(hostState = snackBarHostState) {
                           Snackbar(
                               snackbarData = it,
                               containerColor = Color.DarkGray
                           )
                       }
        },
        topBar = {
            ExpenseTrackerAppBar(
                title = "Transactions",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationDescription = "Back button",
                onNavigationIconClick = { navController.popBackStack() },
                actionIcon = Icons.Filled.Add,
                actionDescription = "Add transaction icon"
            ) {
                navController.navigate(DetailScreen.ADD_TRANSACTION)
            }
        }
    ) { it ->
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
        ) {
//            AccountSelectionSpinner()
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExpenseTrackerTabLayout(
                    values = arrayOf("All", "Income", "Expense"),
                    onClick = {}
                )
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

            val lazyTransactionItems = viewModel.transactionItems.collectAsLazyPagingItems()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                items(lazyTransactionItems.itemCount) {
                    ExpenseTrackerTransactionCardItem(
                        iconId = R.drawable.fuel,
                        iconDescription = "Fuel icon",
                        categoryName = lazyTransactionItems[it]?.categoryId.toString(),
                        transactionDescription = lazyTransactionItems[it]?.message.toString(),
                        amount = lazyTransactionItems[it]?.amount.toString()
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