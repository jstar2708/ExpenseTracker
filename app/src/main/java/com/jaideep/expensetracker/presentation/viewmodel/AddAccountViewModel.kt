package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.model.CategoryDto
import com.jaideep.expensetracker.presentation.utility.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    var screenTitle by mutableStateOf("Add Account")
        private set
    var screenDetail by mutableStateOf("Please provide account details")
        private set
    var accountName = mutableStateOf(TextFieldValue(""))
        private set
    var initialBalance = mutableStateOf(TextFieldValue(""))
        private set
    var isBalanceIncorrect = mutableStateOf(false)
        private set
    var isAccountNameIncorrect = mutableStateOf(false)
        private set
    var exitScreen = mutableStateOf(false)
        private set
    var isAccountSaved = mutableStateOf(false)

    fun saveAccount(
        accountName: String,
        balance: String
        ) = viewModelScope.launch {
            try {
                if (accountName.isBlank()) {
                    isAccountNameIncorrect.value = true
                }
                accountRepository.saveAccount(
                    Account(
                        0,
                        accountName,
                        balance.toDouble(),
                        System.currentTimeMillis()
                    )
                )
                isBalanceIncorrect.value = false
                isAccountSaved.value = true
                exitScreen.value = true
            }
            catch (ne : NumberFormatException) {
                isBalanceIncorrect.value = true
            }
    }
}
