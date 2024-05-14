package com.jaideep.expensetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaideep.expensetracker.data.local.entities.Account
import com.jaideep.expensetracker.domain.repository.AccountRepository
import com.jaideep.expensetracker.model.AccountDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {
    fun saveAccount(accountDto: AccountDto) = viewModelScope.launch {
        accountRepository.saveAccount(
            Account(
                0,
                accountDto.accountName,
                accountDto.balance,
                System.currentTimeMillis()
            )
        )
    }
}