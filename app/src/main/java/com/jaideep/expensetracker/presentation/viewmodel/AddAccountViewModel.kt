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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase
) : ViewModel() {

    private val _account = MutableStateFlow<Account?>(null)
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
    var isEdit by mutableStateOf(false)
    var accountListRetrievalError by mutableStateOf(false)
        private set
    var isAccountListLoading by mutableStateOf(true)
        private set
    var screenTitle by mutableStateOf("Add Account")
        private set
    var screenDetail by mutableStateOf("Please provide account details")
        private set
    var exitScreen = mutableStateOf(false)
        private set
    var isAccountSaved = mutableStateOf(false)
        private set
    var accountRetrievalError by mutableStateOf(false)
        private set
    var isAccountByIdLoading by mutableStateOf(false)
        private set
    var isAccountDeleted by mutableStateOf(false)
        private set

    fun initData(accountId: Int) {
        if (accountId != -1) {
            isEdit = true
            _accountId.value = accountId
            getAccountById()
        }
        getAllAccounts()
    }

    private fun getAccountById() = viewModelScope.launch(EtDispatcher.io) {
        isAccountByIdLoading = true
        try {
            _account.value = accountRepository.getAccountById(_accountId.value)
        } catch (ex: Exception) {
            accountRetrievalError = true
        }
        isAccountByIdLoading = false
    }

    private fun getAllAccounts() = viewModelScope.launch(EtDispatcher.io) {
        getAllAccountsUseCase().collectLatest {
            when (it) {
                is Resource.Loading -> {
                    isAccountListLoading = true
                    accountListRetrievalError = false
                }

                is Resource.Success -> {
                    _accounts.value = it.data
                    if (_accountId.value != -1) {
                        _accountDto.value = _accounts.value.asFlow()
                            .firstOrNull { accountDto -> accountDto.id == _accountId.value }
                        fillAccountData()
                    }
                    isAccountListLoading = false
                    accountListRetrievalError = false
                }

                is Resource.Error -> {
                    isAccountListLoading = false
                    accountListRetrievalError = true
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
            if (_account.value != null) checkDuplicateErrorForUpdateQuery() else checkDuplicateErrorForSaveQuery()
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

    private fun checkDuplicateErrorForSaveQuery(): Boolean {
        return _accounts.value.stream().anyMatch { it.accountName == accountState.value.text }
    }

    private fun checkDuplicateErrorForUpdateQuery(): Boolean {
        return _accounts.value.stream()
            .filter { account -> account.accountName != accountState.value.text }
            .anyMatch { it.accountName == accountState.value.text }
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

    fun deleteAccount() = viewModelScope.launch(EtDispatcher.io) {
        try {
            val account = _account.value
            if (account != null) {
                accountRepository.deleteAccount(account)
                isAccountDeleted = true
            } else {
                isAccountDeleted = false
            }
        } catch (ex: Exception) {
            isAccountByIdLoading = false
        }
        exitScreen.value = true
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

//    fun resetScreenData() {
//        accountState.value = TextFieldWithIconAndErrorPopUpState(
//            text = "",
//            isError = false,
//            showError = false,
//            onValueChange = { updateAccountTextState(it) },
//            onErrorIconClick = { updateAccountErrorState() },
//            errorMessage = ""
//        )
//        amountState.value = TextFieldWithIconAndErrorPopUpState(
//            text = "",
//            isError = false,
//            showError = false,
//            onValueChange = { updateAmountTextState(it) },
//            onErrorIconClick = { updateAmountErrorState() },
//            errorMessage = ""
//        )
//    }
}
