package com.jaideep.expensetracker.domain.usecase

import com.jaideep.expensetracker.common.Resource
import com.jaideep.expensetracker.domain.repository.EtRepository
import com.jaideep.expensetracker.model.dto.TransactionDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetAllCategoryWiseTransactions @Inject constructor(
    private val etRepository: EtRepository
) {
    suspend operator fun invoke(
        categoryName: String, accountName: String, fromDate: Long, toDate: Long
    ): Flow<Resource<List<TransactionDto>>> {
        val data =
            if (accountName == "All Accounts") etRepository.getCategoryWiseAllAccountTransactionsWithDate(
                categoryName, fromDate, toDate
            )
            else etRepository.getCategoryWiseAccountTransactionWithDate(
                categoryName, accountName, fromDate, toDate
            )
        return data.transform<List<TransactionDto>, Resource<List<TransactionDto>>> {
            emit(Resource.Success(it))
        }.onStart {
            emit(Resource.Loading())
        }.catch { it ->
            emit(Resource.Error("Error while fetching category specific transactions, ${it.message}"))
        }
    }
}