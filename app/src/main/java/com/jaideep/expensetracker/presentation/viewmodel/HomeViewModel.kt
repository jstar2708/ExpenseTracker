package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.domain.usecase.calculation.GetAccountBalanceUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentForAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val etRepository: EtRepository,
    private val getAmountSpentForAccountUseCase: GetAmountSpentForAccountUseCase,
    private val getAccountBalanceUseCase: GetAccountBalanceUseCase
): ViewModel() {
    var spentToday = mutableDoubleStateOf(0.0)
    private var selectedAccount = mutableStateOf("")
    var selectedAccountBalance = mutableDoubleStateOf(0.0)

    fun updateSelectedAccount(updatedValue: String) {
        selectedAccount.value = updatedValue
        getSelectedAccountBalance()
    }

    private fun getSelectedAccountBalance() = viewModelScope.launch(EtDispatcher.io) {
        if (selectedAccount.value.isNotBlank()) {
            selectedAccountBalance.doubleValue = getAccountBalanceUseCase(selectedAccount.value)
        }
    }

    private fun getSpentTodayForAccount() = viewModelScope.launch(EtDispatcher.io) {
        if (selectedAccount.value.isNotBlank()) {
            spentToday.doubleValue = getAmountSpentForAccountUseCase(selectedAccount.value)
        }
    }
}