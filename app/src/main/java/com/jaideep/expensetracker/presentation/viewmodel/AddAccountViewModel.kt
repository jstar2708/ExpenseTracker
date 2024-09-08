package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.domain.usecase.GetAllAccountsUseCase
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase
) : ViewModel() {

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(ArrayList())

    init {
        getAllAccounts()
    }

    private fun getAllAccounts() = viewModelScope.launch(EtDispatcher.io) {
        getAllAccountsUseCase().collectLatest {
            when (it) {
                is Resource.Loading -> {
                    isAccountLoading = true
                    accountRetrievalError = false
                }

                is Resource.Success -> {
                    isAccountLoading = false
                    accountRetrievalError = false
                    _accounts.value = it.data
                }

                is Resource.Error -> {
                    isAccountLoading = false
                    accountRetrievalError = true
                }
            }
        }
    }

    val accountState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateAccountTextState(it) },
            onErrorIconClick = { updateAccountErrorState() },
            errorMessage = ""
        )
    )

    val amountState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateAmountTextState(it) },
            onErrorIconClick = { updateAmountErrorState() },
            errorMessage = ""
        )
    )

    var accountRetrievalError by mutableStateOf(false)
        private set
    var isAccountLoading by mutableStateOf(true)
        private set
    var screenTitle by mutableStateOf("Add Account")
        private set
    var screenDetail by mutableStateOf("Please provide account details")
        private set
    var exitScreen = mutableStateOf(false)
        private set
    var isAccountSaved = mutableStateOf(false)

    private fun updateAccountTextState(value: String) {
        accountState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = accountState.value.isError,
            showError = accountState.value.showError,
            onValueChange = accountState.value.onValueChange,
            onErrorIconClick = accountState.value.onErrorIconClick,
            errorMessage = accountState.value.errorMessage
        )
    }

    private fun updateAmountErrorState() {
        amountState.value = TextFieldWithIconAndErrorPopUpState(
            text = amountState.value.text,
            isError = amountState.value.isError,
            showError = !amountState.value.showError,
            onValueChange = amountState.value.onValueChange,
            onErrorIconClick = amountState.value.onErrorIconClick,
            errorMessage = amountState.value.errorMessage
        )
    }

    private fun updateAmountTextState(value: String) {
        amountState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = amountState.value.isError,
            showError = amountState.value.showError,
            onValueChange = amountState.value.onValueChange,
            onErrorIconClick = amountState.value.onErrorIconClick,
            errorMessage = amountState.value.errorMessage
        )
    }

    private fun updateAccountErrorState() {
        accountState.value = TextFieldWithIconAndErrorPopUpState(
            text = accountState.value.text,
            isError = accountState.value.isError,
            showError = !accountState.value.showError,
            onValueChange = accountState.value.onValueChange,
            onErrorIconClick = accountState.value.onErrorIconClick,
            errorMessage = accountState.value.errorMessage
        )
    }

    fun validateAndSaveAccount() {
        val isAmountValid = checkAmountError()
        val isAccountValid = checkAccountError()
        if (isAmountValid && isAccountValid) {
            saveAccount(accountState.value.text, amountState.value.text)
        }
    }

    private fun checkAccountError(): Boolean {
        val isAccountBlank = accountState.value.text.isBlank()
        val duplicateAccount =
            _accounts.value.stream().anyMatch { it.accountName == accountState.value.text }
        if (isAccountBlank || duplicateAccount) {
            accountState.value = TextFieldWithIconAndErrorPopUpState(
                text = accountState.value.text,
                isError = true,
                showError = accountState.value.showError,
                onValueChange = accountState.value.onValueChange,
                onErrorIconClick = accountState.value.onErrorIconClick,
                errorMessage = if (isAccountBlank) "Account cannot be blank" else "Account already exists"

            )
            return false
        }
        return true
    }

    private fun checkAmountError(): Boolean {
        try {
            amountState.value.text.toDouble()
        } catch (ne: NumberFormatException) {
            amountState.value = TextFieldWithIconAndErrorPopUpState(
                text = amountState.value.text,
                isError = true,
                showError = amountState.value.showError,
                onValueChange = amountState.value.onValueChange,
                onErrorIconClick = amountState.value.onErrorIconClick,
                errorMessage = "Enter a valid number"
            )
            return false
        }
        return true
    }

    private fun saveAccount(
        accountName: String, balance: String
    ) = viewModelScope.launch(EtDispatcher.io) {
        accountRepository.saveAccount(
            Account(
                0, accountName, balance.toDouble(), System.currentTimeMillis()
            )
        )
        isAccountSaved.value = true
        exitScreen.value = true
    }
}
