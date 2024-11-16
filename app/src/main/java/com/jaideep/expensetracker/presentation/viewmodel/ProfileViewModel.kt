package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.AppConstants.CURRENCY
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepository
import com.jaideep.expensetracker.domain.repository.CrudRepository
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
    private val crudRepository: CrudRepository,
    private val datastoreRepository: DatastoreRepository,
    private val getUsernameFromDatastoreUseCase: GetUsernameFromDatastoreUseCase,
    private val getAvgMonthlyExpUseCase: GetAvgMonthlyExpUseCase,
    private val getTotalTransactionCountUseCase: GetTotalTransactionCountUseCase,
    private val getTotalExpenditureUseCase: GetTotalExpenditureUseCase,
    private val getMostUsedAccUseCase: GetMostUsedAccUseCase
) : ViewModel() {
    var showDialog by mutableStateOf(false)
        private set
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
    var showCurrencyDialog by mutableStateOf(false)

    fun initData() {
        getUserName()
        getAverageMonthlyExpenditure()
        getMostFrequentlyUsedAccount()
        getTotalExpenditure()
        getTotalTransactionCount()
    }

    private fun getTotalTransactionCount() = viewModelScope.launch(EtDispatcher.io) {
        getTotalTransactionCountUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    totalTransactionsRetrievalError = true
                    isTotalTransactionsLoading = false
                }

                is Resource.Loading -> {
                    isTotalTransactionsLoading = true
                    totalTransactionsRetrievalError = false
                }

                is Resource.Success -> {
                    totalTransactionsRetrievalError = false
                    isTotalTransactionsLoading = false
                    totalTransactions = it.data.toString()
                }
            }
        }
    }

    private fun getTotalExpenditure() = viewModelScope.launch(EtDispatcher.io) {
        getTotalExpenditureUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    totalExpenditureRetrievalError = true
                    isTotalExpenditureLoading = false
                }

                is Resource.Loading -> {
                    totalExpenditureRetrievalError = false
                    isTotalExpenditureLoading = true
                }

                is Resource.Success -> {
                    totalExpenditure = it.data.toString()
                    totalExpenditureRetrievalError = false
                    isTotalExpenditureLoading = false
                }
            }
        }
    }

    private fun getMostFrequentlyUsedAccount() = viewModelScope.launch(EtDispatcher.io) {
        getMostUsedAccUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    mostFrequentlyUsedAccRetrievalError = true
                    isMostFrequentlyUsedAccLoading = false
                }

                is Resource.Loading -> {
                    mostFrequentlyUsedAccRetrievalError = false
                    isMostFrequentlyUsedAccLoading = true
                }

                is Resource.Success -> {
                    mostFrequentlyUsedAccRetrievalError = false
                    isMostFrequentlyUsedAccLoading = false
                    mostFrequentlyUsedAccount = it.data
                }
            }
        }
    }

    private fun getAverageMonthlyExpenditure() = viewModelScope.launch(EtDispatcher.io) {
        getAvgMonthlyExpUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    avgMonthlyExpRetrievalError = true
                    isAvgMonthlyExpLoading = false
                }

                is Resource.Loading -> {
                    avgMonthlyExpRetrievalError = false
                    isAvgMonthlyExpLoading = true
                }

                is Resource.Success -> {
                    avgMonthlyExpenditure = it.data.toString()
                    avgMonthlyExpRetrievalError = false
                    isAvgMonthlyExpLoading = false
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
                    if (it.data != null) {
                        userName = it.data
                        usernameRetrievalError = false
                    }
                    else {
                        usernameRetrievalError = true
                    }
                    isUsernameLoading = false
                }
            }
        }
    }

    fun toggleDialog() {
        showDialog = !showDialog
    }

    fun hideDialog() {
        showDialog = false
    }

    fun clearAllData() {
        clearDatabase()
        clearDatastore()
    }

    private fun clearDatabase() = viewModelScope.launch(EtDispatcher.io) {
        crudRepository.clearAllData()
    }

    private fun clearDatastore() = viewModelScope.launch(EtDispatcher.io) {
        datastoreRepository.clearDatastore()
    }

    fun openCurrencyDialog() {
        showCurrencyDialog = true
    }

    fun hideCurrencyDialog() {
        showCurrencyDialog = false
    }
}