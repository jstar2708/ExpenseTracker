package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.usecase.calculation.GetAccountBalanceUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentFromAccountThisMonth
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentTodayForAccountUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase
import com.jaideep.expensetracker.model.CategoryCardData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAmountSpentTodayForAccountUseCase: GetAmountSpentTodayForAccountUseCase,
    private val getAccountBalanceUseCase: GetAccountBalanceUseCase,
    private val getAmountSpentFromAccountThisMonth: GetAmountSpentFromAccountThisMonth,
    private val getCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase: GetCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase
) : ViewModel() {
    private val _spentToday = MutableStateFlow(0.0)
    val spentToday: StateFlow<Double> = _spentToday
    private val _selectedAccount = MutableStateFlow("All Accounts")
    val selectedAccount: StateFlow<String> = _selectedAccount
    private val _selectedAccountBalance = MutableStateFlow(0.0)
    val selectedAccountBalance: StateFlow<Double> = _selectedAccountBalance
    private val _amountSpentThisMonthFromAcc = MutableStateFlow(0.0)
    val amountSpentThisMonthFromAcc: StateFlow<Double> = _amountSpentThisMonthFromAcc
    private val _getMaxSpentCategoryData = MutableStateFlow<CategoryCardData?>(null)
    val getMaxSpentCategoryData: StateFlow<CategoryCardData?> = _getMaxSpentCategoryData
    var isSelectedAccountBalanceLoading by mutableStateOf(true)
        private set
    var isSpendTodayLoading by mutableStateOf(true)
        private set
    var isAmountSpentThisMonthFromAccLoading by mutableStateOf(true)
        private set
    var isGetMaxSpentCategoryDataLoading by mutableStateOf(true)
        private set
    var selectedAccountBalanceError by mutableStateOf(false)
        private set
    var spendTodayError by mutableStateOf(false)
        private set
    var amountSpentThisMonthFromAccError by mutableStateOf(false)
        private set
    var getMaxSpentCategoryDataError by mutableStateOf(false)
        private set


    private fun getAccountBalance() = viewModelScope.launch(EtDispatcher.io) {
        getAccountBalanceUseCase(selectedAccount.value).collect {
            when (it) {
                is Resource.Loading -> {
                    isSelectedAccountBalanceLoading = true
                    selectedAccountBalanceError = false
                }

                is Resource.Error -> {
                    isSelectedAccountBalanceLoading = false
                    selectedAccountBalanceError = true
                }

                is Resource.Success -> {
                    _selectedAccountBalance.value = it.data
                    isSelectedAccountBalanceLoading = false
                    selectedAccountBalanceError = false
                }
            }
        }
    }

    private fun getAmountSpentTodayForSelectedAccount() = viewModelScope.launch(EtDispatcher.io) {
        getAmountSpentTodayForAccountUseCase(selectedAccount.value).collectLatest {
            when (it) {
                is Resource.Loading -> {
                    isSpendTodayLoading = true
                    spendTodayError = false
                }

                is Resource.Error -> {
                    isSpendTodayLoading = false
                    spendTodayError = true
                }

                is Resource.Success -> {
                    _spentToday.value = it.data
                    isSpendTodayLoading = false
                    spendTodayError = false
                }
            }
        }
    }

    private fun getAmountSpentFromAccountThisMonth() = viewModelScope.launch(EtDispatcher.io) {
        getAmountSpentFromAccountThisMonth(selectedAccount.value).collect {
            when (it) {
                is Resource.Error -> {
                    isAmountSpentThisMonthFromAccLoading = false
                    amountSpentThisMonthFromAccError = true
                }

                is Resource.Loading -> {
                    isAmountSpentThisMonthFromAccLoading = true
                    amountSpentThisMonthFromAccError = false
                }

                is Resource.Success -> {
                    _amountSpentThisMonthFromAcc.value = it.data
                    isAmountSpentThisMonthFromAccLoading = false
                    amountSpentThisMonthFromAccError = false
                }
            }
        }
    }

    private fun getCategoryAmountForMaxAmountWasSpentFromAccountUseCase() =
        viewModelScope.launch(EtDispatcher.io) {
            getCategoryAmountForWhichMaxAmountWasSpentFromAccountUseCase(selectedAccount.value).collect {
                when (it) {
                    is Resource.Error -> {
                        getMaxSpentCategoryDataError = true
                        isGetMaxSpentCategoryDataLoading = false
                    }

                    is Resource.Loading -> {
                        isGetMaxSpentCategoryDataLoading = true
                        getMaxSpentCategoryDataError = false
                    }

                    is Resource.Success -> {
                        _getMaxSpentCategoryData.value = it.data
                        isGetMaxSpentCategoryDataLoading = false
                        getMaxSpentCategoryDataError = false
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