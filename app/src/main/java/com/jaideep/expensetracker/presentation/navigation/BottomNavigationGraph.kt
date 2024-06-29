package com.jaideep.expensetracker.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.common.MainScreen
import com.jaideep.expensetracker.presentation.screens.bottom.category.CategoryScreenRoot
import com.jaideep.expensetracker.presentation.screens.bottom.home.HomeScreen
import com.jaideep.expensetracker.presentation.screens.bottom.settings.SettingsScreen
import com.jaideep.expensetracker.presentation.screens.bottom.transaction.TransactionScreen
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel

@Composable
fun BottomNavigationGraph(
    bottomNavController: NavHostController,
    navHostControllerRoot: NavHostController,
    value: PaddingValues,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = bottomNavController,
        startDestination = MainScreen.HOME,
        modifier = Modifier.padding(value),
        route = Graph.MAIN
    ) {
        composable(MainScreen.HOME) {
            HomeScreen(navHostControllerRoot)
        }
        composable(MainScreen.TRANSACTIONS) {
            TransactionScreen(navHostControllerRoot, mainViewModel)
        }
        composable(MainScreen.CATEGORY) {
            CategoryScreenRoot(navHostControllerRoot, mainViewModel)
        }
        composable(MainScreen.SETTINGS) {
            SettingsScreen(bottomNavController)
        }
    }
}