package com.jaideep.expensetracker.presentation.screens.bottom.home


import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerBlueButton
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerCategoryCard
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.SimpleTextBold
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.R

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(NavController(Application()))
    }
}



@Composable
fun HomeScreen(navControllerRoot: NavController) {
    val savedStateHandle = navControllerRoot.currentBackStackEntry?.savedStateHandle
    val resultAccount =
        savedStateHandle?.get<Boolean>("isAccountSaved")

    val resultTransaction =
        savedStateHandle?.get<Boolean>("isTransactionSaved")

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                           SnackbarHost(
                               hostState = snackBarHostState
                           ) {
                               Snackbar(
                                   snackbarData = it,
                                   containerColor = Color.DarkGray
                               )
                           }
            },
            topBar = {
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
                ExpenseTrackerSpinner(
                    values = listOf("All Account", "Cash"),
                    onValueChanged = { }
                )

                SummaryCard()

                TransactionSummary()
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

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionSummary() {
    SimpleTextBold(
        modifier = Modifier.padding(8.dp), text = "Last Transactions"
    )

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .wrapContentHeight()
    ) {

        for (i in 0 until 5)
            ExpenseTrackerTransactionCardItem(
            iconId = R.drawable.fuel,
            iconDescription = "Fuel icon",
            categoryName = "Fuel",
            transactionDescription = "Petrol in scooter",
            amount = "$49"
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SummaryCard() {
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
                        text = "Monthly Limit \$6000",
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    )
                }
                HeadingTextBold(
                    text = "$4500", modifier = Modifier.weight(1f), textAlignment = TextAlign.Center
                )
            }
            ExpenseTrackerCategoryCard(
                iconId = R.drawable.food,
                iconDescription = "Food icon",
                categoryName = "Food",
                spendValue = "$0 / $1000",
                progressValue = 0.8f,
                trackColor = Color.Yellow
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

