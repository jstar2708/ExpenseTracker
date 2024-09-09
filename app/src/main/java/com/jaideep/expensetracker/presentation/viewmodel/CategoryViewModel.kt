package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class CategoryViewModel : ViewModel() {
    val durationList: ImmutableList<String> = persistentListOf("This Month", "This Year")
    val accountValue = mutableStateOf("All Accounts")
    val durationValue = mutableStateOf("This Month")

    fun onAccountSpinnerValueChanged(account: String) {
        accountValue.value = account
    }

    fun onDurationSpinnerValueChanged(duration: String) {
        durationValue.value = duration
    }
}