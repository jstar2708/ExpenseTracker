package com.jaideep.expensetracker.presentation.screens.bottom.category

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerCategoryCard
import com.jaideep.expensetracker.presentation.component.ExpenseTrackerSpinner

@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen(navController = rememberNavController())
}

@Composable
fun CategoryScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        ExpenseTrackerAppBar(
            title = "Categories",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Back button",
            onNavigationIconClick = { navController.popBackStack() },
            actionIcon = Icons.Filled.Add,
            actionDescription = "Add Category icon"
        ) {
            navController.navigate(DetailScreen.ADD_CATEGORY)
        }
    }) {
        Column(Modifier.padding(it)) {
            Row {
                ExpenseTrackerSpinner(modifier = Modifier.weight(1f),
                    values = listOf("All Account", "Cash"),
                    onValueChanged = { })
                ExpenseTrackerSpinner(modifier = Modifier.weight(1f),
                    values = listOf("This Month", "This Year"),
                    onValueChanged = { })
            }
            for (i in 0..2) {
                ExpenseTrackerCategoryCard(
                    iconId = R.drawable.food,
                    iconDescription = "Food icon",
                    categoryName = "Food",
                    spendValue = "$0 / $1000",
                    progressValue = 0.8f,
                    trackColor = Color.Yellow
                )
            }
        }
    }
}

