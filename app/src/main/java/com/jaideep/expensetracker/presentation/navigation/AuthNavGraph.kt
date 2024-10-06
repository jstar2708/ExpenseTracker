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
import com.jaideep.expensetracker.presentation.screens.splash.SplashScreen
import com.jaideep.expensetracker.presentation.viewmodel.LoginViewModel
import com.jaideep.expensetracker.presentation.viewmodel.RegisterViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthScreen.LOGIN, route = Graph.AUTH
    ) {
        composable(AuthScreen.SPLASH) { navBackStackEntry ->
            val startEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(Graph.AUTH)
            }
            val loginViewModel: LoginViewModel = hiltViewModel(startEntry)
            SplashScreen(navController, loginViewModel)
        }
        composable(AuthScreen.LOGIN) {
            LoginScreenRoot(navController)
        }
        composable(AuthScreen.REGISTER) {
            RegisterScreenRoot(navController)
        }
    }
}