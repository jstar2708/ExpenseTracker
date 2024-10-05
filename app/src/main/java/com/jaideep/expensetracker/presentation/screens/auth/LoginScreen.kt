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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.AuthScreen
import com.jaideep.expensetracker.common.Graph
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.HeadingTextBold
import com.jaideep.expensetracker.presentation.component.MediumText
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.component.other.ExpenseTrackerProgressBar
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.AuthViewModel

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

        }) {

        }
    }
}

@Composable
fun LoginScreenRoot(navController: NavController, authViewModel: AuthViewModel) {
    if (authViewModel.isUsernameLoading) {
        ExpenseTrackerProgressBar(Modifier.size(50.dp))
    }
    else {
        LoginScreen(passwordState = authViewModel.passwordState,
            isLoginCompleted = authViewModel.loginComplete,
            onLogin = authViewModel::onLogin,
            username = authViewModel.username,
            userNotPresent = authViewModel.userNotPresent,
            navigateToMainScreen = {
                navController.navigate(Graph.MAIN)
            },
            navigateToRegisterScreen = {
                navController.navigate(AuthScreen.REGISTER) {
                    navOptions {
                        this.launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId)
                    }
                }
            })
    }
}

@Composable
fun LoginScreen(
    passwordState: TextFieldWithIconAndErrorPopUpState,
    isLoginCompleted: Boolean,
    username: String,
    userNotPresent: Boolean,
    onLogin: () -> Unit,
    navigateToMainScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {
    if (isLoginCompleted) {
        navigateToMainScreen()
    } else if (userNotPresent) {
        navigateToRegisterScreen()
    }
    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                modifier = Modifier.weight(.15f),
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = "App icon"
            )

            MediumText(text = "Welcome back")
            HeadingTextBold(
                modifier = Modifier
                    .weight(.05f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = username,
                textAlignment = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(.2f)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                TextFieldWithIconAndErrorPopUp(
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

                Spacer(modifier = Modifier.height(40.dp))

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