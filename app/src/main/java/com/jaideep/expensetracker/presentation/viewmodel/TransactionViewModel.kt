package com.jaideep.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.jaideep.expensetracker.domain.repository.TransactionPagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionPagingRepository
): ViewModel() {

}