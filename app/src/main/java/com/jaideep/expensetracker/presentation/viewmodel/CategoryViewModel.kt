package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentFromAccountThisMonth
import com.jaideep.expensetracker.domain.usecase.calculation.GetAmountSpentFromAccountThisYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAmountSpentFromAccountThisMonth: GetAmountSpentFromAccountThisMonth,
    private val getAmountSpentFromAccountThisYear: GetAmountSpentFromAccountThisYear
) : ViewModel() {
    val durationList: ImmutableList<String> = persistentListOf("This Month", "This Year")
    val accountValue = mutableStateOf("All Accounts")
    val durationValue = mutableStateOf("This Month")
    private val _amountSpentThisMonthFromAcc = MutableStateFlow(0.0)
    val amountSpentThisMonthFromAcc: StateFlow<Double> = _amountSpentThisMonthFromAcc
    private val _amountSpentThisYearFromAcc = MutableStateFlow(0.0)
    val amountSpentThisYearFromAcc: StateFlow<Double> = _amountSpentThisMonthFromAcc
    var isAmountSpentThisYearFromAccLoading by mutableStateOf(true)
        private set
    var amountSpentThisYearFromAccError by mutableStateOf(false)
        private set
    var isAmountSpentThisMonthFromAccLoading by mutableStateOf(true)
        private set
    var amountSpentThisMonthFromAccError by mutableStateOf(false)
        private set


    fun initData() {
        getAmountSpentFromAccountThisYear()
        getAmountSpentFromAccountThisMonth()
    }

    fun onAccountSpinnerValueChanged(account: String) {
        accountValue.value = account
    }

    fun onDurationSpinnerValueChanged(duration: String) {
        durationValue.value = duration
    }

    private fun getAmountSpentFromAccountThisMonth() = viewModelScope.launch(EtDispatcher.io) {
        getAmountSpentFromAccountThisMonth(accountValue.value).collect {
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

    private fun getAmountSpentFromAccountThisYear() = viewModelScope.launch(EtDispatcher.io) {
        getAmountSpentFromAccountThisYear(accountValue.value).collect {
            when (it) {
                is Resource.Error -> {
                    isAmountSpentThisYearFromAccLoading = false
                    amountSpentThisYearFromAccError = true
                }

                is Resource.Loading -> {
                    isAmountSpentThisYearFromAccLoading = true
                    amountSpentThisYearFromAccError = false
                }

                is Resource.Success -> {
                    _amountSpentThisYearFromAcc.value = it.data
                    isAmountSpentThisYearFromAccLoading = false
                    amountSpentThisYearFromAccError = false
                }
            }
        }
    }
}