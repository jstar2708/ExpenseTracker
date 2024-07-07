package com.jaideep.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.TransactionMethod
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import com.jaideep.expensetracker.domain.usecase.GetAllAccountsUseCase
import com.jaideep.expensetracker.domain.usecase.GetAllCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {
    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(ArrayList())
    var accounts: StateFlow<List<Account>> = _accounts

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(ArrayList())
    var categories: StateFlow<List<Category>> = _categories

    private val _transactions: MutableStateFlow<List<Transaction>> = MutableStateFlow(ArrayList())
    var transactions: StateFlow<List<Transaction>> = _transactions

    val selectedAccount: MutableStateFlow<String> = MutableStateFlow("All accounts")

    private val transactionMethod: MutableStateFlow<TransactionMethod> =
        MutableStateFlow(TransactionMethod.GET_ALL_TRANSACTIONS)

    val transactionItems: MutableStateFlow<Flow<PagingData<Transaction>>> =
        MutableStateFlow(Pager(config = PagingConfig(pageSize = 50), pagingSourceFactory = {
            transactionRepository.getAllTransactions()
        }).flow.cachedIn(viewModelScope))

    init {
        getAllAccounts()
        getAllCategories()
        getTransactionPagingSource()
    }

    private fun getAllAccounts() = viewModelScope.launch {
        getAllAccountsUseCase().collect {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _accounts.value = it.data
                }

                is Resource.Error -> {

                }
            }
        }
    }

    private fun getAllCategories() = viewModelScope.launch {
        getAllCategoriesUseCase().collect {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _categories.value = it.data
                }

                is Resource.Error -> {

                }
            }
        }
    }

    fun updateTransactionMethod(transactionMethod: TransactionMethod) {
        this.transactionMethod.value = transactionMethod
    }

    private fun getTransactionPagingSource() = viewModelScope.launch {
        transactionMethod.collectLatest {
            transactionItems.value =
                Pager(config = PagingConfig(pageSize = 50), pagingSourceFactory = {
                    when (it) {
                        TransactionMethod.GET_DEBIT_TRANSACTIONS -> transactionRepository.getDebitTransactions()
                        TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getTransactionsForAccount(
                            1,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getDebitTransactionsForAccount(
                            1,
                        )

                        TransactionMethod.GET_CREDIT_TRANSACTIONS_FOR_ACCOUNT -> transactionRepository.getCreditTransactionsForAccount(
                            1,
                        )

                        TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionRepository.getCreditTransactionBetweenDatesForAccount(
                            1,
                            1L,
                            1,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionRepository.getDebitTransactionBetweenDatesForAccount(
                            1,
                            1,
                            1,
                        )

                        TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionRepository.getTransactionBetweenDates(
                            1,
                            1,
                        )

                        TransactionMethod.GET_ALL_TRANSACTIONS -> transactionRepository.getAllTransactions()
                        TransactionMethod.GET_CREDIT_TRANSACTIONS -> transactionRepository.getCreditTransactions()
                        TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES -> transactionRepository.getCreditTransactionBetweenDates(
                            1,
                            1,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES -> transactionRepository.getDebitTransactionBetweenDates(
                            1,
                            1,
                        )

                        TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES -> transactionRepository.getTransactionBetweenDates(
                            1,
                            1,
                        )

                    }
                }).flow.cachedIn(viewModelScope)

        }
    }
}