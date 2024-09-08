package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import com.jaideep.expensetracker.model.TextFieldWithIconAndErrorPopUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionPagingRepository: TransactionPagingRepository,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _accounts: MutableStateFlow<List<Account>> = MutableStateFlow(ArrayList())
    var accounts: StateFlow<List<String>> = _accounts.map {
        it.asFlow().map { account ->
            account.accountName
        }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    private val _categories: MutableStateFlow<List<Category>> = MutableStateFlow(ArrayList())
    var categories: StateFlow<List<String>> = _categories.map {
        it.asFlow().map { category ->
            category.categoryName
        }.toList()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val accountState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateAccountTextState(it) },
            onErrorIconClick = { updateAccountErrorState() },
            errorMessage = ""
        )
    )

    val noteState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateNoteTextState(it) },
            onErrorIconClick = { updateNoteErrorState() },
            errorMessage = ""
        )
    )

    val amountState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateAmountTextState(it) },
            onErrorIconClick = { updateAmountErrorState() },
            errorMessage = ""
        )

    )

    val categoryState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateCategoryTextState(it) },
            onErrorIconClick = { updateCategoryErrorState() },
            errorMessage = ""
        )
    )

    val dateState = mutableStateOf(
        TextFieldWithIconAndErrorPopUpState(
            text = "",
            isError = false,
            showError = false,
            onValueChange = { updateDateTextState(it) },
            onErrorIconClick = { updateDateErrorState() },
            errorMessage = ""
        )
    )

    var screenTitle by mutableStateOf("Add Transaction")
        private set
    var radioButtonValue = mutableIntStateOf(0)
        private set
    var screenDetail by mutableStateOf("Please provide transaction details")
        private set

    var accountRetrievalError by mutableStateOf(false)
        private set
    var isAccountLoading by mutableStateOf(true)
        private set
    var categoryRetrievalError by mutableStateOf(false)
        private set
    var isCategoryLoading by mutableStateOf(true)
        private set
    var errorMessage by mutableStateOf("")
        private set
    var exitScreen by mutableStateOf(false)
        private set
    var isTransactionSaved by mutableStateOf(false)
        private set

    init {

        getAllAccounts()
        getAllCategories()
    }

    private fun updateAccountTextState(value: String) {
        accountState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = accountState.value.isError,
            showError = accountState.value.showError,
            onValueChange = accountState.value.onValueChange,
            onErrorIconClick = accountState.value.onErrorIconClick,
            errorMessage = accountState.value.errorMessage
        )
    }

    private fun updateDateErrorState() {
        dateState.value = TextFieldWithIconAndErrorPopUpState(
            text = dateState.value.text,
            isError = dateState.value.isError,
            showError = !dateState.value.showError,
            onValueChange = dateState.value.onValueChange,
            onErrorIconClick = dateState.value.onErrorIconClick,
            errorMessage = dateState.value.errorMessage
        )
    }

    private fun updateDateTextState(value: String) {
        dateState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = dateState.value.isError,
            showError = dateState.value.showError,
            onValueChange = dateState.value.onValueChange,
            onErrorIconClick = dateState.value.onErrorIconClick,
            errorMessage = dateState.value.errorMessage
        )
    }

    private fun updateCategoryErrorState() {
        categoryState.value = TextFieldWithIconAndErrorPopUpState(
            text = categoryState.value.text,
            isError = categoryState.value.isError,
            showError = !categoryState.value.showError,
            onValueChange = categoryState.value.onValueChange,
            onErrorIconClick = categoryState.value.onErrorIconClick,
            errorMessage = categoryState.value.errorMessage
        )
    }

    private fun updateCategoryTextState(value: String) {
        categoryState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = categoryState.value.isError,
            showError = categoryState.value.showError,
            onValueChange = categoryState.value.onValueChange,
            onErrorIconClick = categoryState.value.onErrorIconClick,
            errorMessage = categoryState.value.errorMessage
        )
    }

    private fun updateAmountErrorState() {
        amountState.value = TextFieldWithIconAndErrorPopUpState(
            text = amountState.value.text,
            isError = amountState.value.isError,
            showError = !amountState.value.showError,
            onValueChange = amountState.value.onValueChange,
            onErrorIconClick = amountState.value.onErrorIconClick,
            errorMessage = amountState.value.errorMessage
        )
    }

    private fun updateAmountTextState(value: String) {
        amountState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = amountState.value.isError,
            showError = amountState.value.showError,
            onValueChange = amountState.value.onValueChange,
            onErrorIconClick = amountState.value.onErrorIconClick,
            errorMessage = amountState.value.errorMessage
        )
    }

    private fun updateNoteErrorState() {
        noteState.value = TextFieldWithIconAndErrorPopUpState(
            text = noteState.value.text,
            isError = noteState.value.isError,
            showError = !noteState.value.showError,
            onValueChange = noteState.value.onValueChange,
            onErrorIconClick = noteState.value.onErrorIconClick,
            errorMessage = noteState.value.errorMessage
        )
    }

    private fun updateNoteTextState(value: String) {
        noteState.value = TextFieldWithIconAndErrorPopUpState(
            text = value,
            isError = noteState.value.isError,
            showError = noteState.value.showError,
            onValueChange = noteState.value.onValueChange,
            onErrorIconClick = noteState.value.onErrorIconClick,
            errorMessage = noteState.value.errorMessage
        )
    }

    private fun updateAccountErrorState() {
        accountState.value = TextFieldWithIconAndErrorPopUpState(
            text = accountState.value.text,
            isError = accountState.value.isError,
            showError = !accountState.value.showError,
            onValueChange = accountState.value.onValueChange,
            onErrorIconClick = accountState.value.onErrorIconClick,
            errorMessage = amountState.value.errorMessage
        )
    }

    private fun getAllCategories() = viewModelScope.launch(EtDispatcher.io) {
        getAllCategoriesUseCase().collect {
            when (it) {
                is Resource.Error -> {
                    withContext(EtDispatcher.main) {
                        errorMessage = it.message
                        categoryRetrievalError = true
                        isCategoryLoading = false
                    }
                }

                is Resource.Success -> {
                    withContext(EtDispatcher.main) {
                        _categories.value = it.data
                        isCategoryLoading = false
                    }
                }

                is Resource.Loading -> {
                    withContext(EtDispatcher.main) {
                        delay(Duration.ofMillis(500))
                        isCategoryLoading = true
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
                            errorMessage = it.message
                            accountRetrievalError = true
                            isAccountLoading = false
                        }
                    }

                    is Resource.Success -> {
                        withContext(EtDispatcher.main) {
                            _accounts.value = it.data
                            isAccountLoading = false
                        }
                    }

                    is Resource.Loading -> {
                        withContext(EtDispatcher.main) {
                            delay(Duration.ofMillis(500))
                            isAccountLoading = true
                        }
                    }
                }
            }
        }
    }

    fun toggleRadioButton(value: Int) {
        radioButtonValue.intValue = value
    }

    fun validateAndSaveTransaction() {
        val isAccValid = checkAccountError()
        val isCategoryValid = checkCategoryError()
        val isAmountValid = checkAmountError()
        val isDateValid = checkDateError()
        if (isAccValid && isCategoryValid && isAmountValid && isDateValid) {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val accountName = accountState.value.text
        val categoryName = categoryState.value.text
        val amount = amountState.value.text
        val date = dateState.value.text
        val note = noteState.value.text
        val isCredit = radioButtonValue.intValue
        val account = _accounts.value.stream().filter {
            it.accountName == accountName
        }.findAny()
        val category = _categories.value.stream().filter {
            it.categoryName == categoryName
        }.findAny()

        account.ifPresent {
            category.ifPresent {
                viewModelScope.launch {
                    transactionPagingRepository.saveTransaction(
                        Transaction(
                            0,
                            amount.toDouble(),
                            account.get().id,
                            category.get().id,
                            note,
                            (LocalDate.parse(date).atStartOfDay(ZoneId.of("Asia/Kolkata"))
                                .toEpochSecond() * 1000),
                            isCredit
                        )
                    )
                    isTransactionSaved = true
                    exitScreen = true
                }
            }
        }
    }

    private fun checkDateError(): Boolean {
        try {
            LocalDate.parse(dateState.value.text)
        } catch (de: DateTimeParseException) {
            dateState.value = TextFieldWithIconAndErrorPopUpState(
                text = dateState.value.text,
                isError = true,
                showError = !dateState.value.showError,
                onValueChange = dateState.value.onValueChange,
                onErrorIconClick = dateState.value.onErrorIconClick,
                errorMessage = "Enter a valid date"
            )
            return false
        }
        return true
    }

    private fun checkAmountError(): Boolean {
        try {
            amountState.value.text.toDouble()
        } catch (ne: NumberFormatException) {
            amountState.value = TextFieldWithIconAndErrorPopUpState(
                text = amountState.value.text,
                isError = true,
                showError = amountState.value.showError,
                onValueChange = amountState.value.onValueChange,
                onErrorIconClick = amountState.value.onErrorIconClick,
                errorMessage = "Enter a valid number"
            )
            return false
        }
        return true
    }

    private fun checkCategoryError(): Boolean {
        if (categoryState.value.text.isBlank()) {
            categoryState.value = TextFieldWithIconAndErrorPopUpState(
                text = categoryState.value.text,
                isError = true,
                showError = categoryState.value.showError,
                onValueChange = categoryState.value.onValueChange,
                onErrorIconClick = categoryState.value.onErrorIconClick,
                errorMessage = "Category cannot be empty"
            )
            return false
        }
        return true
    }

    private fun checkAccountError(): Boolean {
        if (accountState.value.text.isBlank()) {
            accountState.value = TextFieldWithIconAndErrorPopUpState(
                text = accountState.value.text,
                isError = true,
                showError = accountState.value.showError,
                onValueChange = accountState.value.onValueChange,
                onErrorIconClick = accountState.value.onErrorIconClick,
                errorMessage = "Account cannot be empty"
            )
            return false
        }
        return true
    }
}
