package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
){
    suspend operator fun invoke() = flow {
        emit(Resource.Loading())
        try {
            val accounts = accountRepository.getAccounts()
            emit(Resource.Success(accounts))
        }
        catch(ex: Exception) {
            emit(Resource.Error("Error occurred while loading accounts"))
        }
    }
}