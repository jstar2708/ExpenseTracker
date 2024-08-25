package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.usecase.calculation.GetAccountBalanceUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentFromAccountThisMonth
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentTodayForAccountUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.RunJobForData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAmountSpentTodayForAccountUseCase: GetAmountSpentTodayForAccountUseCase,
    private val getAccountBalanceUseCase: GetAccountBalanceUseCase,
    private val getAmountSpentFromAccountThisMonth: GetAmountSpentFromAccountThisMonth,
    private val getCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase: GetCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase
) : ViewModel() {
    private val _spentToday = mutableDoubleStateOf(0.0)
    val spentToday: State<Double> = _spentToday
    private val _selectedAccount = mutableStateOf("All Accounts")
    val selectedAccount: State<String> = _selectedAccount
    private val _selectedAccountBalance = mutableDoubleStateOf(0.0)
    val selectedAccountBalance: State<Double> = _selectedAccountBalance
    private val _amountSpentThisMonthFromAcc = mutableDoubleStateOf(0.0)
    val amountSpentThisMonthFromAcc: State<Double> = _amountSpentThisMonthFromAcc
    private val _getMaxSpentCategoryData = mutableStateOf(CategoryCardData())
    val getMaxSpentCategoryData: State<CategoryCardData> = _getMaxSpentCategoryData
    private var runJobForData: MutableStateFlow<RunJobForData<String>> = MutableStateFlow(
        RunJobForData()
    )

    private fun getAccountBalance() = viewModelScope.launch(EtDispatcher.io) {
        getAccountBalanceUseCase(selectedAccount.value).collect {
            when (it) {
                is Resource.Loading -> {}
                is Resource.Error -> {
                    /*
                 Show error message, implement when you create error state variable
                 */
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

    private fun getAmountSpentFromAccountThisMonth() = viewModelScope.launch(EtDispatcher.io) {
        getAmountSpentFromAccountThisMonth(selectedAccount.value).collect {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _amountSpentThisMonthFromAcc.doubleValue = it.data
                }
            }
        }
    }

    private fun getCategoryAmountForMaxAmountWasSpentFromAccountUseCase() =
        viewModelScope.launch(EtDispatcher.io) {
            getCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase(selectedAccount.value).collect {
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        _getMaxSpentCategoryData.value = it.data
                    }
                }
            }
        }

    fun updateSelectedAccount(updatedValue: String) {
        _selectedAccount.value = updatedValue
        getAccountBalance()
        getAmountSpentTodayForSelectedAccount()
        getAmountSpentFromAccountThisMonth()
        getCategoryAmountForMaxAmountWasSpentFromAccountUseCase()
    }

    fun getInitialAccountData() {
        getAccountBalance()
        getAmountSpentTodayForSelectedAccount()
        getCategoryAmountForMaxAmountWasSpentFromAccountUseCase()
        getAmountSpentFromAccountThisMonth()
    }
}