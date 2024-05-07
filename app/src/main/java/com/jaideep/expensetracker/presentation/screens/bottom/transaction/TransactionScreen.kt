package com.jaideep.expensetracker.presentation.screens.bottom.transaction

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.presentation.screens.component.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.screens.component.ExpenseTrackerTabLayout
import com.jaideep.expensetracker.presentation.screens.component.ExpenseTrackerTransactionCardItem
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.theme.md_theme_light_primary
import com.jaideep.expensetracker.presentation.utility.SimpleText
import com.jaideep.expensetracker.presentation.utility.SimpleTextBold

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreenPreview() {
    AppTheme {
        TransactionScreen(NavController(Application()))
    }
}
//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TransactionScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ExpenseTrackerAppBar(
                title = "Transactions",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                navigationDescription = "Back button",
                onNavigationIconClick = { navController.popBackStack() },
                actionIcon = Icons.Filled.Add,
                actionDescription = "Add transaction icon"
            ) {

            }
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .fillMaxWidth()
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

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .wrapContentHeight()
            ) {
                for (i in 0 until 10)
                    ExpenseTrackerTransactionCardItem(
                        iconId = R.drawable.fuel,
                        iconDescription = "Fuel icon",
                        categoryName = "Fuel",
                        transactionDescription = "Petrol in scooter",
                        amount = "$49"
                    )
            }
        }
    }
}