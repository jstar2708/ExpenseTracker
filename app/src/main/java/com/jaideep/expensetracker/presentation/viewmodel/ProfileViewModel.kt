package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.usecase.calculation.GetAvgMonthlyExpUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetMostUsedAccUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetTotalExpenditureUseCase
import com.jaideep.expensetracker.domain.usecase.calculation.GetTotalTransactionCountUseCase
import com.jaideep.expensetracker.domain.usecase.datastore.GetUsernameFromDatastoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUsernameFromDatastoreUseCase: GetUsernameFromDatastoreUseCase,
    private val getAvgMonthlyExpUseCase: GetAvgMonthlyExpUseCase,
    private val getTotalTransactionCountUseCase: GetTotalTransactionCountUseCase,
    private val getTotalExpenditureUseCase: GetTotalExpenditureUseCase,
    private val getMostUsedAccUseCase: GetMostUsedAccUseCase
) : ViewModel() {
    var userName by mutableStateOf("")
        private set
    var isUsernameLoading by mutableStateOf(true)
        private set
    var usernameRetrievalError by mutableStateOf(false)
        private set
    var totalTransactions by mutableStateOf("")
        private set
    var isTotalTransactionsLoading by mutableStateOf(true)
        private set
    var totalTransactionsRetrievalError by mutableStateOf(false)
        private set
    var totalExpenditure by mutableStateOf("")
        private set
    var isTotalExpenditureLoading by mutableStateOf(true)
        private set
    var totalExpenditureRetrievalError by mutableStateOf(false)
        private set
    var avgMonthlyExpenditure by mutableStateOf("")
        private set
    var isAvgMonthlyExpLoading by mutableStateOf(true)
        private set
    var avgMonthlyExpRetrievalError by mutableStateOf(false)
        private set
    var mostFrequentlyUsedAccount by mutableStateOf("")
        private set
    var isMostFrequentlyUsedAccLoading by mutableStateOf(true)
        private set
    var mostFrequentlyUsedAccRetrievalError by mutableStateOf(false)
        private set

    fun initData() {
        getUserName()
    }

    private fun getAverageMonthlyExpenditure() = viewModelScope.launch(EtDispatcher.io) {
        getAvgMonthlyExpUseCase().collect {
            when (it) {
                is Resource.Error -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {

                }
            }
        }
    }
    private fun getUserName() = viewModelScope.launch(EtDispatcher.io) {
        getUsernameFromDatastoreUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    usernameRetrievalError = true
                    isUsernameLoading = false
                }

                is Resource.Loading -> {
                    isUsernameLoading = true
                    usernameRetrievalError = false
                }

                is Resource.Success -> {
                    userName = it.data
                    isUsernameLoading = false
                    usernameRetrievalError = false
                }
            }
        }
    }
}