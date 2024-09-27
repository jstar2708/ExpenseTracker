package com.jaideep.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jaideep.expensetracker.common.AddScreen
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.common.constant.AppConstants.CREATE_SCREEN
import com.jaideep.expensetracker.presentation.screens.cu.CUAccountScreenRoot
import com.jaideep.expensetracker.presentation.screens.cu.CUCategoryScreenRoot
import com.jaideep.expensetracker.presentation.screens.cu.CUTransactionScreenRoot
import com.jaideep.expensetracker.presentation.screens.bottom.BottomNavigationScreen
import com.jaideep.expensetracker.presentation.screens.details.CategoryDetailsScreenRoot
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel

@Composable
fun RootNavigationGraph(mainViewModel: MainViewModel) {
    val navHostControllerRoot = rememberNavController()
    NavHost(
        navController = navHostControllerRoot, startDestination = Graph.MAIN, route = Graph.ROOT
    ) {
        authNavGraph(navController = navHostControllerRoot)

        composable(route = Graph.MAIN) {
            BottomNavigationScreen(navHostControllerRoot, mainViewModel)
        }
        composable(
            AddScreen.CREATE_UPDATE_TRANSACTION_ROUTE, arguments = listOf(navArgument(name = "id") {
                this.type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val transactionId = navBackStackEntry.arguments?.getInt("id") ?: CREATE_SCREEN
            CUTransactionScreenRoot(navHostControllerRoot, transactionId)
        }
        composable(
            AddScreen.CREATE_UPDATE_ACCOUNT_ROUTE, arguments = listOf(navArgument(name = "id") {
                this.type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val accountId = navBackStackEntry.arguments?.getInt("id") ?: CREATE_SCREEN
            CUAccountScreenRoot(navHostControllerRoot, accountId)
        }
        composable(
            AddScreen.CREATE_UPDATE_CATEGORY_ROUTE, arguments = listOf(navArgument(name = "id") {
                this.type = NavType.IntType
            })
        ) { navBackStackEntry ->
            val categoryId = navBackStackEntry.arguments?.getInt("id") ?: CREATE_SCREEN
            CUCategoryScreenRoot(navHostControllerRoot, categoryId)
        }
        composable(
            DetailScreen.CATEGORY_DETAILS_ROUTE,
            arguments = listOf(navArgument(name = "categoryName") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val categoryName = navBackStackEntry.arguments?.getString("categoryName")
            CategoryDetailsScreenRoot(navHostControllerRoot, categoryName)
        }
    }
}