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
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.common.constant.AppConstants.CURRENCY
import com.jaideep.expensetracker.common.constant.TransactionMethod
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.preferences.DatastoreRepository
import com.jaideep.expensetracker.domain.repository.CategoryRepository
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import com.jaideep.expensetracker.domain.usecase.GetAllAccountsUseCase
import com.jaideep.expensetracker.domain.usecase.GetAllCategoriesUseCase
import com.jaideep.expensetracker.domain.usecase.GetAllCategoryCardsDataUseCase
import com.jaideep.expensetracker.domain.usecase.GetInitialTransactionsUseCase
import com.jaideep.expensetracker.model.CategoryCardData
import com.jaideep.expensetracker.model.CategoryCardPayload
import com.jaideep.expensetracker.model.RunJobForData
import com.jaideep.expensetracker.model.TransactionMethodData
import com.jaideep.expensetracker.model.TransactionMethodDataForDates
import com.jaideep.expensetracker.model.dto.AccountDto
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.model.dto.TransactionDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val transactionPagingRepository: TransactionPagingRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getInitialTransactionsUseCase: GetInitialTransactionsUseCase,
    private val getAllCategoryCardsDataUseCase: GetAllCategoryCardsDataUseCase,
    private val categoryRepository: CategoryRepository,
    private val datastoreRepository: DatastoreRepository
) : ViewModel() {
    private val _currentCurrencySymbol: MutableStateFlow<String> = MutableStateFlow(String())
    var currentCurrencySymbol = _currentCurrencySymbol.asStateFlow()
    private val _accounts: MutableStateFlow<List<AccountDto>> = MutableStateFlow(ArrayList())
    var accounts = _accounts.asStateFlow()
    var accountsNames: StateFlow<List<String>> = _accounts.map { list ->
        list.asFlow().map { it.accountName }.toList().toMutableList().apply {
            this.add(0, "All Accounts")
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    private val _categories: MutableStateFlow<List<CategoryDto>> = MutableStateFlow(ArrayList())
    var categories: StateFlow<List<CategoryDto>> = _categories.asStateFlow()

    private val _transactions: MutableStateFlow<List<TransactionDto>> =
        MutableStateFlow(ArrayList())
    var transactions: StateFlow<List<TransactionDto>> = _transactions.asStateFlow()

    private val _transactionMethodData: MutableStateFlow<TransactionMethodData> = MutableStateFlow(
        TransactionMethodData(
            TransactionMethod.GET_ALL_TRANSACTIONS, "All Accounts"
        )
    )

    private val _categoryCardsData: MutableStateFlow<List<CategoryCardData>> =
        MutableStateFlow(ArrayList())
    var categoryCardsData: StateFlow<List<CategoryCardData>> =
        _categoryCardsData.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    private val _pagedTransactionItems: MutableStateFlow<Flow<PagingData<TransactionDto>>> =
        MutableStateFlow(Pager(config = PagingConfig(pageSize = 50), pagingSourceFactory = {
            transactionPagingRepository.getAllTransactions()
        }).flow.cachedIn(viewModelScope))

    val pagedTransactionItems = _pagedTransactionItems.asStateFlow()

    private val _isFirstAppInitialization = MutableLiveData<Boolean>()
    var isFirstAppInitialization = _isFirstAppInitialization

    private val _hasCategoriesLoaded = MutableStateFlow(false)
    private val _hasAccountsLoaded = MutableStateFlow(false)

    private val initialTransactionsJobData: MutableStateFlow<RunJobForData<String>> =
        MutableStateFlow(
            RunJobForData()
        )
    private val categoryCardsDataJob: MutableStateFlow<RunJobForData<CategoryCardPayload>> =
        MutableStateFlow(
            RunJobForData(
                data = CategoryCardPayload("All Accounts", "This Month"),
                job = getAllCategoryCardsData()
            )
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
    var isCategoryCardDataLoading by mutableStateOf(true)
        private set
    var categoryCardDataRetrievalError by mutableStateOf(false)
        private set
    var isAccountsCountZero by mutableStateOf(false)
        private set

    fun initData() {
        getCurrentCurrencyFromDataStore()
        getAllAccounts()
        getAllCategories()
    }

    private fun getCurrentCurrencyFromDataStore() = viewModelScope.launch(EtDispatcher.io) {
        datastoreRepository.getString(CURRENCY).collectLatest { value ->
            _currentCurrencySymbol.value = value ?: "₹"
        }
    }

    private fun getAllCategoryCardsData() = viewModelScope.launch(EtDispatcher.io) {
        categoryCardsDataJob.value.data?.accountName?.let { accountName ->
            categoryCardsDataJob.value.data?.duration?.let { duration ->
                getAllCategoryCardsDataUseCase(
                    accountName, duration
                ).collectLatest {
                    if (this.isActive) {
                        when (it) {
                            is Resource.Loading -> {
                                isCategoryCardDataLoading = true
                                categoryCardDataRetrievalError = false
                            }

                            is Resource.Success -> {
                                _categoryCardsData.value = it.data
                                delay(Duration.ofMillis(500))
                                isCategoryCardDataLoading = false
                                categoryCardDataRetrievalError = false
                            }

                            is Resource.Error -> {
                                isCategoryCardDataLoading = false
                                categoryCardDataRetrievalError = true
                            }
                        }
                    }
                }
            }
        }
    }

    fun addDefaultCategories() {
        viewModelScope.launch(EtDispatcher.io) {
            categoryRepository.saveAllCategories(
                listOf(
                    Category(0, "Food", "Food"),
                    Category(0, "Fuel", "Fuel"),
                    Category(0, "Entertainment", "Entertainment"),
                    Category(0, "Shopping", "Shopping"),
                    Category(0, "Travel", "Travel"),
                    Category(0, "Salary", "Salary"),
                    Category(0, "Hospital", "Hospital"),
                    Category(0, "Medicine", "Medicine"),
                    Category(0, "Mobile Recharge", "Mobile Recharge"),
                    Category(0, "Other", "Other")
                )
            )
            initData()
        }
    }

    private fun getAllAccounts() = viewModelScope.launch(EtDispatcher.io) {
        getAllAccountsUseCase().collectLatest {
            when (it) {
                is Resource.Loading -> {
                    isAccountLoading = true
                    accountRetrievalError = false
                }

                is Resource.Success -> {
                    _accounts.value = it.data
                    delay(Duration.ofMillis(500))
                    _hasAccountsLoaded.value = true
                    isAccountLoading = false
                    accountRetrievalError = false
                    isAccountsCountZero = it.data.isEmpty()
                    _hasAccountsLoaded.collectLatest { hasAccountLoaded ->
                        if (hasAccountLoaded && _transactions.value.isEmpty()) {
                            getTransactionPagingSource()
                            getInitialTransactions(TransactionMethod.GET_ALL_TRANSACTIONS)
                            getAllCategoryCardsData()
                        }
                    }
                }

                is Resource.Error -> {
                    isAccountLoading = false
                    accountRetrievalError = true
                }
            }
        }
    }

    private fun getAllCategories() = viewModelScope.launch(EtDispatcher.io) {
        getAllCategoriesUseCase().collectLatest {
            when (it) {
                is Resource.Loading -> {
                    isCategoryLoading = true
                    categoryRetrievalError = false
                }

                is Resource.Success -> {
                    _categories.value = it.data
                    delay(Duration.ofMillis(500))
                    isCategoryLoading = false
                    _hasCategoriesLoaded.value = true
                    categoryRetrievalError = false
                    _hasCategoriesLoaded.collectLatest { hasCategoriesLoaded ->
                        if (hasCategoriesLoaded && _transactions.value.isEmpty()) {
                            getTransactionPagingSource()
                            getInitialTransactions(TransactionMethod.GET_ALL_TRANSACTIONS)
                            getAllCategoryCardsData()
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
            getInitialTransactionsUseCase(
                TransactionMethodData(
                    transactionMethod, initialTransactionsJobData.value.data ?: ""
                )
            ).collectLatest {
                if (this.isActive) {
                    when (it) {
                        is Resource.Loading -> {
                            isTransactionLoading = true
                            transactionRetrievalError = false
                        }

                        is Resource.Success -> {
                            _transactions.value = it.data
                            delay(Duration.ofMillis(500))
                            isTransactionLoading = false
                            transactionRetrievalError = false
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
                }).flow.cachedIn(viewModelScope)
        }
    }

    fun checkFirstAppInitialization() {
        viewModelScope.launch(EtDispatcher.io) {
            _isFirstAppInitialization.postValue(categoryRepository.getAllCategoriesCount() == 0)
        }
    }

    fun updateInitialTransaction(accountName: String) {
        if (initialTransactionsJobData.value.data == accountName) {
            return
        }
        initialTransactionsJobData.value.job?.cancel()
        initialTransactionsJobData.value = RunJobForData(
            if (accountName == "All Accounts") getInitialTransactions(TransactionMethod.GET_ALL_TRANSACTIONS)
            else getInitialTransactions(TransactionMethod.GET_TRANSACTIONS_FOR_ACCOUNT), accountName
        )
    }

    fun updateCategoryCardsData(categoryCardPayload: CategoryCardPayload) {
        if (categoryCardsDataJob.value.data?.accountName == categoryCardPayload.accountName && categoryCardsDataJob.value.data?.duration == categoryCardPayload.duration) {
            return
        }
        categoryCardsDataJob.value.job?.cancel()
        categoryCardsDataJob.value = RunJobForData(
            data = categoryCardPayload, job = getAllCategoryCardsData()
        )
    }

    fun updateCurrencySymbol(symbol: String = "₹") = viewModelScope.launch(EtDispatcher.io) {
        datastoreRepository.putString(CURRENCY, symbol)
        _currentCurrencySymbol.value = symbol
    }
}