package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        accountRepository.getAccounts().collect {
            emit(Resource.Success(it))
        }
    }.catch {
        emit(Resource.Error("Error occurred while loading accounts, message : ${it.message}"))
    }
}