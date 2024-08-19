package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.asDoubleState
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
    private val _spentToday = mutableDoubleStateOf(0.0)
    val spentToday: State<Double> = _spentToday
    private val _selectedAccount = mutableStateOf("All Accounts")
    val selectedAccount: State<String> = _selectedAccount
    private val _selectedAccountBalance = mutableDoubleStateOf(0.0)
    val selectedAccountBalance: State<Double> = _selectedAccountBalance

    private fun getAccountBalance() = viewModelScope.launch(EtDispatcher.io) {
        getAccountBalanceUseCase(selectedAccount.value).collect {
            when(it) {
                is Resource.Loading -> {}
                is Resource.Error -> {/*
                 Show error message, implement when you create error state variable */
                }
                is Resource.Success -> _selectedAccountBalance.doubleValue = it.data
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
                is Resource.Success -> _spentToday.doubleValue = it.data
            }
        }
    }

    fun updateSelectedAccount(updatedValue: String) {
        _selectedAccount.value = updatedValue
        getAccountBalance()
        getAmountSpentTodayForSelectedAccount()
    }

    fun getInitialAccountData() {
        getAccountBalance()
        getAmountSpentTodayForSelectedAccount()
    }
}