package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccountsCount @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = flow {
        emit(accountRepository.getTotalAccountsCount())
    }
}