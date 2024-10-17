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
import com.jaideep.expensetracker.presentation.screens.splash.SplashScreenRoot
import com.jaideep.expensetracker.presentation.viewmodel.LoginViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController, loginViewModel: LoginViewModel) {
    navigation(
        startDestination = AuthScreen.LOGIN, route = Graph.AUTH
    ) {
//        composable(AuthScreen.SPLASH) {
//            SplashScreenRoot(navController, loginViewModel)
//        }
        composable(AuthScreen.LOGIN) {
            LoginScreenRoot(navController, loginViewModel)
        }
        composable(AuthScreen.REGISTER) {
            RegisterScreenRoot(navController)
        }
    }
}