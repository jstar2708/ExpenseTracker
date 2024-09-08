package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
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
import com.jaideep.expensetracker.model.TransactionMethodData
import com.jaideep.expensetracker.model.TransactionMethodDataForDates
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.model.dto.TransactionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
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
        list.asFlow().map { it.accountName }.toList().toMutableList().apply {
            this.add(0, "All Accounts")
        }
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

    private val _transactionMethodData: MutableStateFlow<TransactionMethodData> = MutableStateFlow(
        TransactionMethodData(
            TransactionMethod.GET_ALL_TRANSACTIONS, "All Accounts"
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _pagedTransactionItems: MutableStateFlow<Flow<PagingData<TransactionDto>>> =
        MutableStateFlow(Pager(config = PagingConfig(pageSize = 50), pagingSourceFactory = {
            transactionPagingRepository.getAllTransactions()
        }).flow.mapLatest { pagingData ->
            pagingData.map { transaction ->
                TransactionDto(
                    transaction.amount,
                    getCategoryDto(transaction.categoryId),
                    transaction.message,
                    LocalDate.ofEpochDay(transaction.createdTime / 86_400_000L),
                    transaction.isCredit == 1
                )
            }
        }.cachedIn(viewModelScope))

    val pagedTransactionItems = _pagedTransactionItems.asStateFlow()

    private val _isFirstAppInitialization = MutableLiveData<Boolean>()
    var isFirstAppInitialization = _isFirstAppInitialization

    private val _hasCategoriesLoaded = MutableStateFlow(false)

    private var runJobForData: MutableStateFlow<RunJobForData<String>> = MutableStateFlow(
        RunJobForData()
    )

    var accountRetrievalError by mutableStateOf(false)
        private set
    var isAccountLoading by mutableStateOf(true)
        private set
    var categoryRetrievalError by mutableStateOf(false)
        private set
    var isCategoryLoading by mutableStateOf(true)
        private set
    var isTransactionLoading by mutableStateOf(true)
        private set
    var transactionRetrievalError by mutableStateOf(false)
        private set

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
                    isAccountLoading = true
                    accountRetrievalError = false
                }

                is Resource.Success -> {
                    isAccountLoading = false
                    accountRetrievalError = false
                    _accounts.value = it.data
                }

                is Resource.Error -> {
                    isAccountLoading = false
                    accountRetrievalError = true
                }
            }
        }
    }

    private fun getAllCategories() = viewModelScope.launch(EtDispatcher.io) {
        getAllCategoriesUseCase().collect {
            when (it) {
                is Resource.Loading -> {
                    isCategoryLoading = true
                    categoryRetrievalError = false
                }

                is Resource.Success -> {
                    _categories.value = it.data
                    isCategoryLoading = false
                    _hasCategoriesLoaded.value = true
                    categoryRetrievalError = false
                    _hasCategoriesLoaded.collectLatest { hasCategoriesLoaded ->
                        if (hasCategoriesLoaded && _transactions.value.isEmpty()) {
                            getTransactionPagingSource()
                            getInitialTransactions(TransactionMethod.GET_ALL_TRANSACTIONS)
                        }
                    }
                }

                is Resource.Error -> {
                    isCategoryLoading = false
                    categoryRetrievalError = true
                }
            }
        }
    }

    private fun getInitialTransactions(transactionMethod: TransactionMethod) =
        viewModelScope.launch(EtDispatcher.io) {
            if (this.isActive) {
                getInitialTransactionsUseCase(
                    TransactionMethodData(
                        transactionMethod, runJobForData.value.data ?: ""
                    )
                ).collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            isTransactionLoading = true
                            transactionRetrievalError = false
                        }

                        is Resource.Success -> {
                            isTransactionLoading = false
                            transactionRetrievalError = false
                            _transactions.value = it.data
                        }

                        is Resource.Error -> {
                            transactionRetrievalError = true
                            isTransactionLoading = false
                        }
                    }
                }
            }
        }

    fun updateTransactionMethod(
        accountName: String,
        isCredit: Boolean,
        isDebit: Boolean,
        startDate: String?,
        endDate: String?
    ) {
        val transactionMethod = if (accountName == "All Accounts") {
            if (isCredit) {
                if (startDate != null && endDate != null && startDate.isNotBlank() && endDate.isNotBlank()) TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES
                else TransactionMethod.GET_CREDIT_TRANSACTIONS
            } else if (isDebit) {
                if (startDate != null && endDate != null && startDate.isNotBlank() && endDate.isNotBlank()) TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES
                else TransactionMethod.GET_DEBIT_TRANSACTIONS
            } else {
                if (startDate != null && endDate != null && startDate.isNotBlank() && endDate.isNotBlank()) {
                    TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES
                } else {
                    TransactionMethod.GET_ALL_TRANSACTIONS
                }
            }
        } else {
            if (isCredit) {
                if (startDate != null && endDate != null && startDate.isNotBlank() && endDate.isNotBlank()) TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT
                else TransactionMethod.GET_CREDIT_TRANSACTIONS_FOR_ACCOUNT
            } else if (isDebit) {
                if (startDate != null && endDate != null && startDate.isNotBlank() && endDate.isNotBlank()) TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT
                else TransactionMethod.GET_DEBIT_TRANSACTIONS_FOR_ACCOUNT
            } else {
                if (startDate != null && endDate != null && startDate.isNotBlank() && endDate.isNotBlank()) TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT
                else TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT
            }
        }
        if (startDate != null && endDate != null && startDate.isNotBlank() && endDate.isNotBlank()) {
            val startDateLong =
                LocalDate.parse(startDate).atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond()
                    .times(1000)
            val endDateLong =
                LocalDate.parse(endDate).atStartOfDay(ZoneId.of("Asia/Kolkata")).toEpochSecond()
                    .times(1000)
            _transactionMethodData.value = TransactionMethodDataForDates(
                transactionMethod, accountName, startDateLong, endDateLong
            )
        } else {
            _transactionMethodData.value = TransactionMethodData(transactionMethod, accountName)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getTransactionPagingSource() = viewModelScope.launch {
        _transactionMethodData.collectLatest { transactionMethodData ->
            _pagedTransactionItems.value =
                Pager(config = PagingConfig(pageSize = 50), pagingSourceFactory = {
                    val accountId =
                        _accounts.value.firstOrNull { it.accountName == transactionMethodData.accountName }?.id
                            ?: 0
                    if (transactionMethodData is TransactionMethodDataForDates) when (transactionMethodData.transactionMethod) {
                        TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionPagingRepository.getCreditTransactionBetweenDatesForAccount(
                            accountId,
                            transactionMethodData.startDate,
                            transactionMethodData.endDate,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionPagingRepository.getDebitTransactionBetweenDatesForAccount(
                            accountId,
                            transactionMethodData.startDate,
                            transactionMethodData.endDate,
                        )

                        TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES_FOR_ACCOUNT -> transactionPagingRepository.getTransactionBetweenDatesForAccount(
                            accountId,
                            transactionMethodData.startDate,
                            transactionMethodData.endDate
                        )

                        TransactionMethod.GET_CREDIT_TRANSACTIONS_BETWEEN_DATES -> transactionPagingRepository.getCreditTransactionBetweenDates(
                            transactionMethodData.startDate,
                            transactionMethodData.endDate,
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_BETWEEN_DATES -> transactionPagingRepository.getDebitTransactionBetweenDates(
                            transactionMethodData.startDate,
                            transactionMethodData.endDate,
                        )

                        TransactionMethod.GET_TRANSACTIONS_BETWEEN_DATES -> transactionPagingRepository.getTransactionBetweenDates(
                            transactionMethodData.startDate,
                            transactionMethodData.endDate,
                        )

                        else -> transactionPagingRepository.getTransactionBetweenDates(
                            transactionMethodData.startDate, transactionMethodData.endDate
                        )
                    }
                    else when (transactionMethodData.transactionMethod) {
                        TransactionMethod.GET_DEBIT_TRANSACTIONS -> transactionPagingRepository.getDebitTransactions()
                        TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT -> transactionPagingRepository.getTransactionsForAccount(
                            accountId
                        )

                        TransactionMethod.GET_DEBIT_TRANSACTIONS_FOR_ACCOUNT -> transactionPagingRepository.getDebitTransactionsForAccount(
                            accountId
                        )

                        TransactionMethod.GET_CREDIT_TRANSACTIONS_FOR_ACCOUNT -> transactionPagingRepository.getCreditTransactionsForAccount(
                            accountId
                        )

                        TransactionMethod.GET_ALL_TRANSACTIONS -> transactionPagingRepository.getAllTransactions()
                        TransactionMethod.GET_CREDIT_TRANSACTIONS -> transactionPagingRepository.getCreditTransactions()
                        else -> transactionPagingRepository.getAllTransactions()

                    }
                }).flow.mapLatest { pagingData ->
                    pagingData.map { transaction ->
                        TransactionDto(
                            transaction.amount,
                            getCategoryDto(transaction.categoryId),
                            transaction.message,
                            LocalDate.ofEpochDay(transaction.createdTime / 86_400_000L),
                            transaction.isCredit == 1
                        )
                    }
                }.cachedIn(viewModelScope)
        }
    }

    private fun getCategoryDto(categoryId: Int): CategoryDto {
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