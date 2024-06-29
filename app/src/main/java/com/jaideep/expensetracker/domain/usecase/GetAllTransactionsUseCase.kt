package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.data.local.entities.Transaction
import com.jaideep.expensetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(loadSize: Int, start: Int) = flow<Resource<List<Transaction>>> {
        emit(Resource.Loading())
//        try {
//            val transactions = transactionRepository.getAllTransactions()
//        }
    }
}