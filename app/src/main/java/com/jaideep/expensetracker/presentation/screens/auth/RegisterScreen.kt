package com.jaideep.expensetracker.presentation.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
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
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.AddScreen
import com.jaideep.expensetracker.common.constant.AppConstants.CREATE_SCREEN
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.MediumText
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.component.textfield.PasswordTextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.RegisterViewModel

@Preview()
@Composable
private fun RegisterScreenPreview() {
    AppTheme {
        RegisterScreen(nameState = TextFieldWithIconAndErrorPopUpState("",
            isError = false,
            showError = false,
            onValueChange = { _ -> },
            onErrorIconClick = {},
            errorMessage = ""
        ), passwordState = TextFieldWithIconAndErrorPopUpState("",
            isError = false,
            showError = false,
            onValueChange = { _ -> },
            onErrorIconClick = {},
            errorMessage = ""
        ), confirmPasswordState = TextFieldWithIconAndErrorPopUpState("",
            isError = false,
            showError = false,
            onValueChange = { _ -> },
            onErrorIconClick = {},
            errorMessage = ""
        ), onRegister = {}, isRegistrationCompleted = false, navigateToCUAccount = {})
    }
}

@Composable
fun RegisterScreenRoot(navController: NavController) {
    val registerViewModel = hiltViewModel<RegisterViewModel>()
    RegisterScreen(nameState = registerViewModel.nameState,
        passwordState = registerViewModel.passwordState,
        confirmPasswordState = registerViewModel.confirmPasswordState,
        onRegister = registerViewModel::onRegister,
        isRegistrationCompleted = registerViewModel.registrationComplete,
        navigateToCUAccount = {
            navController.navigate("${AddScreen.CREATE_UPDATE_ACCOUNT}/$CREATE_SCREEN") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        })
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RegisterScreen(
    nameState: TextFieldWithIconAndErrorPopUpState,
    passwordState: TextFieldWithIconAndErrorPopUpState,
    confirmPasswordState: TextFieldWithIconAndErrorPopUpState,
    isRegistrationCompleted: Boolean,
    navigateToCUAccount: () -> Unit,
    onRegister: () -> Unit
) {
    LaunchedEffect(key1 = isRegistrationCompleted) {
        if (isRegistrationCompleted) {
            navigateToCUAccount()
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
                modifier = Modifier.weight(.3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "App icon"
                )

                Spacer(modifier = Modifier.height(40.dp))

                MediumText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Hello There!\nEnter your details to get started",
                    textAlignment = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(.2f), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                TextFieldWithIconAndErrorPopUp(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Name",
                    icon = Icons.Filled.Person,
                    iconColor = Color.LightGray,
                    borderColor = Color.Gray,
                    text = nameState.text,
                    isError = nameState.isError,
                    errorMessage = nameState.errorMessage,
                    showErrorText = nameState.showError,
                    onValueChange = nameState.onValueChange,
                    onErrorIconClick = nameState.onErrorIconClick
                )

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

                PasswordTextFieldWithIconAndErrorPopUp(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    label = "Re-enter Password ",
                    icon = Icons.Filled.Password,
                    iconColor = Color.LightGray,
                    borderColor = Color.Gray,
                    text = confirmPasswordState.text,
                    isError = confirmPasswordState.isError,
                    errorMessage = confirmPasswordState.errorMessage,
                    showErrorText = confirmPasswordState.showError,
                    onValueChange = confirmPasswordState.onValueChange,
                    onErrorIconClick = confirmPasswordState.onErrorIconClick
                )

                SmallPrimaryColorButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Register",
                    onClick = onRegister
                )
            }
        }
    }
}