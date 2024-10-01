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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import com.jaideep.expensetracker.presentation.component.MediumText
import com.jaideep.expensetracker.presentation.component.button.SmallPrimaryColorButton
import com.jaideep.expensetracker.presentation.component.textfield.TextFieldWithIconAndErrorPopUp
import com.jaideep.expensetracker.presentation.theme.AppTheme
import com.jaideep.expensetracker.presentation.viewmodel.AuthViewModel

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
        ), onRegister = {})
    }
}

@Composable
fun RegisterScreenRoot() {
    val authViewModel: AuthViewModel = hiltViewModel()
    RegisterScreen(
        nameState = authViewModel.nameState,
        passwordState = authViewModel.passwordState,
        confirmPasswordState = authViewModel.confirmPasswordState,
        onRegister = authViewModel::onRegister
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RegisterScreen(
    nameState: TextFieldWithIconAndErrorPopUpState,
    passwordState: TextFieldWithIconAndErrorPopUpState,
    confirmPasswordState: TextFieldWithIconAndErrorPopUpState,
    onRegister: () -> Unit
) {
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

            MediumText(
                modifier = Modifier
                    .weight(.05f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "Hello There!\nEnter your details to get started",
                textAlignment = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(.3f)
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

                Spacer(modifier = Modifier.height(16.dp))

                TextFieldWithIconAndErrorPopUp(
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

                Spacer(modifier = Modifier.height(40.dp))
                SmallPrimaryColorButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "Register"
                ) {

                }
            }
        }
    }
}