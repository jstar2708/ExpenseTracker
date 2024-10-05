package com.jaideep.expensetracker.presentation.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jaideep.expensetracker.common.AuthScreen
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.presentation.screens.auth.LoginScreenRoot
import com.jaideep.expensetracker.presentation.screens.auth.RegisterScreenRoot
import com.jaideep.expensetracker.presentation.viewmodel.AuthViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthScreen.LOGIN, route = Graph.AUTH
    ) {
        composable(AuthScreen.LOGIN) { navBackStackEntry ->
            val startEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(Graph.AUTH)
            }
            val authViewModel: AuthViewModel = hiltViewModel(startEntry)
            LoginScreenRoot(navController, authViewModel)
        }
        composable(AuthScreen.REGISTER) { navBackStackEntry ->
            val startEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(Graph.AUTH)
            }
            val authViewModel: AuthViewModel = hiltViewModel(startEntry)
            RegisterScreenRoot(navController, authViewModel)
        }
    }
}