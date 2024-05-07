package com.jaideep.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.model.AccountDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    fun saveAccount(accountDto: AccountDto) {

    }
}