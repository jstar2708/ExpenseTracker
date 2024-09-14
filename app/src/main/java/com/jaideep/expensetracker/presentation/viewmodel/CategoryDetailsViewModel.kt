package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.usecase.GetAllAccountsUseCase
import com.jaideep.expensetracker.domain.usecase.GetAllCategoryWiseTransactions
import com.jaideep.expensetracker.domain.usecase.GetCategoryByNameUseCase
import com.jaideep.expensetracker.model.DialogState
import com.jaideep.expensetracker.model.dto.CategoryDto
import com.jaideep.expensetracker.model.dto.TransactionDto
import com.jaideep.expensetracker.presentation.utility.Utility.getCategoryIconId
import com.jaideep.expensetracker.presentation.utility.Utility.stringDateToMillis
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
class CategoryDetailsViewModel @Inject constructor(
    private val getAllCategoryWiseTransactions: GetAllCategoryWiseTransactions,
    private val getCategoryUseCase: GetCategoryByNameUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase
) : ViewModel() {
    val categoryName: MutableStateFlow<String> = MutableStateFlow(String())
    private val _transactions: MutableStateFlow<List<Transaction>> = MutableStateFlow(ArrayList())
    var transactions: StateFlow<List<TransactionDto>> = _transactions.map {
        it.asFlow().map { transaction ->
            TransactionDto(
                transaction.id,
                transaction.amount,
                category.value ?: CategoryDto(
                    categoryName.value, getCategoryIconId(categoryName.value)
                ),
                transaction.message,
                LocalDate.ofEpochDay(transaction.createdTime / 86_400_000L),
                transaction.isCredit == 1
            )
        }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(ArrayList())
    var accounts: StateFlow<List<String>> = _accounts.map { list ->
        list.asFlow().map { it.accountName }.toList().toMutableList().apply {
            this.add(0, "All Accounts")
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val accountValue = mutableStateOf("All Accounts")
    val category: MutableStateFlow<CategoryDto?> = MutableStateFlow(null)
    val isCategoryLoading = mutableStateOf(true)
    val categoryRetrievalError = mutableStateOf(false)
    val isTransactionsLoading = mutableStateOf(true)
    val transactionsRetrievalError = mutableStateOf(false)
    val isAccountLoading = mutableStateOf(true)
    val accountRetrievalError = mutableStateOf(false)
    val dialogState = mutableStateOf(
        DialogState(
            showDialog = false,
            fromDate = String(),
            toDate = String(),
            showError = false,
            errorMessage = String()
        )
    )

    private var transactionJob: Job? = null

    fun initData(categoryName: String) {
        this.categoryName.value = categoryName
        transactionJob = getCategoryWiseTransactions()
        getCategory()
        getAllAccounts()
    }

    private fun getCategory() = viewModelScope.launch(EtDispatcher.io) {
        getCategoryUseCase(categoryName.value).collectLatest {
            when (it) {
                is Resource.Error -> {
                    categoryRetrievalError.value = true
                    isCategoryLoading.value = false
                }

                is Resource.Loading -> {
                    categoryRetrievalError.value = false
                    isCategoryLoading.value = true
                }

                is Resource.Success -> {
                    category.value = it.data
                    delay(500)
                    categoryRetrievalError.value = false
                    isCategoryLoading.value = false
                }
            }
        }
    }

    fun onAccountSpinnerValueChanged(account: String) {
        accountValue.value = account
        transactionJob?.cancel()
        transactionJob = getCategoryWiseTransactions()
    }

    fun updateTransactionList() {
        transactionJob?.cancel()
        transactionJob = getCategoryWiseTransactions()
    }

    fun toggleDialogVisibility() {
        dialogState.value = DialogState(
            showDialog = !dialogState.value.showDialog,
            fromDate = dialogState.value.fromDate,
            toDate = dialogState.value.toDate,
            showError = false,
            errorMessage = String()
        )
    }

    fun checkValidDate(): Boolean {
        try {
            val startDate = LocalDate.parse(dialogState.value.fromDate)
            val endDate = LocalDate.parse(dialogState.value.toDate)
            if (!(startDate.isBefore(endDate) || startDate.isEqual(endDate))) {
                showErrorMessage(message = "Start date cannot be greater than end date")
                return false
            }
            return true
        } catch (_: Exception) {
            showErrorMessage(message = "Enter start date and end date")
        }
        return false
    }

    private fun showErrorMessage(message: String) {
        dialogState.value = DialogState(
            showDialog = true,
            fromDate = dialogState.value.fromDate,
            toDate = dialogState.value.toDate,
            showError = true,
            errorMessage = message
        )
    }

    fun updateFromDate(fromDate: String) {
        dialogState.value = DialogState(
            showDialog = dialogState.value.showDialog,
            fromDate = fromDate,
            toDate = dialogState.value.toDate,
            showError = dialogState.value.showError,
            errorMessage = dialogState.value.errorMessage
        )
    }

    fun updateToDate(toDate: String) {
        dialogState.value = DialogState(
            showDialog = dialogState.value.showDialog,
            fromDate = dialogState.value.fromDate,
            toDate = toDate,
            showError = dialogState.value.showError,
            errorMessage = dialogState.value.errorMessage
        )
    }

    fun clearDialogDate() {
        dialogState.value = DialogState(
            showDialog = false,
            fromDate = String(),
            toDate = String(),
            showError = false,
            errorMessage = String()
        )
    }

    private fun getAllAccounts() = viewModelScope.launch(EtDispatcher.io) {
        getAllAccountsUseCase().collectLatest {
            if (this.isActive) {
                when (it) {
                    is Resource.Error -> {
                        accountRetrievalError.value = true
                        isAccountLoading.value = false
                    }

                    is Resource.Loading -> {
                        isAccountLoading.value = true
                        accountRetrievalError.value = false
                    }

                    is Resource.Success -> {
                        _accounts.value = it.data
                        delay(500)
                        isAccountLoading.value = false
                        accountRetrievalError.value = false
                    }
                }
            }
        }
    }

    private fun getCategoryWiseTransactions() = viewModelScope.launch(EtDispatcher.io) {
        val fromDate = stringDateToMillis(dialogState.value.fromDate)
        var toDate = stringDateToMillis(dialogState.value.toDate)
        if (toDate == 0L) {
            toDate = Long.MAX_VALUE
        }

        getAllCategoryWiseTransactions(
            categoryName.value,
            accountValue.value,
            fromDate, toDate
        ).collectLatest {
            if (this.isActive) {
                when (it) {
                    is Resource.Loading -> {
                        isTransactionsLoading.value = true
                        transactionsRetrievalError.value = false
                    }

                    is Resource.Success -> {
                        _transactions.value = it.data
                        delay(500)
                        isTransactionsLoading.value = false
                        transactionsRetrievalError.value = false
                    }

                    is Resource.Error -> {
                        isTransactionsLoading.value = false
                        transactionsRetrievalError.value = true
                    }
                }
            }
        }
    }
}