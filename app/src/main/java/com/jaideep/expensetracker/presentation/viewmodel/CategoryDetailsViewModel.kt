package com.jaideep.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.common.EtDispatcher
import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.dao.EtDao
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.usecase.GetAllCategoryWiseTransactions
import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryDetailsViewModel @Inject constructor(
    private val getAllCategoryWiseTransactions: GetAllCategoryWiseTransactions
): ViewModel() {
    private val _transactions: MutableStateFlow<List<Transaction>> = MutableStateFlow(ArrayList())
    var transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    fun initData() {
        getCategoryWiseTransactions()
    }

    private fun getCategoryWiseTransactions() = viewModelScope.launch (EtDispatcher.io){
        getAllCategoryWiseTransactions("", "" ,0).collectLatest {
            if (this.isActive) {
                when (it) {
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }
}