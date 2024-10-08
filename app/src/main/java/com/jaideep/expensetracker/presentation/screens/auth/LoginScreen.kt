package com.jaideep.expensetracker.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.AddScreen
import com.jaideep.expensetracker.common.AuthScreen
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.common.constant.AppConstants.CREATE_SCREEN
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.MediumBoldText
import com.jaideep.expensetracker.presentation.component.SimpleText
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.textfield.PasswordTextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.LoginViewModel

@Preview
@Composable
private fun LoginScreenPreview() {
    AppTheme {
        LoginScreen(passwordState = TextFieldWithIconAndErrorPopUpState(
            "",
            isError = false,
            showError = false,
            onValueChange = { _ -> },
            onErrorIconClick = {},
            errorMessage = "",
        ), isLoginCompleted = false, username = "Jaideep Kumar Singh", onLogin = {

        }, userNotPresent = false, navigateToMainScreen = {

        }, createAccount = false, navigateToCUAccount = {}, navigateToRegisterScreen = {})
    }
}

@Composable
fun LoginScreenRoot(navController: NavController) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    LaunchedEffect(key1 = true) {
        loginViewModel.isUserPresent()
        loginViewModel.checkAccountCreated()
    }
    if (loginViewModel.isUsernameLoading) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    } else {
        LoginScreen(passwordState = loginViewModel.passwordState,
            isLoginCompleted = loginViewModel.loginComplete,
            onLogin = loginViewModel::onLogin,
            username = loginViewModel.username,
            userNotPresent = loginViewModel.userNotPresent,
            createAccount = loginViewModel.createAccount,
            navigateToMainScreen = {
                navController.navigate(Graph.MAIN).apply {
                    navOptions {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            },
            navigateToRegisterScreen = {
                navController.navigate(AuthScreen.REGISTER).apply {
                    navOptions {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            },
            navigateToCUAccount = { navController.navigate("${AddScreen.CREATE_UPDATE_ACCOUNT}/$CREATE_SCREEN") }).apply {
            navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    passwordState: TextFieldWithIconAndErrorPopUpState,
    isLoginCompleted: Boolean,
    username: String,
    createAccount: Boolean,
    userNotPresent: Boolean,
    onLogin: () -> Unit,
    navigateToMainScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    navigateToCUAccount: () -> Unit
) {

    LaunchedEffect(key1 = isLoginCompleted && createAccount) {
        if (isLoginCompleted && createAccount) {
            navigateToCUAccount()
        }
    }

    LaunchedEffect(key1 = isLoginCompleted && !createAccount) {
        if (isLoginCompleted && !createAccount) {
            navigateToMainScreen()
        }
    }
    LaunchedEffect(key1 = userNotPresent) {
        if (userNotPresent) {
            navigateToRegisterScreen()
        }
    }
    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "App icon"
                )
                Spacer(modifier = Modifier.height(40.dp))

                SimpleText(text = "Welcome back")

                MediumBoldText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = username,
                    textAlignment = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(.2f), verticalArrangement = Arrangement.SpaceEvenly
            ) {

                PasswordTextFieldWithIconAndErrorPopUp(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Password",
                    icon = Icons.Filled.Password,
                    iconColor = Color.LightGray,
                    borderColor = Color.Gray,
                    text = passwordState.text,
                    isError = passwordState.isError,
                    errorMessage = passwordState.errorMessage,
                    showErrorText = passwordState.showError,
                    onValueChange = passwordState.onValueChange,
                    onErrorIconClick = passwordState.onErrorIconClick
                )

                SmallPrimaryColorButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Login",
                    onClick = onLogin
                )
            }
        }
    }
}