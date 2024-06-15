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
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import com.jaideep.expensetracker.domain.usecase.GetAllAccountsUseCase
import com.jaideep.expensetracker.domain.usecase.GetAllCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(ArrayList())
    var accounts: StateFlow<List<Account>> = _accounts

    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(ArrayList())
    var categories: StateFlow<List<Category>> = _categories

    var screenTitle by mutableStateOf("Add Transaction")
        private set
    var radioButtonValue by mutableStateOf("Income")
        private set
    var screenDetail by mutableStateOf("Please provide transaction details")
        private set
    var accountName = mutableStateOf(TextFieldValue(""))
        private set
    var categoryName = mutableStateOf(TextFieldValue(""))
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
        try {
            val account = accounts.value.stream().filter {
                    it.accountName == accountName
                }.findAny()
            val category = categories.value.stream().filter {
                    it.categoryName == categoryName
                }.findAny()

            account.ifPresent {
                category.ifPresent {
                    viewModelScope.launch {
                        transactionRepository.saveTransaction(
                            Transaction(
                                0,
                                amount.toDouble(),
                                account.get().id,
                                category.get().id,
                                note,
                                LocalDateTime.parse(date).toEpochSecond(ZoneOffset.UTC),
                                if (isCredit) 1 else 0
                            )
                        )
                    }
                }
            }
        } catch (ne: NumberFormatException) {
            isAmountIncorrect.value = true
        } catch (de: DateTimeParseException) {
            isDateIncorrect.value = true
        }
    }
}
