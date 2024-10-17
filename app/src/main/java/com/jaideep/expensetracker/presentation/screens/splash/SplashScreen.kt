package com.jaideep.expensetracker.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.AuthScreen
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Preview
@Composable
private fun SplashScreenPreview() {
    AppTheme {
        SplashScreen()
    }
}

@Composable
fun SplashScreenRoot(navController: NavHostController, loginViewModel: LoginViewModel) {
    LaunchedEffect(key1 = true) {
        loginViewModel.checkAccountCreated()
    }
    LaunchedEffect(key1 = loginViewModel.isUsernameLoading) {
        loginViewModel.isUserPresent()
        if (!loginViewModel.isUsernameLoading && !loginViewModel.userNotPresent) {
            navController.navigate(AuthScreen.LOGIN, navOptions = navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            })
        }
        else if (!loginViewModel.isUsernameLoading && loginViewModel.userNotPresent){
            navController.navigate(AuthScreen.REGISTER, navOptions = navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            })
        }
    }
    SplashScreen()
}

@Composable
fun SplashScreen() {
    Surface(Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_icon), contentDescription = "App icon"
            )
        }
    }
}