package com.jaideep.expensetracker.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaideep.expensetracker.common.DetailScreen
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.presentation.screens.add.AddAccountScreenRoot
import com.jaideep.expensetracker.presentation.screens.add.AddCategoryScreenRoot
import com.jaideep.expensetracker.presentation.screens.add.AddTransactionScreenRoot
import com.jaideep.expensetracker.presentation.screens.bottom.BottomNavigationScreen
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
        composable(DetailScreen.ADD_TRANSACTION) {
            AddTransactionScreenRoot(navHostControllerRoot)
        }
        composable(DetailScreen.ADD_ACCOUNT) {
            AddAccountScreenRoot(navHostControllerRoot, mainViewModel)
        }
        composable(DetailScreen.ADD_CATEGORY) {
            AddCategoryScreenRoot(navHostControllerRoot, mainViewModel)
        }
    }
}