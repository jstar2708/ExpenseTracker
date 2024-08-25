package com.jaideep.expensetracker.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jaideep.expensetracker.R
import com.jaideep.expensetracker.common.AppComponents.getCategoryIconId
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.TransactionMethod
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import com.jaideep.expensetracker.domain.usecase.GetAllAccountsUseCase
import com.jaideep.expensetracker.domain.usecase.GetAllCategoriesUseCase
import com.jaideep.expensetracker.domain.usecase.GetInitialTransactionsUseCase
import com.jaideep.expensetracker.model.RunJobForData
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.model.dto.TransactionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionPagingRepository: TransactionPagingRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getInitialTransactionsUseCase: GetInitialTransactionsUseCase,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(ArrayList())
    var accounts: StateFlow<List<String>> = _accounts.map { list ->
        list.asFlow().map { it.accountName }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(ArrayList())
    var categories: StateFlow<List<CategoryDto>> = _categories.map {
        it.asFlow().map { category ->
            CategoryDto(
                category.categoryName, getCategoryIconId(category.iconName)
            )
        }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _transactions: MutableStateFlow<List<Transaction>> = MutableStateFlow(ArrayList())
    var transactions: StateFlow<List<TransactionDto>> = _transactions.map {
        it.asFlow().map { transaction ->
            TransactionDto(
                transaction.amount,
                getCategoryDto(transaction.categoryId),
                transaction.message,
                LocalDate.ofEpochDay(transaction.createdTime / 86_400_000L),
                transaction.isCredit == 1
            )
        }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val transactionMethod: MutableStateFlow<TransactionMethod> =
        MutableStateFlow(TransactionMethod.GET_ALL_TRANSACTIONS)

    val pagedTransactionItems: MutableStateFlow<Flow<PagingData<Transaction>>> =
        MutableStateFlow(Pager(config = PagingConfig(pageSize = 50), pagingSourceFactory = {
            transactionPagingRepository.getAllTransactions()
        }).flow.cachedIn(viewModelScope))

    private val _isFirstAppInitialization = MutableLiveData<Boolean>()
    var isFirstAppInitialization = _isFirstAppInitialization

    private val _hasCategoriesLoaded = MutableStateFlow<Boolean>(false)

    private var runJobForData: MutableStateFlow<RunJobForData<String>> = MutableStateFlow(
        RunJobForData()
    )

    init {
        getAllAccounts()
        getAllCategories()
    }

    fun addDefaultCategories() {
        viewModelScope.launch(EtDispatcher.io) {
            categoryRepository.saveAllCategories(
                listOf(
                    Category(1, "Food", "Food"),
                    Category(2, "Fuel", "Fuel"),
                    Category(3, "Entertainment", "Entertainment"),
                    Category(4, "Shopping", "Shopping"),
                    Category(5, "Travel", "Travel")
                )
            )
        }
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

    private fun getAllCategories() = viewModelScope.launch(EtDispatcher.io) {
        getAllCategoriesUseCase().collect {
            when (it) {
                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    _categories.value = it.data
                    _hasCategoriesLoaded.value = true
                    _hasCategoriesLoaded.collectLatest { hasCategoriesLoaded ->
                        if (hasCategoriesLoaded && _transactions.value.isEmpty()) {
                            getTransactionPagingSource()
                            getInitialTransactions(TransactionMethod.GET_ALL_TRANSACTIONS)
                        }
                    }
                }

                is Resource.Error -> {

                }
            }
        }
    }

    private fun getInitialTransactions(transactionMethod: TransactionMethod) =
        viewModelScope.launch(EtDispatcher.io) {
            if (this.isActive) {
                getInitialTransactionsUseCase(
                    runJobForData.value.data, null, null, transactionMethod
                ).collectLatest {
                    when (it) {
                        is Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            _transactions.value = it.data
                        }

                        is Resource.Error -> {

                        }
                    }
                }
            }
        }

    fun updateTransactionMethod(transactionMethod: TransactionMethod) {
        this.transactionMethod.value = transactionMethod
    }

    private fun getTransactionPagingSource() = viewModelScope.launch {
        transactionMethod.collectLatest {
            pagedTransactionItems.value =
                Pager(config = PagingConfig(pageSize = 50), pagingSourceFactory = {
                    when (it) {
                        TransactionMethod.GET_DEBIT_TRANSACTIONS -> transactionPagingRepository.getDebitTransactions()
                        TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT -> transactionPagingRepository.getTransactionsForAccount(
                            1,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_FOR_ACCOUNT -> transactionPagingRepository.getDebitTransactionsForAccount(
                            1,
                        )

                        TransactionMethod.GET_CREDIT_TRANSACTIONS_FOR_ACCOUNT -> transactionPagingRepository.getCreditTransactionsForAccount(
                            1
                        )

                        TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionPagingRepository.getCreditTransactionBetweenDatesForAccount(
                            1,
                            1L,
                            1,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionPagingRepository.getDebitTransactionBetweenDatesForAccount(
                            1,
                            1,
                            1,
                        )

                        TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionPagingRepository.getTransactionBetweenDates(
                            1,
                            1,
                        )

                        TransactionMethod.GET_ALL_TRANSACTIONS -> transactionPagingRepository.getAllTransactions()
                        TransactionMethod.GET_CREDIT_TRANSACTIONS -> transactionPagingRepository.getCreditTransactions()
                        TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES -> transactionPagingRepository.getCreditTransactionBetweenDates(
                            1,
                            1,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES -> transactionPagingRepository.getDebitTransactionBetweenDates(
                            1,
                            1,
                        )

                        TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES -> transactionPagingRepository.getTransactionBetweenDates(
                            1,
                            1,
                        )
                    }
                }).flow.cachedIn(viewModelScope)

        }
    }

    fun getCategoryDto(categoryId: Int): CategoryDto {
        val category: Category? = _categories.value.find {
            categoryId == it.id
        }
        var categoryDto = CategoryDto("Other", R.drawable.category)
        category?.let {
            categoryDto = CategoryDto(it.categoryName, getCategoryIconId(it.iconName))
        }
        return categoryDto
    }

    fun checkFirstAppInitialization() {
        viewModelScope.launch(EtDispatcher.io) {
            _isFirstAppInitialization.postValue(categoryRepository.getAllCategoriesCount() == 0)
        }
    }

    fun updateInitialTransaction(accountName: String) {
        if (runJobForData.value.data == accountName) {
            return
        }
        runJobForData.value.job?.cancel()
        runJobForData.value = RunJobForData(
            if (accountName == "All Accounts") getInitialTransactions(TransactionMethod.GET_ALL_TRANSACTIONS)
            else getInitialTransactions(TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT), accountName
        )
    }
}