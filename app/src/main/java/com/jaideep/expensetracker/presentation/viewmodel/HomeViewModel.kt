package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.domain.usecase.calculation.GetAccountBalanceUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentTodayForAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val etRepository: EtRepository,
    private val getAmountSpentTodayForAccountUseCase: GetAmountSpentTodayForAccountUseCase,
    private val getAccountBalanceUseCase: GetAccountBalanceUseCase
): ViewModel() {
    var spentToday = mutableDoubleStateOf(0.0)
    var selectedAccount = mutableStateOf("All Accounts")
    var selectedAccountBalance = mutableDoubleStateOf(0.0)

    private fun getAccountBalance() = viewModelScope.launch(EtDispatcher.io) {
        getAccountBalanceUseCase(selectedAccount.value).collect {
            when(it) {
                is Resource.Loading -> {}
                is Resource.Error -> {/*
                 Show error message, implement when you create error state variable */
                }
                is Resource.Success -> selectedAccountBalance.doubleValue = it.data
            }
        }
    }

    private fun getAmountSpentTodayForSelectedAccount() = viewModelScope.launch(EtDispatcher.io) {
        getAmountSpentTodayForAccountUseCase(selectedAccount.value).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {/*
                 Show error message, implement when you create error state variable */
                }
                is Resource.Success -> spentToday.doubleValue = it.data
            }
        }
    }

    fun updateSelectedAccount(updatedValue: String) {
        selectedAccount.value = updatedValue
        getAccountBalance()
        getAmountSpentTodayForSelectedAccount()
    }
}