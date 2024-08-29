package com.jaideep.expensetracker.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.common.MainScreen
import com.jaideep.expensetracker.presentation.screens.bottom.category.CategoryScreenRoot
import com.jaideep.expensetracker.presentation.screens.bottom.home.HomeScreenRoot
import com.jaideep.expensetracker.presentation.screens.bottom.settings.SettingsScreen
import com.jaideep.expensetracker.presentation.screens.bottom.transaction.TransactionScreenRoot
import com.jaideep.expensetracker.presentation.viewmodel.HomeViewModel
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel
import com.jaideep.expensetracker.presentation.viewmodel.TransactionViewModel

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
        composable(MainScreen.HOME) { backStackEntry ->
            val startEntry = remember(backStackEntry) {
                navHostControllerRoot.getBackStackEntry(Graph.MAIN)
            }
            HomeScreenRoot(
                navControllerRoot = navHostControllerRoot,
                mainViewModel = mainViewModel,
                homeViewModel = hiltViewModel<HomeViewModel>(startEntry)
            )
        }
        composable(MainScreen.TRANSACTIONS) { backStackEntry ->
            val startEntry = remember(backStackEntry) {
                navHostControllerRoot.getBackStackEntry(Graph.MAIN)
            }
            TransactionScreenRoot(
                navHostControllerRoot,
                mainViewModel,
                transactionViewModel = hiltViewModel<TransactionViewModel>(startEntry)
            ) {
                bottomNavController.popBackStack()
            }
        }
        composable(MainScreen.CATEGORY) { backStackEntry ->
            val startEntry = remember(backStackEntry) {
                navHostControllerRoot.getBackStackEntry(Graph.MAIN)
            }
            CategoryScreenRoot(
                navHostControllerRoot,
                mainViewModel,
            ) {
                bottomNavController.popBackStack()
            }
        }
        composable(MainScreen.SETTINGS) {
            SettingsScreen(bottomNavController)
        }
    }
}