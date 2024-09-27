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
import com.jaideep.expensetracker.model.dto.AccountDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase
) : ViewModel() {

    private val _accounts: MutableStateFlow<List<AccountDto>> = MutableStateFlow(ArrayList())
    private val _accountDto: MutableStateFlow<AccountDto?> = MutableStateFlow(null)
    private val _accountId: MutableStateFlow<Int> = MutableStateFlow(-1)

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

    fun initData(accountId: Int) {
        _accountId.value = accountId
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
                    _accounts.value = it.data
                    if (_accountId.value != -1) {
                        _accountDto.value = _accounts.value.asFlow()
                            .first { accountDto -> accountDto.id == _accountId.value }
                        fillAccountData()
                    }
                    isAccountLoading = false
                    accountRetrievalError = false
                }

                is Resource.Error -> {
                    isAccountLoading = false
                    accountRetrievalError = true
                }
            }
        }
    }

    private fun fillAccountData() {
        _accountDto.value?.let { accountDto ->
            accountState.value = TextFieldWithIconAndErrorPopUpState(
                text = accountDto.accountName,
                isError = false,
                showError = false,
                onValueChange = accountState.value.onValueChange,
                onErrorIconClick = accountState.value.onErrorIconClick,
                errorMessage = ""
            )
            amountState.value = TextFieldWithIconAndErrorPopUpState(
                text = accountDto.balance.toString(),
                isError = false,
                showError = false,
                onValueChange = amountState.value.onValueChange,
                onErrorIconClick = amountState.value.onErrorIconClick,
                errorMessage = ""
            )
        }
    }

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
        if (_accountId.value != -1) {
            _accountDto.value?.let { accountDto ->
                accountRepository.updateAccount(
                    Account(accountDto.id, accountName, accountDto.balance, accountDto.createdOn)
                )
            }
        } else {
            accountRepository.saveAccount(
                Account(
                    0,
                    accountName,
                    balance.toDouble(),
                    LocalDate.ofEpochDay(System.currentTimeMillis() / 86_400_000L)
                )
            )
        }
        isAccountSaved.value = true
        exitScreen.value = true
    }
}
