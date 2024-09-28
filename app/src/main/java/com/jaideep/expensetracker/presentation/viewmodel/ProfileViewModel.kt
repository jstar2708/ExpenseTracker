package com.jaideep.expensetracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    var userName by mutableStateOf("")
        private set
    var totalTransactions by mutableStateOf("")
        private set
    var totalExpenditure by mutableStateOf("")
        private set
    var avgMonthlyExpenditure by mutableStateOf("")
        private set
    var mostFrequentlyUsedAccount by mutableStateOf("")
        private set

    private fun getUserName() {

    }
}