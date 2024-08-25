package com.jaideep.expensetracker.domain.usecase.calculation

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAccountBalanceUseCase @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(accountName: String) = flow {
        emit(Resource.Loading())
        if (accountName == "All Accounts") {
            etRepository.getAccountBalanceForAllAccounts().collect {
                emit(Resource.Success(it))
            }
        } else {
            etRepository.getAccountBalanceByName(accountName).collect {
                emit(Resource.Success(it))
            }
        }
    }.catch {
        emit(Resource.Error("Error while fetching account balance"))
    }
}