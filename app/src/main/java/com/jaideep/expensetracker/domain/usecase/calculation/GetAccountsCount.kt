package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccountsCount @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        emit(Resource.Success(accountRepository.getTotalAccountsCount()))
    }.catch {
        emit(Resource.Error("Error while loading accounts count."))
    }
}