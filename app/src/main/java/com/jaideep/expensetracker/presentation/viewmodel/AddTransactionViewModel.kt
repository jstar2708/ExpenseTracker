package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.data.local.entities.Category
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import com.jaideep.expensetracker.domain.usecase.GetAllAccountsUseCase
import com.jaideep.expensetracker.domain.usecase.GetAllCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionPagingRepository: TransactionPagingRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(ArrayList())
    var accounts: StateFlow<List<String>> = _accounts.map { it ->
        it.asFlow().map { account ->
            account.accountName
        }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(ArrayList())
    var categories: StateFlow<List<String>> = _categories.map { it ->
        it.asFlow().map { category ->
            category.categoryName
        }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    var screenTitle by mutableStateOf("Add Transaction")
        private set
    var radioButtonValue by mutableStateOf("Income")
        private set
    var screenDetail by mutableStateOf("Please provide transaction details")
        private set
    var accountName = mutableStateOf(TextFieldValue(""))
        private set
    var isAccountNameIncorrect = mutableStateOf(false)
        private set
    var categoryName = mutableStateOf(TextFieldValue(""))
        private set
    var isCategoryNameIncorrect = mutableStateOf(false)
        private set
    var amount = mutableStateOf(TextFieldValue(""))
        private set
    var isAmountIncorrect = mutableStateOf(false)
        private set
    var isDateIncorrect = mutableStateOf(false)
        private set
    var date = mutableStateOf(TextFieldValue(""))
        private set
    var note = mutableStateOf(TextFieldValue(""))
        private set
    var dataRetrievalError = mutableStateOf(false)
        private set
    var isLoading = mutableStateOf(true)
        private set
    var errorMessage = mutableStateOf("")
        private set
    var exitScreen = mutableStateOf(false)
        private set
    var isTransactionSaved = mutableStateOf(false)
        private set

    init {
        getAllAccounts()
        getAllCategories()
    }

    private fun getAllCategories() = viewModelScope.launch(EtDispatcher.io) {
        getAllCategoriesUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    withContext(EtDispatcher.main) {
                        errorMessage.value = it.message
                        dataRetrievalError.value = true
                        isLoading.value = false
                    }
                }

                is Resource.Success -> {
                    withContext(EtDispatcher.main) {
                        _categories.value = it.data
                        isLoading.value = false
                    }
                }

                is Resource.Loading -> {
                    withContext(EtDispatcher.main) {
                        isLoading.value = true
                    }
                }
            }
        }
    }

    private fun getAllAccounts() {
        viewModelScope.launch(EtDispatcher.io) {
            getAllAccountsUseCase().collect {
                when (it) {
                    is Resource.Error -> {
                        withContext(EtDispatcher.main) {
                            errorMessage.value = it.message
                            dataRetrievalError.value = true
                            isLoading.value = false
                        }
                    }

                    is Resource.Success -> {
                        withContext(EtDispatcher.main) {
                            _accounts.value = it.data
                            isLoading.value = false
                        }
                    }

                    is Resource.Loading -> {
                        withContext(EtDispatcher.main) {
                            isLoading.value = true
                        }
                    }
                }
            }
        }
    }

    fun toggleRadioButton(value: String) {
        radioButtonValue = value
    }

    fun saveTransaction(
        accountName: String,
        categoryName: String,
        amount: String,
        date: String,
        note: String,
        isCredit: Boolean
    ) {
        isAccountNameIncorrect.value = accountName.isBlank()
        isCategoryNameIncorrect.value = categoryName.isBlank()
        isDateIncorrect.value = date.isBlank()
        isAmountIncorrect.value = amount.isBlank()
        val account = _accounts.value.stream().filter {
            it.accountName == accountName
        }.findAny()
        val category = _categories.value.stream().filter {
            it.categoryName == categoryName
        }.findAny()

        account.ifPresent {
            category.ifPresent {
                viewModelScope.launch {
                    try {
                        transactionPagingRepository.saveTransaction(
                            Transaction(
                                0,
                                amount.toDouble(),
                                account.get().id,
                                category.get().id,
                                note,
                                LocalDate.parse(date).toEpochDay() * 86_400_000L,
                                if (isCredit) 1 else 0
                            )
                        )

                        isTransactionSaved.value = true
                        exitScreen.value = true
                    } catch (ne: NumberFormatException) {
                        isAmountIncorrect.value = true
                        isTransactionSaved.value = false
                    } catch (de: DateTimeParseException) {
                        isDateIncorrect.value = true
                        isTransactionSaved.value = false
                    }
                }
            }
        }
    }
}
