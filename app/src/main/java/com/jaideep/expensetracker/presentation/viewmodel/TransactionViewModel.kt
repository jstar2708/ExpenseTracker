package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import com.jaideep.expensetracker.model.DialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class
TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    val tabItemsList: ImmutableList<String> = persistentListOf("All", "Income", "Expense")
    val selectedTabValue = mutableIntStateOf(0)
    val selectedAccount = mutableStateOf("All Accounts")
    val dialogState = mutableStateOf(
        DialogState(
            showDialog = false,
            fromDate = String(),
            toDate = String(),
            showError = false,
            errorMessage = String()
        )
    )
    private val transactionIdClicked = mutableIntStateOf(-1)
    val showDeleteDialog = mutableStateOf(false)
    val expandTransactionCard = mutableStateOf(false)

    fun toggleTransactionCard() {
        expandTransactionCard.value = !expandTransactionCard.value
    }

    fun onTransactionDeleteClicked(id: Int) {
        showDeleteDialog.value = true
        transactionIdClicked.intValue = id
    }

    fun hideDeleteDialog() {
        showDeleteDialog.value = false
    }

    fun deleteTransaction() = viewModelScope.launch(EtDispatcher.io) {
        expandTransactionCard.value = false
        if (transactionIdClicked.intValue != -1) {
            transactionRepository.deleteTransactionById(transactionIdClicked.intValue)
        }
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

    fun updateCurrentTab(pos: Int) {
        selectedTabValue.intValue = pos
    }

    fun updateSelectedAccount(accountName: String) {
        selectedAccount.value = accountName
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

    fun getTabValue(pos: Int): String {
        return when (pos) {
            0 -> "All"
            1 -> "Income"
            else -> "Expense"
        }
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

}