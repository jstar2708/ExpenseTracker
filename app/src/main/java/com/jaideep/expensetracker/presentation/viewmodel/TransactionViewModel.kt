package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(

) : ViewModel() {
    private val _selectedTabValue : MutableStateFlow<String> = MutableStateFlow("All")
    val selectedTabValue : StateFlow<String> = _selectedTabValue
    private val _selectedAccount = MutableStateFlow("All Accounts")
    val selectedAccount: StateFlow<String> = _selectedAccount
    private val _showDialog = MutableStateFlow(false)
    val showDialog : StateFlow<Boolean> = _showDialog
    var fromDate = mutableStateOf(TextFieldValue(""))
        private set
    val toDate = mutableStateOf(TextFieldValue(""))

    fun toggleDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun updateCurrentTab(pos: Int) {
        _selectedTabValue.value = when(pos) {
            0 -> "All"
            1 -> "Income"
            else -> "Expense"
        }
    }

    fun updateSelectedAccount(accountName: String) {
        _selectedAccount.value = accountName
    }

    fun checkValidDate() : Boolean {
        try {
            val startDate = LocalDate.parse(fromDate.value.text)
            val endDate = LocalDate.parse(toDate.value.text)
            return startDate.isBefore(endDate) || startDate.isEqual(endDate)
        }
        catch (_: Exception) {
            // Error Handling
        }
        return false
    }


}