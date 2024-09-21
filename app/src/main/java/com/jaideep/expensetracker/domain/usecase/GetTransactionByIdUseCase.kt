package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(transactionId: Int) = flow {
        emit(Resource.Loading())
        emit(Resource.Success(transactionRepository.getTransactionById(transactionId)))
    }.catch {
        emit(Resource.Error("Error while fetching transaction"))
    }
}