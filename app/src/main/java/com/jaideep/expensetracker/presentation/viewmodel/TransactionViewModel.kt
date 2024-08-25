package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.Recomposer
import androidx.lifecycle.ViewModel
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(

) : ViewModel() {
    private val _selectedTabValue : MutableStateFlow<String> = MutableStateFlow("All")
    val selectedTabValue : StateFlow<String> = _selectedTabValue
    private val _selectedAccount = MutableStateFlow("All Accounts")
    val selectedAccount: StateFlow<String> = _selectedAccount

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

}