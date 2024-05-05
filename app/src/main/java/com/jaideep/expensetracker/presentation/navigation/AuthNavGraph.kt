package com.jaideep.expensetracker.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jaideep.expensetracker.common.AuthGraph
import com.jaideep.expensetracker.common.Graph

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AuthGraph.LOGIN.route, route = Graph.AUTH
    ) {
        composable(AuthGraph.LOGIN.route) {
//            LoginScreen(navController)
        }
        composable(AuthGraph.REGISTER.route) {
//            RegisterScreen(navController)
        }
        composable(AuthGraph.SPLASH.route) {
//            SplashScreen(navController)
        }
    }
}