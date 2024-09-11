package com.jaideep.expensetracker.presentation.screens.bottom.category

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.common.AddScreen
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.CategoryCardPayload
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.card.ExpenseTrackerCategoryCard
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerAppBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerSpinner
import com.jaideep.expensetracker.presentation.utility.Utility
import com.jaideep.expensetracker.presentation.viewmodel.CategoryViewModel
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen(navControllerRoot = NavController(Application()),
        accounts = persistentListOf(),
        categoryCards = persistentListOf(),
        durations = persistentListOf(),
        accountSpinnerValue = "All Accounts",
        durationSpinnerValue = "This Month",
        onAccountSpinnerValueChange = {},
        onDurationSpinnerValueChange = {},
        backPress = {})
}

@Composable
fun CategoryScreenRoot(
    navHostControllerRoot: NavHostController,
    mainViewModel: MainViewModel,
    categoryViewModel: CategoryViewModel,
    backPress: () -> Unit
) {
    if (mainViewModel.isAccountLoading || mainViewModel.isTransactionLoading || mainViewModel.isCategoryLoading || mainViewModel.isCategoryCardDataLoading) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    } else if (mainViewModel.transactionRetrievalError || mainViewModel.accountRetrievalError || mainViewModel.categoryRetrievalError || mainViewModel.categoryCardDataRetrievalError) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center
        ) {
            SimpleText(
                text = "Error loading user data", color = Color.Red
            )
        }
    } else {
        CategoryScreen(
            navControllerRoot = navHostControllerRoot,
            accounts = mainViewModel.accounts.collectAsState().value.toImmutableList(),
            categoryCards = mainViewModel.categoryCardsData.collectAsState().value.toImmutableList(),
            durations = categoryViewModel.durationList,
            accountSpinnerValue = categoryViewModel.accountValue.value,
            durationSpinnerValue = categoryViewModel.durationValue.value,
            onAccountSpinnerValueChange = {
                categoryViewModel.onAccountSpinnerValueChanged(it)
                mainViewModel.updateCategoryCardsData(
                    CategoryCardPayload(
                        it, categoryViewModel.durationValue.value
                    )
                )
            },
            onDurationSpinnerValueChange = {
                categoryViewModel.onDurationSpinnerValueChanged(it)
                mainViewModel.updateCategoryCardsData(
                    CategoryCardPayload(
                        categoryViewModel.accountValue.value, it
                    )
                )
            },
            backPress = backPress
        )
    }
}

@Composable
fun CategoryScreen(
    navControllerRoot: NavController,
    accounts: ImmutableList<String>,
    categoryCards: ImmutableList<CategoryCardData>,
    durations: ImmutableList<String>,
    accountSpinnerValue: String,
    durationSpinnerValue: String,
    onAccountSpinnerValueChange: (value: String) -> Unit,
    onDurationSpinnerValueChange: (value: String) -> Unit,
    backPress: () -> Unit
) {
    val savedStateHandle = navControllerRoot.currentBackStackEntry?.savedStateHandle
    val result = savedStateHandle?.get<Boolean>("isCategorySaved")

    val snackBarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) {
            Snackbar(
                snackbarData = it, containerColor = Color.DarkGray
            )
        }
    }, topBar = {
        ExpenseTrackerAppBar(
            title = "Categories",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            navigationDescription = "Back button",
            onNavigationIconClick = { backPress() },
            actionIcon = Icons.Filled.Add,
            actionDescription = "Add Category icon"
        ) {
            navControllerRoot.navigate(AddScreen.ADD_CATEGORY)
        }
    }) {
        Column(Modifier.padding(it)) {
            Row (modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                ExpenseTrackerSpinner(
                    modifier = Modifier.weight(1f).padding(end = 4.dp),
                    initialValue = accountSpinnerValue,
                    values = accounts,
                    onValueChanged = onAccountSpinnerValueChange
                )
                ExpenseTrackerSpinner(
                    modifier = Modifier.weight(1f).padding(start = 4.dp),
                    initialValue = durationSpinnerValue,
                    values = durations,
                    onValueChanged = onDurationSpinnerValueChange
                )
            }
            LazyColumn {
                items(categoryCards) { categoryCardData ->
                    ExpenseTrackerCategoryCard(
                        iconId = Utility.getCategoryIconId(categoryCardData.iconName),
                        iconDescription = categoryCardData.iconName,
                        categoryName = categoryCardData.categoryName,
                        spendValue = "$${categoryCardData.amountSpent} / ${1000}",
                        progressValue = 0.8f,
                        trackColor = Color.Yellow
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = result) {
        if (result != null) {
            if (result == true) snackBarHostState.showSnackbar("Category saved successfully")
            else snackBarHostState.showSnackbar("Error while saving category")
            savedStateHandle["isCategorySaved"] = null
        }
    }
}

