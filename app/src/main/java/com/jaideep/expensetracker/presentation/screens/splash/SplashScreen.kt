package com.jaideep.expensetracker.presentation.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel
) {
    Surface {
        Column {
            Image(painter = painterResource(id = R.drawable.app_icon), contentDescription = "App icon")
        }
        LaunchedEffect(key1 = true) {
            loginViewModel.isUserPresent()
            delay(3000)
        }
    }
}