package com.jaideep.expensetracker.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jaideep.expensetracker.common.AuthScreen
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.presentation.screens.auth.LoginScreenRoot
import com.jaideep.expensetracker.presentation.screens.auth.RegisterScreenRoot
import com.jaideep.expensetracker.presentation.viewmodel.LoginViewModel
import com.jaideep.expensetracker.presentation.viewmodel.MainViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    navigation(
        startDestination = AuthScreen.LOGIN, route = Graph.AUTH
    ) {
        composable(AuthScreen.LOGIN) {
            LoginScreenRoot(navController, loginViewModel)
        }
        composable(AuthScreen.REGISTER) {
            RegisterScreenRoot(navController)
        }
    }
}