package com.jaideep.expensetracker.presentation.viewmodel

import android.provider.Telephony.Carriers.PASSWORD
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.AppConstants.USER_NAME
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepository
import com.jaideep.expensetracker.domain.usecase.calculation.GetAccountsCount
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
    private val getAccountsCount: GetAccountsCount
) : ViewModel() {

    var loginComplete by mutableStateOf(false)
    var userNotPresent by mutableStateOf(true)
    var username by mutableStateOf("")
    var isUsernameLoading by mutableStateOf(true)
    var isAccountCountLoading by mutableStateOf(true)

    var accountCountRetrievalError by mutableStateOf(false)
    var accountCount by mutableStateOf(false)

    var passwordState by mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(text = "",
            isError = false,
            showError = false,
            errorMessage = "",
            onValueChange = { updatePasswordTextState(it) },
            onErrorIconClick = { updatePasswordErrorState() })
    )

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

    fun onLogin() {
        if (!checkErrorsInLogin()) {
            verifyPassword()
        }
    }

    private fun verifyPassword() = viewModelScope.launch(EtDispatcher.io) {
        val password = datastoreRepository.getString(PASSWORD)
        if (password == passwordState.text) {
            loginComplete = true
        } else {
            passwordState = TextFieldWithIconAndErrorPopUpState(
                text = passwordState.text,
                isError = true,
                showError = passwordState.showError,
                errorMessage = "Passwords do not match",
                onValueChange = passwordState.onValueChange,
                onErrorIconClick = passwordState.onErrorIconClick
            )
        }
    }

    private fun checkErrorsInLogin(): Boolean {
        var hasError = false
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
        return hasError
    }

    fun isUserPresent() = viewModelScope.launch(EtDispatcher.io) {
        isUsernameLoading = true
        val username = datastoreRepository.getString(USER_NAME)
        if (username != null) {
            this@LoginViewModel.username = username
            userNotPresent = false
        } else {
            userNotPresent = true
        }
        isUsernameLoading = false
    }

    fun checkAccountCreated() = viewModelScope.launch(EtDispatcher.io) {
        getAccountsCount().collect {
            when(it) {
                is Resource.Error -> {
                    accountCountRetrievalError = true
                    isAccountCountLoading = false
                }
                is Resource.Loading -> {
                    isAccountCountLoading = true
                    accountCountRetrievalError = false
                }
                is Resource.Success -> {
                    accountCountRetrievalError = false
                    isAccountCountLoading = false
                    accountCount = it.data < 1
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.e("ON_CLEARED", "loginViewModel")
    }

    fun onBiometricAuthSuccess() {
        loginComplete = true
    }
}