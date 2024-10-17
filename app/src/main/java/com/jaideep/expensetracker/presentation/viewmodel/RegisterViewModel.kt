package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepository
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {
    var registrationComplete by mutableStateOf(false)
        private set
    var nameState by mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(text = "",
            isError = false,
            showError = false,
            errorMessage = "",
            onValueChange = { updateNameTextState(it) },
            onErrorIconClick = { updateNameErrorState() })
    )
        private set

    var passwordState by mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(text = "",
            isError = false,
            showError = false,
            errorMessage = "",
            onValueChange = { updatePasswordTextState(it) },
            onErrorIconClick = { updatePasswordErrorState() })
    )

    var confirmPasswordState by mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(text = "",
            isError = false,
            showError = false,
            errorMessage = "",
            onValueChange = { updateConfirmPasswordTextState(it) },
            onErrorIconClick = { updateConfirmPasswordErrorState() })
    )

    private fun updateConfirmPasswordTextState(value: String) {
        confirmPasswordState = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = confirmPasswordState.isError,
            showError = confirmPasswordState.showError,
            errorMessage = confirmPasswordState.errorMessage,
            onValueChange = confirmPasswordState.onValueChange,
            onErrorIconClick = confirmPasswordState.onErrorIconClick
        )
    }

    private fun updateConfirmPasswordErrorState() {
        confirmPasswordState = TextFieldWithIconAndErrorPopUpState(
            text = confirmPasswordState.text,
            isError = confirmPasswordState.isError,
            showError = !confirmPasswordState.showError,
            errorMessage = confirmPasswordState.errorMessage,
            onValueChange = confirmPasswordState.onValueChange,
            onErrorIconClick = confirmPasswordState.onErrorIconClick
        )
    }

    private fun updatePasswordErrorState() {
        passwordState = TextFieldWithIconAndErrorPopUpState(
            text = passwordState.text,
            isError = passwordState.isError,
            showError = !passwordState.showError,
            errorMessage = passwordState.errorMessage,
            onValueChange = passwordState.onValueChange,
            onErrorIconClick = passwordState.onErrorIconClick
        )
    }

    private fun updatePasswordTextState(value: String) {
        passwordState = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = passwordState.isError,
            showError = passwordState.showError,
            errorMessage = passwordState.errorMessage,
            onValueChange = passwordState.onValueChange,
            onErrorIconClick = passwordState.onErrorIconClick
        )
    }

    private fun updateNameTextState(value: String) {
        nameState = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = nameState.isError,
            showError = nameState.showError,
            errorMessage = nameState.errorMessage,
            onValueChange = nameState.onValueChange,
            onErrorIconClick = nameState.onErrorIconClick
        )
    }

    private fun updateNameErrorState() {
        nameState = TextFieldWithIconAndErrorPopUpState(
            text = nameState.text,
            isError = nameState.isError,
            showError = !nameState.showError,
            errorMessage = nameState.errorMessage,
            onValueChange = nameState.onValueChange,
            onErrorIconClick = nameState.onErrorIconClick
        )
    }

    private fun checkErrorsInRegistration(): Boolean {
        var hasError = false
        if (nameState.text.isBlank()) {
            nameState = TextFieldWithIconAndErrorPopUpState(
                text = nameState.text,
                isError = true,
                showError = nameState.showError,
                errorMessage = "Name cannot be empty",
                onValueChange = nameState.onValueChange,
                onErrorIconClick = nameState.onErrorIconClick
            )
            hasError = true
        }
        if (passwordState.text.isBlank() && passwordState.text.length < 6) {
            passwordState = TextFieldWithIconAndErrorPopUpState(
                text = passwordState.text,
                isError = true,
                showError = passwordState.showError,
                errorMessage = if (passwordState.text.isBlank()) "Password cannot be blank" else "Password must be of 6 or more characters",
                onValueChange = passwordState.onValueChange,
                onErrorIconClick = passwordState.onErrorIconClick
            )
            hasError = true
        }
        if (confirmPasswordState.text.isBlank() && confirmPasswordState.text != passwordState.text) {
            confirmPasswordState = TextFieldWithIconAndErrorPopUpState(
                text = confirmPasswordState.text,
                isError = true,
                showError = confirmPasswordState.showError,
                errorMessage = "Passwords does not match ",
                onValueChange = confirmPasswordState.onValueChange,
                onErrorIconClick = confirmPasswordState.onErrorIconClick
            )
            hasError = true
        }

        return hasError
    }

    fun onRegister() {
        if (!checkErrorsInRegistration()) {
            saveUserDataToDatastore()
        }
    }

    private fun saveUserDataToDatastore() = viewModelScope.launch(EtDispatcher.io) {
        datastoreRepository.putString("username", nameState.text)
        datastoreRepository.putString("password", passwordState.text)
        registrationComplete = true
    }
}